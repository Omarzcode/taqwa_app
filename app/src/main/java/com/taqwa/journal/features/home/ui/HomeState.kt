package com.taqwa.journal.features.home.ui

import com.taqwa.journal.features.quran.data.DailyAyah

data class HomeState(
    val urgesDefeated: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val streakStatus: String = "",
    val milestoneMessage: String? = null,
    val todayCheckInDone: Boolean = false,
    val totalRelapses: Int = 0,
    val dailyAyah: DailyAyah? = null,
    val memoryCount: Int = 0
) {
    val streakEmoji: String
        get() = when {
            currentStreak >= 100 -> "💎"
            currentStreak >= 30 -> "🏆"
            currentStreak >= 7 -> "🔥"
            currentStreak >= 1 -> "🌱"
            else -> "🤲"
        }

    val streakLabel: String
        get() = when (currentStreak) {
            0 -> "Start your journey"
            1 -> "day strong"
            else -> "days strong"
        }

    val hasMilestone: Boolean
        get() = milestoneMessage != null

    val showMorningCheckIn: Boolean
        get() = !todayCheckInDone
}