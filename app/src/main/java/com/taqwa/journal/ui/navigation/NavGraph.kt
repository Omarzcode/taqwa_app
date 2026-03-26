package com.taqwa.journal.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.viewmodel.JournalViewModel

// Screen routes
object Routes {
    const val HOME = "home"
    const val BREATHING = "breathing"
    const val REALITY_CHECK = "reality_check"
    const val ISLAMIC_REMINDER = "islamic_reminder"
    const val FUTURE_SELF = "future_self"
    const val QUESTIONS = "questions"
    const val VICTORY = "victory"
    const val PAST_ENTRIES = "past_entries"
    const val ENTRY_DETAIL = "entry_detail/{entryId}"

    fun entryDetail(entryId: Int) = "entry_detail/$entryId"
}

@Composable
fun TaqwaNavGraph(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val urgesDefeated by viewModel.urgesDefeatedCount.collectAsState(initial = 0)
    val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())

    // Current entry state
    val situationContext by viewModel.currentSituationContext.collectAsState()
    val selectedFeelings by viewModel.currentFeelings.collectAsState()
    val selectedNeeds by viewModel.currentRealNeed.collectAsState()
    val selectedAlternatives by viewModel.currentAlternative.collectAsState()
    val urgeStrength by viewModel.currentUrgeStrength.collectAsState()
    val freeText by viewModel.currentFreeText.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // ========================
        // HOME SCREEN
        // ========================
        composable(Routes.HOME) {
            HomeScreen(
                urgesDefeated = urgesDefeated,
                onUrgeClick = {
                    viewModel.resetCurrentEntry()
                    navController.navigate(Routes.BREATHING)
                },
                onPastEntriesClick = {
                    navController.navigate(Routes.PAST_ENTRIES)
                }
            )
        }

        // ========================
        // BREATHING SCREEN
        // ========================
        composable(Routes.BREATHING) {
            BreathingScreen(
                onNext = {
                    navController.navigate(Routes.REALITY_CHECK)
                }
            )
        }

        // ========================
        // REALITY CHECK SCREEN
        // ========================
        composable(Routes.REALITY_CHECK) {
            RealityCheckScreen(
                onNext = {
                    navController.navigate(Routes.ISLAMIC_REMINDER)
                }
            )
        }

        // ========================
        // ISLAMIC REMINDER SCREEN
        // ========================
        composable(Routes.ISLAMIC_REMINDER) {
            IslamicReminderScreen(
                onNext = {
                    navController.navigate(Routes.FUTURE_SELF)
                }
            )
        }

        // ========================
        // FUTURE SELF SCREEN
        // ========================
        composable(Routes.FUTURE_SELF) {
            FutureSelfScreen(
                onNext = {
                    navController.navigate(Routes.QUESTIONS)
                }
            )
        }

        // ========================
        // QUESTIONS SCREEN
        // ========================
        composable(Routes.QUESTIONS) {
            QuestionsScreen(
                situationContext = situationContext,
                onSituationContextChange = { viewModel.updateSituationContext(it) },
                selectedFeelings = selectedFeelings,
                onFeelingToggle = { viewModel.toggleFeeling(it) },
                selectedNeeds = selectedNeeds,
                onNeedToggle = { viewModel.toggleRealNeed(it) },
                selectedAlternatives = selectedAlternatives,
                onAlternativeToggle = { viewModel.toggleAlternative(it) },
                urgeStrength = urgeStrength,
                onUrgeStrengthChange = { viewModel.updateUrgeStrength(it) },
                freeText = freeText,
                onFreeTextChange = { viewModel.updateFreeText(it) },
                onFinish = {
                    viewModel.saveEntry()
                    navController.navigate(Routes.VICTORY) {
                        // Clear the flow screens from back stack
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            )
        }

        // ========================
        // VICTORY SCREEN
        // ========================
        composable(Routes.VICTORY) {
            VictoryScreen(
                urgesDefeated = urgesDefeated,
                onGoHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        // ========================
        // PAST ENTRIES SCREEN
        // ========================
        composable(Routes.PAST_ENTRIES) {
            PastEntriesScreen(
                entries = allEntries,
                onEntryClick = { entryId ->
                    navController.navigate(Routes.entryDetail(entryId))
                },
                onDeleteEntry = { entry ->
                    viewModel.deleteEntry(entry)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ========================
        // ENTRY DETAIL SCREEN
        // ========================
        composable(
            route = Routes.ENTRY_DETAIL,
            arguments = listOf(
                navArgument("entryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: 0
            val entry by viewModel.getEntryById(entryId).collectAsState(initial = null)

            EntryDetailScreen(
                entry = entry,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}