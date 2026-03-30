package com.taqwa.journal.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.data.database.CheckInEntry
import com.taqwa.journal.data.database.JournalDatabase
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.export.ExportManager
import com.taqwa.journal.data.export.ExportOptions
import com.taqwa.journal.data.export.ReportData
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.data.preferences.DailyQuranManager
import com.taqwa.journal.data.preferences.NotificationPreferences
import com.taqwa.journal.data.preferences.OnboardingManager
import com.taqwa.journal.data.preferences.PromiseManager
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.data.preferences.ShieldPlan
import com.taqwa.journal.data.preferences.ShieldPlanManager
import com.taqwa.journal.data.preferences.StreakManager
import com.taqwa.journal.data.repository.JournalRepository
import com.taqwa.journal.data.utilities.Validators
import com.taqwa.journal.notification.NotificationScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository
    val streakManager: StreakManager
    val promiseManager: PromiseManager
    private val dailyQuranManager: DailyQuranManager
    private val onboardingManager: OnboardingManager
    val exportManager: ExportManager
    val notificationPreferences: NotificationPreferences
    private val notificationScheduler: NotificationScheduler

    val allEntries: Flow<List<JournalEntry>>
    val urgesDefeatedCount: Flow<Int>

    // ══════════════════════════════════════════
    // EXISTING STATE
    // ══════════════════════════════════════════

    private val _isOnboardingCompleted = MutableStateFlow(true)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

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

    private val _dailyAyah = MutableStateFlow<DailyAyah?>(null)
    val dailyAyah: StateFlow<DailyAyah?> = _dailyAyah.asStateFlow()

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

    // ══════════════════════════════════════════
    // MEMORY BANK STATE - بنك الذاكرة
    // ══════════════════════════════════════════

    val allMemories: Flow<List<MemoryEntry>> = MutableStateFlow<List<MemoryEntry>>(emptyList())
    val memoryCount: Flow<Int> = MutableStateFlow(0)

    private val _quickCatchRelapseLetter = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRelapseLetter: StateFlow<MemoryEntry?> = _quickCatchRelapseLetter.asStateFlow()

    private val _quickCatchVictoryNote = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchVictoryNote: StateFlow<MemoryEntry?> = _quickCatchVictoryNote.asStateFlow()

    private val _quickCatchRandomMemory = MutableStateFlow<MemoryEntry?>(null)
    val quickCatchRandomMemory: StateFlow<MemoryEntry?> = _quickCatchRandomMemory.asStateFlow()

    private val _interventionMemory = MutableStateFlow<MemoryEntry?>(null)
    val interventionMemory: StateFlow<MemoryEntry?> = _interventionMemory.asStateFlow()

    private val _quickCatchCount = MutableStateFlow(0)
    val quickCatchCount: StateFlow<Int> = _quickCatchCount.asStateFlow()

    // ══════════════════════════════════════════
    // MORNING CHECK-IN STATE - الورد الصباحي
    // ══════════════════════════════════════════

    private val _todayCheckInDone = MutableStateFlow(false)
    val todayCheckInDone: StateFlow<Boolean> = _todayCheckInDone.asStateFlow()

    private val _checkInMemory = MutableStateFlow<MemoryEntry?>(null)
    val checkInMemory: StateFlow<MemoryEntry?> = _checkInMemory.asStateFlow()

    val allCheckIns: Flow<List<CheckInEntry>> = MutableStateFlow<List<CheckInEntry>>(emptyList())
    val checkInCount: Flow<Int> = MutableStateFlow(0)

    // ══════════════════════════════════════════
    // SHIELD PLAN STATE - خطة الحماية
    // ══════════════════════════════════════════

    val shieldPlanManager: ShieldPlanManager

    private val _shieldPlans = MutableStateFlow<List<ShieldPlan>>(emptyList())
    val shieldPlans: StateFlow<List<ShieldPlan>> = _shieldPlans.asStateFlow()

    private val _editingPlan = MutableStateFlow<ShieldPlan?>(null)
    val editingPlan: StateFlow<ShieldPlan?> = _editingPlan.asStateFlow()

    // ══════════════════════════════════════════
    // NOTIFICATION STATE
    // ══════════════════════════════════════════

    private val _morningEnabled = MutableStateFlow(true)
    val morningEnabled: StateFlow<Boolean> = _morningEnabled.asStateFlow()

    private val _morningHour = MutableStateFlow(7)
    val morningHour: StateFlow<Int> = _morningHour.asStateFlow()

    private val _morningMinute = MutableStateFlow(0)
    val morningMinute: StateFlow<Int> = _morningMinute.asStateFlow()

    private val _dangerHourEnabled = MutableStateFlow(true)
    val dangerHourEnabled: StateFlow<Boolean> = _dangerHourEnabled.asStateFlow()

    private val _dangerHourDetected = MutableStateFlow(false)
    val dangerHourDetected: StateFlow<Boolean> = _dangerHourDetected.asStateFlow()

    private val _dangerHourStart = MutableStateFlow(-1)
    val dangerHourStart: StateFlow<Int> = _dangerHourStart.asStateFlow()

    private val _dangerHourEnd = MutableStateFlow(-1)
    val dangerHourEnd: StateFlow<Int> = _dangerHourEnd.asStateFlow()

    private val _dangerAlertHour = MutableStateFlow(-1)
    val dangerAlertHour: StateFlow<Int> = _dangerAlertHour.asStateFlow()

    private val _dangerAlertMinute = MutableStateFlow(30)
    val dangerAlertMinute: StateFlow<Int> = _dangerAlertMinute.asStateFlow()

    private val _memoryResurfaceEnabled = MutableStateFlow(true)
    val memoryResurfaceEnabled: StateFlow<Boolean> = _memoryResurfaceEnabled.asStateFlow()

    private val _inactivityEnabled = MutableStateFlow(true)
    val inactivityEnabled: StateFlow<Boolean> = _inactivityEnabled.asStateFlow()

    private val _streakCelebrationEnabled = MutableStateFlow(true)
    val streakCelebrationEnabled: StateFlow<Boolean> = _streakCelebrationEnabled.asStateFlow()

    private val _postRelapseEnabled = MutableStateFlow(true)
    val postRelapseEnabled: StateFlow<Boolean> = _postRelapseEnabled.asStateFlow()

    init {
        val database = JournalDatabase.getDatabase(application)
        val dao = database.journalDao()
        repository = JournalRepository(dao)
        allEntries = repository.allEntries
        urgesDefeatedCount = repository.urgesDefeatedCount

        streakManager = StreakManager(application)
        promiseManager = PromiseManager(application)
        dailyQuranManager = DailyQuranManager(application)
        onboardingManager = OnboardingManager(application)
        exportManager = ExportManager(application)
        notificationPreferences = NotificationPreferences(application)
        notificationScheduler = NotificationScheduler(application)

        _isOnboardingCompleted.value = onboardingManager.isOnboardingCompleted()

        if (streakManager.isFirstTime()) {
            streakManager.startNewStreak()
        }

        refreshStreakData()
        refreshPromiseData()
        refreshDailyAyah()
        refreshNotificationSettings()

        // Initialize memory bank flows
        viewModelScope.launch {
            repository.allMemories.collect { memories ->
                (allMemories as MutableStateFlow).value = memories
            }
        }
        viewModelScope.launch {
            repository.memoryCount.collect { count ->
                (memoryCount as MutableStateFlow).value = count
            }
        }

        // Initialize check-in state
        viewModelScope.launch {
            repository.allCheckIns.collect { checkIns ->
                (allCheckIns as MutableStateFlow).value = checkIns
            }
        }
        viewModelScope.launch {
            repository.checkInCount.collect { count ->
                (checkInCount as MutableStateFlow).value = count
            }
        }

        checkTodayCheckIn()
        shieldPlanManager = ShieldPlanManager(application)
        refreshShieldPlans()

        // Cache a random memory for notifications
        cacheMemoryForNotification()

        // Calculate danger hour from existing data
        updateDangerHourFromData()
    }

    // ══════════════════════════════════════════
    // EXISTING FUNCTIONS (unchanged)
    // ══════════════════════════════════════════

    fun completeOnboarding(whyQuitting: String, firstPromise: String, firstDua: String) {
        if (whyQuitting.isNotBlank()) promiseManager.setWhyQuitting(whyQuitting)
        if (firstPromise.isNotBlank()) promiseManager.addPromise(firstPromise)
        if (firstDua.isNotBlank()) promiseManager.addDua(firstDua)
        onboardingManager.setOnboardingCompleted()
        _isOnboardingCompleted.value = true
        refreshPromiseData()
    }

    fun refreshStreakData() {
        _currentStreak.value = streakManager.getCurrentStreak()
        _longestStreak.value = streakManager.getLongestStreak()
        _streakStatus.value = streakManager.getStreakStatusText()
        _milestoneMessage.value = streakManager.getMilestoneMessage()
        _totalRelapses.value = streakManager.getTotalRelapses()
        _relapseHistory.value = streakManager.getRelapseHistory()
    }

    fun refreshPromiseData() {
        _promises.value = promiseManager.getPromises()
        _whyQuitting.value = promiseManager.getWhyQuitting()
        _duas.value = promiseManager.getDuas()
        _personalReminders.value = promiseManager.getReminders()
        _hasPromiseContent.value = promiseManager.hasContent()
    }

    fun refreshDailyAyah() {
        _dailyAyah.value = dailyQuranManager.getTodaysAyah()
    }

    fun resetStreak(reason: String) {
        streakManager.resetStreak(reason)
        refreshStreakData()
        // Schedule post-relapse follow-up notification
        notificationScheduler.schedulePostRelapseFollowUp()
    }

    fun dismissMilestone() { _milestoneMessage.value = null }

    fun addPromise(promise: String) { promiseManager.addPromise(promise); refreshPromiseData() }
    fun deletePromise(index: Int) { promiseManager.deletePromise(index); refreshPromiseData() }
    fun setWhyQuitting(why: String) { promiseManager.setWhyQuitting(why); refreshPromiseData() }
    fun addDua(dua: String) { promiseManager.addDua(dua); refreshPromiseData() }
    fun deleteDua(index: Int) { promiseManager.deleteDua(index); refreshPromiseData() }
    fun addReminder(reminder: String) { promiseManager.addReminder(reminder); refreshPromiseData() }
    fun deleteReminder(index: Int) { promiseManager.deleteReminder(index); refreshPromiseData() }

    fun updateSituationContext(text: String) { _currentSituationContext.value = text }

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

    fun updateUrgeStrength(strength: Int) { _currentUrgeStrength.value = strength }
    fun updateFreeText(text: String) { _currentFreeText.value = text }

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

                // Update danger hour calculation after new entry
                updateDangerHourFromData()

                // Cache a fresh memory for notifications
                cacheMemoryForNotification()
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

    // ══════════════════════════════════════════
    // SHIELD PLAN FUNCTIONS - خطة الحماية
    // ══════════════════════════════════════════

    fun refreshShieldPlans() {
        _shieldPlans.value = shieldPlanManager.getShieldPlans()
    }

    fun setEditingPlan(plan: ShieldPlan?) {
        _editingPlan.value = plan
    }

    fun updateShieldPlan(plan: ShieldPlan) {
        shieldPlanManager.updatePlan(plan)
        refreshShieldPlans()
    }

    fun addCustomShieldPlan(name: String, emoji: String, description: String, steps: List<String>, note: String) {
        shieldPlanManager.addCustomPlan(name, emoji, description, steps, note)
        refreshShieldPlans()
    }

    fun deleteShieldPlan(triggerId: String) {
        shieldPlanManager.deletePlan(triggerId)
        refreshShieldPlans()
    }

    // ══════════════════════════════════════════
    // MEMORY BANK FUNCTIONS - بنك الذاكرة
    // ══════════════════════════════════════════

    fun saveRelapseLetter(message: String, trigger: String = "") {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_RELAPSE_LETTER,
                    message = message,
                    trigger = trigger,
                    streakAtTime = _currentStreak.value
                )
                repository.insertMemory(memory)
                cacheMemoryForNotification()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun saveVictoryNote(message: String) {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_VICTORY_NOTE,
                    message = message,
                    streakAtTime = _currentStreak.value,
                    urgeStrengthAtTime = _currentUrgeStrength.value
                )
                repository.insertMemory(memory)
                cacheMemoryForNotification()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun saveManualMemory(message: String) {
        viewModelScope.launch {
            try {
                Validators.requireValidMessageLength(message)

                val memory = MemoryEntry(
                    type = MemoryEntry.TYPE_MANUAL,
                    message = message,
                    streakAtTime = _currentStreak.value
                )
                repository.insertMemory(memory)
                cacheMemoryForNotification()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun toggleMemoryPin(memory: MemoryEntry) {
        viewModelScope.launch {
            repository.updateMemory(memory.copy(isPinned = !memory.isPinned))
        }
    }

    fun deleteMemory(memory: MemoryEntry) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
        }
    }

    fun loadQuickCatchData() {
        viewModelScope.launch {
            _quickCatchRelapseLetter.value = repository.getMostRecentRelapseLetter()
            _quickCatchVictoryNote.value = repository.getRandomVictoryNote()
            _quickCatchRandomMemory.value = repository.getRandomPinnedMemory()
                ?: repository.getRandomMemory()
        }
    }

    fun logQuickCatch() {
        _quickCatchCount.value += 1
    }

    fun loadInterventionMemory() {
        viewModelScope.launch {
            _interventionMemory.value =
                repository.getRandomPinnedMemory()
                    ?: repository.getRandomRelapseLetter()
                            ?: repository.getRandomVictoryNote()
                            ?: repository.getRandomMemory()
        }
    }

    fun resetStreakWithLetter(reason: String, letterToSelf: String = "") {
        if (letterToSelf.isNotBlank()) {
            saveRelapseLetter(
                message = letterToSelf,
                trigger = reason
            )
        }
        streakManager.resetStreak(reason)
        refreshStreakData()
        // Schedule post-relapse follow-up notification
        notificationScheduler.schedulePostRelapseFollowUp()
    }

    // ══════════════════════════════════════════
    // CLEAR ALL DATA
    // ══════════════════════════════════════════

    fun clearAllData() {
        viewModelScope.launch {
            val database = JournalDatabase.getDatabase(getApplication())
            database.clearAllTables()

            val app = getApplication<Application>()
            app.getSharedPreferences("taqwa_streak", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_promises", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_daily_quran", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_onboarding", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_shield_plans", 0).edit().clear().apply()

            // Cancel all notifications and clear preferences
            notificationScheduler.cancelAll()
            notificationPreferences.clearAll()

            streakManager.startNewStreak()
            refreshStreakData()
            refreshPromiseData()
            refreshDailyAyah()
            refreshNotificationSettings()
            _isOnboardingCompleted.value = false
            _todayCheckInDone.value = false
            refreshShieldPlans()
        }
    }

    // ══════════════════════════════════════════
    // MORNING CHECK-IN FUNCTIONS - الورد الصباحي
    // ══════════════════════════════════════════

    fun checkTodayCheckIn() {
        viewModelScope.launch {
            val today = java.time.LocalDate.now().toString()
            val existing = repository.getCheckInForDate(today)
            _todayCheckInDone.value = existing != null
        }
    }

    fun loadCheckInMemory() {
        viewModelScope.launch {
            _checkInMemory.value =
                repository.getRandomPinnedMemory()
                    ?: repository.getRandomRelapseLetter()
                            ?: repository.getRandomMemory()
        }
    }

    fun saveCheckIn(mood: String, riskLevel: String, intention: String) {
        viewModelScope.launch {
            try {
                // Validate using centralized validators
                Validators.requireValidMood(mood)
                Validators.requireValidRiskLevel(riskLevel)
                Validators.requireValidIntentionLength(intention)

                val today = java.time.LocalDate.now().toString()
                val checkIn = CheckInEntry(
                    date = today,
                    mood = mood,
                    riskLevel = riskLevel,
                    intention = intention,
                    streakAtTime = _currentStreak.value
                )
                repository.insertCheckIn(checkIn)
                _todayCheckInDone.value = true
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    // ══════════════════════════════════════════
    // EXPORT FUNCTIONS
    // ══════════════════════════════════════════

    suspend fun generateExportData(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String
    ): ReportData {
        val startMs = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMs = endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val entries = repository.getEntriesInRange(startMs, endMs)
        val memories = repository.getMemoriesInRange(startMs, endMs)
        val checkIns = repository.getCheckInsInRange(startMs, endMs)

        val allRelapses = streakManager.getRelapseHistory()
        val filteredRelapses = allRelapses.filter { relapse ->
            try {
                val relapseDate = LocalDate.parse(relapse.date)
                !relapseDate.isBefore(startDate) && !relapseDate.isAfter(endDate)
            } catch (e: Exception) { false }
        }

        return ReportData(
            periodLabel = periodLabel,
            currentStreak = _currentStreak.value,
            longestStreak = _longestStreak.value,
            urgesDefeated = entries.size,
            totalRelapses = filteredRelapses.size,
            journalEntries = entries,
            checkIns = checkIns,
            relapses = filteredRelapses,
            memories = memories,
            shieldPlans = shieldPlanManager.getActivePlans()
        )
    }

    fun exportReport(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String,
        options: ExportOptions,
        onReady: (Intent) -> Unit
    ) {
        viewModelScope.launch {
            val data = generateExportData(startDate, endDate, periodLabel)
            val intent = exportManager.generateAndShare(data, options)
            onReady(intent)
        }
    }

    fun previewExport(
        startDate: LocalDate,
        endDate: LocalDate,
        periodLabel: String,
        options: ExportOptions,
        onReady: (String) -> Unit
    ) {
        viewModelScope.launch {
            val data = generateExportData(startDate, endDate, periodLabel)
            val preview = exportManager.generatePreview(data, options)
            onReady(preview)
        }
    }

    // ══════════════════════════════════════════
    // NOTIFICATION FUNCTIONS
    // ══════════════════════════════════════════

    fun refreshNotificationSettings() {
        _morningEnabled.value = notificationPreferences.isMorningReminderEnabled()
        _morningHour.value = notificationPreferences.getMorningHour()
        _morningMinute.value = notificationPreferences.getMorningMinute()
        _dangerHourEnabled.value = notificationPreferences.isDangerHourEnabled()
        _dangerHourDetected.value = notificationPreferences.isDangerHourDetected()
        _dangerHourStart.value = notificationPreferences.getDangerHourStart()
        _dangerHourEnd.value = notificationPreferences.getDangerHourEnd()
        _dangerAlertHour.value = notificationPreferences.getDangerAlertHour()
        _dangerAlertMinute.value = notificationPreferences.getDangerAlertMinute()
        _memoryResurfaceEnabled.value = notificationPreferences.isMemoryResurfaceEnabled()
        _inactivityEnabled.value = notificationPreferences.isInactivityCheckEnabled()
        _streakCelebrationEnabled.value = notificationPreferences.isStreakCelebrationEnabled()
        _postRelapseEnabled.value = notificationPreferences.isPostRelapseEnabled()
    }

    fun setMorningReminderEnabled(enabled: Boolean) {
        notificationPreferences.setMorningReminderEnabled(enabled)
        _morningEnabled.value = enabled
        if (enabled) {
            notificationScheduler.scheduleMorningReminder(_currentStreak.value)
        } else {
            notificationScheduler.scheduleMorningReminder(_currentStreak.value)
        }
    }

    fun setMorningTime(hour: Int, minute: Int) {
        notificationPreferences.setMorningTime(hour, minute)
        _morningHour.value = hour
        _morningMinute.value = minute
        notificationScheduler.scheduleMorningReminder(_currentStreak.value)
    }

    fun setDangerHourEnabled(enabled: Boolean) {
        notificationPreferences.setDangerHourEnabled(enabled)
        _dangerHourEnabled.value = enabled
        notificationScheduler.scheduleDangerHourAlert(_currentStreak.value)
    }

    fun setMemoryResurfaceEnabled(enabled: Boolean) {
        notificationPreferences.setMemoryResurfaceEnabled(enabled)
        _memoryResurfaceEnabled.value = enabled
        notificationScheduler.scheduleMemoryResurface()
    }

    fun setInactivityCheckEnabled(enabled: Boolean) {
        notificationPreferences.setInactivityCheckEnabled(enabled)
        _inactivityEnabled.value = enabled
        notificationScheduler.scheduleInactivityCheck(_currentStreak.value)
    }

    fun setStreakCelebrationEnabled(enabled: Boolean) {
        notificationPreferences.setStreakCelebrationEnabled(enabled)
        _streakCelebrationEnabled.value = enabled
    }

    fun setPostRelapseEnabled(enabled: Boolean) {
        notificationPreferences.setPostRelapseEnabled(enabled)
        _postRelapseEnabled.value = enabled
    }


    /**
     * Cache a random memory message for use in notifications.
     * Called after saving any memory and on init.
     */
    private fun cacheMemoryForNotification() {
        viewModelScope.launch {
            val memory = repository.getRandomPinnedMemory()
                ?: repository.getRandomRelapseLetter()
                ?: repository.getRandomVictoryNote()
                ?: repository.getRandomMemory()

            if (memory != null) {
                // Truncate to 100 chars for notification
                val truncated = if (memory.message.length > 100) {
                    memory.message.take(100) + "..."
                } else {
                    memory.message
                }
                notificationPreferences.setCachedMemory(truncated)
            }
        }
    }

    /**
     * Analyze journal entry timestamps and update danger hour.
     */
    private fun updateDangerHourFromData() {
        viewModelScope.launch {
            repository.allEntries.collect { entries ->
                if (entries.isNotEmpty()) {
                    val timestamps = entries.map { it.timestamp }
                    val detected = notificationScheduler.calculateDangerHour(timestamps)
                    _dangerHourDetected.value = detected
                    if (detected) {
                        _dangerHourStart.value = notificationPreferences.getDangerHourStart()
                        _dangerHourEnd.value = notificationPreferences.getDangerHourEnd()
                        _dangerAlertHour.value = notificationPreferences.getDangerAlertHour()
                        _dangerAlertMinute.value = notificationPreferences.getDangerAlertMinute()
                        notificationScheduler.scheduleDangerHourAlert(_currentStreak.value)
                    }
                }
            }
        }
    }
}