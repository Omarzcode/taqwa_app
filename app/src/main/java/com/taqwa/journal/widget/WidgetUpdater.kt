package com.taqwa.journal.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

object WidgetUpdater {

    private const val REQUEST_MIDNIGHT_REFRESH = 9001

    /**
     * Immediately refresh all Taqwa widgets.
     * Call after: relapse, check-in, app open, midnight, boot.
     */
    fun updateAllWidgets(context: Context) {
        Log.d("TaqwaWidget", "updateAllWidgets called")
        CoroutineScope(Dispatchers.IO).launch {
            try { TaqwaStreakWidget().updateAll(context) } catch (e: Exception) {
                Log.e("TaqwaWidget", "Failed to update TaqwaStreakWidget", e)
            }
            try { QuranWidget().updateAll(context) } catch (e: Exception) {
                Log.e("TaqwaWidget", "Failed to update QuranWidget", e)
            }
            try { StreakMiniWidget().updateAll(context) } catch (e: Exception) {
                Log.e("TaqwaWidget", "Failed to update StreakMiniWidget", e)
            }
            try { SosWidget().updateAll(context) } catch (e: Exception) {
                Log.e("TaqwaWidget", "Failed to update SosWidget", e)
            }
        }
    }

    /**
     * Schedule an alarm at midnight (00:00:05) to refresh all widgets.
     * The receiver will re-schedule for the next midnight after firing.
     */
    fun scheduleMidnightRefresh(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, WidgetRefreshReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_MIDNIGHT_REFRESH,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Calculate next midnight + 5 seconds (to ensure day has flipped)
        val midnight = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 5)
            set(Calendar.MILLISECOND, 0)
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        midnight.timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        midnight.timeInMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    midnight.timeInMillis,
                    pendingIntent
                )
            }
            Log.d("TaqwaWidget", "Midnight refresh scheduled for: ${midnight.time}")
        } catch (e: SecurityException) {
            // Fallback to inexact
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                midnight.timeInMillis,
                pendingIntent
            )
            Log.d("TaqwaWidget", "Midnight refresh scheduled (inexact) for: ${midnight.time}")
        }
    }
}