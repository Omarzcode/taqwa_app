package com.taqwa.journal.features.urgeflow.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

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

    @Query("SELECT * FROM journal_entries WHERE timestamp >= :startMs AND timestamp <= :endMs ORDER BY timestamp DESC")
    suspend fun getEntriesInRange(startMs: Long, endMs: Long): List<JournalEntry>
}
