package com.taqwa.journal.core.utilities

import java.util.Calendar

/**
 * Calculates victory metrics, streaks, and achievement rewards.
 */
object VictoryCalculator {
    
    data class VictoryStats(
        val currentStreak: Int,
        val longestStreak: Int,
        val totalDaysClean: Int,
        val victoriesThisMonth: Int,
        val victoriesThisWeek: Int,
        val nextMilestone: Int,
        val daysUntilMilestone: Int
    )

    val MILESTONES = listOf(1, 7, 30, 90, 180, 365)

    fun calculateStreak(lastCleanDate: Long, today: Long = System.currentTimeMillis()): Int {
        if (lastCleanDate == 0L) return 0

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = today
        val todayDay = calendar.get(Calendar.DAY_OF_YEAR)
        val todayYear = calendar.get(Calendar.YEAR)

        calendar.timeInMillis = lastCleanDate
        val lastDay = calendar.get(Calendar.DAY_OF_YEAR)
        val lastYear = calendar.get(Calendar.YEAR)

        // Streak broken if more than 1 day has passed
        if (todayYear > lastYear) {
            return if (todayYear == lastYear + 1 && todayDay > 1) 0 else 0
        }

        return if (todayDay - lastDay <= 1) todayDay - lastDay + 1 else 0
    }

    fun getNextMilestone(currentDays: Int): Milestone {
        val nextLevel = MILESTONES.firstOrNull { it > currentDays } ?: MILESTONES.last()
        val reward = getMilestoneReward(nextLevel)
        
        return Milestone(
            days = nextLevel,
            daysRemaining = maxOf(0, nextLevel - currentDays),
            reward = reward
        )
    }

    fun getMilestoneReward(days: Int): String {
        return when (days) {
            1 -> "First victory! 🎉"
            7 -> "One week clean! You're building momentum. 💪"
            30 -> "One month! You're incredibly strong. 🔥"
            90 -> "Three months! Transform is happening. ✨"
            180 -> "Six months! A true champion. 👑"
            365 -> "One full year! You've been reborn. 🏆"
            else -> "Keep going! The next milestone awaits. 🚀"
        }
    }

    fun calculateSuccessRate(
        totalAttempts: Int,
        successfulAttempts: Int
    ): Float {
        if (totalAttempts == 0) return 0f
        return (successfulAttempts.toFloat() / totalAttempts.toFloat()) * 100f
    }

    fun getAchievementBadges(stats: VictoryStats): List<Achievement> {
        val badges = mutableListOf<Achievement>()

        if (stats.currentStreak >= 7) {
            badges.add(Achievement("Week Warrior", "🗓️", "Maintained a 7-day streak"))
        }
        if (stats.currentStreak >= 30) {
            badges.add(Achievement("Month Master", "📅", "Maintained a 30-day streak"))
        }
        if (stats.longestStreak >= 100) {
            badges.add(Achievement("Century Champion", "💯", "Achieved 100-day streak"))
        }
        if (stats.totalDaysClean >= 365) {
            badges.add(Achievement("Year Warrior", "🏆", "One full year clean"))
        }
        if (stats.victoriesThisWeek >= 5) {
            badges.add(Achievement("Weekly Winner", "⭐", "Won 5 victories this week"))
        }

        return badges
    }

    data class Milestone(
        val days: Int,
        val daysRemaining: Int,
        val reward: String
    )

    data class Achievement(
        val name: String,
        val emoji: String,
        val description: String
    )
}
