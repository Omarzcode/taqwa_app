package com.taqwa.journal.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.action.actionStartActivity as appWidgetActionStartActivity
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.taqwa.journal.MainActivity
import com.taqwa.journal.R

class SosWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            SosWidgetContent()
        }
    }
}

@Composable
private fun SosWidgetContent() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(R.drawable.widget_sos_bg))
            .clickable(
                appWidgetActionStartActivity(
                    Intent("com.taqwa.journal.ACTION_URGE").setClassName(
                        "com.taqwa.journal",
                        "com.taqwa.journal.MainActivity"
                    ).putExtra(MainActivity.EXTRA_NAVIGATE_TO, "breathing")
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = "\uD83C\uDD98",
                style = TextStyle(
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = GlanceModifier.height(2.dp))
            Text(
                text = "HELP",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Tap now",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_light),
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}