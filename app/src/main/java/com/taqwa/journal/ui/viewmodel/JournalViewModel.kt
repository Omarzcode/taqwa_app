package com.taqwa.journal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.data.database.JournalDatabase
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.preferences.PromiseManager
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.data.preferences.StreakManager
import com.taqwa.journal.data.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository
    val streakManager: StreakManager
    val promiseManager: PromiseManager

    val allEntries: Flow<List<JournalEntry>>
    val urgesDefeatedCount: Flow<Int>

    // Streak state
    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    private val _longestStreak = MutableStateFlow(0)
    val longestStreak: StateFlow<Int> = _longestStreak.asStateFlow()

    private val _streakStatus = MutableStateFlow("")
    val streakStatus: StateFlow<String> = _streakStatus.asStateFlow()

    private val _milestoneMessage = MutableStateFlow<String?>(null)
    val milestoneMessage: StateFlow<String?> = _milestoneMessage.asStateFlow()

    private val _totalRelapses = MutableStateFlow(0)
    val totalRelapses: StateFlow<Int> = _totalRelapses.asStateFlow()

    private val _relapseHistory = MutableStateFlow<List<RelapseRecord>>(emptyList())
    val relapseHistory: StateFlow<List<RelapseRecord>> = _relapseHistory.asStateFlow()

    // Promise Wall state
    private val _promises = MutableStateFlow<List<String>>(emptyList())
    val promises: StateFlow<List<String>> = _promises.asStateFlow()

    private val _whyQuitting = MutableStateFlow("")
    val whyQuitting: StateFlow<String> = _whyQuitting.asStateFlow()

    private val _duas = MutableStateFlow<List<String>>(emptyList())
    val duas: StateFlow<List<String>> = _duas.asStateFlow()

    private val _personalReminders = MutableStateFlow<List<String>>(emptyList())
    val personalReminders: StateFlow<List<String>> = _personalReminders.asStateFlow()

    private val _hasPromiseContent = MutableStateFlow(false)
    val hasPromiseContent: StateFlow<Boolean> = _hasPromiseContent.asStateFlow()

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

        streakManager = StreakManager(application)
        promiseManager = PromiseManager(application)

        if (streakManager.isFirstTime()) {
            streakManager.startNewStreak()
        }

        refreshStreakData()
        refreshPromiseData()
    }

    // Refresh streak data
    fun refreshStreakData() {
        _currentStreak.value = streakManager.getCurrentStreak()
        _longestStreak.value = streakManager.getLongestStreak()
        _streakStatus.value = streakManager.getStreakStatusText()
        _milestoneMessage.value = streakManager.getMilestoneMessage()
        _totalRelapses.value = streakManager.getTotalRelapses()
        _relapseHistory.value = streakManager.getRelapseHistory()
    }

    // Refresh promise wall data
    fun refreshPromiseData() {
        _promises.value = promiseManager.getPromises()
        _whyQuitting.value = promiseManager.getWhyQuitting()
        _duas.value = promiseManager.getDuas()
        _personalReminders.value = promiseManager.getReminders()
        _hasPromiseContent.value = promiseManager.hasContent()
    }

    // Streak functions
    fun resetStreak(reason: String) {
        streakManager.resetStreak(reason)
        refreshStreakData()
    }

    fun dismissMilestone() {
        _milestoneMessage.value = null
    }

    // Promise Wall functions
    fun addPromise(promise: String) {
        promiseManager.addPromise(promise)
        refreshPromiseData()
    }

    fun deletePromise(index: Int) {
        promiseManager.deletePromise(index)
        refreshPromiseData()
    }

    fun setWhyQuitting(why: String) {
        promiseManager.setWhyQuitting(why)
        refreshPromiseData()
    }

    fun addDua(dua: String) {
        promiseManager.addDua(dua)
        refreshPromiseData()
    }

    fun deleteDua(index: Int) {
        promiseManager.deleteDua(index)
        refreshPromiseData()
    }

    fun addReminder(reminder: String) {
        promiseManager.addReminder(reminder)
        refreshPromiseData()
    }

    fun deleteReminder(index: Int) {
        promiseManager.deleteReminder(index)
        refreshPromiseData()
    }

    // Entry functions
    fun updateSituationContext(text: String) {
        _currentSituationContext.value = text
    }

    fun toggleFeeling(feeling: String) {
        val current = _currentFeelings.value.toMutableList()
        if (current.contains(feeling)) current.remove(feeling)
        else current.add(feeling)
        _currentFeelings.value = current
    }

    fun toggleRealNeed(need: String) {
        val current = _currentRealNeed.value.toMutableList()
        if (current.contains(need)) current.remove(need)
        else current.add(need)
        _currentRealNeed.value = current
    }

    fun toggleAlternative(alternative: String) {
        val current = _currentAlternative.value.toMutableList()
        if (current.contains(alternative)) current.remove(alternative)
        else current.add(alternative)
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

    fun resetCurrentEntry() {
        _currentSituationContext.value = ""
        _currentFeelings.value = emptyList()
        _currentRealNeed.value = emptyList()
        _currentAlternative.value = emptyList()
        _currentUrgeStrength.value = 5
        _currentFreeText.value = ""
    }

    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return repository.getEntryById(entryId)
    }

    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }
}