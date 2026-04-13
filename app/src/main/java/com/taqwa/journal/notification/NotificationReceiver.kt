package com.taqwa.journal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.taqwa.journal.data.preferences.NotificationPreferences
import com.taqwa.journal.data.preferences.StreakManager

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(NotificationScheduler.EXTRA_NOTIFICATION_TYPE) ?: return

        val notificationManager = TaqwaNotificationManager(context)
        notificationManager.createNotificationChannels()

        val streakManager = StreakManager(context)
        val currentStreak = streakManager.getCurrentStreak()

        // Show the notification
        notificationManager.showNotification(type, currentStreak)

        // Reschedule repeating notifications for next occurrence
        val scheduler = NotificationScheduler(context)
        val preferences = NotificationPreferences(context)

        when (type) {
            TaqwaNotificationManager.TYPE_MORNING -> {
                // Check for milestone
                if (notificationManager.isMilestoneDay(currentStreak)) {
                    val lastMilestone = preferences.getLastMilestoneNotified()
                    if (lastMilestone != currentStreak) {
                        preferences.setLastMilestoneNotified(currentStreak)
                        notificationManager.showNotification(
                            TaqwaNotificationManager.TYPE_STREAK,
                            currentStreak
                        )
                    }
                }
                scheduler.scheduleMorningReminder(currentStreak)
            }

            TaqwaNotificationManager.TYPE_EVENING -> {
                scheduler.scheduleEveningReminder(currentStreak)
            }

            TaqwaNotificationManager.TYPE_DANGER_HOUR -> {
                scheduler.scheduleDangerHourAlert(currentStreak)
            }

            TaqwaNotificationManager.TYPE_MEMORY -> {
                scheduler.scheduleMemoryResurface()
            }

            TaqwaNotificationManager.TYPE_FRIDAY -> {
                scheduler.scheduleFridayReminder(currentStreak)
            }

            TaqwaNotificationManager.TYPE_INACTIVITY -> {
                // Reschedule inactivity check from now
                scheduler.scheduleInactivityCheck(currentStreak)
            }

            // Post-relapse is one-time, no reschedule
        }

        // Update last app interaction for inactivity tracking
        preferences.updateLastAppOpen()
    }
}