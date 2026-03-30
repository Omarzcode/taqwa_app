package com.taqwa.journal.data.preferences

import android.content.Context
import com.taqwa.journal.data.utilities.PreferenceListManager

/**
 * Manages user promises, duas, reminders, and motivation.
 * Refactored to use generic PreferenceListManager (eliminates ~90 LOC of duplication).
 */
class PromiseManager(context: Context) {

    private val prefs = context.getSharedPreferences("taqwa_promises", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_PROMISES = "promises"
        private const val KEY_WHY_QUITTING = "why_quitting"
        private const val KEY_DUAS = "duas"
        private const val KEY_REMINDERS = "personal_reminders"
    }

    // Delegate list management to generic utility
    private val promisesManager = PreferenceListManager(prefs, KEY_PROMISES)
    private val duasManager = PreferenceListManager(prefs, KEY_DUAS)
    private val remindersManager = PreferenceListManager(prefs, KEY_REMINDERS)

    // ════════════════════════════════════════
    // PROMISES
    // ════════════════════════════════════════

    fun getPromises(): List<String> = promisesManager.getAll()
    fun addPromise(promise: String) = promisesManager.add(promise)
    fun deletePromise(index: Int) = promisesManager.removeAt(index)

    // ════════════════════════════════════════
    // WHY I'M QUITTING
    // ════════════════════════════════════════

    fun getWhyQuitting(): String = prefs.getString(KEY_WHY_QUITTING, "") ?: ""
    fun setWhyQuitting(why: String) {
        prefs.edit().putString(KEY_WHY_QUITTING, why).apply()
    }

    // ════════════════════════════════════════
    // PERSONAL DUAS
    // ════════════════════════════════════════

    fun getDuas(): List<String> = duasManager.getAll()
    fun addDua(dua: String) = duasManager.add(dua)
    fun deleteDua(index: Int) = duasManager.removeAt(index)

    // ════════════════════════════════════════
    // PERSONAL REMINDERS
    // ════════════════════════════════════════

    fun getReminders(): List<String> = remindersManager.getAll()
    fun addReminder(reminder: String) = remindersManager.add(reminder)
    fun deleteReminder(index: Int) = remindersManager.removeAt(index)

    // ════════════════════════════════════════
    // HELPERS
    // ════════════════════════════════════════

    /**
     * Get a random personal reminder for intervention flow.
     */
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

    /**
     * Check if user has any content.
     */
    fun hasContent(): Boolean {
        return getPromises().isNotEmpty() ||
                getWhyQuitting().isNotEmpty() ||
                getDuas().isNotEmpty() ||
                getReminders().isNotEmpty()
    }
}