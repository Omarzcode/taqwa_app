package com.taqwa.journal.features.checkin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
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
@Entity(
    tableName = "checkin_entries",
    indices = [Index("date", unique = true)]
)
data class CheckInEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // When the check-in was completed
    val timestamp: Long = System.currentTimeMillis(),

    // Date string for easy lookup (e.g., "2024-03-15") - MUST be unique
    val date: String,

    // Mood: "GOOD", "OKAY", or "LOW" (MUST be one of these)
    val mood: String,

    // Self-assessed risk level: "LOW", "MEDIUM", or "HIGH" (MUST be one of these)
    @ColumnInfo(name = "risk_level")
    val riskLevel: String,

    // User's intention for the day (max 500 chars)
    val intention: String = "",

    // Streak at time of check-in (for historical reference)
    @ColumnInfo(name = "streak_at_time")
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
