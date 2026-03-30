package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.data.preferences.ShieldPlan
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class MemoryStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): MemoryScreenState {
        val allMemories by viewModel.allMemories.collectAsState(initial = emptyList())
        val memoryCount by viewModel.memoryCount.collectAsState(initial = 0)
        val quickCatchRelapseLetter by viewModel.quickCatchRelapseLetter.collectAsState()
        val quickCatchVictoryNote by viewModel.quickCatchVictoryNote.collectAsState()
        val quickCatchRandomMemory by viewModel.quickCatchRandomMemory.collectAsState()
        val currentStreak by viewModel.currentStreak.collectAsState()
        val whyQuitting by viewModel.whyQuitting.collectAsState()
        val dailyAyah by viewModel.dailyAyah.collectAsState()
        val shieldPlans by viewModel.shieldPlans.collectAsState()

        return MemoryScreenState(
            allMemories = allMemories,
            memoryCount = memoryCount,
            quickCatchRelapseLetter = quickCatchRelapseLetter,
            quickCatchVictoryNote = quickCatchVictoryNote,
            quickCatchRandomMemory = quickCatchRandomMemory,
            currentStreak = currentStreak,
            whyQuitting = whyQuitting,
            dailyAyah = dailyAyah,
            activePlans = shieldPlans.filter { it.isActive }
        )
    }

    fun loadQuickCatchData() = viewModel.loadQuickCatchData()
    fun logQuickCatch() = viewModel.logQuickCatch()
    fun toggleMemoryPin(memory: MemoryEntry) = viewModel.toggleMemoryPin(memory)
    fun deleteMemory(memory: MemoryEntry) = viewModel.deleteMemory(memory)
    fun saveRelapseLetter(message: String, trigger: String) = viewModel.saveRelapseLetter(message, trigger)
    fun saveVictoryNote(message: String) = viewModel.saveVictoryNote(message)
    fun saveManualMemory(message: String) = viewModel.saveManualMemory(message)
    fun resetCurrentEntry() = viewModel.resetCurrentEntry()
}

data class MemoryScreenState(
    val allMemories: List<MemoryEntry>,
    val memoryCount: Int,
    val quickCatchRelapseLetter: MemoryEntry?,
    val quickCatchVictoryNote: MemoryEntry?,
    val quickCatchRandomMemory: MemoryEntry?,
    val currentStreak: Int,
    val whyQuitting: String,
    val dailyAyah: DailyAyah?,
    val activePlans: List<ShieldPlan>
)