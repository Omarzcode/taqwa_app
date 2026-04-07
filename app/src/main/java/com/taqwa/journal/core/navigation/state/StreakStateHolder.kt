package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.features.streak.data.RelapseRecord
import com.taqwa.journal.core.viewmodel.JournalViewModel

class StreakStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): StreakScreenState {
        val currentStreak = viewModel.currentStreak.collectAsState().value
        val longestStreak = viewModel.longestStreak.collectAsState().value
        val totalRelapses = viewModel.totalRelapses.collectAsState().value
        val relapseHistory = viewModel.relapseHistory.collectAsState().value

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
