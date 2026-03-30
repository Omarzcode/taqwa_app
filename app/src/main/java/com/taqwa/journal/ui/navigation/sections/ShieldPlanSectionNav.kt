package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.navigation.state.ShieldPlanStateHolder
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.shieldPlanSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val stateHolder = ShieldPlanStateHolder(viewModel)

    composable(Routes.SHIELD_PLANS) {
        LaunchedEffect(Unit) { stateHolder.refreshShieldPlans() }
        val state = stateHolder.collectState()

        ShieldPlanScreen(
            plans = state.shieldPlans,
            onEditPlan = { plan ->
                stateHolder.setEditingPlan(plan)
                navController.navigate(Routes.editShieldPlan(plan.triggerId))
            },
            onAddCustomPlan = {
                stateHolder.setEditingPlan(null)
                navController.navigate(Routes.NEW_CUSTOM_SHIELD)
            },
            onDeletePlan = { stateHolder.deleteShieldPlan(it) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = Routes.EDIT_SHIELD_PLAN,
        arguments = listOf(navArgument("planId") { type = NavType.StringType })
    ) {
        val state = stateHolder.collectState()
        EditShieldPlanScreen(
            plan = state.editingPlan,
            isNewCustom = false,
            onSave = { updatedPlan ->
                stateHolder.updateShieldPlan(updatedPlan)
                navController.popBackStack()
            },
            onSaveCustom = { _, _, _, _, _ -> },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.NEW_CUSTOM_SHIELD) {
        EditShieldPlanScreen(
            plan = null,
            isNewCustom = true,
            onSave = { },
            onSaveCustom = { name, emoji, description, steps, note ->
                stateHolder.addCustomShieldPlan(name, emoji, description, steps, note)
                navController.popBackStack()
            },
            onBack = { navController.popBackStack() }
        )
    }
}