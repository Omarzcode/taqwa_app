package com.taqwa.journal.features.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.settings.screens.SettingsScreen
import com.taqwa.journal.features.settings.screens.NotificationSettingsScreen

fun NavGraphBuilder.settingsSection(navController: NavHostController) {
    composable(Routes.SETTINGS_MAIN) {
        SettingsScreen(navController = navController)
    }

    composable(Routes.NOTIFICATION_SETTINGS) {
        NotificationSettingsScreen(navController = navController)
    }
}
