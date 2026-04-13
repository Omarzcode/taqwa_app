package com.taqwa.journal.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    // ══════════════════════════════════════════
    // JOURNAL ENTRIES
    // ══════════════════════════════════════════

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE id = :entryId")
    fun getEntryById(entryId: Int): Flow<JournalEntry?>

    @Query("SELECT COUNT(*) FROM journal_entries WHERE completed = 1")
    fun getUrgesDefeatedCount(): Flow<Int>

    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    @Query("SELECT feelings FROM journal_entries WHERE completed = 1")
    fun getAllFeelings(): Flow<List<String>>

    @Query("SELECT timestamp FROM journal_entries WHERE completed = 1")
    fun getAllTimestamps(): Flow<List<Long>>

    // ══════════════════════════════════════════
    // MEMORY BANK
    // ══════════════════════════════════════════

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntry)

    @Update
    suspend fun updateMemory(memory: MemoryEntry)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntry)

    @Query("SELECT * FROM memory_entries ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntry>>

    @Query("SELECT * FROM memory_entries WHERE type = :type ORDER BY timestamp DESC")
    fun getMemoriesByType(type: String): Flow<List<MemoryEntry>>

    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY timestamp DESC")
    fun getRelapseLetters(): Flow<List<MemoryEntry>>

    @Query("SELECT * FROM memory_entries WHERE type = 'VICTORY_NOTE' ORDER BY timestamp DESC")
    fun getVictoryNotes(): Flow<List<MemoryEntry>>

    @Query("SELECT * FROM memory_entries WHERE isPinned = 1 ORDER BY timestamp DESC")
    fun getPinnedMemories(): Flow<List<MemoryEntry>>

    @Query("SELECT COUNT(*) FROM memory_entries")
    fun getMemoryCount(): Flow<Int>

    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomRelapseLetter(): MemoryEntry?

    @Query("SELECT * FROM memory_entries WHERE type = 'VICTORY_NOTE' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomVictoryNote(): MemoryEntry?

    @Query("SELECT * FROM memory_entries WHERE isPinned = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomPinnedMemory(): MemoryEntry?

    @Query("SELECT * FROM memory_entries ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomMemory(): MemoryEntry?

    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY timestamp DESC LIMIT 1")
    suspend fun getMostRecentRelapseLetter(): MemoryEntry?

    @Query("SELECT COUNT(*) FROM memory_entries WHERE type = :type")
    fun getMemoryCountByType(type: String): Flow<Int>

    // ══════════════════════════════════════════
    // MORNING CHECK-IN
    // ══════════════════════════════════════════

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

    @Update
    suspend fun updateCheckIn(checkIn: CheckInEntry)

    @Delete
    suspend fun deleteCheckIn(checkIn: CheckInEntry)

    // ══════════════════════════════════════════
    // EVENING CHECK-IN
    // ══════════════════════════════════════════

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEveningCheckIn(entry: EveningCheckInEntry)

    @Query("SELECT * FROM evening_checkin_entries ORDER BY timestamp DESC")
    fun getAllEveningCheckIns(): Flow<List<EveningCheckInEntry>>

    @Query("SELECT * FROM evening_checkin_entries WHERE date = :date LIMIT 1")
    suspend fun getEveningCheckInForDate(date: String): EveningCheckInEntry?

    @Query("SELECT COUNT(*) FROM evening_checkin_entries")
    fun getEveningCheckInCount(): Flow<Int>

    @Query("SELECT * FROM evening_checkin_entries ORDER BY timestamp DESC LIMIT 30")
    fun getRecentEveningCheckIns(): Flow<List<EveningCheckInEntry>>

    @Update
    suspend fun updateEveningCheckIn(entry: EveningCheckInEntry)

    @Delete
    suspend fun deleteEveningCheckIn(entry: EveningCheckInEntry)

    // ══════════════════════════════════════════
    // EXPORT — Date Range Queries
    // ══════════════════════════════════════════

    @Query("SELECT * FROM journal_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getEntriesInRange(startMs: Long, endMs: Long): List<JournalEntry>

    @Query("SELECT * FROM memory_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getMemoriesInRange(startMs: Long, endMs: Long): List<MemoryEntry>

    @Query("SELECT * FROM checkin_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getCheckInsInRange(startMs: Long, endMs: Long): List<CheckInEntry>

    @Query("SELECT * FROM evening_checkin_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getEveningCheckInsInRange(startMs: Long, endMs: Long): List<EveningCheckInEntry>
}