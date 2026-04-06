package com.taqwa.journal.features.streak

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.streak.screens.ResetScreen
import com.taqwa.journal.features.streak.screens.RelapseHistoryScreen

fun NavGraphBuilder.streakSection(navController: NavHostController) {
    composable(Routes.RESET) {
        ResetScreen(navController = navController)
    }

    composable(Routes.RELAPSES) {
        RelapseHistoryScreen(navController = navController)
    }
}
