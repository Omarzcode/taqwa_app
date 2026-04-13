package com.taqwa.journal.data.repository

import com.taqwa.journal.data.database.CheckInEntry
import com.taqwa.journal.data.database.EveningCheckInEntry
import com.taqwa.journal.data.database.JournalDao
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.utilities.Validators
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {

    // ══════════════════════════════════════════
    // JOURNAL ENTRIES
    // ══════════════════════════════════════════

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

    // ══════════════════════════════════════════
    // MEMORY BANK
    // ══════════════════════════════════════════

    val allMemories: Flow<List<MemoryEntry>> = journalDao.getAllMemories()

    val relapseLetters: Flow<List<MemoryEntry>> = journalDao.getRelapseLetters()

    val victoryNotes: Flow<List<MemoryEntry>> = journalDao.getVictoryNotes()

    val pinnedMemories: Flow<List<MemoryEntry>> = journalDao.getPinnedMemories()

    val memoryCount: Flow<Int> = journalDao.getMemoryCount()

    suspend fun insertMemory(memory: MemoryEntry) {
        validateMemoryEntry(memory)
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
    // MORNING CHECK-IN
    // ══════════════════════════════════════════

    val allCheckIns: Flow<List<CheckInEntry>> = journalDao.getAllCheckIns()

    val checkInCount: Flow<Int> = journalDao.getCheckInCount()

    val recentCheckIns: Flow<List<CheckInEntry>> = journalDao.getRecentCheckIns()

    suspend fun insertCheckIn(checkIn: CheckInEntry) {
        validateCheckInEntry(checkIn)
        journalDao.insertCheckIn(checkIn)
    }

    suspend fun getCheckInForDate(date: String): CheckInEntry? {
        return journalDao.getCheckInForDate(date)
    }

    suspend fun deleteCheckIn(checkIn: CheckInEntry) {
        journalDao.deleteCheckIn(checkIn)
    }

    suspend fun updateCheckIn(checkIn: CheckInEntry) {
        journalDao.updateCheckIn(checkIn)
    }

    suspend fun getYesterdayCheckIn(): CheckInEntry? {
        val yesterday = java.time.LocalDate.now().minusDays(1).toString()
        return journalDao.getCheckInForDate(yesterday)
    }

    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>> {
        return journalDao.getCheckInsByMood(mood)
    }

    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>> {
        return journalDao.getCheckInsByRisk(riskLevel)
    }

    // ══════════════════════════════════════════
    // EVENING CHECK-IN
    // ══════════════════════════════════════════

    val allEveningCheckIns: Flow<List<EveningCheckInEntry>> = journalDao.getAllEveningCheckIns()

    val eveningCheckInCount: Flow<Int> = journalDao.getEveningCheckInCount()

    val recentEveningCheckIns: Flow<List<EveningCheckInEntry>> = journalDao.getRecentEveningCheckIns()

    suspend fun insertEveningCheckIn(entry: EveningCheckInEntry) {
        validateEveningCheckInEntry(entry)
        journalDao.insertEveningCheckIn(entry)
    }

    suspend fun getEveningCheckInForDate(date: String): EveningCheckInEntry? {
        return journalDao.getEveningCheckInForDate(date)
    }

    suspend fun updateEveningCheckIn(entry: EveningCheckInEntry) {
        journalDao.updateEveningCheckIn(entry)
    }

    suspend fun deleteEveningCheckIn(entry: EveningCheckInEntry) {
        journalDao.deleteEveningCheckIn(entry)
    }

    suspend fun getTodayMorningCheckIn(): CheckInEntry? {
        val today = java.time.LocalDate.now().toString()
        return journalDao.getCheckInForDate(today)
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

    suspend fun getEveningCheckInsInRange(startMs: Long, endMs: Long): List<EveningCheckInEntry> {
        return journalDao.getEveningCheckInsInRange(startMs, endMs)
    }

    // ══════════════════════════════════════════
    // VALIDATION LAYER
    // ══════════════════════════════════════════

    private fun validateJournalEntry(entry: JournalEntry) {
        Validators.requireValidUrgeStrength(entry.urgeStrength)

        val hasAnyAnswer = entry.situationContext.isNotBlank() ||
                entry.feelings.isNotBlank() ||
                entry.realNeed.isNotBlank() ||
                entry.alternativeChosen.isNotBlank() ||
                entry.freeText.isNotBlank()
        require(hasAnyAnswer) {
            "At least one question must be answered before saving entry"
        }

        Validators.requireValidFreeTextLength(entry.freeText)
    }

    private fun validateMemoryEntry(memory: MemoryEntry) {
        Validators.requireValidMemoryType(memory.type)
        Validators.requireValidMessageLength(memory.message)

        if (memory.urgeStrengthAtTime > 0) {
            Validators.requireValidUrgeStrength(memory.urgeStrengthAtTime)
        }

        Validators.requireValidStreak(memory.streakAtTime)
    }

    private fun validateCheckInEntry(checkIn: CheckInEntry) {
        Validators.requireValidDateFormat(checkIn.date)
        Validators.requireValidMood(checkIn.mood)
        Validators.requireValidRiskLevel(checkIn.riskLevel)
        Validators.requireValidIntentionLength(checkIn.intention)
        Validators.requireValidStreak(checkIn.streakAtTime)
    }

    private fun validateEveningCheckInEntry(entry: EveningCheckInEntry) {
        Validators.requireValidDateFormat(entry.date)
        Validators.requireValidStreak(entry.streakAtTime)

        // Validate intention_followed if provided
        entry.intentionFollowed?.let {
            Validators.requireValidIntentionFollowed(it)
        }

        // Validate spiritual score range
        Validators.requireValidSpiritualScore(entry.spiritualScore)

        // Validate text lengths
        entry.hardestMoment?.let { Validators.requireValidFreeTextLength(it) }
        entry.wins?.let { Validators.requireValidFreeTextLength(it) }
        entry.tomorrowConcern?.let { Validators.requireValidFreeTextLength(it) }
        entry.intentionNote?.let { Validators.requireValidFreeTextLength(it) }
    }
}