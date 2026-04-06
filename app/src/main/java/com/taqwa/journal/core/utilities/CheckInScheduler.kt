package com.taqwa.journal.core.utilities

import java.util.Calendar

/**
 * Manages check-in scheduling and frequency rules.
 * Determines if user should be prompted for check-in based on last entry time.
 */
object CheckInScheduler {
    
    enum class CheckInFrequency {
        IMMEDIATE,      // Every time
        ONCE_PER_HOUR,
        TWICE_PER_DAY,
        ONCE_PER_DAY,
        CUSTOM          // User-defined
    }

    fun shouldPromptCheckIn(
        lastCheckInTime: Long?,
        frequency: CheckInFrequency,
        customMinutesInterval: Int? = null
    ): Boolean {
        if (lastCheckInTime == null) return true

        val elapsedMinutes = getElapsedMinutes(lastCheckInTime)
        
        return when (frequency) {
            CheckInFrequency.IMMEDIATE -> true
            CheckInFrequency.ONCE_PER_HOUR -> elapsedMinutes >= 60
            CheckInFrequency.TWICE_PER_DAY -> elapsedMinutes >= 720 // 12 hours
            CheckInFrequency.ONCE_PER_DAY -> elapsedMinutes >= 1440 // 24 hours
            CheckInFrequency.CUSTOM -> {
                if (customMinutesInterval == null) false
                else elapsedMinutes >= customMinutesInterval
            }
        }
    }

    fun getNextCheckInTime(
        lastCheckInTime: Long,
        frequency: CheckInFrequency,
        customMinutesInterval: Int? = null
    ): Long {
        val intervalMs = when (frequency) {
            CheckInFrequency.IMMEDIATE -> return System.currentTimeMillis()
            CheckInFrequency.ONCE_PER_HOUR -> 60 * 60 * 1000L
            CheckInFrequency.TWICE_PER_DAY -> 12 * 60 * 60 * 1000L
            CheckInFrequency.ONCE_PER_DAY -> 24 * 60 * 60 * 1000L
            CheckInFrequency.CUSTOM -> {
                if (customMinutesInterval == null) System.currentTimeMillis()
                else (customMinutesInterval * 60 * 1000).toLong()
            }
        }
        return lastCheckInTime + intervalMs
    }

    fun getCheckInHistory(checkInTimes: List<Long>, days: Int = 7): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        val calendar = Calendar.getInstance()
        val now = System.currentTimeMillis()

        for (i in 0 until days) {
            val dayMillis = now - (i * 24 * 60 * 60 * 1000)
            calendar.timeInMillis = dayMillis
            val dateKey = String.format(
                "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            result[dateKey] = 0
        }

        for (checkInTime in checkInTimes) {
            calendar.timeInMillis = checkInTime
            val dateKey = String.format(
                "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            result[dateKey] = (result[dateKey] ?: 0) + 1
        }

        return result
    }

    private fun getElapsedMinutes(lastTimeMs: Long): Int {
        return ((System.currentTimeMillis() - lastTimeMs) / (60 * 1000)).toInt()
    }
}
