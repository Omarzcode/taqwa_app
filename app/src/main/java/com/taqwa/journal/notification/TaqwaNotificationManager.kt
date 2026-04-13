package com.taqwa.journal.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.taqwa.journal.MainActivity
import com.taqwa.journal.data.preferences.NotificationPreferences
import com.taqwa.journal.ui.navigation.Routes

class TaqwaNotificationManager(private val context: Context) {

    companion object {
        const val CHANNEL_ID_CHECKIN = "taqwa_checkin"
        const val CHANNEL_ID_ALERTS = "taqwa_alerts"
        const val CHANNEL_ID_MOTIVATION = "taqwa_motivation"
        const val CHANNEL_ID_MILESTONES = "taqwa_milestones"
        const val CHANNEL_ID_POST_RELAPSE = "taqwa_post_relapse"

        const val NOTIFICATION_ID_MORNING = 1001
        const val NOTIFICATION_ID_DANGER_HOUR = 1002
        const val NOTIFICATION_ID_MEMORY = 1003
        const val NOTIFICATION_ID_INACTIVITY = 1004
        const val NOTIFICATION_ID_STREAK = 1005
        const val NOTIFICATION_ID_POST_RELAPSE = 1006
        const val NOTIFICATION_ID_EVENING = 1007
        const val NOTIFICATION_ID_FRIDAY = 1008

        const val TYPE_MORNING = "morning"
        const val TYPE_EVENING = "evening"
        const val TYPE_DANGER_HOUR = "danger_hour"
        const val TYPE_MEMORY = "memory"
        const val TYPE_INACTIVITY = "inactivity"
        const val TYPE_STREAK = "streak"
        const val TYPE_POST_RELAPSE = "post_relapse"
        const val TYPE_FRIDAY = "friday"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val preferences = NotificationPreferences(context)

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_ID_CHECKIN,
                    "Check-In Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Morning and evening check-in reminders"
                },
                NotificationChannel(
                    CHANNEL_ID_ALERTS,
                    "Danger Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "High-risk time warnings"
                },
                NotificationChannel(
                    CHANNEL_ID_MOTIVATION,
                    "Encouragement",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Motivational messages and memories"
                },
                NotificationChannel(
                    CHANNEL_ID_MILESTONES,
                    "Streak Milestones",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Streak celebration notifications"
                },
                NotificationChannel(
                    CHANNEL_ID_POST_RELAPSE,
                    "Recovery Support",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Post-relapse follow-up messages"
                }
            )
            notificationManager.createNotificationChannels(channels)
        }
    }

    fun showNotification(type: String, currentStreak: Int = 0) {
        if (!isTypeEnabled(type)) return
        buildAndShowNotification(type, currentStreak)
    }

    fun forceShowNotification(type: String, currentStreak: Int = 0) {
        buildAndShowNotification(type, currentStreak)
    }

    private fun buildAndShowNotification(type: String, currentStreak: Int) {
        createNotificationChannels()

        val content = getNotificationContent(type, currentStreak)
        val deepRoute = getDeepLinkRoute(type)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (deepRoute != null) {
                putExtra(MainActivity.EXTRA_NAVIGATE_TO, deepRoute)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            content.notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Action button intent
        val actionIntent = getActionIntent(type)
        val actionLabel = getActionLabel(type)

        val builder = NotificationCompat.Builder(context, content.channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(content.title)
            .setContentText(content.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content.message))
            .setPriority(content.priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Add action button if available
        if (actionIntent != null && actionLabel != null) {
            val actionPendingIntent = PendingIntent.getActivity(
                context,
                content.notificationId + 500,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.addAction(0, actionLabel, actionPendingIntent)
        }

        try {
            notificationManager.notify(content.notificationId, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun getDeepLinkRoute(type: String): String? {
        return when (type) {
            TYPE_MORNING -> Routes.MORNING_CHECK_IN
            TYPE_EVENING -> Routes.EVENING_CHECK_IN
            TYPE_DANGER_HOUR -> Routes.BREATHING
            TYPE_POST_RELAPSE -> Routes.MEMORY_BANK
            TYPE_MEMORY -> Routes.MEMORY_BANK
            TYPE_FRIDAY -> Routes.MORNING_CHECK_IN
            else -> null
        }
    }

    private fun getActionIntent(type: String): Intent? {
        val route = when (type) {
            TYPE_MORNING -> Routes.MORNING_CHECK_IN
            TYPE_EVENING -> Routes.EVENING_CHECK_IN
            TYPE_DANGER_HOUR -> Routes.BREATHING
            TYPE_POST_RELAPSE -> "write_memory/RELAPSE_LETTER"
            else -> return null
        }
        return Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.EXTRA_NAVIGATE_TO, route)
        }
    }

    private fun getActionLabel(type: String): String? {
        return when (type) {
            TYPE_MORNING -> "Start Check-In"
            TYPE_EVENING -> "Start Reflection"
            TYPE_DANGER_HOUR -> "Open Shield"
            TYPE_POST_RELAPSE -> "Write Letter"
            else -> null
        }
    }

    private fun isTypeEnabled(type: String): Boolean {
        return when (type) {
            TYPE_MORNING -> preferences.isMorningReminderEnabled()
            TYPE_EVENING -> preferences.isEveningReminderEnabled()
            TYPE_DANGER_HOUR -> preferences.isDangerHourEnabled()
            TYPE_MEMORY -> preferences.isMemoryResurfaceEnabled()
            TYPE_INACTIVITY -> preferences.isInactivityCheckEnabled()
            TYPE_STREAK -> preferences.isStreakCelebrationEnabled()
            TYPE_POST_RELAPSE -> preferences.isPostRelapseEnabled()
            TYPE_FRIDAY -> preferences.isFridayReminderEnabled()
            else -> false
        }
    }

    private fun getNotificationContent(type: String, currentStreak: Int): NotificationContent {
        return when (type) {
            TYPE_MORNING -> {
                val messages = listOf(
                    "Day $currentStreak. Set your intention for today.",
                    "Day $currentStreak. Start with awareness, end with strength.",
                    "Day $currentStreak. Allah sees your struggle and rewards it.",
                    "Day $currentStreak. A few minutes of check-in, a day of clarity.",
                    "Day $currentStreak. Rise with purpose. You've got this.",
                    "Day $currentStreak. Your morning intention changes everything.",
                    "Day $currentStreak. Check in with yourself before the world checks in on you."
                )
                NotificationContent(
                    title = "Taqwa \u2600\uFE0F",
                    message = messages.random(),
                    channelId = CHANNEL_ID_CHECKIN,
                    notificationId = NOTIFICATION_ID_MORNING,
                    priority = NotificationCompat.PRIORITY_DEFAULT
                )
            }

            TYPE_EVENING -> {
                val messages = listOf(
                    "Time to reflect on your day. How did it go?",
                    "Day $currentStreak closing. Let's capture your wins.",
                    "Evening reflection time. Close the loop before you rest.",
                    "Your evening wird awaits. A few taps to end the day right.",
                    "How was today? Take 2 minutes to reflect.",
                    "Before you sleep, check in with your soul.",
                    "Day $currentStreak almost done. Finish strong."
                )
                NotificationContent(
                    title = "Taqwa \uD83C\uDF19",
                    message = messages.random(),
                    channelId = CHANNEL_ID_CHECKIN,
                    notificationId = NOTIFICATION_ID_EVENING,
                    priority = NotificationCompat.PRIORITY_DEFAULT
                )
            }

            TYPE_DANGER_HOUR -> {
                val messages = listOf(
                    "Shield up. This is usually your hardest hour.",
                    "Heads up. Stay aware for the next couple hours.",
                    "Your data shows this is a vulnerable time. Stay strong.",
                    "This hour has been tough before. You're ready this time.",
                    "Alert: High-risk window approaching. Open the app if you need support.",
                    "Stay guarded. You know what this hour brings.",
                    "The next 2 hours need extra vigilance. You can do this."
                )
                NotificationContent(
                    title = "Taqwa \u26A0\uFE0F",
                    message = messages.random(),
                    channelId = CHANNEL_ID_ALERTS,
                    notificationId = NOTIFICATION_ID_DANGER_HOUR,
                    priority = NotificationCompat.PRIORITY_HIGH
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
                    title = "Taqwa \uD83D\uDCAD",
                    message = message,
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_MEMORY,
                    priority = NotificationCompat.PRIORITY_LOW
                )
            }

            TYPE_INACTIVITY -> {
                val messages = listOf(
                    "Haven't seen you in a while. Everything okay?",
                    "Just checking in. Your streak is at $currentStreak days.",
                    "It's been a few days. Come say hi.",
                    "Still here for you. Open when you're ready.",
                    "Your journey matters. Don't walk it alone.",
                    "Missing you. Even a quick check-in helps."
                )
                NotificationContent(
                    title = "Taqwa",
                    message = messages.random(),
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_INACTIVITY,
                    priority = NotificationCompat.PRIORITY_DEFAULT
                )
            }

            TYPE_STREAK -> {
                val message = getMilestoneMessage(currentStreak)
                NotificationContent(
                    title = "Taqwa \uD83C\uDFC6 Milestone!",
                    message = message,
                    channelId = CHANNEL_ID_MILESTONES,
                    notificationId = NOTIFICATION_ID_STREAK,
                    priority = NotificationCompat.PRIORITY_DEFAULT
                )
            }

            TYPE_POST_RELAPSE -> {
                val messages = listOf(
                    "You're still here. That takes courage. Write what you're feeling.",
                    "A setback, not the end. Today is a new beginning.",
                    "The strongest people are those who get back up. You're one of them.",
                    "Yesterday is gone. Today, you choose differently.",
                    "One fall doesn't erase your progress. Rise again.",
                    "Allah's mercy is greater than any mistake. Start fresh.",
                    "Write a letter to yourself. It helps more than you think."
                )
                NotificationContent(
                    title = "Taqwa \uD83D\uDCAA",
                    message = messages.random(),
                    channelId = CHANNEL_ID_POST_RELAPSE,
                    notificationId = NOTIFICATION_ID_POST_RELAPSE,
                    priority = NotificationCompat.PRIORITY_HIGH
                )
            }

            TYPE_FRIDAY -> {
                val messages = listOf(
                    "Jumu'ah Mubarak! A day of mercy and forgiveness.",
                    "It's Friday \u2014 the best day of the week. Make extra du'a today.",
                    "Jumu'ah Mubarak! Send salawat on the Prophet \uFE0E\uFE0E and ask for strength.",
                    "Friday is a fresh start. Read Surah Al-Kahf and renew your intention.",
                    "Jumu'ah Mubarak! On this day, there's an hour when every du'a is accepted.",
                    "It's Friday. Pray for yourself, your family, and your journey."
                )
                NotificationContent(
                    title = "Taqwa \uD83D\uDD4C Jumu'ah Mubarak",
                    message = messages.random(),
                    channelId = CHANNEL_ID_MOTIVATION,
                    notificationId = NOTIFICATION_ID_FRIDAY,
                    priority = NotificationCompat.PRIORITY_DEFAULT
                )
            }

            else -> NotificationContent(
                title = "Taqwa",
                message = "Stay strong on your journey.",
                channelId = CHANNEL_ID_CHECKIN,
                notificationId = NOTIFICATION_ID_MORNING,
                priority = NotificationCompat.PRIORITY_DEFAULT
            )
        }
    }

    private fun getMilestoneMessage(days: Int): String {
        return when (days) {
            1 -> "Day 1 complete! The hardest step is the first one. \uD83C\uDF31"
            3 -> "3 days! The initial storm is passing. \uD83D\uDCAA"
            7 -> "One full week! Your brain is starting to rewire. \uD83D\uDD25"
            14 -> "2 weeks! Dopamine receptors are healing. \u2B50"
            21 -> "21 days! A new habit is forming. \uD83C\uDFC5"
            30 -> "30 days! One full month. SubhanAllah. \uD83C\uDFC6"
            40 -> "40 days! In Islamic tradition, 40 days transforms a person. \uD83E\uDD32"
            60 -> "60 days! Two months of strength and discipline. \uD83D\uDC8E"
            90 -> "90 days! A major milestone. Deep healing has occurred. \uD83C\uDF1F"
            120 -> "120 days! Four months. You're a different person now. \u2728"
            180 -> "Half a year! 180 days of courage and taqwa. \uD83C\uDFC6"
            365 -> "ONE FULL YEAR! SubhanAllah. You are proof that change is possible. \uD83D\uDC51"
            500 -> "500 days! You are an inspiration. \uD83C\uDF1F"
            730 -> "TWO YEARS! May Allah continue to bless your journey. \uD83D\uDC8E"
            else -> "Day $days! MashaAllah, keep going! \uD83D\uDD25"
        }
    }

    fun isMilestoneDay(days: Int): Boolean {
        return days in listOf(1, 3, 7, 14, 21, 30, 40, 60, 90, 120, 180, 365, 500, 730)
    }

    private data class NotificationContent(
        val title: String,
        val message: String,
        val channelId: String,
        val notificationId: Int,
        val priority: Int
    )
}