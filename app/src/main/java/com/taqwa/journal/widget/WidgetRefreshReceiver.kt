package com.taqwa.journal.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Receives midnight alarm to refresh all widgets so streak count updates daily.
 * Also reschedules itself for the next midnight.
 */
class WidgetRefreshReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("TaqwaWidget", "WidgetRefreshReceiver: midnight refresh triggered")
        WidgetUpdater.updateAllWidgets(context)
        WidgetUpdater.scheduleMidnightRefresh(context)
    }
}