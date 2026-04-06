package com.taqwa.journal.features.memory

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.memory.screens.MemoryBankScreen
import com.taqwa.journal.features.memory.screens.WriteMemoryScreen

fun NavGraphBuilder.memorySection(navController: NavHostController) {
    composable(Routes.MEMORY_MAIN) {
        MemoryBankScreen(
            onNavigateToWrite = { navController.navigate(Routes.WRITE_MEMORY) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.WRITE_MEMORY) {
        WriteMemoryScreen(
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.MEMORY_DETAIL) { backStackEntry ->
        val memoryId = backStackEntry.arguments?.getString("memoryId") ?: ""
        // Memory detail screen
    }
}
