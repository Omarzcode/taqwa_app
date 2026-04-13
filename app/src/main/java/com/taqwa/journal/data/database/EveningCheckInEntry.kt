package com.taqwa.journal.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "evening_checkin_entries",
    indices = [Index(value = ["date"], unique = true)]
)
data class EveningCheckInEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val timestamp: Long = System.currentTimeMillis(),

    val date: String,

    @ColumnInfo(name = "intention_followed")
    val intentionFollowed: String? = null,

    @ColumnInfo(name = "intention_note")
    val intentionNote: String? = null,

    @ColumnInfo(name = "prayed_five")
    val prayedFive: Boolean = false,

    @ColumnInfo(name = "morning_adhkar")
    val morningAdhkar: Boolean = false,

    @ColumnInfo(name = "evening_adhkar")
    val eveningAdhkar: Boolean = false,

    @ColumnInfo(name = "read_quran")
    val readQuran: Boolean = false,

    @ColumnInfo(name = "made_istighfar")
    val madeIstighfar: Boolean = false,

    @ColumnInfo(name = "lowered_gaze")
    val loweredGaze: Boolean = false,

    @ColumnInfo(name = "spiritual_score")
    val spiritualScore: Int = 0,

    @ColumnInfo(name = "hardest_moment")
    val hardestMoment: String? = null,

    @ColumnInfo(name = "hardest_trigger")
    val hardestTrigger: String? = null,

    val wins: String? = null,

    @ColumnInfo(name = "tomorrow_concern")
    val tomorrowConcern: String? = null,

    @ColumnInfo(name = "streak_at_time")
    val streakAtTime: Int = 0
) {
    companion object {
        const val FOLLOWED_YES = "YES"
        const val FOLLOWED_PARTIALLY = "PARTIALLY"
        const val FOLLOWED_NO = "NO"
    }
}