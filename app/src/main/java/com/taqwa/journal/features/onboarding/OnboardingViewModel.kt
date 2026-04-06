package com.taqwa.journal.features.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.onboarding.OnboardingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardingViewModel(
    application: Application,
    private val onboardingManager: OnboardingManager
) : AndroidViewModel(application) {

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    init {
        _isOnboardingCompleted.value = onboardingManager.isOnboardingCompleted()
    }

    fun completeOnboarding(whyQuitting: String, firstPromise: String, firstDua: String) {
        if (whyQuitting.isNotBlank()) onboardingManager.setWhyQuitting(whyQuitting)
        if (firstPromise.isNotBlank()) onboardingManager.addPromise(firstPromise)
        if (firstDua.isNotBlank()) onboardingManager.addDua(firstDua)
        onboardingManager.setOnboardingCompleted()
        _isOnboardingCompleted.value = true
    }

    fun setCompleted() {
        onboardingManager.setOnboardingCompleted()
        _isOnboardingCompleted.value = true
    }
}
