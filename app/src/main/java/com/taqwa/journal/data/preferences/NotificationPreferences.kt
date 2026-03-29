package com.taqwa.journal.data.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Notification Preferences — إعدادات التنبيهات
 *
 * Stores user preferences for each notification type,
 * last app open time, cached memories for notifications,
 * and detected danger hour.
 */
class NotificationPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_notifications", Context.MODE_PRIVATE)

    companion object {
        // Toggle keys
        private const val KEY_MORNING_ENABLED = "morning_enabled"
        private const val KEY_DANGER_HOUR_ENABLED = "danger_hour_enabled"
        private const val KEY_MEMORY_RESURFACE_ENABLED = "memory_resurface_enabled"
        private const val KEY_INACTIVITY_ENABLED = "inactivity_enabled"
        private const val KEY_STREAK_CELEBRATION_ENABLED = "streak_celebration_enabled"
        private const val KEY_POST_RELAPSE_ENABLED = "post_relapse_enabled"

        // Morning reminder time
        private const val KEY_MORNING_HOUR = "morning_hour"
        private const val KEY_MORNING_MINUTE = "morning_minute"

        // Danger hour detection
        private const val KEY_DANGER_HOUR_START = "danger_hour_start"
        private const val KEY_DANGER_HOUR_END = "danger_hour_end"
        private const val KEY_DANGER_ALERT_HOUR = "danger_alert_hour"
        private const val KEY_DANGER_ALERT_MINUTE = "danger_alert_minute"
        private const val KEY_DANGER_HOUR_DETECTED = "danger_hour_detected"

        // App usage tracking
        private const val KEY_LAST_APP_OPEN = "last_app_open"

        // Cached memory for notification
        private const val KEY_CACHED_MEMORY = "cached_memory"

        // Last milestone notified
        private const val KEY_LAST_MILESTONE_NOTIFIED = "last_milestone_notified"
    }

    // ══════════════════════════════════════════
    // TOGGLE GETTERS/SETTERS
    // ══════════════════════════════════════════

    fun isMorningReminderEnabled(): Boolean = prefs.getBoolean(KEY_MORNING_ENABLED, true)
    fun setMorningReminderEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_MORNING_ENABLED, enabled).apply()
    }

    fun isDangerHourEnabled(): Boolean = prefs.getBoolean(KEY_DANGER_HOUR_ENABLED, true)
    fun setDangerHourEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DANGER_HOUR_ENABLED, enabled).apply()
    }

    fun isMemoryResurfaceEnabled(): Boolean = prefs.getBoolean(KEY_MEMORY_RESURFACE_ENABLED, true)
    fun setMemoryResurfaceEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_MEMORY_RESURFACE_ENABLED, enabled).apply()
    }

    fun isInactivityCheckEnabled(): Boolean = prefs.getBoolean(KEY_INACTIVITY_ENABLED, true)
    fun setInactivityCheckEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_INACTIVITY_ENABLED, enabled).apply()
    }

    fun isStreakCelebrationEnabled(): Boolean = prefs.getBoolean(KEY_STREAK_CELEBRATION_ENABLED, true)
    fun setStreakCelebrationEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_STREAK_CELEBRATION_ENABLED, enabled).apply()
    }

    fun isPostRelapseEnabled(): Boolean = prefs.getBoolean(KEY_POST_RELAPSE_ENABLED, true)
    fun setPostRelapseEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_POST_RELAPSE_ENABLED, enabled).apply()
    }

    // ══════════════════════════════════════════
    // MORNING REMINDER TIME
    // ══════════════════════════════════════════

    fun getMorningHour(): Int = prefs.getInt(KEY_MORNING_HOUR, 7)
    fun getMorningMinute(): Int = prefs.getInt(KEY_MORNING_MINUTE, 0)

    fun setMorningTime(hour: Int, minute: Int) {
        prefs.edit()
            .putInt(KEY_MORNING_HOUR, hour)
            .putInt(KEY_MORNING_MINUTE, minute)
            .apply()
    }

    // ══════════════════════════════════════════
    // DANGER HOUR
    // ══════════════════════════════════════════

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

    // ══════════════════════════════════════════
    // APP USAGE TRACKING
    // ══════════════════════════════════════════

    fun getLastAppOpen(): Long = prefs.getLong(KEY_LAST_APP_OPEN, System.currentTimeMillis())

    fun updateLastAppOpen() {
        prefs.edit().putLong(KEY_LAST_APP_OPEN, System.currentTimeMillis()).apply()
    }

    // ══════════════════════════════════════════
    // CACHED MEMORY FOR NOTIFICATIONS
    // ══════════════════════════════════════════

    fun getCachedMemory(): String = prefs.getString(KEY_CACHED_MEMORY, "") ?: ""

    fun setCachedMemory(memory: String) {
        prefs.edit().putString(KEY_CACHED_MEMORY, memory).apply()
    }

    // ══════════════════════════════════════════
    // MILESTONE TRACKING
    // ══════════════════════════════════════════

    fun getLastMilestoneNotified(): Int = prefs.getInt(KEY_LAST_MILESTONE_NOTIFIED, 0)

    fun setLastMilestoneNotified(days: Int) {
        prefs.edit().putInt(KEY_LAST_MILESTONE_NOTIFIED, days).apply()
    }

    // ══════════════════════════════════════════
    // CLEAR ALL
    // ══════════════════════════════════════════

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}