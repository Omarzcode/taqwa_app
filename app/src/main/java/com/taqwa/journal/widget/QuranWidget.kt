package com.taqwa.journal.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.taqwa.journal.MainActivity
import com.taqwa.journal.R
import com.taqwa.journal.data.preferences.DailyQuranManager

class QuranWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val quranManager = DailyQuranManager(context)
        val todaysAyah = quranManager.getTodaysAyah()

        provideContent {
            QuranWidgetContent(
                arabic = todaysAyah.arabic,
                translation = todaysAyah.translation,
                reference = todaysAyah.reference
            )
        }
    }
}

@Composable
private fun QuranWidgetContent(
    arabic: String,
    translation: String,
    reference: String
) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(R.drawable.widget_bg_quran))
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp)
    ) {
        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            // Header
            Text(
                text = "\uD83D\uDCD6 Daily Verse",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_muted),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier.fillMaxWidth()
            )

            Spacer(modifier = GlanceModifier.height(8.dp))

            // Arabic
            Text(
                text = arabic,
                style = TextStyle(
                    color = ColorProvider(R.color.widget_gold),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 3,
                modifier = GlanceModifier.fillMaxWidth()
            )

            Spacer(modifier = GlanceModifier.height(8.dp))

            // Gold divider
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 40.dp)
                    .background(ImageProvider(R.drawable.widget_divider_gold))
            ) {}

            Spacer(modifier = GlanceModifier.height(8.dp))

            // Translation
            Text(
                text = "\u201C$translation\u201D",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_light),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 3,
                modifier = GlanceModifier.fillMaxWidth()
            )

            Spacer(modifier = GlanceModifier.height(6.dp))

            // Reference
            Text(
                text = "\u2014 $reference",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_gold),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier.fillMaxWidth()
            )
        }
    }
}