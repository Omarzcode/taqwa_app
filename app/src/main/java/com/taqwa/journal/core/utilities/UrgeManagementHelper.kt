package com.taqwa.journal.core.utilities

/**
 * Helper functions for urge management flow.
 * Validates user inputs, provides suggestions, tracks patterns.
 */
object UrgeManagementHelper {
    
    enum class UrgeSeverity {
        MILD,      // 1-3
        MODERATE,  // 4-6
        SEVERE     // 7-10
    }

    fun getSeverityLevel(rating: Int): UrgeSeverity {
        return when {
            rating <= 3 -> UrgeSeverity.MILD
            rating <= 6 -> UrgeSeverity.MODERATE
            else -> UrgeSeverity.SEVERE
        }
    }

    fun validateUrgeRating(rating: Int): Boolean {
        return rating in 1..10
    }

    fun validateTriggerDescription(description: String): Boolean {
        return description.trim().length >= 5 && description.length <= 500
    }

    fun validateCopingStrategy(strategy: String): Boolean {
        return strategy.trim().length >= 3 && strategy.length <= 200
    }

    // Returns suggestions for what to do next based on current urge severity
    fun getSuggestedTools(severity: UrgeSeverity): List<String> {
        return when (severity) {
            UrgeSeverity.MILD -> listOf(
                "Use a distraction technique",
                "Text a support buddy",
                "Review your shield plan"
            )
            UrgeSeverity.MODERATE -> listOf(
                "Start breathing exercise",
                "Review Islamic reminders",
                "Go for a walk",
                "Call a trusted person"
            )
            UrgeSeverity.SEVERE -> listOf(
                "Breathing exercise NOW",
                "Call crisis support",
                "Leave the situation",
                "Review your future self"
            )
        }
    }

    fun categorizeCommonTriggers(triggers: List<String>): Map<String, List<String>> {
        val categories = mapOf(
            "emotional" to listOf("sad", "angry", "lonely", "stressed", "anxious", "depressed"),
            "social" to listOf("group", "party", "friends", "alone", "rejection", "social"),
            "environmental" to listOf("location", "place", "smell", "sight", "weather", "time"),
            "physical" to listOf("tired", "hungry", "restless", "pain", "discomfort"),
            "digital" to listOf("video", "message", "notification", "app", "online")
        )

        val result = mutableMapOf<String, List<String>>()
        for ((category, keywords) in categories) {
            result[category] = triggers.filter { trigger ->
                keywords.any { trigger.lowercase().contains(it) }
            }
        }
        return result
    }

    fun estimateSuccessRate(
        urgesSinceLapse: Int,
        daysSinceLapse: Int,
        copingStrategiesUsed: Int
    ): Float {
        // Simple formula: more urges resisted, more days clean, more strategies = higher success
        val urgeResistFactor = minOf(urgesSinceLapse.toFloat() / 10f, 1f)
        val cleanDaysFactor = minOf(daysSinceLapse.toFloat() / 30f, 1f)
        val strategyFactor = minOf(copingStrategiesUsed.toFloat() / 5f, 1f)

        return (urgeResistFactor * 0.4f + cleanDaysFactor * 0.4f + strategyFactor * 0.2f) * 100f
    }
}
