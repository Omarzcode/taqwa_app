package com.taqwa.journal.features.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.notifications.NotificationPreferences
import com.taqwa.journal.features.notifications.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationSettingsViewModel(
    application: Application,
    val notificationPreferences: NotificationPreferences,
    private val notificationScheduler: NotificationScheduler
) : AndroidViewModel(application) {

    private val _morningEnabled = MutableStateFlow(true)
    val morningEnabled: StateFlow<Boolean> = _morningEnabled.asStateFlow()

    private val _morningHour = MutableStateFlow(7)
    val morningHour: StateFlow<Int> = _morningHour.asStateFlow()

    private val _morningMinute = MutableStateFlow(0)
    val morningMinute: StateFlow<Int> = _morningMinute.asStateFlow()

    private val _dangerHourEnabled = MutableStateFlow(true)
    val dangerHourEnabled: StateFlow<Boolean> = _dangerHourEnabled.asStateFlow()

    private val _dangerHourDetected = MutableStateFlow(false)
    val dangerHourDetected: StateFlow<Boolean> = _dangerHourDetected.asStateFlow()

    private val _dangerHourStart = MutableStateFlow(-1)
    val dangerHourStart: StateFlow<Int> = _dangerHourStart.asStateFlow()

    private val _dangerHourEnd = MutableStateFlow(-1)
    val dangerHourEnd: StateFlow<Int> = _dangerHourEnd.asStateFlow()

    private val _dangerAlertHour = MutableStateFlow(-1)
    val dangerAlertHour: StateFlow<Int> = _dangerAlertHour.asStateFlow()

    private val _dangerAlertMinute = MutableStateFlow(30)
    val dangerAlertMinute: StateFlow<Int> = _dangerAlertMinute.asStateFlow()

    private val _memoryResurfaceEnabled = MutableStateFlow(true)
    val memoryResurfaceEnabled: StateFlow<Boolean> = _memoryResurfaceEnabled.asStateFlow()

    private val _inactivityEnabled = MutableStateFlow(true)
    val inactivityEnabled: StateFlow<Boolean> = _inactivityEnabled.asStateFlow()

    private val _streakCelebrationEnabled = MutableStateFlow(true)
    val streakCelebrationEnabled: StateFlow<Boolean> = _streakCelebrationEnabled.asStateFlow()

    private val _postRelapseEnabled = MutableStateFlow(true)
    val postRelapseEnabled: StateFlow<Boolean> = _postRelapseEnabled.asStateFlow()

    init {
        refreshNotificationSettings()
    }

    fun refreshNotificationSettings() {
        _morningEnabled.value = notificationPreferences.isMorningReminderEnabled()
        _morningHour.value = notificationPreferences.getMorningHour()
        _morningMinute.value = notificationPreferences.getMorningMinute()
        _dangerHourEnabled.value = notificationPreferences.isDangerHourEnabled()
        _dangerHourDetected.value = notificationPreferences.isDangerHourDetected()
        _dangerHourStart.value = notificationPreferences.getDangerHourStart()
        _dangerHourEnd.value = notificationPreferences.getDangerHourEnd()
        _dangerAlertHour.value = notificationPreferences.getDangerAlertHour()
        _dangerAlertMinute.value = notificationPreferences.getDangerAlertMinute()
        _memoryResurfaceEnabled.value = notificationPreferences.isMemoryResurfaceEnabled()
        _inactivityEnabled.value = notificationPreferences.isInactivityCheckEnabled()
        _streakCelebrationEnabled.value = notificationPreferences.isStreakCelebrationEnabled()
        _postRelapseEnabled.value = notificationPreferences.isPostRelapseEnabled()
    }

    fun setMorningReminderEnabled(enabled: Boolean, currentStreak: Int) {
        notificationPreferences.setMorningReminderEnabled(enabled)
        _morningEnabled.value = enabled
        notificationScheduler.scheduleMorningReminder(currentStreak)
    }

    fun setMorningTime(hour: Int, minute: Int, currentStreak: Int) {
        notificationPreferences.setMorningTime(hour, minute)
        _morningHour.value = hour
        _morningMinute.value = minute
        notificationScheduler.scheduleMorningReminder(currentStreak)
    }

    fun setDangerHourEnabled(enabled: Boolean, currentStreak: Int) {
        notificationPreferences.setDangerHourEnabled(enabled)
        _dangerHourEnabled.value = enabled
        notificationScheduler.scheduleDangerHourAlert(currentStreak)
    }

    fun setMemoryResurfaceEnabled(enabled: Boolean) {
        notificationPreferences.setMemoryResurfaceEnabled(enabled)
        _memoryResurfaceEnabled.value = enabled
        notificationScheduler.scheduleMemoryResurface()
    }

    fun setInactivityCheckEnabled(enabled: Boolean, currentStreak: Int) {
        notificationPreferences.setInactivityCheckEnabled(enabled)
        _inactivityEnabled.value = enabled
        notificationScheduler.scheduleInactivityCheck(currentStreak)
    }

    fun setStreakCelebrationEnabled(enabled: Boolean) {
        notificationPreferences.setStreakCelebrationEnabled(enabled)
        _streakCelebrationEnabled.value = enabled
    }

    fun setPostRelapseEnabled(enabled: Boolean) {
        notificationPreferences.setPostRelapseEnabled(enabled)
        _postRelapseEnabled.value = enabled
    }
}
