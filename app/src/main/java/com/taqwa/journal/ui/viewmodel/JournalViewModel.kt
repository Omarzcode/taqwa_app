package com.taqwa.journal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.data.database.JournalDatabase
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository

    // All entries from database
    val allEntries: Flow<List<JournalEntry>>

    // Urges defeated count
    val urgesDefeatedCount: Flow<Int>

    // Current entry being built during the flow
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

    init {
        val database = JournalDatabase.getDatabase(application)
        val dao = database.journalDao()
        repository = JournalRepository(dao)
        allEntries = repository.allEntries
        urgesDefeatedCount = repository.urgesDefeatedCount
    }

    // Update functions for each question
    fun updateSituationContext(text: String) {
        _currentSituationContext.value = text
    }

    fun toggleFeeling(feeling: String) {
        val current = _currentFeelings.value.toMutableList()
        if (current.contains(feeling)) {
            current.remove(feeling)
        } else {
            current.add(feeling)
        }
        _currentFeelings.value = current
    }

    fun toggleRealNeed(need: String) {
        val current = _currentRealNeed.value.toMutableList()
        if (current.contains(need)) {
            current.remove(need)
        } else {
            current.add(need)
        }
        _currentRealNeed.value = current
    }

    fun toggleAlternative(alternative: String) {
        val current = _currentAlternative.value.toMutableList()
        if (current.contains(alternative)) {
            current.remove(alternative)
        } else {
            current.add(alternative)
        }
        _currentAlternative.value = current
    }

    fun updateUrgeStrength(strength: Int) {
        _currentUrgeStrength.value = strength
    }

    fun updateFreeText(text: String) {
        _currentFreeText.value = text
    }

    // Save the complete entry to database
    fun saveEntry() {
        viewModelScope.launch {
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
        }
    }

    // Reset all current entry data for a new flow
    fun resetCurrentEntry() {
        _currentSituationContext.value = ""
        _currentFeelings.value = emptyList()
        _currentRealNeed.value = emptyList()
        _currentAlternative.value = emptyList()
        _currentUrgeStrength.value = 5
        _currentFreeText.value = ""
    }

    // Get single entry by ID
    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return repository.getEntryById(entryId)
    }

    // Delete entry
    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }
}