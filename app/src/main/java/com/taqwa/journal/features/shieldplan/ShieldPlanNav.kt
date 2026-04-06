package com.taqwa.journal.features.shieldplan

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.shieldplan.screens.ShieldPlanScreen
import com.taqwa.journal.features.shieldplan.screens.EditShieldPlanScreen

fun NavGraphBuilder.shieldplanSection(navController: NavHostController) {
    composable(Routes.SHIELD_PLAN_MAIN) {
        ShieldPlanScreen(navController = navController)
    }

    composable(Routes.EDIT_SHIELD_PLAN) {
        EditShieldPlanScreen(navController = navController)
    }
}
