package com.taqwa.journal.ui.navigation.sections

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.ui.navigation.FlowBackHandler
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.navigation.state.UrgeFlowStateHolder
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.urgeFlowSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val stateHolder = UrgeFlowStateHolder(viewModel)

    composable(Routes.BREATHING) {
        FlowBackHandler(navController)
        BreathingScreen(
            onNext = { navController.navigate(Routes.REALITY_CHECK) }
        )
    }

    composable(Routes.REALITY_CHECK) {
        FlowBackHandler(navController)
        RealityCheckScreen(
            onNext = { navController.navigate(Routes.ISLAMIC_REMINDER) }
        )
    }

    composable(Routes.ISLAMIC_REMINDER) {
        FlowBackHandler(navController)
        val state = stateHolder.collectState()
        IslamicReminderScreen(
            onNext = {
                if (state.hasPromiseContent) navController.navigate(Routes.PERSONAL_REMINDER)
                else navController.navigate(Routes.FUTURE_SELF)
            }
        )
    }

    composable(Routes.PERSONAL_REMINDER) {
        FlowBackHandler(navController)
        val state = stateHolder.collectState()
        PersonalReminderScreen(
            whyQuitting = state.whyQuitting,
            promises = state.promises,
            duas = state.duas,
            reminders = state.personalReminders,
            onNext = { navController.navigate(Routes.FUTURE_SELF) }
        )
    }

    composable(Routes.FUTURE_SELF) {
        FlowBackHandler(navController)
        FutureSelfScreen(
            onNext = { navController.navigate(Routes.QUESTIONS) }
        )
    }

    composable(Routes.QUESTIONS) {
        FlowBackHandler(navController)
        val state = stateHolder.collectState()
        QuestionsScreen(
            situationContext = state.situationContext,
            onSituationContextChange = { stateHolder.updateSituationContext(it) },
            selectedFeelings = state.selectedFeelings,
            onFeelingToggle = { stateHolder.toggleFeeling(it) },
            selectedNeeds = state.selectedNeeds,
            onNeedToggle = { stateHolder.toggleRealNeed(it) },
            selectedAlternatives = state.selectedAlternatives,
            onAlternativeToggle = { stateHolder.toggleAlternative(it) },
            urgeStrength = state.urgeStrength,
            onUrgeStrengthChange = { stateHolder.updateUrgeStrength(it) },
            freeText = state.freeText,
            onFreeTextChange = { stateHolder.updateFreeText(it) },
            onFinish = {
                stateHolder.saveEntry()
                navController.navigate(Routes.VICTORY) {
                    popUpTo(Routes.HOME) { inclusive = false }
                }
            }
        )
    }

    composable(Routes.VICTORY) {
        val state = stateHolder.collectState()
        BackHandler {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.HOME) { inclusive = true }
            }
        }
        VictoryScreen(
            urgesDefeated = state.urgesDefeated,
            onGoHome = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            onWriteVictoryNote = {
                navController.navigate(Routes.writeMemory(MemoryEntry.TYPE_VICTORY_NOTE))
            }
        )
    }
}