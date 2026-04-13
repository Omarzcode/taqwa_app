package com.taqwa.journal.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Daily Check-In Entry - الورد اليومي
 *
 * A daily self-assessment backed by addiction recovery science:
 * - Sleep quality tracking (Brower, 2001 — #1 relapse predictor)
 * - Mood awareness (CBT foundation)
 * - Gratitude practice (Emmons & McCullough, 2003)
 * - Risk/vulnerability assessment (Marlatt & Gordon relapse prevention)
 * - Intention setting (Gollwiger implementation intentions)
 * - Yesterday accountability loop (behavioral feedback)
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

    // Mood: "GOOD", "OKAY", or "LOW"
    val mood: String,

    // Self-assessed risk level: "LOW", "MEDIUM", or "HIGH"
    @ColumnInfo(name = "risk_level")
    val riskLevel: String,

    // User's intention for the day (max 500 chars)
    val intention: String = "",

    // Streak at time of check-in (for historical reference)
    @ColumnInfo(name = "streak_at_time")
    val streakAtTime: Int = 0,

    // Sleep quality: "GOOD", "OKAY", or "BAD" (null for old entries)
    @ColumnInfo(name = "sleep_quality", defaultValue = "")
    val sleepQuality: String = "",

    // One thing grateful for today
    @ColumnInfo(name = "gratitude", defaultValue = "")
    val gratitude: String = "",

    // Did user follow yesterday's intention? (null = no yesterday data)
    @ColumnInfo(name = "yesterday_followed", defaultValue = "0")
    val yesterdayFollowed: Boolean = false,

    // Was this check-in done during morning hours (4-11)?
    @ColumnInfo(name = "is_morning", defaultValue = "1")
    val isMorning: Boolean = true
) {
    companion object {
        const val MOOD_GOOD = "GOOD"
        const val MOOD_OKAY = "OKAY"
        const val MOOD_LOW = "LOW"

        const val RISK_LOW = "LOW"
        const val RISK_MEDIUM = "MEDIUM"
        const val RISK_HIGH = "HIGH"

        const val SLEEP_GOOD = "GOOD"
        const val SLEEP_OKAY = "OKAY"
        const val SLEEP_BAD = "BAD"
    }
}