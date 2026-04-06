package com.taqwa.journal.features.export

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.export.screens.ExportScreen

fun NavGraphBuilder.exportSection(navController: NavHostController) {
    composable(Routes.EXPORT) {
        ExportScreen(navController = navController)
    }
}
