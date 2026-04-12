package com.taqwa.journal.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
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
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.action.actionStartActivity as appWidgetActionStartActivity
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
    ayahText: String,
    ayahRef: String,
    nextMilestone: Int,
    checkedIn: Boolean,
    isMorning: Boolean
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(R.color.widget_bg)
            .clickable(actionStartActivity<MainActivity>())
            .padding(14.dp)
    ) {
        // Section 1: Header
        HeaderSection(currentStreak, statusText, nextMilestone, longestStreak)

        // Flexible spacer pushes content apart
        Spacer(modifier = GlanceModifier.defaultWeight())

        // Section 2: Check-in
        CheckInSection(checkedIn, isMorning)

        // Flexible spacer
        Spacer(modifier = GlanceModifier.defaultWeight())

        // Section 3: Quran
        QuranSection(ayahText, ayahRef)

        // Flexible spacer
        Spacer(modifier = GlanceModifier.defaultWeight())

        // Section 4: Buttons
        ButtonsSection()
    }
}

@Composable
private fun HeaderSection(
    currentStreak: Int,
    statusText: String,
    nextMilestone: Int,
    longestStreak: Int
) {
    Column(modifier = GlanceModifier.fillMaxWidth()) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Text(
                text = "Taqwa",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_gold),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = GlanceModifier.defaultWeight())
            Text(
                text = "Day $currentStreak",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Row(
            modifier = GlanceModifier.fillMaxWidth().padding(top = 2.dp)
        ) {
            Text(
                text = statusText,
                style = TextStyle(
                    color = ColorProvider(R.color.widget_green),
                    fontSize = 11.sp
                )
            )
            Spacer(modifier = GlanceModifier.defaultWeight())
            Text(
                text = "Next: $nextMilestone | Best: $longestStreak",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_muted),
                    fontSize = 10.sp
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
            .background(R.color.widget_card)
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
                    text = "Checked in today",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_green_light),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = GlanceModifier.defaultWeight())
                Text(
                    text = "View",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_text_muted),
                        fontSize = 11.sp
                    )
                )
            } else {
                Text(
                    text = if (isMorning) "Morning Check-In" else "Daily Check-In",
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_orange),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = GlanceModifier.defaultWeight())
                Text(
                    text = "START",
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
            text = "\"$ayahText\"",
            style = TextStyle(
                color = ColorProvider(R.color.widget_text_light),
                fontSize = 12.sp
            ),
            maxLines = 3
        )
        Text(
            text = ayahRef,
            style = TextStyle(
                color = ColorProvider(R.color.widget_gold),
                fontSize = 11.sp
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
                .background(R.color.widget_primary_dark)
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
                text = "Shield Plan",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }
        Spacer(modifier = GlanceModifier.width(8.dp))
        Box(
            modifier = GlanceModifier
                .defaultWeight()
                .background(R.color.widget_red)
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
                text = "Urge SOS",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_white),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}