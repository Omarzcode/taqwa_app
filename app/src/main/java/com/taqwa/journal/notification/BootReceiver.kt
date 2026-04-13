package com.taqwa.journal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.taqwa.journal.data.preferences.StreakManager
import com.taqwa.journal.widget.WidgetUpdater

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("TaqwaWidget", "BootReceiver: device booted, refreshing everything")

            val streakManager = StreakManager(context)
            val currentStreak = streakManager.getCurrentStreak()

            // Create notification channels
            val notificationManager = TaqwaNotificationManager(context)
            notificationManager.createNotificationChannels()

            // Re-schedule ALL notifications
            val scheduler = NotificationScheduler(context)
            scheduler.scheduleAll(currentStreak)

            // Refresh all widgets
            WidgetUpdater.updateAllWidgets(context)

            // Re-schedule midnight widget refresh
            WidgetUpdater.scheduleMidnightRefresh(context)
        }
    }
}