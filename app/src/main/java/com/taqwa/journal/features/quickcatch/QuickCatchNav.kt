package com.taqwa.journal.features.quickcatch

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.quickcatch.screens.QuickCatchScreen

fun NavGraphBuilder.quickcatchSection(navController: NavHostController) {
    composable(Routes.QUICK_CATCH) {
        QuickCatchScreen(navController = navController)
    }
}
