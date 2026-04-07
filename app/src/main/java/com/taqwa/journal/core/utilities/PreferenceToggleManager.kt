package com.taqwa.journal.core.utilities

import android.content.SharedPreferences

/**
 * Generic utility for managing a boolean toggle in SharedPreferences.
 * Eliminates duplication across NotificationPreferences, etc.
 */
class PreferenceToggleManager(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean = false
) {
    fun isEnabled(): Boolean = prefs.getBoolean(key, defaultValue)

    fun setEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(key, enabled).apply()
    }

    fun toggle(): Boolean {
        val newValue = !isEnabled()
        setEnabled(newValue)
        return newValue
    }
}
