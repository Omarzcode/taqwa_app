package com.taqwa.journal.data.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.taqwa.journal.data.database.CheckInEntry
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.data.preferences.ShieldPlan
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

/**
 * Export Manager — مدير التصدير
 *
 * Generates formatted reports from app data for a specified date range.
 * Uses streaming write approach for memory efficiency with large datasets.
 *
 * Privacy note: The exported file uses neutral language.
 * Title says "Taqwa — Personal Journal Report" not anything sensitive.
 */
class ExportManager(private val context: Context) {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
    private val dateTimeFormat = SimpleDateFormat("MMM dd, yyyy — hh:mm a", Locale.ENGLISH)
    private val fileNameFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)

    /**
     * Generate a full report and return a share Intent.
     * Uses streaming write — safe for large datasets (months/years).
     */
    fun generateAndShare(
        reportData: ReportData,
        options: ExportOptions
    ): Intent {
        val file = writeReportToFile(reportData, options)
        return createShareIntent(file)
    }

    /**
     * Generate report content as a string (for preview only).
     * Preview uses StringBuilder since it's displayed in-app with limited scroll.
     */
    fun generatePreview(
        reportData: ReportData,
        options: ExportOptions
    ): String {
        val sb = StringBuilder()
        writeHeader(sb, reportData)
        if (options.includeStreakStats) writeStatistics(sb, reportData)
        if (options.includePatterns && reportData.journalEntries.size >= 3) writePatterns(sb, reportData)
        if (options.includeJournalEntries && reportData.journalEntries.isNotEmpty()) writeJournalEntries(sb, reportData)
        if (options.includeCheckIns && reportData.checkIns.isNotEmpty()) writeCheckIns(sb, reportData)
        if (options.includeRelapses && reportData.relapses.isNotEmpty()) writeRelapses(sb, reportData)
        if (options.includeMemories && reportData.memories.isNotEmpty()) writeMemories(sb, reportData)
        if (options.includeShieldPlans && reportData.shieldPlans.isNotEmpty()) writeShieldPlans(sb, reportData)
        writeFooter(sb)
        return sb.toString()
    }

    // ══════════════════════════════════════════
    // FILE WRITING — Streaming approach
    // ══════════════════════════════════════════

    /**
     * Write report directly to file using BufferedWriter with UTF-8 encoding.
     * This approach is memory-efficient for large datasets.
     */
    private fun writeReportToFile(data: ReportData, options: ExportOptions): File {
        val fileName = "Taqwa_Report_${fileNameFormat.format(Date())}.txt"
        val dir = File(context.cacheDir, "exports")
        dir.mkdirs()

        // Clean old exports to prevent cache buildup
        cleanOldExports(dir)

        val file = File(dir, fileName)

        // Use explicit UTF-8 encoding with BufferedWriter for reliability
        BufferedWriter(
            OutputStreamWriter(
                FileOutputStream(file),
                StandardCharsets.UTF_8
            )
        ).use { writer ->
            writeHeaderToFile(writer, data)
            if (options.includeStreakStats) writeStatisticsToFile(writer, data)
            if (options.includePatterns && data.journalEntries.size >= 3) writePatternsToFile(writer, data)
            if (options.includeJournalEntries && data.journalEntries.isNotEmpty()) writeJournalEntriesToFile(writer, data)
            if (options.includeCheckIns && data.checkIns.isNotEmpty()) writeCheckInsToFile(writer, data)
            if (options.includeRelapses && data.relapses.isNotEmpty()) writeRelapsesToFile(writer, data)
            if (options.includeMemories && data.memories.isNotEmpty()) writeMemoriesToFile(writer, data)
            if (options.includeShieldPlans && data.shieldPlans.isNotEmpty()) writeShieldPlansToFile(writer, data)
            writeFooterToFile(writer)
            writer.flush()
        }

        return file
    }

    /**
     * Clean export files older than 1 hour to prevent cache buildup.
     */
    private fun cleanOldExports(dir: File) {
        val oneHourAgo = System.currentTimeMillis() - (60 * 60 * 1000)
        dir.listFiles()?.forEach { file ->
            if (file.lastModified() < oneHourAgo) {
                file.delete()
            }
        }
    }

    // ══════════════════════════════════════════
    // STREAMING WRITERS (BufferedWriter — for file export)
    // ══════════════════════════════════════════

    private fun writeHeaderToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("===================================================")
        w.writeLine("       TAQWA - Personal Journal Report")
        w.writeLine("===================================================")
        w.writeLine("")
        w.writeLine("Period: ${data.periodLabel}")
        w.writeLine("Generated: ${dateTimeFormat.format(Date())}")
        w.writeLine("")
    }

    private fun writeStatisticsToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("STATISTICS")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")
        w.writeLine("  Current Streak:    ${data.currentStreak} days")
        w.writeLine("  Longest Streak:    ${data.longestStreak} days")
        w.writeLine("  Urges Defeated:    ${data.urgesDefeated}")
        w.writeLine("  Total Relapses:    ${data.totalRelapses}")

        if (data.journalEntries.isNotEmpty()) {
            val avgStrength = data.journalEntries.map { it.urgeStrength }.average()
            w.writeLine("  Avg Urge Strength: ${"%.1f".format(avgStrength)}/10")
            w.writeLine("  Entries in Period: ${data.journalEntries.size}")
        }

        if (data.checkIns.isNotEmpty()) {
            w.writeLine("  Check-Ins:         ${data.checkIns.size}")
            val goodDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_GOOD }
            val okayDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_OKAY }
            val lowDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_LOW }
            w.writeLine("  Mood Breakdown:    Good: $goodDays | Okay: $okayDays | Low: $lowDays")

            val highRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_HIGH }
            val medRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_MEDIUM }
            val lowRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_LOW }
            w.writeLine("  Risk Levels:       Low: $lowRisk | Med: $medRisk | High: $highRisk")
        }

        w.writeLine("")
    }

    private fun writePatternsToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("PATTERN ANALYSIS")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        // Time patterns
        val timePatterns = analyzeTimePatterns(data.journalEntries)
        if (timePatterns.isNotEmpty()) {
            w.writeLine("  Urge Times:")
            timePatterns.forEach { (period, count) ->
                w.writeLine("     $period: $count entries")
            }
            w.writeLine("")
        }

        // Feeling patterns
        val feelings = analyzeFeelings(data.journalEntries)
        if (feelings.isNotEmpty()) {
            w.writeLine("  Top Feelings:")
            feelings.take(5).forEach { (feeling, count) ->
                w.writeLine("     $feeling: $count times")
            }
            w.writeLine("")
        }

        // Need patterns
        val needs = analyzeNeeds(data.journalEntries)
        if (needs.isNotEmpty()) {
            w.writeLine("  Real Needs Behind Urges:")
            needs.take(5).forEach { (need, count) ->
                w.writeLine("     $need: $count times")
            }
            w.writeLine("")
        }

        // Alternative effectiveness
        val alternatives = analyzeAlternatives(data.journalEntries)
        if (alternatives.isNotEmpty()) {
            w.writeLine("  Strategies Used:")
            alternatives.take(5).forEach { (alt, count) ->
                w.writeLine("     $alt: $count times")
            }
            w.writeLine("")
        }

        // Strength trend
        if (data.journalEntries.size >= 4) {
            val sorted = data.journalEntries.sortedBy { it.timestamp }
            val firstHalf = sorted.take(sorted.size / 2).map { it.urgeStrength }.average()
            val secondHalf = sorted.takeLast(sorted.size / 2).map { it.urgeStrength }.average()
            val change = ((secondHalf - firstHalf) / firstHalf * 100).toInt()
            if (change < 0) {
                w.writeLine("  Urge strength decreased by ${-change}% over this period!")
            } else if (change > 0) {
                w.writeLine("  Urge strength increased by $change%. Stay vigilant.")
            } else {
                w.writeLine("  Urge strength remained stable.")
            }
            w.writeLine("")
        }
    }

    private fun writeJournalEntriesToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("JOURNAL ENTRIES (${data.journalEntries.size})")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        data.journalEntries.sortedByDescending { it.timestamp }.forEach { entry ->
            w.writeLine("  Date: ${dateTimeFormat.format(Date(entry.timestamp))}")

            val bar = "#".repeat(entry.urgeStrength) + ".".repeat(10 - entry.urgeStrength)
            w.writeLine("  Urge Strength: ${entry.urgeStrength}/10 [$bar]")

            if (entry.situationContext.isNotBlank()) {
                w.writeLine("  Situation: ${entry.situationContext}")
            }
            if (entry.feelings.isNotBlank()) {
                w.writeLine("  Feelings: ${entry.feelings}")
            }
            if (entry.realNeed.isNotBlank()) {
                w.writeLine("  Real Need: ${entry.realNeed}")
            }
            if (entry.alternativeChosen.isNotBlank()) {
                w.writeLine("  Alternative: ${entry.alternativeChosen}")
            }
            if (entry.freeText.isNotBlank()) {
                w.writeLine("  Notes: \"${entry.freeText}\"")
            }
            w.writeLine("  ---")
            w.writeLine("")
        }
    }

    private fun writeCheckInsToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("CHECK-IN HISTORY (${data.checkIns.size})")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        data.checkIns.sortedByDescending { it.timestamp }.forEach { checkIn ->
            w.writeLine("  ${checkIn.date}  Mood: ${checkIn.mood}  Risk: ${checkIn.riskLevel}  Day ${checkIn.streakAtTime}")
            if (checkIn.intention.isNotBlank()) {
                w.writeLine("     Intention: \"${checkIn.intention}\"")
            }
        }
        w.writeLine("")
    }

    private fun writeRelapsesToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("RELAPSE HISTORY (${data.relapses.size})")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        data.relapses.forEach { relapse ->
            w.writeLine("  ${relapse.date}  --  Lost ${relapse.streakLost}-day streak")
            if (relapse.reason.isNotBlank()) {
                w.writeLine("     Reason: \"${relapse.reason}\"")
            }
        }
        w.writeLine("")

        // Relapse trends
        if (data.relapses.size >= 2) {
            val gaps = data.relapses.zipWithNext().mapNotNull { (a, b) ->
                try {
                    val dateA = java.time.LocalDate.parse(a.date)
                    val dateB = java.time.LocalDate.parse(b.date)
                    java.time.temporal.ChronoUnit.DAYS.between(dateB, dateA).toInt()
                } catch (e: Exception) { null }
            }
            if (gaps.isNotEmpty()) {
                w.writeLine("  Average gap between relapses: ${gaps.average().toInt()} days")
                if (gaps.size >= 2 && gaps.first() > gaps.last()) {
                    w.writeLine("  Gaps are increasing -- you're getting stronger!")
                }
            }
            w.writeLine("")
        }
    }

    private fun writeMemoriesToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("MEMORY BANK (${data.memories.size})")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        val relapseLetters = data.memories.filter { it.type == MemoryEntry.TYPE_RELAPSE_LETTER }
        val victoryNotes = data.memories.filter { it.type == MemoryEntry.TYPE_VICTORY_NOTE }
        val manualNotes = data.memories.filter { it.type == MemoryEntry.TYPE_MANUAL }

        if (relapseLetters.isNotEmpty()) {
            w.writeLine("  RELAPSE LETTERS (${relapseLetters.size}):")
            relapseLetters.forEach { memory ->
                w.writeLine("  ${dateFormat.format(Date(memory.timestamp))} (Day ${memory.streakAtTime}):")
                w.writeLine("  \"${memory.message}\"")
                if (memory.trigger.isNotBlank()) {
                    w.writeLine("  Trigger: ${memory.trigger}")
                }
                w.writeLine("")
            }
        }

        if (victoryNotes.isNotEmpty()) {
            w.writeLine("  VICTORY NOTES (${victoryNotes.size}):")
            victoryNotes.forEach { memory ->
                w.writeLine("  ${dateFormat.format(Date(memory.timestamp))} (Day ${memory.streakAtTime}):")
                w.writeLine("  \"${memory.message}\"")
                w.writeLine("")
            }
        }

        if (manualNotes.isNotEmpty()) {
            w.writeLine("  PERSONAL NOTES (${manualNotes.size}):")
            manualNotes.forEach { memory ->
                w.writeLine("  ${dateFormat.format(Date(memory.timestamp))}: \"${memory.message}\"")
            }
            w.writeLine("")
        }
    }

    private fun writeShieldPlansToFile(w: BufferedWriter, data: ReportData) {
        w.writeLine("---------------------------------------------------")
        w.writeLine("SHIELD PLANS")
        w.writeLine("---------------------------------------------------")
        w.writeLine("")

        data.shieldPlans.forEach { plan ->
            w.writeLine("  ${plan.emoji} ${plan.triggerName}")
            if (plan.triggerNameAr.isNotBlank()) {
                w.writeLine("     ${plan.triggerNameAr}")
            }
            w.writeLine("     ${plan.description}")
            plan.steps.forEachIndexed { i, step ->
                w.writeLine("     ${i + 1}. $step")
            }
            if (plan.personalNote.isNotBlank()) {
                w.writeLine("     Note: \"${plan.personalNote}\"")
            }
            w.writeLine("")
        }
    }

    private fun writeFooterToFile(w: BufferedWriter) {
        w.writeLine("===================================================")
        w.writeLine("Generated by Taqwa - Personal Journal")
        w.writeLine("")
        w.writeLine("\"But as for he who feared standing before his Lord")
        w.writeLine(" and restrained the soul from desire,")
        w.writeLine(" then indeed, Paradise will be his refuge.\"")
        w.writeLine("-- An-Nazi'at 79:40-41")
        w.writeLine("===================================================")
    }

    // ══════════════════════════════════════════
    // PREVIEW WRITERS (StringBuilder — for in-app display)
    // ══════════════════════════════════════════

    private fun writeHeader(sb: StringBuilder, data: ReportData) {
        sb.appendLine("===================================================")
        sb.appendLine("       TAQWA - Personal Journal Report")
        sb.appendLine("===================================================")
        sb.appendLine()
        sb.appendLine("Period: ${data.periodLabel}")
        sb.appendLine("Generated: ${dateTimeFormat.format(Date())}")
        sb.appendLine()
    }

    private fun writeStatistics(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83D\uDCCA STATISTICS")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()
        sb.appendLine("  Current Streak:    ${data.currentStreak} days")
        sb.appendLine("  Longest Streak:    ${data.longestStreak} days")
        sb.appendLine("  Urges Defeated:    ${data.urgesDefeated}")
        sb.appendLine("  Total Relapses:    ${data.totalRelapses}")

        if (data.journalEntries.isNotEmpty()) {
            val avgStrength = data.journalEntries.map { it.urgeStrength }.average()
            sb.appendLine("  Avg Urge Strength: ${"%.1f".format(avgStrength)}/10")
            sb.appendLine("  Entries in Period: ${data.journalEntries.size}")
        }

        if (data.checkIns.isNotEmpty()) {
            sb.appendLine("  Check-Ins:         ${data.checkIns.size}")
            val goodDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_GOOD }
            val okayDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_OKAY }
            val lowDays = data.checkIns.count { it.mood == CheckInEntry.MOOD_LOW }
            sb.appendLine("  Mood Breakdown:    \uD83D\uDE0A Good: $goodDays | \uD83D\uDE10 Okay: $okayDays | \uD83D\uDE14 Low: $lowDays")

            val highRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_HIGH }
            val medRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_MEDIUM }
            val lowRisk = data.checkIns.count { it.riskLevel == CheckInEntry.RISK_LOW }
            sb.appendLine("  Risk Levels:       \uD83D\uDFE2 Low: $lowRisk | \uD83D\uDFE1 Med: $medRisk | \uD83D\uDD34 High: $highRisk")
        }

        sb.appendLine()
    }

    private fun writePatterns(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83D\uDCC8 PATTERN ANALYSIS")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        val timePatterns = analyzeTimePatterns(data.journalEntries)
        if (timePatterns.isNotEmpty()) {
            sb.appendLine("  \u23F0 Urge Times:")
            timePatterns.forEach { (period, count) ->
                sb.appendLine("     $period: $count entries")
            }
            sb.appendLine()
        }

        val feelings = analyzeFeelings(data.journalEntries)
        if (feelings.isNotEmpty()) {
            sb.appendLine("  \uD83D\uDCAD Top Feelings:")
            feelings.take(5).forEach { (feeling, count) ->
                sb.appendLine("     $feeling: $count times")
            }
            sb.appendLine()
        }

        val needs = analyzeNeeds(data.journalEntries)
        if (needs.isNotEmpty()) {
            sb.appendLine("  \uD83C\uDFAF Real Needs Behind Urges:")
            needs.take(5).forEach { (need, count) ->
                sb.appendLine("     $need: $count times")
            }
            sb.appendLine()
        }

        val alternatives = analyzeAlternatives(data.journalEntries)
        if (alternatives.isNotEmpty()) {
            sb.appendLine("  \u2705 Strategies Used:")
            alternatives.take(5).forEach { (alt, count) ->
                sb.appendLine("     $alt: $count times")
            }
            sb.appendLine()
        }

        if (data.journalEntries.size >= 4) {
            val sorted = data.journalEntries.sortedBy { it.timestamp }
            val firstHalf = sorted.take(sorted.size / 2).map { it.urgeStrength }.average()
            val secondHalf = sorted.takeLast(sorted.size / 2).map { it.urgeStrength }.average()
            val change = ((secondHalf - firstHalf) / firstHalf * 100).toInt()
            if (change < 0) {
                sb.appendLine("  \uD83D\uDCC9 Urge strength decreased by ${-change}% over this period!")
            } else if (change > 0) {
                sb.appendLine("  \uD83D\uDCC8 Urge strength increased by $change%. Stay vigilant.")
            } else {
                sb.appendLine("  \u27A1\uFE0F Urge strength remained stable.")
            }
            sb.appendLine()
        }
    }

    private fun writeJournalEntries(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83D\uDCCB JOURNAL ENTRIES (${data.journalEntries.size})")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        data.journalEntries.sortedByDescending { it.timestamp }.forEach { entry ->
            sb.appendLine("  \uD83D\uDCC5 ${dateTimeFormat.format(Date(entry.timestamp))}")
            val bar = "#".repeat(entry.urgeStrength) + ".".repeat(10 - entry.urgeStrength)
            sb.appendLine("  Urge Strength: ${entry.urgeStrength}/10 [$bar]")

            if (entry.situationContext.isNotBlank()) sb.appendLine("  Situation: ${entry.situationContext}")
            if (entry.feelings.isNotBlank()) sb.appendLine("  Feelings: ${entry.feelings}")
            if (entry.realNeed.isNotBlank()) sb.appendLine("  Real Need: ${entry.realNeed}")
            if (entry.alternativeChosen.isNotBlank()) sb.appendLine("  Alternative: ${entry.alternativeChosen}")
            if (entry.freeText.isNotBlank()) sb.appendLine("  Notes: \"${entry.freeText}\"")
            sb.appendLine("  ---")
            sb.appendLine()
        }
    }

    private fun writeCheckIns(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\u2600\uFE0F CHECK-IN HISTORY (${data.checkIns.size})")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        data.checkIns.sortedByDescending { it.timestamp }.forEach { checkIn ->
            val moodEmoji = when (checkIn.mood) {
                CheckInEntry.MOOD_GOOD -> "\uD83D\uDE0A"
                CheckInEntry.MOOD_OKAY -> "\uD83D\uDE10"
                else -> "\uD83D\uDE14"
            }
            val riskEmoji = when (checkIn.riskLevel) {
                CheckInEntry.RISK_LOW -> "\uD83D\uDFE2"
                CheckInEntry.RISK_MEDIUM -> "\uD83D\uDFE1"
                else -> "\uD83D\uDD34"
            }
            sb.appendLine("  \uD83D\uDCC5 ${checkIn.date}  $moodEmoji ${checkIn.mood}  $riskEmoji Risk: ${checkIn.riskLevel}  Day ${checkIn.streakAtTime}")
            if (checkIn.intention.isNotBlank()) {
                sb.appendLine("     Intention: \"${checkIn.intention}\"")
            }
        }
        sb.appendLine()
    }

    private fun writeRelapses(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83D\uDCC9 RELAPSE HISTORY (${data.relapses.size})")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        data.relapses.forEach { relapse ->
            sb.appendLine("  \uD83D\uDCC5 ${relapse.date}  --  Lost ${relapse.streakLost}-day streak")
            if (relapse.reason.isNotBlank()) {
                sb.appendLine("     Reason: \"${relapse.reason}\"")
            }
        }
        sb.appendLine()

        if (data.relapses.size >= 2) {
            val gaps = data.relapses.zipWithNext().mapNotNull { (a, b) ->
                try {
                    val dateA = java.time.LocalDate.parse(a.date)
                    val dateB = java.time.LocalDate.parse(b.date)
                    java.time.temporal.ChronoUnit.DAYS.between(dateB, dateA).toInt()
                } catch (e: Exception) { null }
            }
            if (gaps.isNotEmpty()) {
                sb.appendLine("  Average gap between relapses: ${gaps.average().toInt()} days")
                if (gaps.size >= 2 && gaps.first() > gaps.last()) {
                    sb.appendLine("  Gaps are increasing -- you're getting stronger!")
                }
            }
            sb.appendLine()
        }
    }

    private fun writeMemories(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83E\uDDE0 MEMORY BANK (${data.memories.size})")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        val relapseLetters = data.memories.filter { it.type == MemoryEntry.TYPE_RELAPSE_LETTER }
        val victoryNotes = data.memories.filter { it.type == MemoryEntry.TYPE_VICTORY_NOTE }
        val manualNotes = data.memories.filter { it.type == MemoryEntry.TYPE_MANUAL }

        if (relapseLetters.isNotEmpty()) {
            sb.appendLine("  \uD83D\uDCDD RELAPSE LETTERS (${relapseLetters.size}):")
            relapseLetters.forEach { memory ->
                sb.appendLine("  ${dateFormat.format(Date(memory.timestamp))} (Day ${memory.streakAtTime}):")
                sb.appendLine("  \"${memory.message}\"")
                if (memory.trigger.isNotBlank()) sb.appendLine("  Trigger: ${memory.trigger}")
                sb.appendLine()
            }
        }

        if (victoryNotes.isNotEmpty()) {
            sb.appendLine("  \uD83C\uDFC6 VICTORY NOTES (${victoryNotes.size}):")
            victoryNotes.forEach { memory ->
                sb.appendLine("  ${dateFormat.format(Date(memory.timestamp))} (Day ${memory.streakAtTime}):")
                sb.appendLine("  \"${memory.message}\"")
                sb.appendLine()
            }
        }

        if (manualNotes.isNotEmpty()) {
            sb.appendLine("  \uD83D\uDCAD PERSONAL NOTES (${manualNotes.size}):")
            manualNotes.forEach { memory ->
                sb.appendLine("  ${dateFormat.format(Date(memory.timestamp))}: \"${memory.message}\"")
            }
            sb.appendLine()
        }
    }

    private fun writeShieldPlans(sb: StringBuilder, data: ReportData) {
        sb.appendLine("---------------------------------------------------")
        sb.appendLine("\uD83D\uDEE1\uFE0F SHIELD PLANS")
        sb.appendLine("---------------------------------------------------")
        sb.appendLine()

        data.shieldPlans.forEach { plan ->
            sb.appendLine("  ${plan.emoji} ${plan.triggerName}")
            if (plan.triggerNameAr.isNotBlank()) sb.appendLine("     ${plan.triggerNameAr}")
            sb.appendLine("     ${plan.description}")
            plan.steps.forEachIndexed { i, step ->
                sb.appendLine("     ${i + 1}. $step")
            }
            if (plan.personalNote.isNotBlank()) sb.appendLine("     Note: \"${plan.personalNote}\"")
            sb.appendLine()
        }
    }

    private fun writeFooter(sb: StringBuilder) {
        sb.appendLine("===================================================")
        sb.appendLine("Generated by Taqwa - Personal Journal")
        sb.appendLine()
        sb.appendLine("\"But as for he who feared standing before his Lord")
        sb.appendLine(" and restrained the soul from desire,")
        sb.appendLine(" then indeed, Paradise will be his refuge.\"")
        sb.appendLine("-- An-Nazi'at 79:40-41")
        sb.appendLine("===================================================")
    }

    // ══════════════════════════════════════════
    // ANALYSIS HELPERS
    // ══════════════════════════════════════════

    private fun analyzeTimePatterns(entries: List<JournalEntry>): List<Pair<String, Int>> {
        return entries.groupBy { entry ->
            val cal = Calendar.getInstance().apply { timeInMillis = entry.timestamp }
            when (cal.get(Calendar.HOUR_OF_DAY)) {
                in 5..11 -> "Morning (5am-12pm)"
                in 12..16 -> "Afternoon (12pm-5pm)"
                in 17..22 -> "Evening (5pm-11pm)"
                in 23..23, in 0..2 -> "Night (11pm-3am)"
                else -> "Late Night (3am-5am)"
            }
        }.map { it.key to it.value.size }
            .sortedByDescending { it.second }
    }

    private fun analyzeFeelings(entries: List<JournalEntry>): List<Pair<String, Int>> {
        return entries.flatMap { it.feelings.split(",").map { f -> f.trim() } }
            .filter { it.isNotBlank() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
    }

    private fun analyzeNeeds(entries: List<JournalEntry>): List<Pair<String, Int>> {
        return entries.flatMap { it.realNeed.split(",").map { n -> n.trim() } }
            .filter { it.isNotBlank() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
    }

    private fun analyzeAlternatives(entries: List<JournalEntry>): List<Pair<String, Int>> {
        return entries.flatMap { it.alternativeChosen.split(",").map { a -> a.trim() } }
            .filter { it.isNotBlank() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
    }

    // ══════════════════════════════════════════
    // SHARE INTENT
    // ══════════════════════════════════════════

    private fun createShareIntent(file: File): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Taqwa - Personal Journal Report")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    // ══════════════════════════════════════════
    // EXTENSION — BufferedWriter helper
    // ══════════════════════════════════════════

    private fun BufferedWriter.writeLine(text: String) {
        write(text)
        newLine()
    }
}

/**
 * All data for the report, filtered to the selected period.
 */
data class ReportData(
    val periodLabel: String,
    val currentStreak: Int,
    val longestStreak: Int,
    val urgesDefeated: Int,
    val totalRelapses: Int,
    val journalEntries: List<JournalEntry>,
    val checkIns: List<CheckInEntry>,
    val relapses: List<RelapseRecord>,
    val memories: List<MemoryEntry>,
    val shieldPlans: List<ShieldPlan>
)

/**
 * What sections to include in the export.
 */
data class ExportOptions(
    val includeJournalEntries: Boolean = true,
    val includeCheckIns: Boolean = true,
    val includeRelapses: Boolean = true,
    val includeMemories: Boolean = true,
    val includeStreakStats: Boolean = true,
    val includePatterns: Boolean = true,
    val includeShieldPlans: Boolean = true
)