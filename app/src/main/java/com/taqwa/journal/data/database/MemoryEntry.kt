package com.taqwa.journal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Memory Bank Entry - بنك الذاكرة
 *
 * Stores the user's own words from moments of clarity and pain.
 * These become the most powerful weapons against future urges
 * because they are the user's OWN voice, not generic quotes.
 *
 * Types:
 * - RELAPSE_LETTER: Written after relapse, when pain is fresh
 * - VICTORY_NOTE: Written after defeating an urge, when pride is strong
 * - MANUAL: Written anytime from the Memory Bank screen
 */
@Entity(tableName = "memory_entries")
data class MemoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // When this memory was written
    val timestamp: Long = System.currentTimeMillis(),

    // The type of memory: "RELAPSE_LETTER", "VICTORY_NOTE", "MANUAL"
    val type: String,

    // The actual message - user's own words
    val message: String,

    // Optional: what triggered the moment (for relapse letters)
    val trigger: String = "",

    // Optional: the streak that was active when this was written
    val streakAtTime: Int = 0,

    // Optional: urge strength at the time (for victory notes)
    val urgeStrengthAtTime: Int = 0,

    // Is this entry pinned/favorited (shows more often during intervention)
    val isPinned: Boolean = false
) {
    companion object {
        const val TYPE_RELAPSE_LETTER = "RELAPSE_LETTER"
        const val TYPE_VICTORY_NOTE = "VICTORY_NOTE"
        const val TYPE_MANUAL = "MANUAL"
    }
}