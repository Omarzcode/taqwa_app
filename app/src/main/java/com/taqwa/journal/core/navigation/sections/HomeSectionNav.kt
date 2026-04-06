package com.taqwa.journal.core.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.home.screens.HomeScreen
import com.taqwa.journal.features.home.ui.HomeAction
import com.taqwa.journal.features.home.ui.HomeState
import com.taqwa.journal.core.viewmodel.JournalViewModel

fun NavGraphBuilder.homeSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.HOME) {
        val urgesDefeated = viewModel.urgesDefeatedCount.collectAsState(initial = 0).value
        val currentStreak = viewModel.currentStreak.collectAsState().value
        val longestStreak = viewModel.longestStreak.collectAsState().value
        val streakStatus = viewModel.streakStatus.collectAsState().value
        val milestoneMessage = viewModel.milestoneMessage.collectAsState().value
        val todayCheckInDone = viewModel.todayCheckInDone.collectAsState().value
        val totalRelapses = viewModel.totalRelapses.collectAsState().value
        val dailyAyah = viewModel.dailyAyah.collectAsState().value
        val memoryCount = viewModel.memoryCount.collectAsState(initial = 0).value

        LaunchedEffect(Unit) {
            viewModel.refreshStreakData()
            viewModel.refreshPromiseData()
            viewModel.refreshDailyAyah()
            viewModel.checkTodayCheckIn()
        }

        val homeState = HomeState(
            urgesDefeated = urgesDefeated,
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            streakStatus = streakStatus,
            milestoneMessage = milestoneMessage,
            todayCheckInDone = todayCheckInDone,
            totalRelapses = totalRelapses,
            dailyAyah = dailyAyah,
            memoryCount = memoryCount
        )
        HomeScreen(
            state = homeState,
            onAction = { action ->
                when (action) {
                    HomeAction.StartUrgeFlow -> {
                        viewModel.resetCurrentEntry()
                        navController.navigate(Routes.BREATHING)
                    }
                    HomeAction.OpenQuickCatch -> {
                        viewModel.loadQuickCatchData()
                        navController.navigate(Routes.QUICK_CATCH)
                    }
                    HomeAction.OpenMorningCheckIn -> {
                        viewModel.loadCheckInMemory()
                        navController.navigate(Routes.MORNING_CHECK_IN)
                    }
                    HomeAction.DismissMilestone -> {
                        viewModel.dismissMilestone()
                    }
                }
            }
        )
    }
}
