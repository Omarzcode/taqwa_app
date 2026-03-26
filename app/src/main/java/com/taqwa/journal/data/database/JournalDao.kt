package com.taqwa.journal.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    // Insert a new journal entry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry)

    // Get all entries ordered by newest first
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    // Get a single entry by ID
    @Query("SELECT * FROM journal_entries WHERE id = :entryId")
    fun getEntryById(entryId: Int): Flow<JournalEntry?>

    // Get total count of entries (urges defeated)
    @Query("SELECT COUNT(*) FROM journal_entries WHERE completed = 1")
    fun getUrgesDefeatedCount(): Flow<Int>

    // Delete an entry
    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    // Get all feelings for pattern analysis (future feature)
    @Query("SELECT feelings FROM journal_entries WHERE completed = 1")
    fun getAllFeelings(): Flow<List<String>>

    // Get all timestamps for pattern analysis (future feature)
    @Query("SELECT timestamp FROM journal_entries WHERE completed = 1")
    fun getAllTimestamps(): Flow<List<Long>>
}