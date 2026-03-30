package com.taqwa.journal.data.utilities

import android.content.SharedPreferences
import androidx.compose.runtime.key

/**
 * Generic manager for boolean toggle settings in SharedPreferences.
 * Eliminates boilerplate for identical get/set pairs.
 *
 * Example usage in NotificationPreferences:
 * ```
 * private val morningToggle = PreferenceToggleManager(prefs, "morning_enabled", defaultTrue)
 * fun isMorningReminderEnabled(): Boolean = morningToggle.isEnabled()
 * fun setMorningReminderEnabled(enabled: Boolean) = morningToggle.setEnabled(enabled)
 * ```
 *
 * Reduces 2 functions per toggle (12 LOC × 6 = 72 LOC) to simple delegations.
 */
class PreferenceToggleManager(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean = true
) {

    /**
     * Get current toggle state.
     */
    fun isEnabled(): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    /**
     * Set toggle state.
     */
    fun setEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(key, enabled).apply()
    }

    /**
     * Toggle the current state (flip true/false).
     */
    fun toggle() {
        setEnabled(!isEnabled())
    }

    /**
     * Reset to default value.
     */
    fun reset() {
        setEnabled(defaultValue)
    }
}
