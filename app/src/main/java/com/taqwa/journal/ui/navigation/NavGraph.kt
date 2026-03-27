package com.taqwa.journal.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.taqwa.journal.ui.components.BottomNavItem
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.theme.AccentRed
import com.taqwa.journal.ui.theme.BackgroundCard
import com.taqwa.journal.ui.theme.VanillaCustard
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
    const val CALENDAR = "calendar"
    const val SETTINGS = "settings"

    fun entryDetail(entryId: Int) = "entry_detail/$entryId"

    // Routes where bottom nav should be visible
    val bottomNavRoutes = setOf(
        HOME, PAST_ENTRIES, CALENDAR, PATTERN_ANALYSIS, SETTINGS
    )

    // Routes that are part of the urge flow (hide bottom nav)
    val urgeFlowRoutes = setOf(
        BREATHING, REALITY_CHECK, ISLAMIC_REMINDER,
        PERSONAL_REMINDER, FUTURE_SELF, QUESTIONS, VICTORY
    )
}

@Composable
fun shouldShowBottomNav(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute in Routes.bottomNavRoutes
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun NavHostController.navigateToBottomNavItem(item: BottomNavItem) {
    navigate(item.route) {
        popUpTo(Routes.HOME) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun FlowBackHandler(navController: NavHostController) {
    var showQuitDialog by remember { mutableStateOf(false) }
    BackHandler { showQuitDialog = true }
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("⚠️ Don't Give Up!", fontWeight = FontWeight.Bold) },
            text = {
                Text("You're in the middle of fighting an urge.\n\nThe urge WANTS you to close this app.\nDon't let it win.\n\nStay strong. Keep going.")
            },
            confirmButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("💪 Keep Going", color = VanillaCustard)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showQuitDialog = false
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }) { Text("Quit Flow", color = AccentRed) }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
fun TaqwaNavGraph(navController: NavHostController, viewModel: JournalViewModel) {
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
    val dailyAyah by viewModel.dailyAyah.collectAsState()
    val situationContext by viewModel.currentSituationContext.collectAsState()
    val selectedFeelings by viewModel.currentFeelings.collectAsState()
    val selectedNeeds by viewModel.currentRealNeed.collectAsState()
    val selectedAlternatives by viewModel.currentAlternative.collectAsState()
    val urgeStrength by viewModel.currentUrgeStrength.collectAsState()
    val freeText by viewModel.currentFreeText.collectAsState()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            LaunchedEffect(Unit) {
                viewModel.refreshStreakData()
                viewModel.refreshPromiseData()
                viewModel.refreshDailyAyah()
            }
            HomeScreen(
                urgesDefeated = urgesDefeated, currentStreak = currentStreak,
                longestStreak = longestStreak, streakStatus = streakStatus,
                milestoneMessage = milestoneMessage, totalRelapses = totalRelapses,
                dailyAyah = dailyAyah,
                onDismissMilestone = { viewModel.dismissMilestone() },
                onUrgeClick = {
                    viewModel.resetCurrentEntry()
                    navController.navigate(Routes.BREATHING)
                },
                onPastEntriesClick = { navController.navigate(Routes.PAST_ENTRIES) },
                onResetStreakClick = { navController.navigate(Routes.RESET_STREAK) },
                onRelapseHistoryClick = { navController.navigate(Routes.RELAPSE_HISTORY) },
                onPatternAnalysisClick = { navController.navigate(Routes.PATTERN_ANALYSIS) },
                onPromiseWallClick = { navController.navigate(Routes.PROMISE_WALL) },
                onCalendarClick = { navController.navigate(Routes.CALENDAR) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.BREATHING) {
            FlowBackHandler(navController)
            BreathingScreen(onNext = { navController.navigate(Routes.REALITY_CHECK) })
        }

        composable(Routes.REALITY_CHECK) {
            FlowBackHandler(navController)
            RealityCheckScreen(onNext = { navController.navigate(Routes.ISLAMIC_REMINDER) })
        }

        composable(Routes.ISLAMIC_REMINDER) {
            FlowBackHandler(navController)
            IslamicReminderScreen(
                onNext = {
                    if (hasPromiseContent) navController.navigate(Routes.PERSONAL_REMINDER)
                    else navController.navigate(Routes.FUTURE_SELF)
                }
            )
        }

        composable(Routes.PERSONAL_REMINDER) {
            FlowBackHandler(navController)
            PersonalReminderScreen(
                whyQuitting = whyQuitting, promises = promises,
                duas = duas, reminders = personalReminders,
                onNext = { navController.navigate(Routes.FUTURE_SELF) }
            )
        }

        composable(Routes.FUTURE_SELF) {
            FlowBackHandler(navController)
            FutureSelfScreen(onNext = { navController.navigate(Routes.QUESTIONS) })
        }

        composable(Routes.QUESTIONS) {
            FlowBackHandler(navController)
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

        composable(Routes.VICTORY) {
            BackHandler {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            }
            VictoryScreen(
                urgesDefeated = urgesDefeated,
                onGoHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PAST_ENTRIES) {
            PastEntriesScreen(
                entries = allEntries,
                onEntryClick = { navController.navigate(Routes.entryDetail(it)) },
                onDeleteEntry = { viewModel.deleteEntry(it) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ENTRY_DETAIL,
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: 0
            val entry by viewModel.getEntryById(entryId).collectAsState(initial = null)
            EntryDetailScreen(entry = entry, onBack = { navController.popBackStack() })
        }

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

        composable(Routes.RELAPSE_HISTORY) {
            RelapseHistoryScreen(
                relapseHistory = relapseHistory,
                totalRelapses = totalRelapses,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PATTERN_ANALYSIS) {
            PatternAnalysisScreen(
                entries = allEntries,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROMISE_WALL) {
            LaunchedEffect(Unit) { viewModel.refreshPromiseData() }
            PromiseWallScreen(
                promises = promises, whyQuitting = whyQuitting,
                duas = duas, reminders = personalReminders,
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

        composable(Routes.CALENDAR) {
            val streakStartDate = remember {
                viewModel.streakManager.getStreakStartDate()?.toString()
            }
            CalendarScreen(
                entries = allEntries,
                relapseHistory = relapseHistory,
                streakStartDate = streakStartDate,
                onBack = { navController.popBackStack() }
            )
        }

        // In NavGraph.kt — update the Settings composable:

        composable(Routes.SETTINGS) {
            SettingsScreen(
                totalEntries = allEntries.size,
                totalRelapses = totalRelapses,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                onClearAllData = { viewModel.clearAllData() },
                onRelapseHistoryClick = { navController.navigate(Routes.RELAPSE_HISTORY) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}