package com.taqwa.journal.core.utilities

import java.util.concurrent.TimeUnit

/**
 * Date/time formatting utilities for consistent display across app.
 */
object DateTimeFormatter {
    
    fun formatTime(timestampMs: Long): String {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        
        val hourOfDay = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)
        
        return String.format("%02d:%02d", hourOfDay, minute)
    }

    fun formatDate(timestampMs: Long): String {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH) + 1
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    fun formatDateTime(timestampMs: Long): String {
        return "${formatDate(timestampMs)} ${formatTime(timestampMs)}"
    }

    fun formatRelativeTime(timestampMs: Long, now: Long = System.currentTimeMillis()): String {
        val diffMs = now - timestampMs
        
        return when {
            diffMs < 60_000 -> "Just now"
            diffMs < 3_600_000 -> "${TimeUnit.MILLISECONDS.toMinutes(diffMs)} minutes ago"
            diffMs < 86_400_000 -> "${TimeUnit.MILLISECONDS.toHours(diffMs)} hours ago"
            diffMs < 604_800_000 -> "${TimeUnit.MILLISECONDS.toDays(diffMs)} days ago"
            else -> formatDate(timestampMs)
        }
    }

    fun formatDay(timestampMs: Long): String {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        
        val dayNames = arrayOf(
            "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"
        )
        
        return dayNames[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1]
    }

    fun formatMonthYear(timestampMs: Long): String {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        
        val month = monthNames[calendar.get(java.util.Calendar.MONTH)]
        val year = calendar.get(java.util.Calendar.YEAR)
        
        return "$month $year"
    }

    fun getStartOfDay(timestampMs: Long): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getEndOfDay(timestampMs: Long): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestampMs
        }
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
        calendar.set(java.util.Calendar.MINUTE, 59)
        calendar.set(java.util.Calendar.SECOND, 59)
        calendar.set(java.util.Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    fun isSameDay(time1: Long, time2: Long): Boolean {
        return formatDate(time1) == formatDate(time2)
    }

    fun isSameWeek(time1: Long, time2: Long): Boolean {
        val cal1 = java.util.Calendar.getInstance().apply { timeInMillis = time1 }
        val cal2 = java.util.Calendar.getInstance().apply { timeInMillis = time2 }
        
        return cal1.get(java.util.Calendar.WEEK_OF_YEAR) == cal2.get(java.util.Calendar.WEEK_OF_YEAR) &&
               cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR)
    }
}
