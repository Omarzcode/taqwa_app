package com.taqwa.journal.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PromiseManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_promises", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_PROMISES = "promises"
        private const val KEY_WHY_QUITTING = "why_quitting"
        private const val KEY_DUAS = "duas"
        private const val KEY_REMINDERS = "personal_reminders"
    }

    // ========================
    // PROMISES
    // ========================
    fun getPromises(): List<String> {
        val raw = prefs.getString(KEY_PROMISES, "") ?: ""
        if (raw.isEmpty()) return emptyList()
        return raw.split("|||").filter { it.isNotEmpty() }
    }

    fun addPromise(promise: String) {
        val current = getPromises().toMutableList()
        current.add(promise)
        prefs.edit().putString(KEY_PROMISES, current.joinToString("|||")).apply()
    }

    fun deletePromise(index: Int) {
        val current = getPromises().toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            prefs.edit().putString(KEY_PROMISES, current.joinToString("|||")).apply()
        }
    }

    // ========================
    // WHY I'M QUITTING
    // ========================
    fun getWhyQuitting(): String {
        return prefs.getString(KEY_WHY_QUITTING, "") ?: ""
    }

    fun setWhyQuitting(why: String) {
        prefs.edit().putString(KEY_WHY_QUITTING, why).apply()
    }

    // ========================
    // PERSONAL DUAS
    // ========================
    fun getDuas(): List<String> {
        val raw = prefs.getString(KEY_DUAS, "") ?: ""
        if (raw.isEmpty()) return emptyList()
        return raw.split("|||").filter { it.isNotEmpty() }
    }

    fun addDua(dua: String) {
        val current = getDuas().toMutableList()
        current.add(dua)
        prefs.edit().putString(KEY_DUAS, current.joinToString("|||")).apply()
    }

    fun deleteDua(index: Int) {
        val current = getDuas().toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            prefs.edit().putString(KEY_DUAS, current.joinToString("|||")).apply()
        }
    }

    // ========================
    // PERSONAL REMINDERS
    // ========================
    fun getReminders(): List<String> {
        val raw = prefs.getString(KEY_REMINDERS, "") ?: ""
        if (raw.isEmpty()) return emptyList()
        return raw.split("|||").filter { it.isNotEmpty() }
    }

    fun addReminder(reminder: String) {
        val current = getReminders().toMutableList()
        current.add(reminder)
        prefs.edit().putString(KEY_REMINDERS, current.joinToString("|||")).apply()
    }

    fun deleteReminder(index: Int) {
        val current = getReminders().toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            prefs.edit().putString(KEY_REMINDERS, current.joinToString("|||")).apply()
        }
    }

    // Get a random personal reminder for intervention flow
    fun getRandomReminder(): String? {
        val all = mutableListOf<String>()
        all.addAll(getPromises().map { "💪 Your promise: \"$it\"" })
        all.addAll(getReminders().map { "📝 You wrote: \"$it\"" })
        val why = getWhyQuitting()
        if (why.isNotEmpty()) {
            all.add("🎯 You're doing this because: \"$why\"")
        }
        all.addAll(getDuas().map { "🤲 Your dua: \"$it\"" })

        return if (all.isNotEmpty()) all.random() else null
    }

    // Check if user has any content
    fun hasContent(): Boolean {
        return getPromises().isNotEmpty() ||
                getWhyQuitting().isNotEmpty() ||
                getDuas().isNotEmpty() ||
                getReminders().isNotEmpty()
    }
}