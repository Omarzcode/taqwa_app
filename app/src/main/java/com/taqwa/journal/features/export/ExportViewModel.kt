package com.taqwa.journal.features.export

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.features.export.ExportManager
import com.taqwa.journal.features.export.ExportOptions
import com.taqwa.journal.features.export.ReportData
import com.taqwa.journal.features.urgeflow.data.UrgeFlowRepository
import com.taqwa.journal.features.memory.data.MemoryRepository
import com.taqwa.journal.features.checkin.data.CheckInRepository
import com.taqwa.journal.features.streak.data.StreakManager
import com.taqwa.journal.features.shieldplan.data.ShieldPlanManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class ExportViewModel(
    application: Application,
    private val urgeFlowRepository: UrgeFlowRepository,
    private val memoryRepository: MemoryRepository,
    private val checkInRepository: CheckInRepository,
    private val streakManager: StreakManager,
    private val shieldPlanManager: ShieldPlanManager,
    val exportManager: ExportManager
) : AndroidViewModel(application) {

    fun exportReport(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String,
        options: ExportOptions,
        onReady: (Intent) -> Unit
    ) {
        viewModelScope.launch {
            val data = generateExportData(startDate, endDate, periodLabel)
            val intent = exportManager.generateAndShare(data, options)
            onReady(intent)
        }
    }

    fun previewExport(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String,
        options: ExportOptions,
        onReady: (String) -> Unit
    ) {
        viewModelScope.launch {
            val data = generateExportData(startDate, endDate, periodLabel)
            val preview = exportManager.generatePreview(data, options)
            onReady(preview)
        }
    }

    private suspend fun generateExportData(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String
    ): ReportData {
        val startMs = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMs = endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val entries = urgeFlowRepository.getEntriesInRange(startMs, endMs)
        val memories = memoryRepository.getMemoriesInRange(startMs, endMs)
        val checkIns = checkInRepository.getCheckInsInRange(startMs, endMs)

        val allRelapses = streakManager.getRelapseHistory()
        val filteredRelapses = allRelapses.filter { relapse ->
            try {
                val relapseDate = LocalDate.parse(relapse.date)
                !relapseDate.isBefore(startDate) && !relapseDate.isAfter(endDate)
            } catch (e: Exception) { false }
        }

        return ReportData(
            periodLabel = periodLabel,
            currentStreak = streakManager.getCurrentStreak(),
            longestStreak = streakManager.getLongestStreak(),
            urgesDefeated = entries.size,
            totalRelapses = filteredRelapses.size,
            journalEntries = entries,
            checkIns = checkIns,
            relapses = filteredRelapses,
            memories = memories,
            shieldPlans = shieldPlanManager.getActivePlans()
        )
    }
}
