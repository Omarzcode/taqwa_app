package com.taqwa.journal.features.urgeflow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import com.taqwa.journal.features.urgeflow.data.UrgeFlowRepository
import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UrgeFlowViewModel(
    application: Application,
    private val repository: UrgeFlowRepository
) : AndroidViewModel(application) {

    val allEntries: Flow<List<JournalEntry>> = repository.allEntries
    val urgesDefeatedCount: Flow<Int> = repository.urgesDefeatedCount

    // Current entry being built during intervention flow
    private val _currentSituationContext = MutableStateFlow("")
    val currentSituationContext: StateFlow<String> = _currentSituationContext.asStateFlow()

    private val _currentFeelings = MutableStateFlow<List<String>>(emptyList())
    val currentFeelings: StateFlow<List<String>> = _currentFeelings.asStateFlow()

    private val _currentRealNeed = MutableStateFlow<List<String>>(emptyList())
    val currentRealNeed: StateFlow<List<String>> = _currentRealNeed.asStateFlow()

    private val _currentAlternative = MutableStateFlow<List<String>>(emptyList())
    val currentAlternative: StateFlow<List<String>> = _currentAlternative.asStateFlow()

    private val _currentUrgeStrength = MutableStateFlow(5)
    val currentUrgeStrength: StateFlow<Int> = _currentUrgeStrength.asStateFlow()

    private val _currentFreeText = MutableStateFlow("")
    val currentFreeText: StateFlow<String> = _currentFreeText.asStateFlow()

    fun updateSituationContext(text: String) {
        _currentSituationContext.value = text
    }

    fun toggleFeeling(feeling: String) {
        val current = _currentFeelings.value.toMutableList()
        if (current.contains(feeling)) current.remove(feeling) else current.add(feeling)
        _currentFeelings.value = current
    }

    fun toggleRealNeed(need: String) {
        val current = _currentRealNeed.value.toMutableList()
        if (current.contains(need)) current.remove(need) else current.add(need)
        _currentRealNeed.value = current
    }

    fun toggleAlternative(alternative: String) {
        val current = _currentAlternative.value.toMutableList()
        if (current.contains(alternative)) current.remove(alternative) else current.add(alternative)
        _currentAlternative.value = current
    }

    fun updateUrgeStrength(strength: Int) {
        _currentUrgeStrength.value = strength
    }

    fun updateFreeText(text: String) {
        _currentFreeText.value = text
    }

    fun saveEntry() {
        viewModelScope.launch {
            try {
                // Validate at least one question answered
                val hasContent = _currentSituationContext.value.isNotBlank() ||
                        _currentFeelings.value.isNotEmpty() ||
                        _currentRealNeed.value.isNotEmpty() ||
                        _currentAlternative.value.isNotEmpty() ||
                        _currentFreeText.value.isNotBlank()
                require(hasContent) { "Please answer at least one question before saving" }

                // Validate urge strength using centralized validator
                Validators.requireValidUrgeStrength(_currentUrgeStrength.value)

                val entry = JournalEntry(
                    situationContext = _currentSituationContext.value,
                    feelings = _currentFeelings.value.joinToString(","),
                    realNeed = _currentRealNeed.value.joinToString(","),
                    alternativeChosen = _currentAlternative.value.joinToString(","),
                    urgeStrength = _currentUrgeStrength.value,
                    freeText = _currentFreeText.value,
                    completed = true
                )
                repository.insertEntry(entry)
                resetCurrentEntry()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun resetCurrentEntry() {
        _currentSituationContext.value = ""
        _currentFeelings.value = emptyList()
        _currentRealNeed.value = emptyList()
        _currentAlternative.value = emptyList()
        _currentUrgeStrength.value = 5
        _currentFreeText.value = ""
    }

    fun getEntryById(entryId: Int): Flow<JournalEntry?> = repository.getEntryById(entryId)

    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch { repository.deleteEntry(entry) }
    }
}
