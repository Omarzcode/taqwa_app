package com.taqwa.journal.data.utilities

import android.content.SharedPreferences
import androidx.compose.runtime.key

/**
 * Generic reusable CRUD manager for string lists in SharedPreferences.
 * Eliminates boilerplate code in preference managers (PromiseManager, etc).
 *
 * Usage:
 * ```
 * val promiseManager = PreferenceListManager(prefs, "promises_key")
 * promiseManager.getAll()    // returns List<String>
 * promiseManager.add("Be strong")
 * promiseManager.remove("Be strong")
 * ```
 *
 * Replaces ~90 lines of duplicate CRUD code across managers.
 */
class PreferenceListManager(
    private val prefs: SharedPreferences,
    private val storageKey: String
) {

    // ════════════════════════════════════════
    // READ OPERATIONS
    // ════════════════════════════════════════

    /**
     * Get all items from the list.
     * Returns empty list if key not found or value is empty.
     */
    fun getAll(): List<String> {
        val csvString = prefs.getString(storageKey, "") ?: ""
        return CsvPreferenceParser.parseList(csvString)
    }

    /**
     * Get item by index.
     * Returns null if index out of bounds.
     */
    fun getByIndex(index: Int): String? {
        val list = getAll()
        return if (index in list.indices) list[index] else null
    }

    /**
     * Get count of items.
     */
    fun count(): Int {
        return getAll().size
    }

    /**
     * Check if list contains item.
     */
    fun contains(item: String): Boolean {
        return item in getAll()
    }

    // ════════════════════════════════════════
    // WRITE OPERATIONS
    // ════════════════════════════════════════

    /**
     * Add item to the list.
     * Does not add duplicates (idempotent).
     */
    fun add(item: String) {
        require(item.isNotBlank()) { "Cannot add empty item" }

        val csvString = prefs.getString(storageKey, "") ?: ""
        val updated = CsvPreferenceParser.addToList(csvString, item)
        save(updated)
    }

    /**
     * Add multiple items at once.
     */
    fun addAll(items: List<String>) {
        items.forEach { require(it.isNotBlank()) { "Cannot add empty item" } }

        val csvString = prefs.getString(storageKey, "") ?: ""
        var updated = csvString
        for (item in items) {
            updated = CsvPreferenceParser.addToList(updated, item)
        }
        save(updated)
    }

    /**
     * Remove item by exact value match.
     */
    fun remove(item: String) {
        val csvString = prefs.getString(storageKey, "") ?: ""
        val updated = CsvPreferenceParser.removeFromList(csvString, item)
        save(updated)
    }

    /**
     * Remove item by index.
     */
    fun removeAt(index: Int) {
        val csvString = prefs.getString(storageKey, "") ?: ""
        val updated = CsvPreferenceParser.removeFromListByIndex(csvString, index)
        save(updated)
    }

    /**
     * Update item (replace old value with new value).
     */
    fun update(oldValue: String, newValue: String) {
        require(newValue.isNotBlank()) { "Cannot update with empty value" }

        val csvString = prefs.getString(storageKey, "") ?: ""
        val updated = CsvPreferenceParser.updateInList(csvString, oldValue, newValue)
        save(updated)
    }

    /**
     * Clear all items from the list.
     */
    fun clear() {
        prefs.edit().remove(storageKey).apply()
    }

    /**
     * Replace entire list with new contents.
     */
    fun setAll(newItems: List<String>) {
        newItems.forEach { require(it.isNotBlank()) { "Cannot set empty item" } }
        val csvString = CsvPreferenceParser.serializeList(newItems)
        save(csvString)
    }

    // ════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════

    /**
     * Internal save operation.
     * Always use this for consistency.
     */
    private fun save(csvString: String) {
        prefs.edit().putString(storageKey, csvString).apply()
    }
}
