package com.taqwa.journal.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.viewmodel.JournalViewModel

object Routes {
    const val HOME = "home"
    const val BREATHING = "breathing"
    const val REALITY_CHECK = "reality_check"
    const val ISLAMIC_REMINDER = "islamic_reminder"
    const val PERSONAL_REMINDER = "personal_reminder"
    const val FUTURE_SELF = "future_self"
    const val QUESTIONS = "questions"
    const val VICTORY = "victory"
    const val PAST_ENTRIES = "past_entries"
    const val ENTRY_DETAIL = "entry_detail/{entryId}"
    const val RESET_STREAK = "reset_streak"
    const val RELAPSE_HISTORY = "relapse_history"
    const val PATTERN_ANALYSIS = "pattern_analysis"
    const val PROMISE_WALL = "promise_wall"

    fun entryDetail(entryId: Int) = "entry_detail/$entryId"
}

@Composable
fun TaqwaNavGraph(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val urgesDefeated by viewModel.urgesDefeatedCount.collectAsState(initial = 0)
    val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())

    val currentStreak by viewModel.currentStreak.collectAsState()
    val longestStreak by viewModel.longestStreak.collectAsState()
    val streakStatus by viewModel.streakStatus.collectAsState()
    val milestoneMessage by viewModel.milestoneMessage.collectAsState()
    val totalRelapses by viewModel.totalRelapses.collectAsState()
    val relapseHistory by viewModel.relapseHistory.collectAsState()

    val promises by viewModel.promises.collectAsState()
    val whyQuitting by viewModel.whyQuitting.collectAsState()
    val duas by viewModel.duas.collectAsState()
    val personalReminders by viewModel.personalReminders.collectAsState()
    val hasPromiseContent by viewModel.hasPromiseContent.collectAsState()

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
        // HOME
        composable(Routes.HOME) {
            LaunchedEffect(Unit) {
                viewModel.refreshStreakData()
                viewModel.refreshPromiseData()
            }

            HomeScreen(
                urgesDefeated = urgesDefeated,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                streakStatus = streakStatus,
                milestoneMessage = milestoneMessage,
                totalRelapses = totalRelapses,
                onDismissMilestone = { viewModel.dismissMilestone() },
                onUrgeClick = {
                    viewModel.resetCurrentEntry()
                    navController.navigate(Routes.BREATHING)
                },
                onPastEntriesClick = {
                    navController.navigate(Routes.PAST_ENTRIES)
                },
                onResetStreakClick = {
                    navController.navigate(Routes.RESET_STREAK)
                },
                onRelapseHistoryClick = {
                    navController.navigate(Routes.RELAPSE_HISTORY)
                },
                onPatternAnalysisClick = {
                    navController.navigate(Routes.PATTERN_ANALYSIS)
                },
                onPromiseWallClick = {
                    navController.navigate(Routes.PROMISE_WALL)
                }
            )
        }

        // BREATHING
        composable(Routes.BREATHING) {
            BreathingScreen(
                onNext = { navController.navigate(Routes.REALITY_CHECK) }
            )
        }

        // REALITY CHECK
        composable(Routes.REALITY_CHECK) {
            RealityCheckScreen(
                onNext = { navController.navigate(Routes.ISLAMIC_REMINDER) }
            )
        }

        // ISLAMIC REMINDER
        composable(Routes.ISLAMIC_REMINDER) {
            IslamicReminderScreen(
                onNext = {
                    // If user has promise content, show it
                    if (hasPromiseContent) {
                        navController.navigate(Routes.PERSONAL_REMINDER)
                    } else {
                        navController.navigate(Routes.FUTURE_SELF)
                    }
                }
            )
        }

        // PERSONAL REMINDER (only shown if user has content)
        composable(Routes.PERSONAL_REMINDER) {
            PersonalReminderScreen(
                whyQuitting = whyQuitting,
                promises = promises,
                duas = duas,
                reminders = personalReminders,
                onNext = {
                    navController.navigate(Routes.FUTURE_SELF)
                }
            )
        }

        // FUTURE SELF
        composable(Routes.FUTURE_SELF) {
            FutureSelfScreen(
                onNext = { navController.navigate(Routes.QUESTIONS) }
            )
        }

        // QUESTIONS
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
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            )
        }

        // VICTORY
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

        // PAST ENTRIES
        composable(Routes.PAST_ENTRIES) {
            PastEntriesScreen(
                entries = allEntries,
                onEntryClick = { entryId ->
                    navController.navigate(Routes.entryDetail(entryId))
                },
                onDeleteEntry = { entry -> viewModel.deleteEntry(entry) },
                onBack = { navController.popBackStack() }
            )
        }

        // ENTRY DETAIL
        composable(
            route = Routes.ENTRY_DETAIL,
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: 0
            val entry by viewModel.getEntryById(entryId).collectAsState(initial = null)
            EntryDetailScreen(
                entry = entry,
                onBack = { navController.popBackStack() }
            )
        }

        // RESET STREAK
        composable(Routes.RESET_STREAK) {
            ResetScreen(
                currentStreak = currentStreak,
                onReset = { reason ->
                    viewModel.resetStreak(reason)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // RELAPSE HISTORY
        composable(Routes.RELAPSE_HISTORY) {
            RelapseHistoryScreen(
                relapseHistory = relapseHistory,
                totalRelapses = totalRelapses,
                onBack = { navController.popBackStack() }
            )
        }

        // PATTERN ANALYSIS
        composable(Routes.PATTERN_ANALYSIS) {
            PatternAnalysisScreen(
                entries = allEntries,
                onBack = { navController.popBackStack() }
            )
        }

        // PROMISE WALL
        composable(Routes.PROMISE_WALL) {
            LaunchedEffect(Unit) {
                viewModel.refreshPromiseData()
            }

            PromiseWallScreen(
                promises = promises,
                whyQuitting = whyQuitting,
                duas = duas,
                reminders = personalReminders,
                onAddPromise = { viewModel.addPromise(it) },
                onDeletePromise = { viewModel.deletePromise(it) },
                onSetWhyQuitting = { viewModel.setWhyQuitting(it) },
                onAddDua = { viewModel.addDua(it) },
                onDeleteDua = { viewModel.deleteDua(it) },
                onAddReminder = { viewModel.addReminder(it) },
                onDeleteReminder = { viewModel.deleteReminder(it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}