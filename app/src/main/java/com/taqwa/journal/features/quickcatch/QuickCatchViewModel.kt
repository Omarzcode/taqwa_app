package com.taqwa.journal.features.quickcatch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.memory.data.MemoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.viewModelScope
import kotlinx.coroutines.launch

class QuickCatchViewModel(
    application: Application,
    private val memoryRepository: MemoryRepository
) : AndroidViewModel(application) {

    private val _quickCatchRelapseLetter = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRelapseLetter: StateFlow<MemoryEntry?> = _quickCatchRelapseLetter.asStateFlow()

    private val _quickCatchVictoryNote = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchVictoryNote: StateFlow<MemoryEntry?> = _quickCatchVictoryNote.asStateFlow()

    private val _quickCatchRandomMemory = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRandomMemory: StateFlow<MemoryEntry?> = _quickCatchRandomMemory.asStateFlow()

    private val _quickCatchCount = MutableStateFlow(0)
    val quickCatchCount: StateFlow<Int> = _quickCatchCount.asStateFlow()

    fun loadQuickCatchData() {
        viewModelScope.launch {
            _quickCatchRelapseLetter.value = memoryRepository.getMostRecentRelapseLetter()
            _quickCatchVictoryNote.value = memoryRepository.getRandomVictoryNote()
            _quickCatchRandomMemory.value = memoryRepository.getRandomPinnedMemory()
                ?: memoryRepository.getRandomMemory()
        }
    }

    fun logQuickCatch() {
        _quickCatchCount.value += 1
    }
}
