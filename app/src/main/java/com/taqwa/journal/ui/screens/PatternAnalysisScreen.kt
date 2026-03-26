package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.taqwa.journal.ui.theme.*
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
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
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "📊  My Patterns",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BackgroundDark,
                titleContentColor = TextWhite
            )
        )

        if (entries.size < 3) {
            // Not enough data
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = "📊",
                        fontSize = 64.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Need More Data",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Complete at least 3 urge entries\nto see your patterns.\n\nYou have ${entries.size} so far.",
                        fontSize = 14.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { entries.size / 3f },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PrimaryLight,
                        trackColor = BackgroundLight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${entries.size}/3 entries",
                        fontSize = 13.sp,
                        color = TextGray
                    )
                }
            }
        } else {
            // Show patterns
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Summary card
                SummaryCard(entries)

                // Time patterns
                TimePatternCard(entries)

                // Feelings patterns
                FeelingsPatternCard(entries)

                // Real needs patterns
                NeedsPatternCard(entries)

                // Alternatives that work
                AlternativesCard(entries)

                // Urge strength trend
                UrgeStrengthCard(entries)

                // Insight card
                InsightCard(entries)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ========================
// Summary Card
// ========================
@Composable
private fun SummaryCard(entries: List<JournalEntry>) {
    val avgStrength = entries.map { it.urgeStrength }.average()
    val totalEntries = entries.size

    // Calculate entries this week vs last week
    val now = System.currentTimeMillis()
    val oneWeekMs = 7 * 24 * 60 * 60 * 1000L
    val thisWeek = entries.count { it.timestamp > now - oneWeekMs }
    val lastWeek = entries.count {
        it.timestamp > now - (2 * oneWeekMs) && it.timestamp <= now - oneWeekMs
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📋 Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MiniStat("Total Entries", "$totalEntries")
                MiniStat("Avg Strength", String.format("%.1f", avgStrength))
                MiniStat("This Week", "$thisWeek")
            }

            if (lastWeek > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                val change = thisWeek - lastWeek
                val changeText = when {
                    change < 0 -> "↓ ${-change} fewer urges than last week! 💪"
                    change > 0 -> "↑ $change more urges than last week"
                    else -> "Same as last week"
                }
                val changeColor = when {
                    change < 0 -> AccentGreen
                    change > 0 -> AccentOrange
                    else -> TextGray
                }
                Text(
                    text = changeText,
                    fontSize = 13.sp,
                    color = changeColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun MiniStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AccentGold
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextGray
        )
    }
}

// ========================
// Time Pattern Card
// ========================
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
            .atZone(ZoneId.systemDefault())
            .hour

        when (hour) {
            in 6..11 -> timeSlots["🌅 Morning (6AM-12PM)"] =
                timeSlots["🌅 Morning (6AM-12PM)"]!! + 1
            in 12..16 -> timeSlots["☀️ Afternoon (12PM-5PM)"] =
                timeSlots["☀️ Afternoon (12PM-5PM)"]!! + 1
            in 17..20 -> timeSlots["🌆 Evening (5PM-9PM)"] =
                timeSlots["🌆 Evening (5PM-9PM)"]!! + 1
            in 21..23 -> timeSlots["🌙 Night (9PM-12AM)"] =
                timeSlots["🌙 Night (9PM-12AM)"]!! + 1
            else -> timeSlots["🌑 Late Night (12AM-6AM)"] =
                timeSlots["🌑 Late Night (12AM-6AM)"]!! + 1
        }
    }

    val sortedSlots = timeSlots.entries.sortedByDescending { it.value }
    val maxCount = sortedSlots.maxOf { it.value }.coerceAtLeast(1)

    PatternCard(
        title = "🕐 When Do Urges Hit?",
        subtitle = "Your danger zone: ${sortedSlots.first().key}",
        items = sortedSlots.map {
            PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
        }
    )
}

// ========================
// Feelings Pattern Card
// ========================
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
            title = "💭 Most Common Feelings",
            subtitle = "Your #1 trigger feeling: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

// ========================
// Needs Pattern Card
// ========================
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
            title = "🎯 What You Actually Need",
            subtitle = "Most common real need: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

// ========================
// Alternatives Card
// ========================
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
            title = "💪 Activities That Help You",
            subtitle = "Your go-to: ${sorted.first().key}",
            items = sorted.map {
                PatternItem(it.key, it.value, it.value.toFloat() / maxCount)
            }
        )
    }
}

// ========================
// Urge Strength Card
// ========================
@Composable
private fun UrgeStrengthCard(entries: List<JournalEntry>) {
    if (entries.size < 2) return

    val recentEntries = entries.sortedByDescending { it.timestamp }
    val recentAvg = recentEntries.take(3).map { it.urgeStrength }.average()
    val olderAvg = recentEntries.takeLast(
        (recentEntries.size / 2).coerceAtLeast(1)
    ).map { it.urgeStrength }.average()

    val trend = recentAvg - olderAvg

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "📈 Urge Strength Trend",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", olderAvg),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextGray
                    )
                    Text("Earlier", fontSize = 12.sp, color = TextGray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "→",
                        fontSize = 28.sp,
                        color = TextGray
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", recentAvg),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (trend < 0) AccentGreen else AccentOrange
                    )
                    Text("Recent", fontSize = 12.sp, color = TextGray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val trendText = when {
                trend < -1 -> "📉 Your urges are getting WEAKER. Keep going!"
                trend < 0 -> "📉 Slight improvement. You're on the right track!"
                trend < 1 -> "↔️ Staying stable. Keep pushing!"
                else -> "📈 Urges are stronger lately. Stay vigilant!"
            }
            val trendColor = when {
                trend < 0 -> AccentGreen
                trend < 1 -> TextGray
                else -> AccentOrange
            }

            Text(
                text = trendText,
                fontSize = 14.sp,
                color = trendColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ========================
// Insight Card
// ========================
@Composable
private fun InsightCard(entries: List<JournalEntry>) {
    // Generate personalized insight
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
            .atZone(ZoneId.systemDefault())
            .hour
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

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryDark.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "🧠 Personal Insight",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
            Spacer(modifier = Modifier.height(12.dp))

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
                fontSize = 14.sp,
                color = TextLight,
                lineHeight = 22.sp
            )
        }
    }
}

// ========================
// Reusable Pattern Card
// ========================
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = PrimaryLight,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            items.forEach { item ->
                Column(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.label,
                            fontSize = 13.sp,
                            color = TextLight,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${item.count}x",
                            fontSize = 13.sp,
                            color = TextGray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { item.percentage },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PrimaryLight,
                        trackColor = BackgroundLight
                    )
                }
            }
        }
    }
}