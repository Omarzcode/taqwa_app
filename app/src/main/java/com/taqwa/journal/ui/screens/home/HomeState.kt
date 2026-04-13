package com.taqwa.journal.ui.screens.home

import com.taqwa.journal.data.preferences.DailyAyah

data class HomeState(
    val urgesDefeated: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val streakStatus: String = "",
    val milestoneMessage: String? = null,
    val todayCheckInDone: Boolean = false,
    val checkInLoaded: Boolean = false,
    val todayEveningCheckInDone: Boolean = false,
    val eveningCheckInLoaded: Boolean = false,
    val totalRelapses: Int = 0,
    val dailyAyah: DailyAyah? = null,
    val memoryCount: Int = 0
) {
    val streakEmoji: String
        get() = when {
            currentStreak >= 100 -> "\uD83D\uDC8E"
            currentStreak >= 30 -> "\uD83C\uDFC6"
            currentStreak >= 7 -> "\uD83D\uDD25"
            currentStreak >= 1 -> "\uD83C\uDF31"
            else -> "\uD83E\uDD32"
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
        get() {
            val hour = java.time.LocalTime.now().hour
            return checkInLoaded && !todayCheckInDone && hour < 17
        }

    val showEveningCheckIn: Boolean
        get() {
            val hour = java.time.LocalTime.now().hour
            return eveningCheckInLoaded && !todayEveningCheckInDone && hour >= 17
        }
}