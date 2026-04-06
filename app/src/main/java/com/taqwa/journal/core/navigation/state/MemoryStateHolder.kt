package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.quran.data.DailyQuranManager.DailyAyah
import com.taqwa.journal.features.shieldplan.data.ShieldPlanManager.ShieldPlan
import com.taqwa.journal.core.viewmodel.JournalViewModel

class MemoryStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): MemoryScreenState {
        val allMemories = viewModel.allMemories.collectAsState(initial = emptyList()).value
        val memoryCount = viewModel.memoryCount.collectAsState(initial = 0).value
        val quickCatchRelapseLetter = viewModel.quickCatchRelapseLetter.collectAsState().value
        val quickCatchVictoryNote = viewModel.quickCatchVictoryNote.collectAsState().value
        val quickCatchRandomMemory = viewModel.quickCatchRandomMemory.collectAsState().value
        val currentStreak = viewModel.currentStreak.collectAsState().value
        val whyQuitting = viewModel.whyQuitting.collectAsState().value
        val dailyAyah = viewModel.dailyAyah.collectAsState().value
        val shieldPlans = viewModel.shieldPlans.collectAsState().value

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
