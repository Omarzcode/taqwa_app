package com.taqwa.journal.features.checkin.data

import com.taqwa.journal.core.utilities.Validators
import kotlinx.coroutines.flow.Flow

class CheckInRepository(private val checkInDao: CheckInDao) {

    val allCheckIns: Flow<List<CheckInEntry>> = checkInDao.getAllCheckIns()

    val checkInCount: Flow<Int> = checkInDao.getCheckInCount()

    val recentCheckIns: Flow<List<CheckInEntry>> = checkInDao.getRecentCheckIns()

    suspend fun insertCheckIn(checkIn: CheckInEntry) {
        validateCheckInEntry(checkIn)
        checkInDao.insertCheckIn(checkIn)
    }

    suspend fun getCheckInForDate(date: String): CheckInEntry? {
        return checkInDao.getCheckInForDate(date)
    }

    suspend fun deleteCheckIn(checkIn: CheckInEntry) {
        checkInDao.deleteCheckIn(checkIn)
    }

    fun getCheckInsByMood(mood: String): Flow<List<CheckInEntry>> {
        return checkInDao.getCheckInsByMood(mood)
    }

    fun getCheckInsByRisk(riskLevel: String): Flow<List<CheckInEntry>> {
        return checkInDao.getCheckInsByRisk(riskLevel)
    }

    suspend fun getCheckInsInRange(startMs: Long, endMs: Long): List<CheckInEntry> {
        return checkInDao.getCheckInsInRange(startMs, endMs)
    }

    private fun validateCheckInEntry(entry: CheckInEntry) {
        Validators.requireValidMood(entry.mood)
        Validators.requireValidRiskLevel(entry.riskLevel)
        Validators.requireValidIntentionLength(entry.intention)
    }
}
