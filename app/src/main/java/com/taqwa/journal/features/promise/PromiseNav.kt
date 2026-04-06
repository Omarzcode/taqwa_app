package com.taqwa.journal.features.promise

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.core.navigation.state.PromiseWallStateHolder
import com.taqwa.journal.core.viewmodel.JournalViewModel
import com.taqwa.journal.features.promise.screens.PromiseWallScreen

fun NavGraphBuilder.promiseSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val promiseState = PromiseWallStateHolder(viewModel)
    
    composable(Routes.PROMISES) {
        LaunchedEffect(Unit) { promiseState.refreshPromiseData() }
        val state = promiseState.collectState()

        PromiseWallScreen(
            promises = state.promises,
            whyQuitting = state.whyQuitting,
            duas = state.duas,
            reminders = state.personalReminders,
            onAddPromise = { promiseState.addPromise(it) },
            onDeletePromise = { promiseState.deletePromise(it) },
            onSetWhyQuitting = { promiseState.setWhyQuitting(it) },
            onAddDua = { promiseState.addDua(it) },
            onDeleteDua = { promiseState.deleteDua(it) },
            onAddReminder = { promiseState.addReminder(it) },
            onDeleteReminder = { promiseState.deleteReminder(it) },
            onBack = { navController.popBackStack() }
        )
    }
}
