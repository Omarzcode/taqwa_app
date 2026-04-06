package com.taqwa.journal.features.tools

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.tools.screens.ToolsScreen

fun NavGraphBuilder.toolsSection(navController: NavHostController) {
    composable(Routes.TOOLS_MAIN) {
        ToolsScreen(navController = navController)
    }
}
