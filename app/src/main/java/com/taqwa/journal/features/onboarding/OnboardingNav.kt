package com.taqwa.journal.features.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.onboarding.screens.OnboardingScreen

fun NavGraphBuilder.onboardingSection(navController: NavHostController) {
    composable(Routes.ONBOARDING) {
        OnboardingScreen(navController = navController)
    }
}
