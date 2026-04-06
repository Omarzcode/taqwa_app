package com.taqwa.journal.features.notifications

import android.content.Context
import android.content.SharedPreferences
import com.taqwa.journal.core.utilities.PreferenceToggleManager

/**
 * Notification Preferences — إعدادات التنبيهات
 */
class NotificationPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_notifications", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_MORNING_ENABLED = "morning_enabled"
        private const val KEY_DANGER_HOUR_ENABLED = "danger_hour_enabled"
        private const val KEY_MEMORY_RESURFACE_ENABLED = "memory_resurface_enabled"
        private const val KEY_INACTIVITY_ENABLED = "inactivity_enabled"
        private const val KEY_STREAK_CELEBRATION_ENABLED = "streak_celebration_enabled"
        private const val KEY_POST_RELAPSE_ENABLED = "post_relapse_enabled"

        private const val KEY_MORNING_HOUR = "morning_hour"
        private const val KEY_MORNING_MINUTE = "morning_minute"

        private const val KEY_DANGER_HOUR_START = "danger_hour_start"
        private const val KEY_DANGER_HOUR_END = "danger_hour_end"
        private const val KEY_DANGER_ALERT_HOUR = "danger_alert_hour"
        private const val KEY_DANGER_ALERT_MINUTE = "danger_alert_minute"
        private const val KEY_DANGER_HOUR_DETECTED = "danger_hour_detected"

        private const val KEY_LAST_APP_OPEN = "last_app_open"
        private const val KEY_CACHED_MEMORY = "cached_memory"
        private const val KEY_LAST_MILESTONE_NOTIFIED = "last_milestone_notified"
    }

    private val morningToggle = PreferenceToggleManager(prefs, KEY_MORNING_ENABLED, true)
    private val dangerHourToggle = PreferenceToggleManager(prefs, KEY_DANGER_HOUR_ENABLED, true)
    private val memoryResurfaceToggle = PreferenceToggleManager(prefs, KEY_MEMORY_RESURFACE_ENABLED, true)
    private val inactivityToggle = PreferenceToggleManager(prefs, KEY_INACTIVITY_ENABLED, true)
    private val streakCelebrationToggle = PreferenceToggleManager(prefs, KEY_STREAK_CELEBRATION_ENABLED, true)
    private val postRelapseToggle = PreferenceToggleManager(prefs, KEY_POST_RELAPSE_ENABLED, true)

    fun isMorningReminderEnabled(): Boolean = morningToggle.isEnabled()
    fun setMorningReminderEnabled(enabled: Boolean) = morningToggle.setEnabled(enabled)

    fun isDangerHourEnabled(): Boolean = dangerHourToggle.isEnabled()
    fun setDangerHourEnabled(enabled: Boolean) = dangerHourToggle.setEnabled(enabled)

    fun isMemoryResurfaceEnabled(): Boolean = memoryResurfaceToggle.isEnabled()
    fun setMemoryResurfaceEnabled(enabled: Boolean) = memoryResurfaceToggle.setEnabled(enabled)

    fun isInactivityCheckEnabled(): Boolean = inactivityToggle.isEnabled()
    fun setInactivityCheckEnabled(enabled: Boolean) = inactivityToggle.setEnabled(enabled)

    fun isStreakCelebrationEnabled(): Boolean = streakCelebrationToggle.isEnabled()
    fun setStreakCelebrationEnabled(enabled: Boolean) = streakCelebrationToggle.setEnabled(enabled)

    fun isPostRelapseEnabled(): Boolean = postRelapseToggle.isEnabled()
    fun setPostRelapseEnabled(enabled: Boolean) = postRelapseToggle.setEnabled(enabled)

    fun getMorningHour(): Int = prefs.getInt(KEY_MORNING_HOUR, 7)
    fun getMorningMinute(): Int = prefs.getInt(KEY_MORNING_MINUTE, 0)

    fun setMorningTime(hour: Int, minute: Int) {
        prefs.edit()
            .putInt(KEY_MORNING_HOUR, hour)
            .putInt(KEY_MORNING_MINUTE, minute)
            .apply()
    }

    fun isDangerHourDetected(): Boolean = prefs.getBoolean(KEY_DANGER_HOUR_DETECTED, false)

    fun getDangerHourStart(): Int = prefs.getInt(KEY_DANGER_HOUR_START, -1)
    fun getDangerHourEnd(): Int = prefs.getInt(KEY_DANGER_HOUR_END, -1)
    fun getDangerAlertHour(): Int = prefs.getInt(KEY_DANGER_ALERT_HOUR, -1)
    fun getDangerAlertMinute(): Int = prefs.getInt(KEY_DANGER_ALERT_MINUTE, 30)

    fun setDangerHour(startHour: Int, endHour: Int, alertHour: Int, alertMinute: Int) {
        prefs.edit()
            .putBoolean(KEY_DANGER_HOUR_DETECTED, true)
            .putInt(KEY_DANGER_HOUR_START, startHour)
            .putInt(KEY_DANGER_HOUR_END, endHour)
            .putInt(KEY_DANGER_ALERT_HOUR, alertHour)
            .putInt(KEY_DANGER_ALERT_MINUTE, alertMinute)
            .apply()
    }

    fun clearDangerHour() {
        prefs.edit()
            .putBoolean(KEY_DANGER_HOUR_DETECTED, false)
            .remove(KEY_DANGER_HOUR_START)
            .remove(KEY_DANGER_HOUR_END)
            .remove(KEY_DANGER_ALERT_HOUR)
            .remove(KEY_DANGER_ALERT_MINUTE)
            .apply()
    }

    fun getLastAppOpen(): Long = prefs.getLong(KEY_LAST_APP_OPEN, System.currentTimeMillis())

    fun updateLastAppOpen() {
        prefs.edit().putLong(KEY_LAST_APP_OPEN, System.currentTimeMillis()).apply()
    }

    fun getCachedMemory(): String = prefs.getString(KEY_CACHED_MEMORY, "") ?: ""

    fun setCachedMemory(memory: String) {
        prefs.edit().putString(KEY_CACHED_MEMORY, memory).apply()
    }

    fun getLastMilestoneNotified(): Int = prefs.getInt(KEY_LAST_MILESTONE_NOTIFIED, 0)

    fun setLastMilestoneNotified(days: Int) {
        prefs.edit().putInt(KEY_LAST_MILESTONE_NOTIFIED, days).apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
