package com.taqwa.journal.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.taqwa.journal.data.utilities.CsvPreferenceParser

/**
 * Shield Plan Manager - خطة الحماية
 *
 * Manages pre-written defense strategies for each trigger type.
 * Based on the user's own analysis of their 4 root triggers:
 *
 * 1. الفراغ + الكسل (Boredom/Emptiness)
 * 2. الغضب + الحزن (Anger/Sadness)
 * 3. البصر → الخاطرة → التفكير (Visual trigger chain)
 * 4. التذكير (Memory/Association trigger)
 * + Custom triggers
 *
 * Each plan contains:
 * - Trigger name and description
 * - Step-by-step action plan (up to 5 steps)
 * - Personal note to self
 * - Whether the plan is active/enabled
 */
class ShieldPlanManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_shield_plans", Context.MODE_PRIVATE)

    companion object {
        // Separator for lists within a plan
        private const val STEP_SEP = "|||"
        // Separator between plan fields
        private const val FIELD_SEP = ":::"
        // Separator between plans
        private const val PLAN_SEP = "<<<>>>"

        private const val KEY_PLANS = "shield_plans"

        // Default trigger types
        val DEFAULT_TRIGGERS = listOf(
            TriggerType(
                id = "boredom",
                emoji = "🛋️",
                name = "Boredom / Emptiness",
                nameAr = "الفراغ + الكسل",
                description = "When you're idle, have nothing to do, or feel empty and lazy",
                defaultSteps = listOf(
                    "Get up physically — change your position",
                    "Make wudu",
                    "Call or text a friend",
                    "Open Quran or a beneficial book",
                    "Go for a walk outside"
                )
            ),
            TriggerType(
                id = "emotions",
                emoji = "😔",
                name = "Anger / Sadness",
                nameAr = "الغضب + الحزن",
                description = "When you're upset, hurt, stressed, or need to escape emotional pain",
                defaultSteps = listOf(
                    "Recognize: You're sad/angry, not aroused",
                    "The urge is a lie — porn won't fix this feeling",
                    "Make dua for the real problem",
                    "Write what's actually bothering you",
                    "Exercise or do something physical"
                )
            ),
            TriggerType(
                id = "visual",
                emoji = "👁️",
                name = "Visual Trigger",
                nameAr = "البصر → الخاطرة",
                description = "When you see something that triggers a thought — online, in public, or in media",
                defaultSteps = listOf(
                    "Lower your gaze immediately — physically look away",
                    "Say: أعوذ بالله من الشيطان الرجيم",
                    "The thought is NOT a sin — engaging with it is",
                    "Close the app/browser/screen NOW",
                    "Open Taqwa and use Quick Catch"
                )
            ),
            TriggerType(
                id = "memory",
                emoji = "🔔",
                name = "Memory / Association",
                nameAr = "التذكير",
                description = "When a place, sound, name, or situation reminds you of the old habit",
                defaultSteps = listOf(
                    "Acknowledge it: 'This is a memory, not a need'",
                    "The memory will pass — it always does",
                    "Physically leave the place if possible",
                    "Replace the association — do something new here",
                    "Read your Memory Bank — remember the pain"
                )
            ),
            TriggerType(
                id = "late_night",
                emoji = "🌙",
                name = "Late Night / Alone",
                nameAr = "الليل + الوحدة",
                description = "When it's late at night, you're alone, and defenses are down",
                defaultSteps = listOf(
                    "Put the phone in another room — NOW",
                    "Make wudu and pray 2 rakaat",
                    "Read أذكار النوم",
                    "If you can't sleep, listen to Quran",
                    "Tomorrow morning you'll thank yourself"
                )
            )
        )
    }

    /**
     * Get all shield plans (default + custom)
     */
    fun getShieldPlans(): List<ShieldPlan> {
        val raw = prefs.getString(KEY_PLANS, null)

        if (raw.isNullOrEmpty()) {
            // Return default plans (not yet customized)
            return DEFAULT_TRIGGERS.map { trigger ->
                ShieldPlan(
                    triggerId = trigger.id,
                    emoji = trigger.emoji,
                    triggerName = trigger.name,
                    triggerNameAr = trigger.nameAr,
                    description = trigger.description,
                    steps = trigger.defaultSteps,
                    personalNote = "",
                    isActive = true,
                    isCustom = false
                )
            }
        }

        return raw.split(PLAN_SEP).mapNotNull { planStr ->
            parsePlan(planStr)
        }
    }

    /**
     * Save all shield plans
     */
    fun saveShieldPlans(plans: List<ShieldPlan>) {
        val raw = plans.joinToString(PLAN_SEP) { plan ->
            val fields = listOf(
                plan.triggerId,
                plan.emoji,
                plan.triggerName,
                plan.triggerNameAr,
                plan.description,
                plan.steps.joinToString(STEP_SEP),
                plan.personalNote,
                plan.isActive.toString(),
                plan.isCustom.toString()
            )
            // Use CsvPreferenceParser for safe serialization with quote handling
            // This prevents data corruption if fields contain the FIELD_SEP delimiter
            CsvPreferenceParser.serializeDelimitedLine(fields, FIELD_SEP)
        }
        prefs.edit().putString(KEY_PLANS, raw).apply()
    }

    /**
     * Update a specific shield plan
     */
    fun updatePlan(updatedPlan: ShieldPlan) {
        val plans = getShieldPlans().toMutableList()
        val index = plans.indexOfFirst { it.triggerId == updatedPlan.triggerId }
        if (index >= 0) {
            plans[index] = updatedPlan
        } else {
            plans.add(updatedPlan)
        }
        saveShieldPlans(plans)
    }

    /**
     * Add a custom trigger plan
     */
    fun addCustomPlan(name: String, emoji: String, description: String, steps: List<String>, note: String) {
        val plans = getShieldPlans().toMutableList()
        val id = "custom_${System.currentTimeMillis()}"
        plans.add(
            ShieldPlan(
                triggerId = id,
                emoji = emoji,
                triggerName = name,
                triggerNameAr = "",
                description = description,
                steps = steps,
                personalNote = note,
                isActive = true,
                isCustom = true
            )
        )
        saveShieldPlans(plans)
    }

    /**
     * Delete a custom plan (can't delete defaults)
     */
    fun deletePlan(triggerId: String) {
        val plans = getShieldPlans().toMutableList()
        plans.removeAll { it.triggerId == triggerId && it.isCustom }
        saveShieldPlans(plans)
    }

    /**
     * Get a specific plan by trigger ID
     */
    fun getPlanById(triggerId: String): ShieldPlan? {
        return getShieldPlans().find { it.triggerId == triggerId }
    }

    /**
     * Get active plans only
     */
    fun getActivePlans(): List<ShieldPlan> {
        return getShieldPlans().filter { it.isActive }
    }

    /**
     * Check if any plans have been customized
     */
    fun hasCustomizedPlans(): Boolean {
        return prefs.getString(KEY_PLANS, null) != null
    }

    private fun parsePlan(str: String): ShieldPlan? {
        return try {
            // Use CsvPreferenceParser for safe parsing with quote handling
            // This prevents corruption if fields contain the FIELD_SEP delimiter
            val fields = CsvPreferenceParser.parseDelimitedLine(str, FIELD_SEP)
            if (fields.size < 9) return null

            ShieldPlan(
                triggerId = fields[0],
                emoji = fields[1],
                triggerName = fields[2],
                triggerNameAr = fields[3],
                description = fields[4],
                steps = fields[5].split(STEP_SEP).filter { it.isNotBlank() },
                personalNote = fields[6],
                isActive = fields[7].toBooleanStrictOrNull() ?: true,
                isCustom = fields[8].toBooleanStrictOrNull() ?: false
            )
        } catch (e: Exception) {
            null
        }
    }
}

data class TriggerType(
    val id: String,
    val emoji: String,
    val name: String,
    val nameAr: String,
    val description: String,
    val defaultSteps: List<String>
)

data class ShieldPlan(
    val triggerId: String,
    val emoji: String,
    val triggerName: String,
    val triggerNameAr: String,
    val description: String,
    val steps: List<String>,
    val personalNote: String,
    val isActive: Boolean,
    val isCustom: Boolean
)