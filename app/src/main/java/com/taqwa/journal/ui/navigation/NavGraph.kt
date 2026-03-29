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
import com.taqwa.journal.data.database.MemoryEntry
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

    // Memory Bank
    const val QUICK_CATCH = "quick_catch"
    const val MEMORY_BANK = "memory_bank"
    const val WRITE_MEMORY = "write_memory/{memoryType}"

    // Morning Check-In
    const val MORNING_CHECK_IN = "morning_check_in"

    // Shield Plans
    const val SHIELD_PLANS = "shield_plans"
    const val EDIT_SHIELD_PLAN = "edit_shield_plan/{planId}"
    const val NEW_CUSTOM_SHIELD = "new_custom_shield"

    // Export
    const val EXPORT = "export"

    // Notifications
    const val NOTIFICATION_SETTINGS = "notification_settings"

    fun entryDetail(entryId: Int) = "entry_detail/$entryId"
    fun writeMemory(type: String) = "write_memory/$type"
    fun editShieldPlan(planId: String) = "edit_shield_plan/$planId"

    val bottomNavRoutes = setOf(
        HOME, PAST_ENTRIES, CALENDAR, PATTERN_ANALYSIS, SETTINGS
    )

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
        popUpTo(Routes.HOME) { saveState = true }
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

    // ── Collect all state ──
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

    // Memory Bank
    val allMemories by viewModel.allMemories.collectAsState(initial = emptyList())
    val memoryCountValue by viewModel.memoryCount.collectAsState(initial = 0)
    val quickCatchRelapseLetter by viewModel.quickCatchRelapseLetter.collectAsState()
    val quickCatchVictoryNote by viewModel.quickCatchVictoryNote.collectAsState()
    val quickCatchRandomMemory by viewModel.quickCatchRandomMemory.collectAsState()

    // Shield Plans
    val shieldPlans by viewModel.shieldPlans.collectAsState()
    val editingPlan by viewModel.editingPlan.collectAsState()

    // Notification settings
    val morningEnabled by viewModel.morningEnabled.collectAsState()
    val morningHour by viewModel.morningHour.collectAsState()
    val morningMinute by viewModel.morningMinute.collectAsState()
    val dangerHourEnabled by viewModel.dangerHourEnabled.collectAsState()
    val dangerHourDetected by viewModel.dangerHourDetected.collectAsState()
    val dangerHourStart by viewModel.dangerHourStart.collectAsState()
    val dangerHourEnd by viewModel.dangerHourEnd.collectAsState()
    val dangerAlertHour by viewModel.dangerAlertHour.collectAsState()
    val dangerAlertMinute by viewModel.dangerAlertMinute.collectAsState()
    val memoryResurfaceEnabled by viewModel.memoryResurfaceEnabled.collectAsState()
    val inactivityEnabled by viewModel.inactivityEnabled.collectAsState()
    val streakCelebrationEnabled by viewModel.streakCelebrationEnabled.collectAsState()
    val postRelapseEnabled by viewModel.postRelapseEnabled.collectAsState()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        // ══════════════════════════════════════════
        // HOME
        // ══════════════════════════════════════════

        composable(Routes.HOME) {
            val todayCheckInDone by viewModel.todayCheckInDone.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.refreshStreakData()
                viewModel.refreshPromiseData()
                viewModel.refreshDailyAyah()
                viewModel.checkTodayCheckIn()
            }

            HomeScreen(
                urgesDefeated = urgesDefeated,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                streakStatus = streakStatus,
                milestoneMessage = milestoneMessage,
                todayCheckInDone = todayCheckInDone,
                totalRelapses = totalRelapses,
                dailyAyah = dailyAyah,
                memoryCount = memoryCountValue,
                onDismissMilestone = { viewModel.dismissMilestone() },
                onUrgeClick = {
                    viewModel.resetCurrentEntry()
                    navController.navigate(Routes.BREATHING)
                },
                onQuickCatchClick = {
                    viewModel.loadQuickCatchData()
                    navController.navigate(Routes.QUICK_CATCH)
                },
                onMorningCheckInClick = {
                    viewModel.loadCheckInMemory()
                    navController.navigate(Routes.MORNING_CHECK_IN)
                },
                onShieldPlansClick = { navController.navigate(Routes.SHIELD_PLANS) },
                onMemoryBankClick = { navController.navigate(Routes.MEMORY_BANK) },
                onResetStreakClick = { navController.navigate(Routes.RESET_STREAK) },
                onPastEntriesClick = { navController.navigate(Routes.PAST_ENTRIES) },
                onRelapseHistoryClick = { navController.navigate(Routes.RELAPSE_HISTORY) },
                onPatternAnalysisClick = { navController.navigate(Routes.PATTERN_ANALYSIS) },
                onPromiseWallClick = { navController.navigate(Routes.PROMISE_WALL) },
                onCalendarClick = { navController.navigate(Routes.CALENDAR) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) }
            )
        }

        // ══════════════════════════════════════════
        // URGE INTERVENTION FLOW
        // ══════════════════════════════════════════

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
                whyQuitting = whyQuitting,
                promises = promises,
                duas = duas,
                reminders = personalReminders,
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
                },
                onWriteVictoryNote = {
                    navController.navigate(Routes.writeMemory(MemoryEntry.TYPE_VICTORY_NOTE))
                }
            )
        }

        // ══════════════════════════════════════════
        // QUICK CATCH & MEMORY BANK
        // ══════════════════════════════════════════

        composable(Routes.QUICK_CATCH) {
            QuickCatchScreen(
                currentStreak = currentStreak,
                whyQuitting = whyQuitting,
                relapseLetter = quickCatchRelapseLetter,
                victoryNote = quickCatchVictoryNote,
                randomMemory = quickCatchRandomMemory,
                dailyAyah = dailyAyah,
                activePlans = shieldPlans.filter { it.isActive },
                onCaughtIt = {
                    viewModel.logQuickCatch()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onNeedFullFlow = {
                    viewModel.resetCurrentEntry()
                    navController.navigate(Routes.BREATHING) {
                        popUpTo(Routes.QUICK_CATCH) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.MEMORY_BANK) {
            MemoryBankScreen(
                memories = allMemories,
                onAddMemory = {
                    navController.navigate(Routes.writeMemory(MemoryEntry.TYPE_MANUAL))
                },
                onTogglePin = { viewModel.toggleMemoryPin(it) },
                onDeleteMemory = { viewModel.deleteMemory(it) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.WRITE_MEMORY,
            arguments = listOf(navArgument("memoryType") { type = NavType.StringType })
        ) { backStackEntry ->
            val memoryType = backStackEntry.arguments?.getString("memoryType")
                ?: MemoryEntry.TYPE_MANUAL

            WriteMemoryScreen(
                memoryType = memoryType,
                currentStreak = currentStreak,
                onSave = { message, trigger ->
                    when (memoryType) {
                        MemoryEntry.TYPE_RELAPSE_LETTER -> viewModel.saveRelapseLetter(message, trigger)
                        MemoryEntry.TYPE_VICTORY_NOTE -> viewModel.saveVictoryNote(message)
                        else -> viewModel.saveManualMemory(message)
                    }
                    navController.popBackStack()
                },
                onSkip = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════
        // MORNING CHECK-IN
        // ══════════════════════════════════════════

        composable(Routes.MORNING_CHECK_IN) {
            val checkInMemory by viewModel.checkInMemory.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.loadCheckInMemory()
                viewModel.refreshDailyAyah()
            }

            MorningCheckInScreen(
                currentStreak = currentStreak,
                dailyAyah = dailyAyah,
                memoryBankEntry = checkInMemory,
                onComplete = { mood, risk, intention ->
                    viewModel.saveCheckIn(mood, risk, intention)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════
        // SHIELD PLANS
        // ══════════════════════════════════════════

        composable(Routes.SHIELD_PLANS) {
            LaunchedEffect(Unit) { viewModel.refreshShieldPlans() }
            ShieldPlanScreen(
                plans = shieldPlans,
                onEditPlan = { plan ->
                    viewModel.setEditingPlan(plan)
                    navController.navigate(Routes.editShieldPlan(plan.triggerId))
                },
                onAddCustomPlan = {
                    viewModel.setEditingPlan(null)
                    navController.navigate(Routes.NEW_CUSTOM_SHIELD)
                },
                onDeletePlan = { viewModel.deleteShieldPlan(it) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.EDIT_SHIELD_PLAN,
            arguments = listOf(navArgument("planId") { type = NavType.StringType })
        ) {
            EditShieldPlanScreen(
                plan = editingPlan,
                isNewCustom = false,
                onSave = { updatedPlan ->
                    viewModel.updateShieldPlan(updatedPlan)
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
                    viewModel.addCustomShieldPlan(name, emoji, description, steps, note)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════
        // EXPORT
        // ══════════════════════════════════════════

        composable(Routes.EXPORT) {
            ExportScreen(
                onExport = { startDate, endDate, periodLabel, options, onReady ->
                    viewModel.exportReport(startDate, endDate, periodLabel, options, onReady)
                },
                onPreview = { startDate, endDate, periodLabel, options, onReady ->
                    viewModel.previewExport(startDate, endDate, periodLabel, options, onReady)
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════
        // NOTIFICATION SETTINGS
        // ══════════════════════════════════════════

        composable(Routes.NOTIFICATION_SETTINGS) {
            LaunchedEffect(Unit) { viewModel.refreshNotificationSettings() }

            NotificationSettingsScreen(
                morningEnabled = morningEnabled,
                morningHour = morningHour,
                morningMinute = morningMinute,
                dangerHourEnabled = dangerHourEnabled,
                dangerHourDetected = dangerHourDetected,
                dangerHourStart = dangerHourStart,
                dangerHourEnd = dangerHourEnd,
                dangerAlertHour = dangerAlertHour,
                dangerAlertMinute = dangerAlertMinute,
                memoryResurfaceEnabled = memoryResurfaceEnabled,
                inactivityEnabled = inactivityEnabled,
                streakCelebrationEnabled = streakCelebrationEnabled,
                postRelapseEnabled = postRelapseEnabled,
                onMorningToggle = { viewModel.setMorningReminderEnabled(it) },
                onMorningTimeChange = { h, m -> viewModel.setMorningTime(h, m) },
                onDangerHourToggle = { viewModel.setDangerHourEnabled(it) },
                onMemoryResurfaceToggle = { viewModel.setMemoryResurfaceEnabled(it) },
                onInactivityToggle = { viewModel.setInactivityCheckEnabled(it) },
                onStreakCelebrationToggle = { viewModel.setStreakCelebrationEnabled(it) },
                onPostRelapseToggle = { viewModel.setPostRelapseEnabled(it) },
                onBack = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════
        // EXISTING SCREENS
        // ══════════════════════════════════════════

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
                onResetWithLetter = { reason, letter ->
                    viewModel.resetStreakWithLetter(reason, letter)
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

        composable(Routes.SETTINGS) {
            SettingsScreen(
                totalEntries = allEntries.size,
                totalRelapses = totalRelapses,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                onClearAllData = { viewModel.clearAllData() },
                onRelapseHistoryClick = { navController.navigate(Routes.RELAPSE_HISTORY) },
                onExportClick = { navController.navigate(Routes.EXPORT) },
                onNotificationSettingsClick = { navController.navigate(Routes.NOTIFICATION_SETTINGS) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}