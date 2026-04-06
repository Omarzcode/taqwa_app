package com.taqwa.journal.core.utilities

import com.taqwa.journal.features.checkin.data.CheckInEntry
import com.taqwa.journal.features.memory.data.MemoryEntry

/**
 * Centralized validation constants and functions.
 * Single source of truth for all validation rules across the app.
 * Prevents duplication and makes it easy to adjust constraints globally.
 */
object Validators {

    // ════════════════════════════════════════
    // CONSTANTS - Update here for all usages
    // ════════════════════════════════════════

    const val URGE_STRENGTH_MIN = 1
    const val URGE_STRENGTH_MAX = 10
    const val MAX_FREETEXT_LENGTH = 10000
    const val MAX_MESSAGE_LENGTH = 10000
    const val MAX_INTENTION_LENGTH = 500
    const val DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}"

    const val HOUR_MIN = 0
    const val HOUR_MAX = 23
    const val MINUTE_MIN = 0
    const val MINUTE_MAX = 59

    // ════════════════════════════════════════
    // URGE STRENGTH VALIDATION
    // ════════════════════════════════════════

    fun isValidUrgeStrength(strength: Int): Boolean {
        return strength in URGE_STRENGTH_MIN..URGE_STRENGTH_MAX
    }

    fun requireValidUrgeStrength(strength: Int) {
        require(strength in URGE_STRENGTH_MIN..URGE_STRENGTH_MAX) {
            "Urge strength must be between $URGE_STRENGTH_MIN and $URGE_STRENGTH_MAX, got $strength"
        }
    }

    // ════════════════════════════════════════
    // STRING LENGTH VALIDATION
    // ════════════════════════════════════════

    fun isValidMessageLength(message: String): Boolean {
        return message.length in 1..MAX_MESSAGE_LENGTH
    }

    fun requireValidMessageLength(message: String) {
        require(message.isNotBlank()) { "Message cannot be empty" }
        require(message.length <= MAX_MESSAGE_LENGTH) {
            "Message exceeds maximum length of $MAX_MESSAGE_LENGTH characters"
        }
    }

    fun isValidFreeTextLength(text: String): Boolean {
        return text.length <= MAX_FREETEXT_LENGTH
    }

    fun requireValidFreeTextLength(text: String) {
        require(text.length <= MAX_FREETEXT_LENGTH) {
            "Text exceeds maximum length of $MAX_FREETEXT_LENGTH characters"
        }
    }

    fun isValidIntentionLength(intention: String): Boolean {
        return intention.length <= MAX_INTENTION_LENGTH
    }

    fun requireValidIntentionLength(intention: String) {
        require(intention.length <= MAX_INTENTION_LENGTH) {
            "Intention exceeds maximum length of $MAX_INTENTION_LENGTH characters"
        }
    }

    // ════════════════════════════════════════
    // ENUM VALIDATION
    // ════════════════════════════════════════

    fun isValidMemoryType(type: String): Boolean {
        return type in listOf(
            MemoryEntry.TYPE_RELAPSE_LETTER,
            MemoryEntry.TYPE_VICTORY_NOTE,
            MemoryEntry.TYPE_MANUAL
        )
    }

    fun requireValidMemoryType(type: String) {
        require(isValidMemoryType(type)) {
            "Invalid memory type '$type'. Must be one of: " +
                    "${MemoryEntry.TYPE_RELAPSE_LETTER}, " +
                    "${MemoryEntry.TYPE_VICTORY_NOTE}, " +
                    "${MemoryEntry.TYPE_MANUAL}"
        }
    }

    fun isValidMood(mood: String): Boolean {
        return mood in listOf(
            CheckInEntry.MOOD_GOOD,
            CheckInEntry.MOOD_OKAY,
            CheckInEntry.MOOD_LOW
        )
    }

    fun requireValidMood(mood: String) {
        require(isValidMood(mood)) {
            "Invalid mood '$mood'. Must be one of: " +
                    "${CheckInEntry.MOOD_GOOD}, " +
                    "${CheckInEntry.MOOD_OKAY}, " +
                    "${CheckInEntry.MOOD_LOW}"
        }
    }

    fun isValidRiskLevel(riskLevel: String): Boolean {
        return riskLevel in listOf(
            CheckInEntry.RISK_LOW,
            CheckInEntry.RISK_MEDIUM,
            CheckInEntry.RISK_HIGH
        )
    }

    fun requireValidRiskLevel(riskLevel: String) {
        require(isValidRiskLevel(riskLevel)) {
            "Invalid risk level '$riskLevel'. Must be one of: " +
                    "${CheckInEntry.RISK_LOW}, " +
                    "${CheckInEntry.RISK_MEDIUM}, " +
                    "${CheckInEntry.RISK_HIGH}"
        }
    }

    // ════════════════════════════════════════
    // DATE VALIDATION
    // ════════════════════════════════════════

    fun isValidDateFormat(date: String): Boolean {
        return date.matches(Regex(DATE_REGEX))
    }

    fun requireValidDateFormat(date: String) {
        require(isValidDateFormat(date)) {
            "Invalid date format '$date'. Expected format: yyyy-MM-dd"
        }
    }

    // ════════════════════════════════════════
    // TIME VALIDATION
    // ════════════════════════════════════════

    fun isValidHour(hour: Int): Boolean {
        return hour in HOUR_MIN..HOUR_MAX
    }

    fun requireValidHour(hour: Int) {
        require(isValidHour(hour)) {
            "Invalid hour $hour. Must be between $HOUR_MIN and $HOUR_MAX"
        }
    }

    fun isValidMinute(minute: Int): Boolean {
        return minute in MINUTE_MIN..MINUTE_MAX
    }

    fun requireValidMinute(minute: Int) {
        require(isValidMinute(minute)) {
            "Invalid minute $minute. Must be between $MINUTE_MIN and $MINUTE_MAX"
        }
    }

    // ════════════════════════════════════════
    // STREAK VALIDATION
    // ════════════════════════════════════════

    fun isValidStreak(streak: Int): Boolean {
        return streak >= 0
    }

    fun requireValidStreak(streak: Int) {
        require(isValidStreak(streak)) {
            "Streak cannot be negative, got $streak"
        }
    }
}
