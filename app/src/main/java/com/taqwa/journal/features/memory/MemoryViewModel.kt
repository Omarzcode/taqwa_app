package com.taqwa.journal.features.memory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.memory.data.MemoryRepository
import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MemoryViewModel(
    application: Application,
    private val repository: MemoryRepository
) : AndroidViewModel(application) {

    val allMemories: Flow<List<MemoryEntry>> = repository.allMemories
    val memoryCount: Flow<Int> = repository.memoryCount
    val relapseLetters: Flow<List<MemoryEntry>> = repository.relapseLetters
    val victoryNotes: Flow<List<MemoryEntry>> = repository.victoryNotes
    val pinnedMemories: Flow<List<MemoryEntry>> = repository.pinnedMemories

    private val _quickCatchRelapseLetter = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRelapseLetter: StateFlow<MemoryEntry?> = _quickCatchRelapseLetter.asStateFlow()

    private val _quickCatchVictoryNote = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchVictoryNote: StateFlow<MemoryEntry?> = _quickCatchVictoryNote.asStateFlow()

    private val _quickCatchRandomMemory = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRandomMemory: StateFlow<MemoryEntry?> = _quickCatchRandomMemory.asStateFlow()

    private val _interventionMemory = MutableStateFlow<MemoryEntry?>(null)
    val interventionMemory: StateFlow<MemoryEntry?> = _interventionMemory.asStateFlow()

    fun saveRelapseLetter(message: String, trigger: String = "", currentStreak: Int) {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_RELAPSE_LETTER,
                    message = message,
                    trigger = trigger,
                    streakAtTime = currentStreak
                )
                repository.insertMemory(memory)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun saveVictoryNote(message: String, currentStreak: Int, currentUrgeStrength: Int) {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_VICTORY_NOTE,
                    message = message,
                    streakAtTime = currentStreak,
                    urgeStrengthAtTime = currentUrgeStrength
                )
                repository.insertMemory(memory)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun saveManualMemory(message: String, currentStreak: Int) {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_MANUAL,
                    message = message,
                    streakAtTime = currentStreak
                )
                repository.insertMemory(memory)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun toggleMemoryPin(memory: MemoryEntry) {
        viewModelScope.launch {
            repository.updateMemory(memory.copy(isPinned = !memory.isPinned))
        }
    }

    fun deleteMemory(memory: MemoryEntry) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
        }
    }

    fun loadQuickCatchData() {
        viewModelScope.launch {
            _quickCatchRelapseLetter.value = repository.getMostRecentRelapseLetter()
            _quickCatchVictoryNote.value = repository.getRandomVictoryNote()
            _quickCatchRandomMemory.value = repository.getRandomPinnedMemory()
                ?: repository.getRandomMemory()
        }
    }

    fun loadInterventionMemory() {
        viewModelScope.launch {
            _interventionMemory.value =
                repository.getRandomPinnedMemory()
                    ?: repository.getRandomRelapseLetter()
                    ?: repository.getRandomVictoryNote()
                    ?: repository.getRandomMemory()
        }
    }

    fun getMemoriesByType(type: String): Flow<List<MemoryEntry>> {
        return repository.getMemoriesByType(type)
    }

    fun getMemoryCountByType(type: String): Flow<Int> {
        return repository.getMemoryCountByType(type)
    }
}
