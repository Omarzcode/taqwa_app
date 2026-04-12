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
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.action.actionStartActivity as appWidgetActionStartActivity
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
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.taqwa.journal.MainActivity
import com.taqwa.journal.R
import com.taqwa.journal.data.preferences.DailyQuranManager
import com.taqwa.journal.data.preferences.StreakManager
import com.taqwa.journal.data.database.JournalDatabase
import java.time.LocalDate
import java.time.LocalTime

class TaqwaStreakWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val streakManager = StreakManager(context)
        val quranManager = DailyQuranManager(context)

        val currentStreak = streakManager.getCurrentStreak()
        val longestStreak = streakManager.getLongestStreak()
        val statusText = streakManager.getStreakStatusText()
        val todaysAyah = quranManager.getTodaysAyah()
        val nextMilestone = getNextMilestone(currentStreak)

        val streakEmoji = when {
            currentStreak >= 100 -> "\uD83D\uDC8E"
            currentStreak >= 30 -> "\uD83C\uDFC6"
            currentStreak >= 7 -> "\uD83D\uDD25"
            currentStreak >= 1 -> "\uD83C\uDF31"
            else -> "\uD83E\uDD32"
        }

        val checkedInToday = try {
            val db = JournalDatabase.getDatabase(context)
            val today = LocalDate.now().toString()
            db.journalDao().getCheckInForDate(today) != null
        } catch (e: Exception) {
            false
        }

        val currentHour = LocalTime.now().hour
        val isMorning = currentHour in 4..11

        provideContent {
            WidgetBody(
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                statusText = statusText,
                streakEmoji = streakEmoji,
                ayahText = todaysAyah.translation,
                ayahRef = todaysAyah.reference,
                nextMilestone = nextMilestone,
                checkedIn = checkedInToday,
                isMorning = isMorning
            )
        }
    }

    private fun getNextMilestone(current: Int): Int {
        val milestones = listOf(1, 3, 7, 14, 21, 30, 60, 90, 180, 365)
        return milestones.firstOrNull { it > current } ?: (current + 30)
    }
}

@Composable
private fun WidgetBody(
    currentStreak: Int,
    longestStreak: Int,
    statusText: String,
    streakEmoji: String,
    ayahText: String,
    ayahRef: String,
    nextMilestone: Int,
    checkedIn: Boolean,
    isMorning: Boolean
) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(R.drawable.widget_bg_rounded))
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp)
    ) {
        Column(modifier = GlanceModifier.fillMaxSize()) {
            // Header
            HeaderSection(currentStreak, statusText, streakEmoji, nextMilestone, longestStreak)

            Spacer(modifier = GlanceModifier.defaultWeight())

            // Divider
            Box(modifier = GlanceModifier.fillMaxWidth().height(1.dp).background(ImageProvider(R.drawable.widget_divider_gold))) {}

            Spacer(modifier = GlanceModifier.defaultWeight())

            // Check-in
            CheckInSection(checkedIn, isMorning)

            Spacer(modifier = GlanceModifier.defaultWeight())

            // Quran
            QuranSection(ayahText, ayahRef)

            Spacer(modifier = GlanceModifier.defaultWeight())

            // Buttons
            ButtonsSection()
        }
    }
}

@Composable
private fun HeaderSection(
    currentStreak: Int,
    statusText: String,
    streakEmoji: String,
    nextMilestone: Int,
    longestStreak: Int
) {
    Column(modifier = GlanceModifier.fillMaxWidth()) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                    Text(
                        text = "$streakEmoji Taqwa",
                        style = TextStyle(
                            color = ColorProvider(R.color.widget_gold),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Text(
                    text = statusText,
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_green),
                        fontSize = 10.sp
                    ),
                    modifier = GlanceModifier.padding(top = 1.dp)
                )
            }
            Spacer(modifier = GlanceModifier.defaultWeight())
            Column(horizontalAlignment = Alignment.Horizontal.End) {
                Text(
                    text = "$currentStreak",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_text_white),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                )
                Text(
                    text = if (currentStreak == 1) "day" else "days",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_text_muted),
                        fontSize = 10.sp,
                        textAlign = TextAlign.End
                    )
                )
            }
        }
        Row(
            modifier = GlanceModifier.fillMaxWidth().padding(top = 2.dp)
        ) {
            Text(
                text = "\u2192 $nextMilestone days",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_muted),
                    fontSize = 9.sp
                )
            )
            Spacer(modifier = GlanceModifier.defaultWeight())
            Text(
                text = "Best: $longestStreak",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_muted),
                    fontSize = 9.sp
                )
            )
        }
    }
}

@Composable
private fun CheckInSection(checkedIn: Boolean, isMorning: Boolean) {
    Box(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(ImageProvider(R.drawable.widget_checkin_bg))
            .clickable(
                appWidgetActionStartActivity(
                    Intent("com.taqwa.journal.ACTION_CHECKIN").setClassName(
                        "com.taqwa.journal",
                        "com.taqwa.journal.MainActivity"
                    ).putExtra(MainActivity.EXTRA_NAVIGATE_TO, "morning_check_in")
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            )
            .padding(10.dp)
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            if (checkedIn) {
                Text(
                    text = "\u2705  Checked in today",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_green_light),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            } else {
                Text(
                    text = if (isMorning) "\u2600\uFE0F  Morning Check-In" else "\uD83C\uDF19  Evening Check-In",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_orange),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = GlanceModifier.defaultWeight())
                Text(
                    text = "START \u203A",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_gold),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun QuranSection(ayahText: String, ayahRef: String) {
    Column(modifier = GlanceModifier.fillMaxWidth()) {
        Text(
            text = "\u201C$ayahText\u201D",
            style = TextStyle(
                color = ColorProvider(R.color.widget_text_light),
                fontSize = 13.sp
            ),
            maxLines = 3
        )
        Text(
            text = "\u2014 $ayahRef",
            style = TextStyle(
                color = ColorProvider(R.color.widget_gold),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = GlanceModifier.padding(top = 3.dp)
        )
    }
}

@Composable
private fun ButtonsSection() {
    Row(modifier = GlanceModifier.fillMaxWidth()) {
        Box(
            modifier = GlanceModifier
                .defaultWeight()
                .background(ImageProvider(R.drawable.widget_btn_brown))
                .clickable(
                    appWidgetActionStartActivity(
                        Intent("com.taqwa.journal.ACTION_SHIELD").setClassName(
                            "com.taqwa.journal",
                            "com.taqwa.journal.MainActivity"
                        ).putExtra(MainActivity.EXTRA_NAVIGATE_TO, "shield_plans")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\uD83D\uDEE1\uFE0F Shield Plan",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }
        Spacer(modifier = GlanceModifier.width(8.dp))
        Box(
            modifier = GlanceModifier
                .defaultWeight()
                .background(ImageProvider(R.drawable.widget_btn_red))
                .clickable(
                    appWidgetActionStartActivity(
                        Intent("com.taqwa.journal.ACTION_URGE").setClassName(
                            "com.taqwa.journal",
                            "com.taqwa.journal.MainActivity"
                        ).putExtra(MainActivity.EXTRA_NAVIGATE_TO, "breathing")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\uD83C\uDD98 Urge SOS",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}