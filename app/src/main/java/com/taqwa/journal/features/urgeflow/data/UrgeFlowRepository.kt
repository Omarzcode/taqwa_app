package com.taqwa.journal.features.urgeflow.data

import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow

class UrgeFlowRepository(private val journalDao: JournalDao) {

    val allEntries: Flow<List<JournalEntry>> = journalDao.getAllEntries()

    val urgesDefeatedCount: Flow<Int> = journalDao.getUrgesDefeatedCount()

    suspend fun insertEntry(entry: JournalEntry) {
        validateJournalEntry(entry)
        journalDao.insertEntry(entry)
    }

    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return journalDao.getEntryById(entryId)
    }

    suspend fun deleteEntry(entry: JournalEntry) {
        journalDao.deleteEntry(entry)
    }

    suspend fun getEntriesInRange(startMs: Long, endMs: Long): List<JournalEntry> {
        return journalDao.getEntriesInRange(startMs, endMs)
    }

    fun getAllFeelings(): Flow<List<String>> {
        return journalDao.getAllFeelings()
    }

    fun getAllTimestamps(): Flow<List<Long>> {
        return journalDao.getAllTimestamps()
    }

    private fun validateJournalEntry(entry: JournalEntry) {
        Validators.requireValidUrgeStrength(entry.urgeStrength)

        val hasAnyAnswer = entry.situationContext.isNotBlank() ||
                entry.feelings.isNotBlank() ||
                entry.realNeed.isNotBlank() ||
                entry.alternativeChosen.isNotBlank() ||
                entry.freeText.isNotBlank()
        require(hasAnyAnswer) { "JournalEntry must have at least one answered question" }
    }
}
