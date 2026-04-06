package com.taqwa.journal.features.memory.data

import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow

class MemoryRepository(private val memoryDao: MemoryDao) {

    val allMemories: Flow<List<MemoryEntry>> = memoryDao.getAllMemories()

    val relapseLetters: Flow<List<MemoryEntry>> = memoryDao.getRelapseLetters()

    val victoryNotes: Flow<List<MemoryEntry>> = memoryDao.getVictoryNotes()

    val pinnedMemories: Flow<List<MemoryEntry>> = memoryDao.getPinnedMemories()

    val memoryCount: Flow<Int> = memoryDao.getMemoryCount()

    suspend fun insertMemory(memory: MemoryEntry) {
        validateMemoryEntry(memory)
        memoryDao.insertMemory(memory)
    }

    suspend fun updateMemory(memory: MemoryEntry) {
        memoryDao.updateMemory(memory)
    }

    suspend fun deleteMemory(memory: MemoryEntry) {
        memoryDao.deleteMemory(memory)
    }

    suspend fun getRandomRelapseLetter(): MemoryEntry? {
        return memoryDao.getRandomRelapseLetter()
    }

    suspend fun getRandomVictoryNote(): MemoryEntry? {
        return memoryDao.getRandomVictoryNote()
    }

    suspend fun getRandomPinnedMemory(): MemoryEntry? {
        return memoryDao.getRandomPinnedMemory()
    }

    suspend fun getRandomMemory(): MemoryEntry? {
        return memoryDao.getRandomMemory()
    }

    suspend fun getMostRecentRelapseLetter(): MemoryEntry? {
        return memoryDao.getMostRecentRelapseLetter()
    }

    fun getMemoriesByType(type: String): Flow<List<MemoryEntry>> {
        return memoryDao.getMemoriesByType(type)
    }

    fun getMemoryCountByType(type: String): Flow<Int> {
        return memoryDao.getMemoryCountByType(type)
    }

    suspend fun getMemoriesInRange(startMs: Long, endMs: Long): List<MemoryEntry> {
        return memoryDao.getMemoriesInRange(startMs, endMs)
    }

    private fun validateMemoryEntry(memory: MemoryEntry) {
        Validators.requireValidMessageLength(memory.message)
    }
}
