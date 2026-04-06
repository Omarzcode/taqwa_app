package com.taqwa.journal.core.utilities

/**
 * Manages breathing exercise timing, validation, and completion tracking.
 * Supports common breathing patterns: Box, 4-7-8, Coherent.
 */
object BreathingExerciseManager {
    
    enum class BreathingPattern(
        val displayName: String,
        val inhaleSeconds: Int,
        val holdSeconds: Int,
        val exhaleSeconds: Int,
        val cycles: Int
    ) {
        BOX(
            displayName = "Box Breathing",
            inhaleSeconds = 4,
            holdSeconds = 4,
            exhaleSeconds = 4,
            cycles = 5
        ),
        FOUR_SEVEN_EIGHT(
            displayName = "4-7-8 Breathing",
            inhaleSeconds = 4,
            holdSeconds = 7,
            exhaleSeconds = 8,
            cycles = 4
        ),
        COHERENT(
            displayName = "Coherent Breathing",
            inhaleSeconds = 5,
            holdSeconds = 0,
            exhaleSeconds = 5,
            cycles = 6
        );

        val totalDurationSeconds: Int
            get() = (inhaleSeconds + holdSeconds + exhaleSeconds) * cycles
    }

    fun getTotalDuration(pattern: BreathingPattern): Int = pattern.totalDurationSeconds

    fun isValidCompletion(pattern: BreathingPattern, completedCycles: Int): Boolean {
        return completedCycles >= pattern.cycles
    }

    fun calculateProgress(
        pattern: BreathingPattern,
        currentCycle: Int,
        phaseProgress: Float // 0-1 within current phase
    ): Float {
        val totalPhases = pattern.cycles * 3 // inhale, hold, exhale = 3 phases per cycle
        val completedPhases = (currentCycle - 1) * 3
        val currentPhaseProgress = phaseProgress
        return (completedPhases + currentPhaseProgress) / totalPhases
    }
}

data class BreathingSession(
    val id: String,
    val pattern: BreathingExerciseManager.BreathingPattern,
    val startTime: Long,
    val completedCycles: Int,
    val isCompleted: Boolean,
    val sessionNotes: String? = null
)
