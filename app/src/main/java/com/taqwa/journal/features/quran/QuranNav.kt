package com.taqwa.journal.features.quran

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.urgeflow.screens.IslamicReminderScreen

fun NavGraphBuilder.quranSection(navController: NavHostController) {
    composable(Routes.ISLAMIC_REMINDER) {
        IslamicReminderScreen(navController = navController)
    }
}
