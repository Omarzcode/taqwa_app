package com.taqwa.journal.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.taqwa.journal.data.preferences.NotificationPreferences
import java.util.Calendar

class NotificationScheduler(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val preferences = NotificationPreferences(context)

    companion object {
        const val REQUEST_MORNING = 2001
        const val REQUEST_DANGER_HOUR = 2002
        const val REQUEST_MEMORY = 2003
        const val REQUEST_INACTIVITY = 2004
        const val REQUEST_EVENING = 2005
        const val REQUEST_POST_RELAPSE = 2006
        const val REQUEST_FRIDAY = 2007

        const val EXTRA_NOTIFICATION_TYPE = "notification_type"
        const val EXTRA_CURRENT_STREAK = "current_streak"
    }

    fun scheduleAll(currentStreak: Int) {
        scheduleMorningReminder(currentStreak)
        scheduleEveningReminder(currentStreak)
        scheduleDangerHourAlert(currentStreak)
        scheduleMemoryResurface()
        scheduleInactivityCheck(currentStreak)
        scheduleFridayReminder(currentStreak)
    }

    fun cancelAll() {
        cancelAlarm(REQUEST_MORNING)
        cancelAlarm(REQUEST_EVENING)
        cancelAlarm(REQUEST_DANGER_HOUR)
        cancelAlarm(REQUEST_MEMORY)
        cancelAlarm(REQUEST_INACTIVITY)
        cancelAlarm(REQUEST_POST_RELAPSE)
        cancelAlarm(REQUEST_FRIDAY)
    }

    // ── Morning Reminder ──

    fun scheduleMorningReminder(currentStreak: Int) {
        if (!preferences.isMorningReminderEnabled()) {
            cancelAlarm(REQUEST_MORNING)
            return
        }

        val hour = preferences.getMorningHour()
        val minute = preferences.getMorningMinute()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleExact(
            requestCode = REQUEST_MORNING,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_MORNING,
            streak = currentStreak
        )
    }

    // ── Evening Reminder ──

    fun scheduleEveningReminder(currentStreak: Int) {
        if (!preferences.isEveningReminderEnabled()) {
            cancelAlarm(REQUEST_EVENING)
            return
        }

        val hour = preferences.getEveningHour()
        val minute = preferences.getEveningMinute()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleExact(
            requestCode = REQUEST_EVENING,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_EVENING,
            streak = currentStreak
        )
    }

    // ── Danger Hour Alert ──

    fun scheduleDangerHourAlert(currentStreak: Int) {
        if (!preferences.isDangerHourEnabled() || !preferences.isDangerHourDetected()) {
            cancelAlarm(REQUEST_DANGER_HOUR)
            return
        }

        val alertHour = preferences.getDangerAlertHour()
        val alertMinute = preferences.getDangerAlertMinute()

        if (alertHour < 0) {
            cancelAlarm(REQUEST_DANGER_HOUR)
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alertHour)
            set(Calendar.MINUTE, alertMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleExact(
            requestCode = REQUEST_DANGER_HOUR,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_DANGER_HOUR,
            streak = currentStreak
        )
    }

    // ── Memory Resurface ──

    fun scheduleMemoryResurface() {
        if (!preferences.isMemoryResurfaceEnabled()) {
            cancelAlarm(REQUEST_MEMORY)
            return
        }

        val randomHour = (14..20).random()
        val randomMinute = (0..59).random()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, randomHour)
            set(Calendar.MINUTE, randomMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleExact(
            requestCode = REQUEST_MEMORY,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_MEMORY,
            streak = 0
        )
    }

    // ── Inactivity Check ──

    fun scheduleInactivityCheck(currentStreak: Int) {
        if (!preferences.isInactivityCheckEnabled()) {
            cancelAlarm(REQUEST_INACTIVITY)
            return
        }

        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 2)
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        scheduleExact(
            requestCode = REQUEST_INACTIVITY,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_INACTIVITY,
            streak = currentStreak
        )
    }

    // ── Post-Relapse Follow-Up ──

    fun schedulePostRelapseFollowUp() {
        if (!preferences.isPostRelapseEnabled()) {
            cancelAlarm(REQUEST_POST_RELAPSE)
            return
        }

        // Fire 2 hours from now
        val triggerTime = System.currentTimeMillis() + (2 * 60 * 60 * 1000L)

        scheduleExact(
            requestCode = REQUEST_POST_RELAPSE,
            triggerAtMillis = triggerTime,
            type = TaqwaNotificationManager.TYPE_POST_RELAPSE,
            streak = 0
        )
    }

    // ── Friday Reminder ──

    fun scheduleFridayReminder(currentStreak: Int) {
        if (!preferences.isFridayReminderEnabled()) {
            cancelAlarm(REQUEST_FRIDAY)
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Find next Friday
            while (get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
            // If it's already past 9 AM on Friday, schedule next Friday
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        scheduleExact(
            requestCode = REQUEST_FRIDAY,
            triggerAtMillis = calendar.timeInMillis,
            type = TaqwaNotificationManager.TYPE_FRIDAY,
            streak = currentStreak
        )
    }

    // ── Danger Hour Calculation ──

    fun calculateDangerHour(timestamps: List<Long>): Boolean {
        if (timestamps.size < 5) {
            preferences.clearDangerHour()
            return false
        }

        val hourCounts = IntArray(24)
        timestamps.forEach { timestamp ->
            val cal = Calendar.getInstance().apply { timeInMillis = timestamp }
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            hourCounts[hour]++
        }

        var maxCount = 0
        var peakStartHour = 0

        for (h in 0..23) {
            val nextH = (h + 1) % 24
            val windowCount = hourCounts[h] + hourCounts[nextH]
            if (windowCount > maxCount) {
                maxCount = windowCount
                peakStartHour = h
            }
        }

        if (maxCount < 3) {
            preferences.clearDangerHour()
            return false
        }

        val peakEndHour = (peakStartHour + 2) % 24
        val alertHour: Int
        val alertMinute: Int
        if (peakStartHour == 0) {
            alertHour = 23
            alertMinute = 30
        } else {
            alertHour = peakStartHour - 1
            alertMinute = 30
        }

        preferences.setDangerHour(peakStartHour, peakEndHour, alertHour, alertMinute)
        return true
    }

    // ── Internal Helpers ──

    private fun scheduleExact(
        requestCode: Int,
        triggerAtMillis: Long,
        type: String,
        streak: Int
    ) {
        val pendingIntent = createPendingIntent(requestCode, type, streak)

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }

    private fun createPendingIntent(requestCode: Int, type: String, streak: Int): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(EXTRA_NOTIFICATION_TYPE, type)
            putExtra(EXTRA_CURRENT_STREAK, streak)
        }
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun cancelAlarm(requestCode: Int) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}