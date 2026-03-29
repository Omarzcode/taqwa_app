package com.taqwa.journal.data.repository

import com.taqwa.journal.data.database.CheckInEntry
import com.taqwa.journal.data.database.JournalDao
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.database.MemoryEntry
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {

    // ══════════════════════════════════════════
    // JOURNAL ENTRIES
    // ══════════════════════════════════════════

    val allEntries: Flow<List<JournalEntry>> = journalDao.getAllEntries()

    val urgesDefeatedCount: Flow<Int> = journalDao.getUrgesDefeatedCount()

    suspend fun insertEntry(entry: JournalEntry) {
        journalDao.insertEntry(entry)
    }

    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return journalDao.getEntryById(entryId)
    }

    suspend fun deleteEntry(entry: JournalEntry) {
        journalDao.deleteEntry(entry)
    }

    // ══════════════════════════════════════════
    // MEMORY BANK - بنك الذاكرة
    // ══════════════════════════════════════════

    val allMemories: Flow<List<MemoryEntry>> = journalDao.getAllMemories()

    val relapseLetters: Flow<List<MemoryEntry>> = journalDao.getRelapseLetters()

    val victoryNotes: Flow<List<MemoryEntry>> = journalDao.getVictoryNotes()

    val pinnedMemories: Flow<List<MemoryEntry>> = journalDao.getPinnedMemories()

    val memoryCount: Flow<Int> = journalDao.getMemoryCount()

    suspend fun insertMemory(memory: MemoryEntry) {
        journalDao.insertMemory(memory)
    }

    suspend fun updateMemory(memory: MemoryEntry) {
        journalDao.updateMemory(memory)
    }

    suspend fun deleteMemory(memory: MemoryEntry) {
        journalDao.deleteMemory(memory)
    }

    suspend fun getRandomRelapseLetter(): MemoryEntry? {
        return journalDao.getRandomRelapseLetter()
    }

    suspend fun getRandomVictoryNote(): MemoryEntry? {
        return journalDao.getRandomVictoryNote()
    }

    suspend fun getRandomPinnedMemory(): MemoryEntry? {
        return journalDao.getRandomPinnedMemory()
    }

    suspend fun getRandomMemory(): MemoryEntry? {
        return journalDao.getRandomMemory()
    }

    suspend fun getMostRecentRelapseLetter(): MemoryEntry? {
        return journalDao.getMostRecentRelapseLetter()
    }

    fun getMemoriesByType(type: String): Flow<List<MemoryEntry>> {
        return journalDao.getMemoriesByType(type)
    }

    fun getMemoryCountByType(type: String): Flow<Int> {
        return journalDao.getMemoryCountByType(type)
    }

    // ══════════════════════════════════════════
    // MORNING CHECK-IN - الورد الصباحي
    // ══════════════════════════════════════════

    val allCheckIns: Flow<List<CheckInEntry>> = journalDao.getAllCheckIns()

    val checkInCount: Flow<Int> = journalDao.getCheckInCount()

    val recentCheckIns: Flow<List<CheckInEntry>> = journalDao.getRecentCheckIns()

    suspend fun insertCheckIn(checkIn: CheckInEntry) {
        journalDao.insertCheckIn(checkIn)
    }

    suspend fun getCheckInForDate(date: String): CheckInEntry? {
        return journalDao.getCheckInForDate(date)
    }

    suspend fun deleteCheckIn(checkIn: CheckInEntry) {
        journalDao.deleteCheckIn(checkIn)
    }

    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>> {
        return journalDao.getCheckInsByMood(mood)
    }

    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>> {
        return journalDao.getCheckInsByRisk(riskLevel)
    }

    // ══════════════════════════════════════════
    // EXPORT — Date Range Queries
    // ══════════════════════════════════════════

    suspend fun getEntriesInRange(startMs: Long, endMs: Long): List<JournalEntry> {
        return journalDao.getEntriesInRange(startMs, endMs)
    }

    suspend fun getMemoriesInRange(startMs: Long, endMs: Long): List<MemoryEntry> {
        return journalDao.getMemoriesInRange(startMs, endMs)
    }

    suspend fun getCheckInsInRange(startMs: Long, endMs: Long): List<CheckInEntry> {
        return journalDao.getCheckInsInRange(startMs, endMs)
    }
}