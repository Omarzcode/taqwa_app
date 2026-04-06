package com.taqwa.journal.core.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.core.navigation.state.StreakStateHolder
import com.taqwa.journal.features.browse.screens.*
import com.taqwa.journal.core.viewmodel.JournalViewModel
import com.taqwa.journal.features.analysis.screens.PatternAnalysisScreen
import com.taqwa.journal.features.streak.screens.RelapseHistoryScreen
import com.taqwa.journal.features.streak.screens.ResetScreen

fun NavGraphBuilder.browseSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val streakState = StreakStateHolder(viewModel)

    composable(Routes.PAST_ENTRIES) {
        val allEntries = viewModel.allEntries.collectAsState(initial = emptyList()).value

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
        val entry = viewModel.getEntryById(entryId).collectAsState(initial = null).value

        EntryDetailScreen(
            entry = entry,
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.CALENDAR) {
        val allEntries = viewModel.allEntries.collectAsState(initial = emptyList()).value
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
        val allEntries = viewModel.allEntries.collectAsState(initial = emptyList()).value

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
            onBack = { navController.popBackStack() }
        )
    }
}
