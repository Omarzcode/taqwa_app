package com.taqwa.journal.features.memory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

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

    @Query("SELECT * FROM memory_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getMemoriesInRange(startMs: Long, endMs: Long): List<MemoryEntry>
}
