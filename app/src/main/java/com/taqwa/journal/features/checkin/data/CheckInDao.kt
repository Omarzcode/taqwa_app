package com.taqwa.journal.features.checkin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckInDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCheckIn(checkIn: CheckInEntry)

    @Query("SELECT * FROM checkin_entries ORDER BY timestamp DESC")
    fun getAllCheckIns(): Flow<List<CheckInEntry>>

    @Query("SELECT * FROM checkin_entries WHERE date = :date LIMIT 1")
    suspend fun getCheckInForDate(date: String): CheckInEntry?

    @Query("SELECT COUNT(*) FROM checkin_entries")
    fun getCheckInCount(): Flow<Int>

    @Query("SELECT * FROM checkin_entries ORDER BY timestamp DESC LIMIT 30")
    fun getRecentCheckIns(): Flow<List<CheckInEntry>>

    @Query("SELECT * FROM checkin_entries WHERE mood = :mood ORDER BY timestamp DESC")
    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>>

    @Query("SELECT * FROM checkin_entries WHERE risk_level = :riskLevel ORDER BY timestamp DESC")
    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>>

    @Delete
    suspend fun deleteCheckIn(checkIn: CheckInEntry)

    @Query("SELECT * FROM checkin_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getCheckInsInRange(startMs: Long, endMs: Long): List<CheckInEntry>
}
