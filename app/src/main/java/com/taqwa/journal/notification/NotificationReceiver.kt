package com.taqwa.journal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.taqwa.journal.data.preferences.NotificationPreferences
import com.taqwa.journal.data.preferences.StreakManager

/**
 * Notification Receiver — مستقبل التنبيهات
 *
 * BroadcastReceiver that fires when an alarm triggers.
 * Shows the notification and reschedules repeating alarms.
 */
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(NotificationScheduler.EXTRA_NOTIFICATION_TYPE) ?: return
        val streak = intent.getIntExtra(NotificationScheduler.EXTRA_CURRENT_STREAK, 0)

        val notificationManager = TaqwaNotificationManager(context)
        notificationManager.createNotificationChannels()

        // Get fresh streak data
        val streakManager = StreakManager(context)
        val currentStreak = streakManager.getCurrentStreak()

        // Show the notification
        notificationManager.showNotification(type, currentStreak)

        // Reschedule repeating notifications for next day
        val scheduler = NotificationScheduler(context)
        when (type) {
            TaqwaNotificationManager.TYPE_MORNING -> {
                // Check for milestone
                val preferences = NotificationPreferences(context)
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
                // Reschedule for tomorrow
                scheduler.scheduleMorningReminder(currentStreak)
            }

            TaqwaNotificationManager.TYPE_DANGER_HOUR -> {
                // Reschedule for tomorrow
                scheduler.scheduleDangerHourAlert(currentStreak)
            }

            TaqwaNotificationManager.TYPE_MEMORY -> {
                // Reschedule for tomorrow at a new random time
                scheduler.scheduleMemoryResurface()
            }

            // Inactivity and post-relapse are one-time, no reschedule needed
        }
    }
}