package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class StreakStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): StreakScreenState {
        val currentStreak by viewModel.currentStreak.collectAsState()
        val longestStreak by viewModel.longestStreak.collectAsState()
        val totalRelapses by viewModel.totalRelapses.collectAsState()
        val relapseHistory by viewModel.relapseHistory.collectAsState()

        return StreakScreenState(
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            totalRelapses = totalRelapses,
            relapseHistory = relapseHistory
        )
    }

    fun refreshStreakData() = viewModel.refreshStreakData()
    fun resetStreak(reason: String) = viewModel.resetStreak(reason)
    fun resetStreakWithLetter(reason: String, letter: String) = viewModel.resetStreakWithLetter(reason, letter)
}

data class StreakScreenState(
    val currentStreak: Int,
    val longestStreak: Int,
    val totalRelapses: Int,
    val relapseHistory: List<RelapseRecord>
)