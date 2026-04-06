package com.taqwa.journal.features.streak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.streak.data.StreakManager
import com.taqwa.journal.features.streak.data.RelapseRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StreakViewModel(
    application: Application,
    val streakManager: StreakManager
) : AndroidViewModel(application) {

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

    init {
        refreshStreakData()
        if (streakManager.isFirstTime()) {
            streakManager.startNewStreak()
        }
    }

    fun refreshStreakData() {
        _currentStreak.value = streakManager.getCurrentStreak()
        _longestStreak.value = streakManager.getLongestStreak()
        _streakStatus.value = streakManager.getStreakStatusText()
        _milestoneMessage.value = streakManager.getMilestoneMessage()
        _totalRelapses.value = streakManager.getTotalRelapses()
        _relapseHistory.value = streakManager.getRelapseHistory()
    }

    fun resetStreak(reason: String) {
        streakManager.resetStreak(reason)
        refreshStreakData()
    }

    fun dismissMilestone() {
        _milestoneMessage.value = null
    }

    fun getTotalRelapses(): Int = _totalRelapses.value

    fun getRelapseHistory(): List<RelapseRecord> = _relapseHistory.value
}
