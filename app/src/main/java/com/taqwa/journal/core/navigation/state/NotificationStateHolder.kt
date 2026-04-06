package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.core.viewmodel.JournalViewModel

class NotificationStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): NotificationScreenState {
        val morningEnabled = viewModel.morningEnabled.collectAsState().value
        val morningHour = viewModel.morningHour.collectAsState().value
        val morningMinute = viewModel.morningMinute.collectAsState().value
        val dangerHourEnabled = viewModel.dangerHourEnabled.collectAsState().value
        val dangerHourDetected = viewModel.dangerHourDetected.collectAsState().value
        val dangerHourStart = viewModel.dangerHourStart.collectAsState().value
        val dangerHourEnd = viewModel.dangerHourEnd.collectAsState().value
        val dangerAlertHour = viewModel.dangerAlertHour.collectAsState().value
        val dangerAlertMinute = viewModel.dangerAlertMinute.collectAsState().value
        val memoryResurfaceEnabled = viewModel.memoryResurfaceEnabled.collectAsState().value
        val inactivityEnabled = viewModel.inactivityEnabled.collectAsState().value
        val streakCelebrationEnabled = viewModel.streakCelebrationEnabled.collectAsState().value
        val postRelapseEnabled = viewModel.postRelapseEnabled.collectAsState().value

        return NotificationScreenState(
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
            postRelapseEnabled = postRelapseEnabled
        )
    }

    fun refreshSettings() = viewModel.refreshNotificationSettings()
    fun setMorningEnabled(enabled: Boolean) = viewModel.setMorningReminderEnabled(enabled)
    fun setMorningTime(hour: Int, minute: Int) = viewModel.setMorningTime(hour, minute)
    fun setDangerHourEnabled(enabled: Boolean) = viewModel.setDangerHourEnabled(enabled)
    fun setMemoryResurfaceEnabled(enabled: Boolean) = viewModel.setMemoryResurfaceEnabled(enabled)
    fun setInactivityEnabled(enabled: Boolean) = viewModel.setInactivityCheckEnabled(enabled)
    fun setStreakCelebrationEnabled(enabled: Boolean) = viewModel.setStreakCelebrationEnabled(enabled)
    fun setPostRelapseEnabled(enabled: Boolean) = viewModel.setPostRelapseEnabled(enabled)
}

data class NotificationScreenState(
    val morningEnabled: Boolean,
    val morningHour: Int,
    val morningMinute: Int,
    val dangerHourEnabled: Boolean,
    val dangerHourDetected: Boolean,
    val dangerHourStart: Int,
    val dangerHourEnd: Int,
    val dangerAlertHour: Int,
    val dangerAlertMinute: Int,
    val memoryResurfaceEnabled: Boolean,
    val inactivityEnabled: Boolean,
    val streakCelebrationEnabled: Boolean,
    val postRelapseEnabled: Boolean
)
