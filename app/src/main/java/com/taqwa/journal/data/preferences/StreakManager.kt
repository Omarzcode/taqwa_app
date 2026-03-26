package com.taqwa.journal.data.preferences

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StreakManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_streak", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_STREAK_START_DATE = "streak_start_date"
        private const val KEY_LONGEST_STREAK = "longest_streak"
        private const val KEY_TOTAL_RELAPSES = "total_relapses"
        private const val KEY_RELAPSE_HISTORY = "relapse_history"
    }

    // Get current streak in days
    fun getCurrentStreak(): Int {
        val startDate = getStreakStartDate() ?: return 0
        val today = LocalDate.now()
        return ChronoUnit.DAYS.between(startDate, today).toInt()
    }

    // Get streak start date
    fun getStreakStartDate(): LocalDate? {
        val dateString = prefs.getString(KEY_STREAK_START_DATE, null)
        return if (dateString != null) LocalDate.parse(dateString) else null
    }

    // Start a new streak
    fun startNewStreak() {
        val today = LocalDate.now().toString()
        prefs.edit().putString(KEY_STREAK_START_DATE, today).apply()
    }

    // Reset streak (relapse)
    fun resetStreak(reason: String) {
        // Save current streak if it's the longest
        val currentStreak = getCurrentStreak()
        val longestStreak = getLongestStreak()
        if (currentStreak > longestStreak) {
            prefs.edit().putInt(KEY_LONGEST_STREAK, currentStreak).apply()
        }

        // Increment total relapses
        val totalRelapses = getTotalRelapses()
        prefs.edit().putInt(KEY_TOTAL_RELAPSES, totalRelapses + 1).apply()

        // Save to relapse history
        // Format: "date|||reason|||streakLost;;;date|||reason|||streakLost"
        val history = getRelapseHistoryRaw()
        val newEntry = "${LocalDate.now()}|||${reason}|||${currentStreak}"
        val updatedHistory = if (history.isEmpty()) newEntry else "$newEntry;;;$history"
        prefs.edit().putString(KEY_RELAPSE_HISTORY, updatedHistory).apply()

        // Start new streak
        startNewStreak()
    }

    // Get longest streak ever
    fun getLongestStreak(): Int {
        val saved = prefs.getInt(KEY_LONGEST_STREAK, 0)
        val current = getCurrentStreak()
        return maxOf(saved, current)
    }

    // Get total relapses
    fun getTotalRelapses(): Int {
        return prefs.getInt(KEY_TOTAL_RELAPSES, 0)
    }

    // Get relapse history as list
    fun getRelapseHistory(): List<RelapseRecord> {
        val raw = getRelapseHistoryRaw()
        if (raw.isEmpty()) return emptyList()

        return raw.split(";;;").mapNotNull { entry ->
            val parts = entry.split("|||")
            if (parts.size == 3) {
                RelapseRecord(
                    date = parts[0],
                    reason = parts[1],
                    streakLost = parts[2].toIntOrNull() ?: 0
                )
            } else null
        }
    }

    private fun getRelapseHistoryRaw(): String {
        return prefs.getString(KEY_RELAPSE_HISTORY, "") ?: ""
    }

    // Get milestone message if applicable
    fun getMilestoneMessage(): String? {
        val streak = getCurrentStreak()
        return when (streak) {
            1 -> "🌱 Day 1 - Every journey starts with a single step!"
            3 -> "💪 3 Days - Your brain is starting to rewire!"
            7 -> "⭐ 1 Week! - Dopamine receptors are healing!"
            14 -> "🌟 2 Weeks! - Mental clarity is improving!"
            21 -> "🔥 3 Weeks! - New neural pathways forming!"
            30 -> "🏆 1 MONTH! - Major milestone! Brain fog is lifting!"
            60 -> "👑 2 MONTHS! - Significant brain recovery!"
            90 -> "🎖️ 90 DAYS! - Your brain has substantially rewired!"
            180 -> "💎 6 MONTHS! - You are a warrior!"
            365 -> "🏅 1 YEAR! - You are FREE!"
            else -> null
        }
    }

    // Get streak status text
    fun getStreakStatusText(): String {
        val streak = getCurrentStreak()
        return when {
            streak == 0 -> "Start your journey today"
            streak < 7 -> "Building momentum..."
            streak < 30 -> "Getting stronger every day!"
            streak < 90 -> "Your brain is healing! 🧠"
            else -> "You are a champion! 👑"
        }
    }

    // Check if first time
    fun isFirstTime(): Boolean {
        return getStreakStartDate() == null
    }
}

// Data class for relapse records
data class RelapseRecord(
    val date: String,
    val reason: String,
    val streakLost: Int
)