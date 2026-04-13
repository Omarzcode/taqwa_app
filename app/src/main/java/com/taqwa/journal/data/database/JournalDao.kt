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
    // MEMORY BANK - بنك الذاكرة
    // ══════════════════════════════════════════

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntry)

    @Update
    suspend fun updateMemory(memory: MemoryEntry)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntry)

    // Get all memories, newest first
    @Query("SELECT * FROM memory_entries ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntry>>

    // Get memories by type
    @Query("SELECT * FROM memory_entries WHERE type = :type ORDER BY timestamp DESC")
    fun getMemoriesByType(type: String): Flow<List<MemoryEntry>>

    // Get only relapse letters (for showing during urge intervention)
    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY timestamp DESC")
    fun getRelapseLetters(): Flow<List<MemoryEntry>>

    // Get only victory notes
    @Query("SELECT * FROM memory_entries WHERE type = 'VICTORY_NOTE' ORDER BY timestamp DESC")
    fun getVictoryNotes(): Flow<List<MemoryEntry>>

    // Get pinned memories (shown more frequently)
    @Query("SELECT * FROM memory_entries WHERE isPinned = 1 ORDER BY timestamp DESC")
    fun getPinnedMemories(): Flow<List<MemoryEntry>>

    // Get total memory count
    @Query("SELECT COUNT(*) FROM memory_entries")
    fun getMemoryCount(): Flow<Int>

    // Get a random relapse letter (for Quick Catch and intervention flow)
    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomRelapseLetter(): MemoryEntry?

    // Get a random victory note
    @Query("SELECT * FROM memory_entries WHERE type = 'VICTORY_NOTE' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomVictoryNote(): MemoryEntry?

    // Get a random pinned memory (priority during intervention)
    @Query("SELECT * FROM memory_entries WHERE isPinned = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomPinnedMemory(): MemoryEntry?

    // Get a random memory of any type
    @Query("SELECT * FROM memory_entries ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomMemory(): MemoryEntry?

    // Get the most recent relapse letter (freshest pain)
    @Query("SELECT * FROM memory_entries WHERE type = 'RELAPSE_LETTER' ORDER BY timestamp DESC LIMIT 1")
    suspend fun getMostRecentRelapseLetter(): MemoryEntry?

    // Get memory count by type
    @Query("SELECT COUNT(*) FROM memory_entries WHERE type = :type")
    fun getMemoryCountByType(type: String): Flow<Int>

    // ══════════════════════════════════════════
    // MORNING CHECK-IN - الورد الصباحي
    // ══════════════════════════════════════════

    // ABORT on duplicate date (user can only check in once per day)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCheckIn(checkIn: CheckInEntry)

    // Get all check-ins, newest first
    @Query("SELECT * FROM checkin_entries ORDER BY timestamp DESC")
    fun getAllCheckIns(): Flow<List<CheckInEntry>>

    // Get check-in for a specific date
    @Query("SELECT * FROM checkin_entries WHERE date = :date LIMIT 1")
    suspend fun getCheckInForDate(date: String): CheckInEntry?

    // Get check-in count
    @Query("SELECT COUNT(*) FROM checkin_entries")
    fun getCheckInCount(): Flow<Int>

    // Get recent check-ins for pattern analysis (last 30)
    @Query("SELECT * FROM checkin_entries ORDER BY timestamp DESC LIMIT 30")
    fun getRecentCheckIns(): Flow<List<CheckInEntry>>

    // Get check-ins by mood (for correlation analysis)
    @Query("SELECT * FROM checkin_entries WHERE mood = :mood ORDER BY timestamp DESC")
    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>>

    // ✅ CORRECT — use the @ColumnInfo name "risk_level"
    @Query("SELECT * FROM checkin_entries WHERE risk_level = :riskLevel ORDER BY timestamp DESC")
    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>>
    // Update an existing check-in
    @Update
    suspend fun updateCheckIn(checkIn: CheckInEntry)
    // Delete a check-in
    @Delete
    suspend fun deleteCheckIn(checkIn: CheckInEntry)

    // ══════════════════════════════════════════
    // EXPORT — Date Range Queries
    // ══════════════════════════════════════════

    @Query("SELECT * FROM journal_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getEntriesInRange(startMs: Long, endMs: Long): List<JournalEntry>

    @Query("SELECT * FROM memory_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getMemoriesInRange(startMs: Long, endMs: Long): List<MemoryEntry>

    @Query("SELECT * FROM checkin_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getCheckInsInRange(startMs: Long, endMs: Long): List<CheckInEntry>
}