package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.ui.components.EmptyStateCard
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun RelapseHistoryScreen(
    relapseHistory: List<RelapseRecord>,
    totalRelapses: Int,
    currentStreak: Int,
    longestStreak: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "\uD83D\uDCC9  Relapse History",
            onBack = onBack
        )

        if (relapseHistory.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                contentAlignment = Alignment.Center
            ) {
                EmptyStateCard(
                    emoji = "\uD83C\uDFC6",
                    title = "No relapses recorded",
                    subtitle = "Keep going strong!\nMay Allah keep you steadfast."
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing),
                contentPadding = PaddingValues(
                    top = TaqwaDimens.spaceS,
                    bottom = TaqwaDimens.spaceL
                )
            ) {
                // Summary card
                item {
                    TaqwaCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Relapses: $totalRelapses",
                                style = TaqwaType.sectionTitle,
                                color = AccentOrange
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                SummaryMiniStat(
                                    emoji = "\uD83D\uDD25",
                                    value = "$currentStreak",
                                    label = "Current"
                                )
                                SummaryMiniStat(
                                    emoji = "\uD83D\uDC51",
                                    value = "$longestStreak",
                                    label = "Best"
                                )
                                SummaryMiniStat(
                                    emoji = "\uD83D\uDCC8",
                                    value = calculateAverageStreak(relapseHistory),
                                    label = "Average"
                                )
                                SummaryMiniStat(
                                    emoji = "\uD83C\uDFAF",
                                    value = findTopTrigger(relapseHistory),
                                    label = "Top Trigger"
                                )
                            }

                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                            Text(
                                text = "Every relapse is a lesson.\nStudy your patterns. Learn. Grow.",
                                style = TaqwaType.bodySmall,
                                color = TextGray,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                itemsIndexed(relapseHistory) { index, record ->
                    RelapseCard(
                        number = index + 1,
                        record = record
                    )
                }

                item {
                    TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "\u0648\u064E\u0644\u064E\u0627 \u062A\u064E\u064A\u0652\u0623\u064E\u0633\u064F\u0648\u0627 \u0645\u0650\u0646 \u0631\u0651\u064E\u0648\u0652\u062D\u0650 \u0627\u0644\u0644\u0651\u064E\u0647\u0650",
                                style = TaqwaType.arabicMedium,
                                color = VanillaCustard,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "\"And do not despair of the mercy of Allah.\"",
                                style = TaqwaType.bodySmall,
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "\u2014 Surah Yusuf 12:87",
                                style = TaqwaType.captionSmall,
                                color = TextGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryMiniStat(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
        Text(
            text = value,
            style = TaqwaType.statValue,
            color = TextWhite,
            maxLines = 1
        )
        Text(
            text = label,
            style = TaqwaType.statLabel,
            color = TextGray,
            maxLines = 1
        )
    }
}

@Composable
private fun RelapseCard(
    number: Int,
    record: RelapseRecord
) {
    val formattedDate = try {
        val date = LocalDate.parse(record.date)
        date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e: Exception) {
        record.date
    }

    val daysAgo = try {
        val date = LocalDate.parse(record.date)
        val days = ChronoUnit.DAYS.between(date, LocalDate.now()).toInt()
        when (days) {
            0 -> "Today"
            1 -> "Yesterday"
            else -> "${days}d ago"
        }
    } catch (e: Exception) { "" }

    // Parse rich recovery data from reason string
    val parsed = parseRelapseReason(record.reason)

    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Relapse #$number",
                    style = TaqwaType.cardTitle,
                    color = AccentOrange
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = formattedDate,
                        style = TaqwaType.bodySmall,
                        color = TextGray
                    )
                    if (daysAgo.isNotEmpty()) {
                        Text(
                            text = daysAgo,
                            style = TaqwaType.captionSmall,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            if (record.streakLost > 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "\uD83D\uDCC9", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = "Lost a ${record.streakLost}-day streak",
                        style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
                        color = TextLight
                    )
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            // Rich data display (from recovery flow)
            if (parsed.isRichData) {
                HorizontalDivider(
                    color = DividerColor,
                    thickness = TaqwaDimens.dividerThickness
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                if (parsed.trigger.isNotBlank()) {
                    RelapseDetailRow(
                        emoji = "\u26A1",
                        label = "Trigger",
                        value = parsed.trigger
                    )
                }
                if (parsed.feeling.isNotBlank()) {
                    RelapseDetailRow(
                        emoji = "\uD83D\uDCAD",
                        label = "Feeling",
                        value = parsed.feeling
                    )
                }
                if (parsed.situation.isNotBlank()) {
                    RelapseDetailRow(
                        emoji = "\uD83D\uDDFA\uFE0F",
                        label = "Situation",
                        value = parsed.situation
                    )
                }
            } else if (record.reason.isNotEmpty()) {
                // Simple reason (old format or manual reset)
                Text(
                    text = "\uD83D\uDCAD Reflection:",
                    style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = PrimaryLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
                Text(
                    text = record.reason,
                    style = TaqwaType.body.copy(lineHeight = 20.sp),
                    color = TextGray
                )
            }
        }
    }
}

@Composable
private fun RelapseDetailRow(
    emoji: String,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXXS),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = emoji, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
        Column {
            Text(
                text = label,
                style = TaqwaType.captionSmall,
                color = TextMuted
            )
            Text(
                text = value,
                style = TaqwaType.body.copy(lineHeight = 20.sp),
                color = TextLight
            )
        }
    }
    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
}

// ════════════════════════════════════════════
// HELPER FUNCTIONS
// ════════════════════════════════════════════

private data class ParsedReason(
    val trigger: String = "",
    val feeling: String = "",
    val situation: String = "",
    val isRichData: Boolean = false
)

private fun parseRelapseReason(reason: String): ParsedReason {
    if (!reason.contains("Trigger:") && !reason.contains("Feeling:") && !reason.contains("Situation:")) {
        return ParsedReason(isRichData = false)
    }

    val parts = reason.split("; ")
    var trigger = ""
    var feeling = ""
    var situation = ""

    parts.forEach { part ->
        when {
            part.startsWith("Trigger: ") -> trigger = part.removePrefix("Trigger: ")
            part.startsWith("Feeling: ") -> feeling = part.removePrefix("Feeling: ")
            part.startsWith("Situation: ") -> situation = part.removePrefix("Situation: ")
        }
    }

    return ParsedReason(
        trigger = trigger,
        feeling = feeling,
        situation = situation,
        isRichData = true
    )
}

private fun calculateAverageStreak(history: List<RelapseRecord>): String {
    if (history.isEmpty()) return "0"
    val streaks = history.map { it.streakLost }
    val avg = streaks.average()
    return if (avg >= 1) "${avg.toInt()}d" else "0d"
}

private fun findTopTrigger(history: List<RelapseRecord>): String {
    if (history.isEmpty()) return "-"

    val triggerCounts = mutableMapOf<String, Int>()
    history.forEach { record ->
        val parsed = parseRelapseReason(record.reason)
        if (parsed.trigger.isNotBlank()) {
            triggerCounts[parsed.trigger] = (triggerCounts[parsed.trigger] ?: 0) + 1
        }
    }

    return if (triggerCounts.isNotEmpty()) {
        triggerCounts.maxByOrNull { it.value }?.key ?: "-"
    } else "-"
}