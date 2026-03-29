package com.taqwa.journal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.taqwa.journal.data.preferences.StreakManager

/**
 * Boot Receiver — مستقبل إعادة التشغيل
 *
 * Re-schedules all notification alarms after device reboot.
 * Alarms are lost when the device restarts.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val streakManager = StreakManager(context)
            val currentStreak = streakManager.getCurrentStreak()

            val notificationManager = TaqwaNotificationManager(context)
            notificationManager.createNotificationChannels()

            val scheduler = NotificationScheduler(context)
            scheduler.scheduleAll(currentStreak)
        }
    }
}