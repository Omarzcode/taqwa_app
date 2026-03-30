package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.navigation.state.NotificationStateHolder
import com.taqwa.journal.ui.navigation.state.PromiseWallStateHolder
import com.taqwa.journal.ui.navigation.state.StreakStateHolder
import com.taqwa.journal.ui.screens.*
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.settingsSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    val streakState = StreakStateHolder(viewModel)
    val notificationState = NotificationStateHolder(viewModel)
    val promiseState = PromiseWallStateHolder(viewModel)

    composable(Routes.SETTINGS) {
        val allEntries by viewModel.allEntries.collectAsState(initial = emptyList())
        val streak = streakState.collectState()

        SettingsScreen(
            totalEntries = allEntries.size,
            totalRelapses = streak.totalRelapses,
            currentStreak = streak.currentStreak,
            longestStreak = streak.longestStreak,
            onClearAllData = { viewModel.clearAllData() },
            onRelapseHistoryClick = { navController.navigate(Routes.RELAPSE_HISTORY) },
            onExportClick = { navController.navigate(Routes.EXPORT) },
            onNotificationSettingsClick = { navController.navigate(Routes.NOTIFICATION_SETTINGS) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.NOTIFICATION_SETTINGS) {
        LaunchedEffect(Unit) { notificationState.refreshSettings() }
        val state = notificationState.collectState()

        NotificationSettingsScreen(
            morningEnabled = state.morningEnabled,
            morningHour = state.morningHour,
            morningMinute = state.morningMinute,
            dangerHourEnabled = state.dangerHourEnabled,
            dangerHourDetected = state.dangerHourDetected,
            dangerHourStart = state.dangerHourStart,
            dangerHourEnd = state.dangerHourEnd,
            dangerAlertHour = state.dangerAlertHour,
            dangerAlertMinute = state.dangerAlertMinute,
            memoryResurfaceEnabled = state.memoryResurfaceEnabled,
            inactivityEnabled = state.inactivityEnabled,
            streakCelebrationEnabled = state.streakCelebrationEnabled,
            postRelapseEnabled = state.postRelapseEnabled,
            onMorningToggle = { notificationState.setMorningEnabled(it) },
            onMorningTimeChange = { h, m -> notificationState.setMorningTime(h, m) },
            onDangerHourToggle = { notificationState.setDangerHourEnabled(it) },
            onMemoryResurfaceToggle = { notificationState.setMemoryResurfaceEnabled(it) },
            onInactivityToggle = { notificationState.setInactivityEnabled(it) },
            onStreakCelebrationToggle = { notificationState.setStreakCelebrationEnabled(it) },
            onPostRelapseToggle = { notificationState.setPostRelapseEnabled(it) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.PROMISE_WALL) {
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

    composable(Routes.MORNING_CHECK_IN) {
        val checkInMemory by viewModel.checkInMemory.collectAsState()
        val currentStreak by viewModel.currentStreak.collectAsState()
        val dailyAyah by viewModel.dailyAyah.collectAsState()

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
}