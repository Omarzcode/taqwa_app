package com.taqwa.journal.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.taqwa.journal.MainActivity
import com.taqwa.journal.R
import com.taqwa.journal.data.preferences.StreakManager

class StreakMiniWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val streakManager = StreakManager(context)
        val currentStreak = streakManager.getCurrentStreak()

        provideContent {
            StreakMiniContent(currentStreak)
        }
    }
}

@Composable
private fun StreakMiniContent(currentStreak: Int) {
    val emoji = when {
        currentStreak >= 100 -> "\uD83D\uDC8E"
        currentStreak >= 30 -> "\uD83C\uDFC6"
        currentStreak >= 7 -> "\uD83D\uDD25"
        currentStreak >= 1 -> "\uD83C\uDF31"
        else -> "\uD83E\uDD32"
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(R.color.widget_bg)
            .clickable(actionStartActivity<MainActivity>())
            .padding(8.dp),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(
            text = emoji,
            style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center)
        )
        Text(
            text = "$currentStreak",
            style = TextStyle(
                color = ColorProvider(R.color.widget_text_white),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = if (currentStreak == 1) "day" else "days",
            style = TextStyle(
                color = ColorProvider(R.color.widget_text_muted),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}