package com.taqwa.journal.features.urgeflow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.urgeflow.screens.*

fun NavGraphBuilder.urgeflowSection(navController: NavHostController) {
    composable(Routes.BREATHING) {
        BreathingScreen(
            onNext = { navController.navigate(Routes.REALITY_CHECK) },
            currentStep = 1,
            totalSteps = 7
        )
    }

    composable(Routes.REALITY_CHECK) {
        RealityCheckScreen(
            onNext = { navController.navigate(Routes.ISLAMIC_REMINDER) },
            currentStep = 2,
            totalSteps = 7
        )
    }

    composable(Routes.ISLAMIC_REMINDER) {
        IslamicReminderScreen(navController = navController)
    }

    composable(Routes.PERSONAL_REMINDER) {
        PersonalReminderScreen(
            onNext = { navController.navigate(Routes.FUTURE_SELF) },
            currentStep = 4,
            totalSteps = 7
        )
    }

    composable(Routes.FUTURE_SELF) {
        FutureSelfScreen(
            onNext = { navController.navigate(Routes.QUICK_CATCH) },
            currentStep = 5,
            totalSteps = 7
        )
    }

    composable(Routes.QUICK_CATCH) {
        QuickCatchScreen(navController = navController)
    }

    composable(Routes.VICTORY) {
        VictoryScreen(
            onNext = { navController.popBackStack() },
            currentStep = 7,
            totalSteps = 7
        )
    }
}
