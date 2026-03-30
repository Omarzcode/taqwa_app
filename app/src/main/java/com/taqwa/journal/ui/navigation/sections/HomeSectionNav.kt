package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.screens.HomeScreen
import com.taqwa.journal.ui.screens.home.HomeAction
import com.taqwa.journal.ui.screens.home.HomeState
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.homeSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.HOME) {
        val urgesDefeated by viewModel.urgesDefeatedCount.collectAsState(initial = 0)
        val currentStreak by viewModel.currentStreak.collectAsState()
        val longestStreak by viewModel.longestStreak.collectAsState()
        val streakStatus by viewModel.streakStatus.collectAsState()
        val milestoneMessage by viewModel.milestoneMessage.collectAsState()
        val todayCheckInDone by viewModel.todayCheckInDone.collectAsState()
        val totalRelapses by viewModel.totalRelapses.collectAsState()
        val dailyAyah by viewModel.dailyAyah.collectAsState()
        val memoryCount by viewModel.memoryCount.collectAsState(initial = 0)

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