package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.screens.ToolsAction
import com.taqwa.journal.ui.screens.ToolsScreen
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.toolsSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.TOOLS) {
        val todayCheckInDone by viewModel.todayCheckInDone.collectAsState()
        val memoryCount by viewModel.memoryCount.collectAsState(initial = 0)

        LaunchedEffect(Unit) {
            viewModel.checkTodayCheckIn()
        }

        ToolsScreen(
            todayCheckInDone = todayCheckInDone,
            memoryCount = memoryCount,
            onAction = { action ->
                when (action) {
                    ToolsAction.OpenShieldPlans -> navController.navigate(Routes.SHIELD_PLANS)
                    ToolsAction.OpenMemoryBank -> navController.navigate(Routes.MEMORY_BANK)
                    ToolsAction.OpenPromiseWall -> navController.navigate(Routes.PROMISE_WALL)
                    ToolsAction.OpenMorningCheckIn -> {
                        viewModel.loadCheckInMemory()
                        navController.navigate(Routes.MORNING_CHECK_IN)
                    }
                    ToolsAction.OpenPastEntries -> navController.navigate(Routes.PAST_ENTRIES)
                    ToolsAction.OpenCalendar -> navController.navigate(Routes.CALENDAR)
                    ToolsAction.OpenPatternAnalysis -> navController.navigate(Routes.PATTERN_ANALYSIS)
                    ToolsAction.OpenRelapseHistory -> navController.navigate(Routes.RELAPSE_HISTORY)
                    ToolsAction.OpenResetStreak -> navController.navigate(Routes.RESET_STREAK)
                    ToolsAction.OpenKnowledge -> navController.navigate(Routes.KNOWLEDGE_CATEGORIES)
                }
            }
        )
    }
}