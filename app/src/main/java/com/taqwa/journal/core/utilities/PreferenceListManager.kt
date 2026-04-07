package com.taqwa.journal.core.utilities

import android.content.SharedPreferences

/**
 * Generic utility for managing a list of strings in SharedPreferences.
 * Eliminates duplication across PromiseManager, etc.
 */
class PreferenceListManager(
    private val prefs: SharedPreferences,
    private val key: String
) {
    fun getAll(): List<String> {
        val raw = prefs.getString(key, "") ?: ""
        return if (raw.isEmpty()) emptyList()
        else raw.split("||").filter { it.isNotEmpty() }
    }

    fun add(item: String) {
        val current = getAll().toMutableList()
        current.add(item.trim())
        save(current)
    }

    fun removeAt(index: Int) {
        val current = getAll().toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            save(current)
        }
    }

    private fun save(items: List<String>) {
        prefs.edit().putString(key, items.joinToString("||")).apply()
    }
}
