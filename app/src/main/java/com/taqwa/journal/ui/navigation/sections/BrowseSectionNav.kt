package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.navigation.state.StreakStateHolder
import com.taqwa.journal.ui.screens.CalendarScreen
import com.taqwa.journal.ui.screens.EntryDetailScreen
import com.taqwa.journal.ui.screens.PastEntriesScreen
import com.taqwa.journal.ui.screens.PatternAnalysisScreen
import com.taqwa.journal.ui.screens.RelapseHistoryScreen
import com.taqwa.journal.ui.screens.ResetScreen
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.browseSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val streakState = StreakStateHolder(viewModel)

    composable(Routes.PAST_ENTRIES) {
        val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())

        PastEntriesScreen(
            entries = allEntries,
            onEntryClick = { navController.navigate(Routes.entryDetail(it)) },
            onDeleteEntry = { viewModel.deleteEntry(it) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = Routes.ENTRY_DETAIL,
        arguments = listOf(navArgument("entryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val entryId = backStackEntry.arguments?.getInt("entryId") ?: 0
        val entry by viewModel.getEntryById(entryId).collectAsState(initial = null)

        EntryDetailScreen(
            entry = entry,
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.CALENDAR) {
        val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())
        val streak = streakState.collectState()

        val streakStartDate = remember {
            viewModel.streakManager.getStreakStartDate()?.toString()
        }

        CalendarScreen(
            entries = allEntries,
            relapseHistory = streak.relapseHistory,
            streakStartDate = streakStartDate,
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.PATTERN_ANALYSIS) {
        val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())

        PatternAnalysisScreen(
            entries = allEntries,
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.RESET_STREAK) {
        val streak = streakState.collectState()

        ResetScreen(
            currentStreak = streak.currentStreak,
            onReset = { reason ->
                streakState.resetStreak(reason)
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            onResetWithLetter = { reason, letter ->
                streakState.resetStreakWithLetter(reason, letter)
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }
    composable(Routes.RELAPSE_HISTORY) {
        val streak = streakState.collectState()

        RelapseHistoryScreen(
            relapseHistory = streak.relapseHistory,
            totalRelapses = streak.totalRelapses,
            currentStreak = streak.currentStreak,
            longestStreak = streak.longestStreak,
            onBack = { navController.popBackStack() }
        )
    }
}