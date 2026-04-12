package com.taqwa.journal.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object WidgetUpdater {

    fun updateAllWidgets(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try { TaqwaStreakWidget().updateAll(context) } catch (_: Exception) {}
            try { QuranWidget().updateAll(context) } catch (_: Exception) {}
            try { StreakMiniWidget().updateAll(context) } catch (_: Exception) {}
            try { SosWidget().updateAll(context) } catch (_: Exception) {}
        }
    }
}