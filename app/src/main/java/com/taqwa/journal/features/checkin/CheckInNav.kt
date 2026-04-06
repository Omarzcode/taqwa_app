package com.taqwa.journal.features.checkin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.checkin.screens.MorningCheckInScreen

fun NavGraphBuilder.checkinSection(navController: NavHostController) {
    composable(Routes.MORNING_CHECK_IN) {
        MorningCheckInScreen(navController = navController)
    }
}
