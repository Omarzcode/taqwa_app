package com.taqwa.journal.features.onboarding

import android.content.Context
import android.content.SharedPreferences

class OnboardingManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_onboarding", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_WHY_QUITTING = "why_quitting"
        private const val KEY_FIRST_PROMISE = "first_promise"
        private const val KEY_FIRST_DUA = "first_dua"
    }

    fun isOnboardingCompleted(): Boolean {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
    }

    fun setWhyQuitting(why: String) {
        prefs.edit().putString(KEY_WHY_QUITTING, why).apply()
    }

    fun getWhyQuitting(): String {
        return prefs.getString(KEY_WHY_QUITTING, "") ?: ""
    }

    fun addPromise(promise: String) {
        prefs.edit().putString(KEY_FIRST_PROMISE, promise).apply()
    }

    fun getPromise(): String {
        return prefs.getString(KEY_FIRST_PROMISE, "") ?: ""
    }

    fun addDua(dua: String) {
        prefs.edit().putString(KEY_FIRST_DUA, dua).apply()
    }

    fun getDua(): String {
        return prefs.getString(KEY_FIRST_DUA, "") ?: ""
    }
}
