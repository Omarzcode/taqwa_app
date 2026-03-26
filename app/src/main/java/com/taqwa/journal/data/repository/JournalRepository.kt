package com.taqwa.journal.data.repository

import com.taqwa.journal.data.database.JournalDao
import com.taqwa.journal.data.database.JournalEntry
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {

    // Get all entries
    val allEntries: Flow<List<JournalEntry>> = journalDao.getAllEntries()

    // Get urges defeated count
    val urgesDefeatedCount: Flow<Int> = journalDao.getUrgesDefeatedCount()

    // Insert new entry
    suspend fun insertEntry(entry: JournalEntry) {
        journalDao.insertEntry(entry)
    }

    // Get single entry by ID
    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return journalDao.getEntryById(entryId)
    }

    // Delete entry
    suspend fun deleteEntry(entry: JournalEntry) {
        journalDao.deleteEntry(entry)
    }
}