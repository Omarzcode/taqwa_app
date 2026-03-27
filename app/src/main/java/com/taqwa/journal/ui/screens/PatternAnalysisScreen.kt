package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.ui.components.EmptyState
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import java.time.Instant
import java.time.ZoneId

@Composable
fun PatternAnalysisScreen(
    entries: List<JournalEntry>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "📊  My Patterns",
            onBack = onBack
        )

        if (entries.size < 3) {
            EmptyState(
                emoji = "📊",
                title = "Need More Data",
                description = "Complete at least 3 urge entries\nto see your patterns.\n\nYou have ${entries.size} so far."
            ) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                LinearProgressIndicator(
                    progress = { entries.size / 3f },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = PrimaryLight,
                    trackColor = BackgroundLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "${entries.size}/3 entries",
                    style = TaqwaType.caption,
                    color = TextGray
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
            ) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

                SummaryCard(entries)
                TimePatternCard(entries)
                FeelingsPatternCard(entries)
                NeedsPatternCard(entries)
                AlternativesCard(entries)
                UrgeStrengthCard(entries)
                InsightCard(entries)

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }
        }
    }
}

@Composable
private fun SummaryCard(entries: List<JournalEntry>) {
    val avgStrength = entries.map { it.urgeStrength }.average()
    val totalEntries = entries.size
    val now = System.currentTimeMillis()
    val oneWeekMs = 7 * 24 * 60 * 60 * 1000L
    val thisWeek = entries.count { it.timestamp > now - oneWeekMs }
    val lastWeek = entries.count {
        it.timestamp > now - (2 * oneWeekMs) && it.timestamp <= now - oneWeekMs
    }

    TaqwaCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📋  Overview",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PatternMiniStat("Total Entries", "$totalEntries")
                PatternMiniStat("Avg Strength", String.format("%.1f", avgStrength))
                PatternMiniStat("This Week", "$thisWeek")
            }

            if (lastWeek > 0) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                val change = thisWeek - lastWeek
                val changeText = when {
                    change < 0 -> "↓ ${-change} fewer urges than last week! 💪"
                    change > 0 -> "↑ $change more urges than last week"
                    else -> "Same as last week"
                }
                Text(
                    text = changeText,
                    style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = when {
                        change < 0 -> AccentGreen
                        change > 0 -> AccentOrange
                        else -> TextGray
                    }
                )
            }
        }
    }
}

@Composable
private fun PatternMiniStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = TaqwaType.screenTitle,
            color = VanillaCustard
        )
        Text(
            text = label,
            style = TaqwaType.captionSmall,
            color = TextGray
        )
    }
}

@Composable
private fun TimePatternCard(entries: List<JournalEntry>) {
    val timeSlots = mutableMapOf(
        "🌅 Morning (6AM-12PM)" to 0,
        "☀️ Afternoon (12PM-5PM)" to 0,
        "🌆 Evening (5PM-9PM)" to 0,
        "🌙 Night (9PM-12AM)" to 0,
        "🌑 Late Night (12AM-6AM)" to 0
    )

    entries.forEach { entry ->
        val hour = Instant.ofEpochMilli(entry.timestamp)
            .atZone(ZoneId.systemDefault()).hour
        when (hour) {
            in 6..11 -> timeSlots["🌅 Morning (6AM-12PM)"] = timeSlots["🌅 Morning (6AM-12PM)"]!! + 1
            in 12..16 -> timeSlots["☀️ Afternoon (12PM-5PM)"] = timeSlots["☀️ Afternoon (12PM-5PM)"]!! + 1
            in 17..20 -> timeSlots["🌆 Evening (5PM-9PM)"] = timeSlots["🌆 Evening (5PM-9PM)"]!! + 1
            in 21..23 -> timeSlots["🌙 Night (9PM-12AM)"] = timeSlots["🌙 Night (9PM-12AM)"]!! + 1
            else -> timeSlots["🌑 Late Night (12AM-6AM)"] = timeSlots["🌑 Late Night (12AM-6AM)"]!! + 1
        }
    }

    val sortedSlots = timeSlots.entries.sortedByDescending { it.value }
    val maxCount = sortedSlots.maxOf { it.value }.coerceAtLeast(1)

    PatternCard(
        title = "🕐  When Do Urges Hit?",
        subtitle = "Your danger zone: ${sortedSlots.first().key}",
        items = sortedSlots.map {
            PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
        }
    )
}

@Composable
private fun FeelingsPatternCard(entries: List<JournalEntry>) {
    val feelingsCount = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        entry.feelings.split(",").forEach { feeling ->
            val trimmed = feeling.trim()
            if (trimmed.isNotEmpty()) {
                feelingsCount[trimmed] = (feelingsCount[trimmed] ?: 0) + 1
            }
        }
    }

    val sorted = feelingsCount.entries.sortedByDescending { it.value }.take(6)
    val maxCount = sorted.maxOfOrNull { it.value }?.coerceAtLeast(1) ?: 1

    if (sorted.isNotEmpty()) {
        PatternCard(
            title = "💭  Most Common Feelings",
            subtitle = "Your #1 trigger feeling: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

@Composable
private fun NeedsPatternCard(entries: List<JournalEntry>) {
    val needsCount = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        entry.realNeed.split(",").forEach { need ->
            val trimmed = need.trim()
            if (trimmed.isNotEmpty()) {
                needsCount[trimmed] = (needsCount[trimmed] ?: 0) + 1
            }
        }
    }

    val sorted = needsCount.entries.sortedByDescending { it.value }.take(5)
    val maxCount = sorted.maxOfOrNull { it.value }?.coerceAtLeast(1) ?: 1

    if (sorted.isNotEmpty()) {
        PatternCard(
            title = "🎯  What You Actually Need",
            subtitle = "Most common real need: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

@Composable
private fun AlternativesCard(entries: List<JournalEntry>) {
    val altCount = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        entry.alternativeChosen.split(",").forEach { alt ->
            val trimmed = alt.trim()
            if (trimmed.isNotEmpty()) {
                altCount[trimmed] = (altCount[trimmed] ?: 0) + 1
            }
        }
    }

    val sorted = altCount.entries.sortedByDescending { it.value }.take(5)
    val maxCount = sorted.maxOfOrNull { it.value }?.coerceAtLeast(1) ?: 1

    if (sorted.isNotEmpty()) {
        PatternCard(
            title = "💪  Activities That Help You",
            subtitle = "Your go-to: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

@Composable
private fun UrgeStrengthCard(entries: List<JournalEntry>) {
    if (entries.size < 2) return

    val recentEntries = entries.sortedByDescending { it.timestamp }
    val recentAvg = recentEntries.take(3).map { it.urgeStrength }.average()
    val olderAvg = recentEntries.takeLast(
        (recentEntries.size / 2).coerceAtLeast(1)
    ).map { it.urgeStrength }.average()

    val trend = recentAvg - olderAvg

    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "📈  Urge Strength Trend",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", olderAvg),
                        style = TaqwaType.screenTitle,
                        color = TextGray
                    )
                    Text(
                        text = "Earlier",
                        style = TaqwaType.captionSmall,
                        color = TextGray
                    )
                }
                Text(
                    text = "→",
                    style = TaqwaType.screenTitle,
                    color = TextMuted
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", recentAvg),
                        style = TaqwaType.screenTitle,
                        color = if (trend < 0) AccentGreen else AccentOrange
                    )
                    Text(
                        text = "Recent",
                        style = TaqwaType.captionSmall,
                        color = TextGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            val trendText = when {
                trend < -1 -> "📉 Your urges are getting WEAKER. Keep going!"
                trend < 0 -> "📉 Slight improvement. You're on the right track!"
                trend < 1 -> "↔️ Staying stable. Keep pushing!"
                else -> "📈 Urges are stronger lately. Stay vigilant!"
            }

            Text(
                text = trendText,
                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = when {
                    trend < 0 -> AccentGreen
                    trend < 1 -> TextGray
                    else -> AccentOrange
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun InsightCard(entries: List<JournalEntry>) {
    val feelingsCount = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        entry.feelings.split(",").forEach { feeling ->
            val trimmed = feeling.trim()
            if (trimmed.isNotEmpty()) {
                feelingsCount[trimmed] = (feelingsCount[trimmed] ?: 0) + 1
            }
        }
    }
    val topFeeling = feelingsCount.maxByOrNull { it.value }?.key ?: ""

    val needsCount = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        entry.realNeed.split(",").forEach { need ->
            val trimmed = need.trim()
            if (trimmed.isNotEmpty()) {
                needsCount[trimmed] = (needsCount[trimmed] ?: 0) + 1
            }
        }
    }
    val topNeed = needsCount.maxByOrNull { it.value }?.key ?: ""

    val timeSlots = mutableMapOf<String, Int>()
    entries.forEach { entry ->
        val hour = Instant.ofEpochMilli(entry.timestamp)
            .atZone(ZoneId.systemDefault()).hour
        val slot = when (hour) {
            in 6..11 -> "morning"
            in 12..16 -> "afternoon"
            in 17..20 -> "evening"
            in 21..23 -> "night"
            else -> "late night"
        }
        timeSlots[slot] = (timeSlots[slot] ?: 0) + 1
    }
    val topTime = timeSlots.maxByOrNull { it.value }?.key ?: ""

    TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.4f) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "🧠  Personal Insight",
                style = TaqwaType.sectionTitle,
                color = VanillaCustard
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = buildString {
                    append("Based on your ${entries.size} journal entries:\n\n")
                    if (topTime.isNotEmpty()) {
                        append("⏰ Your danger zone is $topTime. ")
                        append("Be extra careful during this time. ")
                        append("Plan activities in advance.\n\n")
                    }
                    if (topFeeling.isNotEmpty()) {
                        append("💭 You most often feel $topFeeling before an urge. ")
                        append("When you notice this feeling, immediately open Taqwa.\n\n")
                    }
                    if (topNeed.isNotEmpty()) {
                        append("🎯 What you actually need is $topNeed. ")
                        append("Find healthy ways to fulfill this need daily.\n\n")
                    }
                    append("Remember: Understanding your patterns is the first step to breaking them.")
                },
                style = TaqwaType.body.copy(lineHeight = 22.sp),
                color = TextLight
            )
        }
    }
}

data class PatternItem(
    val label: String,
    val count: Int,
    val percentage: Float
)

@Composable
private fun PatternCard(
    title: String,
    subtitle: String,
    items: List<PatternItem>
) {
    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
            Text(
                text = subtitle,
                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = PrimaryLight
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            items.forEach { item ->
                Column(modifier = Modifier.padding(vertical = TaqwaDimens.spaceXS)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.label,
                            style = TaqwaType.bodySmall,
                            color = TextLight,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${item.count}x",
                            style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = TextGray
                        )
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    LinearProgressIndicator(
                        progress = { item.percentage },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PrimaryLight,
                        trackColor = BackgroundLight
                    )
                }
            }
        }
    }
}