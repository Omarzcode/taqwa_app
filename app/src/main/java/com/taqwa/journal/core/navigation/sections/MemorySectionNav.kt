package com.taqwa.journal.core.navigation.sections

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.core.navigation.state.MemoryStateHolder
import com.taqwa.journal.features.memory.screens.*
import com.taqwa.journal.features.quickcatch.screens.QuickCatchScreen
import com.taqwa.journal.core.viewmodel.JournalViewModel

fun NavGraphBuilder.memorySection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val stateHolder = MemoryStateHolder(viewModel)

    composable(Routes.QUICK_CATCH) {
        val state = stateHolder.collectState()
        QuickCatchScreen(
            currentStreak = state.currentStreak,
            whyQuitting = state.whyQuitting,
            relapseLetter = state.quickCatchRelapseLetter,
            victoryNote = state.quickCatchVictoryNote,
            randomMemory = state.quickCatchRandomMemory,
            dailyAyah = state.dailyAyah,
            activePlans = state.activePlans,
            onCaughtIt = {
                stateHolder.logQuickCatch()
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            onNeedFullFlow = {
                stateHolder.resetCurrentEntry()
                navController.navigate(Routes.BREATHING) {
                    popUpTo(Routes.QUICK_CATCH) { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.MEMORY_BANK) {
        val state = stateHolder.collectState()
        MemoryBankScreen(
            memories = state.allMemories,
            onAddMemory = {
                navController.navigate(Routes.writeMemory(MemoryEntry.TYPE_MANUAL))
            },
            onTogglePin = { stateHolder.toggleMemoryPin(it) },
            onDeleteMemory = { stateHolder.deleteMemory(it) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = Routes.WRITE_MEMORY,
        arguments = listOf(navArgument("memoryType") { type = NavType.StringType })
    ) { backStackEntry ->
        val memoryType = backStackEntry.arguments?.getString("memoryType")
            ?: MemoryEntry.TYPE_MANUAL
        val state = stateHolder.collectState()

        WriteMemoryScreen(
            memoryType = memoryType,
            currentStreak = state.currentStreak,
            onSave = { message, trigger ->
                when (memoryType) {
                    MemoryEntry.TYPE_RELAPSE_LETTER -> stateHolder.saveRelapseLetter(message, trigger)
                    MemoryEntry.TYPE_VICTORY_NOTE -> stateHolder.saveVictoryNote(message)
                    else -> stateHolder.saveManualMemory(message)
                }
                navController.popBackStack()
            },
            onSkip = { navController.popBackStack() },
            onBack = { navController.popBackStack() }
        )
    }
}
