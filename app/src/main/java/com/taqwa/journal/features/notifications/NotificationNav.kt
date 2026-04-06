package com.taqwa.journal.features.notifications

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.settings.screens.NotificationSettingsScreen

fun NavGraphBuilder.notificationSection(navController: NavHostController) {
    composable(Routes.NOTIFICATION_SETTINGS) {
        NotificationSettingsScreen(navController = navController)
    }
}
