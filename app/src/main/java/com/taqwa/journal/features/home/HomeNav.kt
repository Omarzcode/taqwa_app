package com.taqwa.journal.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.home.screens.HomeScreen

fun NavGraphBuilder.homeSection(navController: NavHostController) {
    composable(Routes.HOME_MAIN) {
        HomeScreen(
            state = HomeState(), // TODO: Get from ViewModel
            onAction = { /* TODO: Handle actions */ }
        )
    }
}
