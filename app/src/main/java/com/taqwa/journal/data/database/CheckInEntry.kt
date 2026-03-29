package com.taqwa.journal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Morning Check-In Entry - الورد الصباحي
 *
 * A daily 60-second self-assessment that:
 * - Sets a mental shield before the day begins
 * - Creates positive daily engagement with the app
 * - Tracks mood and risk level for pattern analysis
 * - Surfaces a memory bank entry to fight forgetting (النسيان)
 * - Stores an intention for the day
 */
@Entity(tableName = "checkin_entries")
data class CheckInEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // When the check-in was completed
    val timestamp: Long = System.currentTimeMillis(),

    // Date string for easy lookup (e.g., "2024-03-15")
    val date: String,

    // Mood: "GOOD", "OKAY", "LOW"
    val mood: String,

    // Self-assessed risk level: "LOW", "MEDIUM", "HIGH"
    val riskLevel: String,

    // User's intention for the day
    val intention: String = "",

    // Streak at time of check-in (for historical reference)
    val streakAtTime: Int = 0
) {
    companion object {
        const val MOOD_GOOD = "GOOD"
        const val MOOD_OKAY = "OKAY"
        const val MOOD_LOW = "LOW"

        const val RISK_LOW = "LOW"
        const val RISK_MEDIUM = "MEDIUM"
        const val RISK_HIGH = "HIGH"
    }
}