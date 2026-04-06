package com.taqwa.journal.features.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.taqwa.journal.MainActivity
import com.taqwa.journal.features.notifications.NotificationPreferences

/**
 * Taqwa Notification Manager — مدير التنبيهات
 *
 * Handles creating notification channels and building/showing notifications.
 * All notification content uses neutral, privacy-safe language.
 */
class TaqwaNotificationManager(private val context: Context) {

    companion object {
        const val CHANNEL_ID_DAILY = "taqwa_daily"
        const val CHANNEL_ID_ALERTS = "taqwa_alerts"
        const val CHANNEL_ID_MOTIVATION = "taqwa_motivation"

        const val NOTIFICATION_ID_MORNING = 1001
        const val NOTIFICATION_ID_DANGER_HOUR = 1002
        const val NOTIFICATION_ID_MEMORY = 1003
        const val NOTIFICATION_ID_INACTIVITY = 1004
        const val NOTIFICATION_ID_STREAK = 1005
        const val NOTIFICATION_ID_POST_RELAPSE = 1006

        const val TYPE_MORNING = "morning"
        const val TYPE_DANGER_HOUR = "danger_hour"
        const val TYPE_MEMORY = "memory"
        const val TYPE_INACTIVITY = "inactivity"
        const val TYPE_STREAK = "streak"
        const val TYPE_POST_RELAPSE = "post_relapse"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val preferences = NotificationPreferences(context)

    /**
     * Create all notification channels. Call once on app start.
     */
    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dailyChannel = NotificationChannel(
                CHANNEL_ID_DAILY,
                "Daily Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Morning reminders and check-ins"
            }

            val alertsChannel = NotificationChannel(
                CHANNEL_ID_ALERTS,
                "Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important alerts and warnings"
            }

            val motivationChannel = NotificationChannel(
                CHANNEL_ID_MOTIVATION,
                "Motivation",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Motivational messages and memories"
            }

            notificationManager.createNotificationChannels(
                listOf(dailyChannel, alertsChannel, motivationChannel)
            )
        }
    }

    /**
     * Show a notification based on type.
     * Respects user toggle settings.
     */
    fun showNotification(type: String, currentStreak: Int = 0) {
        if (!isTypeEnabled(type)) return
        buildAndShowNotification(type, currentStreak)
    }

    /**
     * Force show a notification — bypasses toggle check.
     * Used for testing only.
     */
    fun forceShowNotification(type: String, currentStreak: Int = 0) {
        buildAndShowNotification(type, currentStreak)
    }

    private fun buildAndShowNotification(type: String, currentStreak: Int) {
        // Ensure channels exist
        createNotificationChannels()

        val content = getNotificationContent(type, currentStreak)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            content.notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, content.channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(content.title)
            .setContentText(content.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content.message))
            .setPriority(
                if (type == TYPE_DANGER_HOUR) NotificationCompat.PRIORITY_HIGH
                else NotificationCompat.PRIORITY_DEFAULT
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        try {
            notificationManager.notify(content.notificationId, notification)
        } catch (e: SecurityException) {
            // Permission not granted — silently fail
            e.printStackTrace()
        }
    }

    private fun isTypeEnabled(type: String): Boolean {
        return when (type) {
            TYPE_MORNING -> preferences.isMorningReminderEnabled()
            TYPE_DANGER_HOUR -> preferences.isDangerHourEnabled()
            TYPE_MEMORY -> preferences.isMemoryResurfaceEnabled()
            TYPE_INACTIVITY -> preferences.isInactivityCheckEnabled()
            TYPE_STREAK -> preferences.isStreakCelebrationEnabled()
            TYPE_POST_RELAPSE -> preferences.isPostRelapseEnabled()
            else -> false
        }
    }

    private fun getNotificationContent(
        type: String,
        currentStreak: Int
    ): NotificationContent {
        return when (type) {
            TYPE_MORNING -> {
                val messages = listOf(
                    "Day $currentStreak. Your strength continues.",
                    "Day $currentStreak. Another day of victory ahead.",
                    "Day $currentStreak. Allah sees your struggle and rewards it.",
                    "Day $currentStreak. You're stronger than yesterday.",
                    "Day $currentStreak. Rise with purpose today.",
                    "Day $currentStreak. Every morning is a fresh start.",
                    "Day $currentStreak. Keep building. Keep growing."
                )
                NotificationContent(
                    title = "Taqwa",
                    message = messages.random(),
                    channelId = CHANNEL_ID_DAILY,
                    notificationId = NOTIFICATION_ID_MORNING
                )
            }

            TYPE_DANGER_HOUR -> {
                val messages = listOf(
                    "Shield up. This is usually your hardest hour.",
                    "Heads up. Stay aware for the next couple hours.",
                    "Your data shows this is a vulnerable time. Stay strong.",
                    "This hour has been tough before. You're ready this time.",
                    "Alert: High-risk window. Open the app if you need support.",
                    "Stay guarded. You know what this hour brings."
                )
                NotificationContent(
                    title = "Taqwa - Stay Alert",
                    message = messages.random(),
                    channelId = CHANNEL_ID_ALERTS,
                    notificationId = NOTIFICATION_ID_DANGER_HOUR
                )
            }

            TYPE_MEMORY -> {
                val cachedMemory = preferences.getCachedMemory()
                val message = if (cachedMemory.isNotBlank()) {
                    "Remember: \"$cachedMemory\""
                } else {
                    "You have memories waiting in your bank. Take a look."
                }
                NotificationContent(
                    title = "Taqwa - A Memory For You",
                    message = message,
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_MEMORY
                )
            }

            TYPE_INACTIVITY -> {
                val messages = listOf(
                    "Haven't seen you in a while. Everything okay?",
                    "Just checking in. Your streak is at $currentStreak days.",
                    "It's been a few days. Come say hi.",
                    "Still here for you. Open when you're ready.",
                    "Your journey matters. Don't walk it alone."
                )
                NotificationContent(
                    title = "Taqwa",
                    message = messages.random(),
                    channelId = CHANNEL_ID_DAILY,
                    notificationId = NOTIFICATION_ID_INACTIVITY
                )
            }

            TYPE_STREAK -> {
                val message = getMilestoneMessage(currentStreak)
                NotificationContent(
                    title = "Taqwa - Milestone!",
                    message = message,
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_STREAK
                )
            }

            TYPE_POST_RELAPSE -> {
                val messages = listOf(
                    "Day 1 again. You've done this before. Let's go.",
                    "A setback, not the end. Today is a new beginning.",
                    "The strongest people are those who get back up. You're one of them.",
                    "Yesterday is gone. Today, you choose differently.",
                    "One fall doesn't erase your progress. Rise again."
                )
                NotificationContent(
                    title = "Taqwa",
                    message = messages.random(),
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_POST_RELAPSE
                )
            }

            else -> NotificationContent(
                title = "Taqwa",
                message = "Stay strong on your journey.",
                channelId = CHANNEL_ID_DAILY,
                notificationId = NOTIFICATION_ID_MORNING
            )
        }
    }

    private fun getMilestoneMessage(days: Int): String {
        return when (days) {
            1 -> "Day 1 complete! The hardest step is the first one."
            3 -> "3 days! The initial storm is passing."
            7 -> "One full week! Your brain is starting to rewire."
            14 -> "2 weeks! Dopamine receptors are healing."
            21 -> "21 days! A new habit is forming."
            30 -> "30 days! One full month. Your brain has healed significantly."
            40 -> "40 days! In Islamic tradition, 40 days transforms a person."
            60 -> "60 days! Two months of strength and discipline."
            90 -> "90 days! A major milestone. Deep healing has occurred."
            120 -> "120 days! Four months. You're a different person now."
            180 -> "Half a year! 180 days of courage."
            365 -> "ONE FULL YEAR! SubhanAllah. You did it."
            else -> "Day $days! Keep going, champion."
        }
    }

    /**
     * Check if a streak day is a milestone worth celebrating.
     */
    fun isMilestoneDay(days: Int): Boolean {
        return days in listOf(1, 3, 7, 14, 21, 30, 40, 60, 90, 120, 180, 365, 500, 730)
    }

    private data class NotificationContent(
        val title: String,
        val message: String,
        val channelId: String,
        val notificationId: Int
    )
}
