package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class NotificationStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): NotificationScreenState {
        val morningEnabled by viewModel.morningEnabled.collectAsState()
        val morningHour by viewModel.morningHour.collectAsState()
        val morningMinute by viewModel.morningMinute.collectAsState()
        val eveningEnabled by viewModel.eveningEnabled.collectAsState()
        val eveningHour by viewModel.eveningHour.collectAsState()
        val eveningMinute by viewModel.eveningMinute.collectAsState()
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
        val fridayEnabled by viewModel.fridayEnabled.collectAsState()

        return NotificationScreenState(
            morningEnabled = morningEnabled,
            morningHour = morningHour,
            morningMinute = morningMinute,
            eveningEnabled = eveningEnabled,
            eveningHour = eveningHour,
            eveningMinute = eveningMinute,
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
            fridayEnabled = fridayEnabled
        )
    }

    fun refreshSettings() = viewModel.refreshNotificationSettings()
    fun setMorningEnabled(enabled: Boolean) = viewModel.setMorningReminderEnabled(enabled)
    fun setMorningTime(hour: Int, minute: Int) = viewModel.setMorningTime(hour, minute)
    fun setEveningEnabled(enabled: Boolean) = viewModel.setEveningReminderEnabled(enabled)
    fun setEveningTime(hour: Int, minute: Int) = viewModel.setEveningTime(hour, minute)
    fun setDangerHourEnabled(enabled: Boolean) = viewModel.setDangerHourEnabled(enabled)
    fun setMemoryResurfaceEnabled(enabled: Boolean) = viewModel.setMemoryResurfaceEnabled(enabled)
    fun setInactivityEnabled(enabled: Boolean) = viewModel.setInactivityCheckEnabled(enabled)
    fun setStreakCelebrationEnabled(enabled: Boolean) = viewModel.setStreakCelebrationEnabled(enabled)
    fun setPostRelapseEnabled(enabled: Boolean) = viewModel.setPostRelapseEnabled(enabled)
    fun setFridayEnabled(enabled: Boolean) = viewModel.setFridayReminderEnabled(enabled)
}

data class NotificationScreenState(
    val morningEnabled: Boolean,
    val morningHour: Int,
    val morningMinute: Int,
    val eveningEnabled: Boolean,
    val eveningHour: Int,
    val eveningMinute: Int,
    val dangerHourEnabled: Boolean,
    val dangerHourDetected: Boolean,
    val dangerHourStart: Int,
    val dangerHourEnd: Int,
    val dangerAlertHour: Int,
    val dangerAlertMinute: Int,
    val memoryResurfaceEnabled: Boolean,
    val inactivityEnabled: Boolean,
    val streakCelebrationEnabled: Boolean,
    val postRelapseEnabled: Boolean,
    val fridayEnabled: Boolean
)