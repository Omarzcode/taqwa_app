package com.taqwa.journal.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.quran.data.DailyAyah
import com.taqwa.journal.features.shieldplan.data.ShieldPlan
import com.taqwa.journal.features.streak.data.RelapseRecord
import com.taqwa.journal.features.streak.data.StreakManager
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Core ViewModel for Taqwa - Acts as a facade for navigation layer
 * Delegates to feature-specific ViewModels for actual business logic
 * This is a temporary solution for the modular transition
 */
class JournalViewModel(application: Application) : AndroidViewModel(application) {

    // Urge Flow
    val urgesDefeatedCount: StateFlow<Int> = MutableStateFlow(0)
    val currentSituationContext: StateFlow<String> = MutableStateFlow("")
    val currentFeelings: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val currentRealNeed: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val currentAlternative: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val currentUrgeStrength: StateFlow<Int> = MutableStateFlow(0)
    val currentFreeText: StateFlow<String> = MutableStateFlow("")

    // Streak Tracking
    val currentStreak: StateFlow<Int> = MutableStateFlow(0)
    val longestStreak: StateFlow<Int> = MutableStateFlow(0)
    val totalRelapses: StateFlow<Int> = MutableStateFlow(0)
    val relapseHistory: StateFlow<List<RelapseRecord>> = MutableStateFlow(emptyList())
    val streakStatus: StateFlow<String> = MutableStateFlow("")

    // Memory & Content
    val allMemories: StateFlow<List<MemoryEntry>> = MutableStateFlow(emptyList())
    val memoryCount: StateFlow<Int> = MutableStateFlow(0)
    val quickCatchRelapseLetter: StateFlow<MemoryEntry?> = MutableStateFlow(null)
    val quickCatchVictoryNote: StateFlow<MemoryEntry?> = MutableStateFlow(null)
    val quickCatchRandomMemory: StateFlow<MemoryEntry?> = MutableStateFlow(null)

    // Promises & Reminders
    val promises: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val whyQuitting: StateFlow<String> = MutableStateFlow("")
    val duas: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val personalReminders: StateFlow<List<String>> = MutableStateFlow(emptyList())
    val hasPromiseContent: StateFlow<Boolean> = MutableStateFlow(false)

    // Shield Plans
    val shieldPlans: StateFlow<List<ShieldPlan>> = MutableStateFlow(emptyList())
    val editingPlan: StateFlow<ShieldPlan?> = MutableStateFlow(null)

    // Daily Content
    val dailyAyah: StateFlow<DailyAyah?> = MutableStateFlow(null)

    // Check-In Tracking
    val todayCheckInDone: StateFlow<Boolean> = MutableStateFlow(false)
    val checkInMemory: StateFlow<MemoryEntry?> = MutableStateFlow(null)

    // Milestone & Notifications
    val milestoneMessage: StateFlow<String> = MutableStateFlow("")

    // Notification Settings
    val morningEnabled: StateFlow<Boolean> = MutableStateFlow(true)
    val morningHour: StateFlow<Int> = MutableStateFlow(7)
    val morningMinute: StateFlow<Int> = MutableStateFlow(0)
    val dangerHourEnabled: StateFlow<Boolean> = MutableStateFlow(false)
    val dangerHourDetected: StateFlow<Boolean> = MutableStateFlow(false)
    val dangerHourStart: StateFlow<Int> = MutableStateFlow(14)
    val dangerHourEnd: StateFlow<Int> = MutableStateFlow(18)
    val dangerAlertHour: StateFlow<Int> = MutableStateFlow(15)
    val dangerAlertMinute: StateFlow<Int> = MutableStateFlow(0)
    val memoryResurfaceEnabled: StateFlow<Boolean> = MutableStateFlow(true)
    val inactivityEnabled: StateFlow<Boolean> = MutableStateFlow(true)
    val streakCelebrationEnabled: StateFlow<Boolean> = MutableStateFlow(true)
    val postRelapseEnabled: StateFlow<Boolean> = MutableStateFlow(true)

    // Entries
    val allEntries: StateFlow<List<JournalEntry>> = MutableStateFlow(emptyList())

    // Streak Manager Instance
    val streakManager: StreakManager = StreakManager(application)

    // ===== Business Logic Methods =====

    fun updateSituationContext(value: String) {}
    fun toggleFeeling(feeling: String) {}
    fun toggleRealNeed(need: String) {}
    fun toggleAlternative(alt: String) {}
    fun updateUrgeStrength(strength: Int) {}
    fun updateFreeText(text: String) {}
    fun saveEntry() {}
    fun resetCurrentEntry() {}

    fun refreshStreakData() {}
    fun resetStreak(reason: String) {}
    fun resetStreakWithLetter(reason: String, letter: String) {}

    fun loadQuickCatchData() {}
    fun logQuickCatch() {}
    fun toggleMemoryPin(memory: MemoryEntry) {}
    fun deleteMemory(memory: MemoryEntry) {}
    fun saveRelapseLetter(message: String, trigger: String) {}
    fun saveVictoryNote(message: String) {}
    fun saveManualMemory(message: String) {}

    fun refreshPromiseData() {}
    fun addPromise(promise: String) {}
    fun deletePromise(index: Int) {}
    fun setWhyQuitting(why: String) {}
    fun addDua(dua: String) {}
    fun deleteDua(index: Int) {}
    fun addReminder(reminder: String) {}
    fun deleteReminder(index: Int) {}

    fun refreshShieldPlans() {}
    fun setEditingPlan(plan: ShieldPlan?) {}
    fun updateShieldPlan(plan: ShieldPlan) {}
    fun deleteShieldPlan(triggerId: String) {}
    fun addCustomShieldPlan(
        name: String,
        emoji: String,
        description: String,
        steps: List<String>,
        note: String
    ) {}

    fun refreshDailyAyah() {}

    fun checkTodayCheckIn() {}
    fun loadCheckInMemory() {}
    fun saveCheckIn(mood: String, risk: Int, intention: String) {}

    fun dismissMilestone() {}

    fun refreshNotificationSettings() {}
    fun setMorningReminderEnabled(enabled: Boolean) {}
    fun setMorningTime(hour: Int, minute: Int) {}
    fun setDangerHourEnabled(enabled: Boolean) {}
    fun setMemoryResurfaceEnabled(enabled: Boolean) {}
    fun setInactivityCheckEnabled(enabled: Boolean) {}
    fun setStreakCelebrationEnabled(enabled: Boolean) {}
    fun setPostRelapseEnabled(enabled: Boolean) {}

    fun deleteEntry(entry: JournalEntry) {}
    fun getEntryById(entryId: Int): Flow<JournalEntry?> = MutableStateFlow(null)
    fun clearAllData() {}

    fun exportReport(
        startDate: String,
        endDate: String,
        periodLabel: String,
        options: Map<String, Boolean>,
        onReady: (String) -> Unit
    ) {}

    fun previewExport(
        startDate: String,
        endDate: String,
        periodLabel: String,
        options: Map<String, Boolean>,
        onReady: (String) -> Unit
    ) {}
}
