package com.taqwa.journal.features.checkin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.features.checkin.data.CheckInEntry
import com.taqwa.journal.features.checkin.data.CheckInRepository
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CheckInViewModel(
    application: Application,
    private val repository: CheckInRepository
) : AndroidViewModel(application) {

    val allCheckIns: Flow<List<CheckInEntry>> = repository.allCheckIns
    val checkInCount: Flow<Int> = repository.checkInCount
    val recentCheckIns: Flow<List<CheckInEntry>> = repository.recentCheckIns

    private val _todayCheckInDone = MutableStateFlow(false)
    val todayCheckInDone: StateFlow<Boolean> = _todayCheckInDone.asStateFlow()

    private val _checkInMemory = MutableStateFlow<MemoryEntry?>(null)
    val checkInMemory: StateFlow<MemoryEntry?> = _checkInMemory.asStateFlow()

    fun checkTodayCheckIn() {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            val existing = repository.getCheckInForDate(today)
            _todayCheckInDone.value = existing != null
        }
    }

    fun saveCheckIn(mood: String, riskLevel: String, intention: String, currentStreak: Int) {
        viewModelScope.launch {
            try {
                Validators.requireValidMood(mood)
                Validators.requireValidRiskLevel(riskLevel)
                Validators.requireValidIntentionLength(intention)

                val today = LocalDate.now().toString()
                val checkIn = CheckInEntry(
                    date = today,
                    mood = mood,
                    riskLevel = riskLevel,
                    intention = intention,
                    streakAtTime = currentStreak
                )
                repository.insertCheckIn(checkIn)
                _todayCheckInDone.value = true
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    fun deleteCheckIn(checkIn: CheckInEntry) {
        viewModelScope.launch {
            repository.deleteCheckIn(checkIn)
        }
    }

    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>> {
        return repository.getCheckInsByMood(mood)
    }

    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>> {
        return repository.getCheckInsByRisk(riskLevel)
    }

    fun setCheckInMemory(memory: MemoryEntry?) {
        _checkInMemory.value = memory
    }
}
