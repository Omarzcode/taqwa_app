package com.taqwa.journal.features.browse.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import com.taqwa.journal.features.streak.data.StreakManager.RelapseRecord
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun CalendarScreen(
    entries: List<JournalEntry>,
    relapseHistory: List<RelapseRecord>,
    streakStartDate: String?,
    onBack: () -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()

    val entryDates = remember(entries) {
        entries.map { entry ->
            java.time.Instant.ofEpochMilli(entry.timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }.toSet()
    }

    val relapseDates = remember(relapseHistory) {
        relapseHistory.mapNotNull { record ->
            try { LocalDate.parse(record.date) } catch (e: Exception) { null }
        }.toSet()
    }

    val streakStart = remember(streakStartDate) {
        try {
            if (streakStartDate != null) LocalDate.parse(streakStartDate) else null
        } catch (e: Exception) { null }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "📅  My Calendar",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            // Streak info
            if (streakStart != null) {
                val streakDays = ChronoUnit.DAYS.between(streakStart, today).toInt()
                TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🔥", fontSize = 20.sp)
                            Text(
                                text = "$streakDays days",
                                style = TaqwaType.cardTitle,
                                color = VanillaCustard
                            )
                            Text(
                                text = "Current Streak",
                                style = TaqwaType.statLabel,
                                color = TextGray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "📅", fontSize = 20.sp)
                            Text(
                                text = streakStart.format(
                                    java.time.format.DateTimeFormatter.ofPattern("MMM dd")
                                ),
                                style = TaqwaType.cardTitle,
                                color = TextWhite
                            )
                            Text(
                                text = "Streak Started",
                                style = TaqwaType.statLabel,
                                color = TextGray
                            )
                        }
                    }
                }
            }

            // Legend
            TaqwaCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LegendItem(color = AccentGreen, label = "Clean Day")
                    LegendItem(color = VanillaCustard, label = "Urge Defeated")
                    LegendItem(color = AccentRed, label = "Relapse")
                }
            }

            // Calendar
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Month navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { currentMonth = currentMonth.minusMonths(1) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Previous Month",
                                tint = TextWhite
                            )
                        }

                        Text(
                            text = "${
                                currentMonth.month.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.getDefault()
                                )
                            } ${currentMonth.year}",
                            style = TaqwaType.sectionTitle,
                            color = VanillaCustard
                        )

                        IconButton(
                            onClick = {
                                if (currentMonth.isBefore(YearMonth.now())) {
                                    currentMonth = currentMonth.plusMonths(1)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Next Month",
                                tint = if (currentMonth.isBefore(YearMonth.now()))
                                    TextWhite else TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    // Day headers
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                            Text(
                                text = day,
                                style = TaqwaType.captionSmall.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = TextMuted,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                    // Calendar grid
                    val firstDayOfMonth = currentMonth.atDay(1)
                    val daysInMonth = currentMonth.lengthOfMonth()
                    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value
                    val totalCells = (startDayOfWeek - 1) + daysInMonth
                    val rows = (totalCells + 6) / 7

                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (col in 1..7) {
                                val dayIndex = row * 7 + col - (startDayOfWeek - 1)

                                if (dayIndex < 1 || dayIndex > daysInMonth) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                    )
                                } else {
                                    val date = currentMonth.atDay(dayIndex)
                                    val isToday = date == today
                                    val isFuture = date.isAfter(today)
                                    val isRelapse = relapseDates.contains(date)
                                    val hasEntry = entryDates.contains(date)
                                    val isInStreak = streakStart != null &&
                                            !date.isBefore(streakStart) &&
                                            !date.isAfter(today) &&
                                            !isRelapse
                                    val isBeforeStreak = streakStart == null ||
                                            date.isBefore(streakStart)

                                    val bgColor = when {
                                        isFuture -> BackgroundCard
                                        isRelapse -> AccentRed.copy(alpha = 0.25f)
                                        hasEntry && isInStreak -> VanillaCustard.copy(alpha = 0.25f)
                                        isInStreak -> AccentGreen.copy(alpha = 0.12f)
                                        isBeforeStreak -> BackgroundCard
                                        else -> BackgroundCard
                                    }

                                    val textColor = when {
                                        isFuture -> TextMuted
                                        isToday -> VanillaCustard
                                        isRelapse -> AccentRed
                                        hasEntry && isInStreak -> VanillaCustard
                                        isInStreak -> AccentGreen
                                        else -> TextGray
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                            .clip(CircleShape)
                                            .background(bgColor)
                                            .then(
                                                if (isToday) Modifier.border(
                                                    width = 1.5.dp,
                                                    color = VanillaCustard,
                                                    shape = CircleShape
                                                ) else Modifier
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "$dayIndex",
                                                style = TaqwaType.bodySmall.copy(
                                                    fontWeight = if (isToday) FontWeight.Bold
                                                    else FontWeight.Normal
                                                ),
                                                color = textColor
                                            )
                                            if (!isFuture && !isBeforeStreak &&
                                                (isRelapse || hasEntry)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(4.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                            if (isRelapse) AccentRed
                                                            else VanillaCustard
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Monthly Stats
            MonthlyStatsCard(
                currentMonth = currentMonth,
                entryDates = entryDates,
                relapseDates = relapseDates,
                streakStart = streakStart,
                today = today
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }
    }
}

@Composable
private fun LegendItem(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceXS)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.5f))
        )
        Text(
            text = label,
            style = TaqwaType.captionSmall,
            color = TextGray
        )
    }
}

@Composable
private fun MonthlyStatsCard(
    currentMonth: YearMonth,
    entryDates: Set<LocalDate>,
    relapseDates: Set<LocalDate>,
    streakStart: LocalDate?,
    today: LocalDate
) {
    val monthStart = currentMonth.atDay(1)
    val monthEnd = if (currentMonth == YearMonth.now()) today
    else currentMonth.atEndOfMonth()

    val effectiveStart = if (streakStart != null && streakStart.isAfter(monthStart)) {
        streakStart
    } else {
        monthStart
    }

    val daysInRange = if (!monthEnd.isBefore(effectiveStart)) {
        ChronoUnit.DAYS.between(effectiveStart, monthEnd).toInt() + 1
    } else 0

    val urgesThisMonth = entryDates.count { date ->
        date.month == currentMonth.month && date.year == currentMonth.year
    }

    val relapsesThisMonth = relapseDates.count { date ->
        date.month == currentMonth.month && date.year == currentMonth.year
    }

    val cleanDays = if (daysInRange > 0) {
        (0 until daysInRange).count { offset ->
            val date = effectiveStart.plusDays(offset.toLong())
            !relapseDates.contains(date) && !date.isAfter(today)
        }
    } else 0

    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "📊 Monthly Summary",
                style = TaqwaType.cardTitle,
                color = VanillaCustard
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MonthStat(value = "$cleanDays", label = "Clean Days", emoji = "🟢")
                MonthStat(value = "$urgesThisMonth", label = "Defeated", emoji = "🛡️")
                MonthStat(value = "$relapsesThisMonth", label = "Relapses", emoji = "🔴")
            }

            if (daysInRange > 0) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                val successRate = ((cleanDays.toFloat() / daysInRange) * 100).toInt()
                LinearProgressIndicator(
                    progress = { cleanDays.toFloat() / daysInRange },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AccentGreen,
                    trackColor = BackgroundLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "$successRate% Clean this month",
                    style = TaqwaType.caption.copy(fontWeight = FontWeight.Medium),
                    color = when {
                        successRate >= 80 -> AccentGreen
                        successRate >= 50 -> AccentOrange
                        else -> AccentRed
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun MonthStat(value: String, label: String, emoji: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
        Text(
            text = value,
            style = TaqwaType.statValue.copy(fontSize = 20.sp),
            color = TextWhite
        )
        Text(
            text = label,
            style = TaqwaType.statLabel,
            color = TextGray
        )
    }
}