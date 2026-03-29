package com.taqwa.journal.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.export.ExportOptions
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExportScreen(
    onExport: (startDate: LocalDate, endDate: LocalDate, periodLabel: String, options: ExportOptions, onReady: (Intent) -> Unit) -> Unit,
    onPreview: (startDate: LocalDate, endDate: LocalDate, periodLabel: String, options: ExportOptions, onReady: (String) -> Unit) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val today = remember { LocalDate.now() }
    val displayFormat = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    // Period selection
    var selectedPeriod by remember { mutableStateOf("WEEK") }
    var customStartDate by remember { mutableStateOf(today.minusDays(7)) }
    var customEndDate by remember { mutableStateOf(today) }
    var showCustomRange by remember { mutableStateOf(false) }

    // Export options
    var includeJournal by remember { mutableStateOf(true) }
    var includeCheckIns by remember { mutableStateOf(true) }
    var includeRelapses by remember { mutableStateOf(true) }
    var includeMemories by remember { mutableStateOf(true) }
    var includeStats by remember { mutableStateOf(true) }
    var includePatterns by remember { mutableStateOf(true) }
    var includeShieldPlans by remember { mutableStateOf(true) }

    // Preview
    var previewText by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Calculate dates based on period
    val (startDate, endDate, periodLabel) = remember(selectedPeriod, customStartDate, customEndDate) {
        when (selectedPeriod) {
            "TODAY" -> Triple(today, today, "Today (${today.format(displayFormat)})")
            "WEEK" -> Triple(today.minusDays(6), today, "This Week (${today.minusDays(6).format(displayFormat)} — ${today.format(displayFormat)})")
            "MONTH" -> Triple(today.minusDays(29), today, "This Month (${today.minusDays(29).format(displayFormat)} — ${today.format(displayFormat)})")
            "ALL" -> Triple(LocalDate.of(2020, 1, 1), today, "All Time")
            "CUSTOM" -> Triple(customStartDate, customEndDate, "Custom (${customStartDate.format(displayFormat)} — ${customEndDate.format(displayFormat)})")
            else -> Triple(today.minusDays(6), today, "This Week")
        }
    }

    val options = remember(includeJournal, includeCheckIns, includeRelapses, includeMemories, includeStats, includePatterns, includeShieldPlans) {
        ExportOptions(
            includeJournalEntries = includeJournal,
            includeCheckIns = includeCheckIns,
            includeRelapses = includeRelapses,
            includeMemories = includeMemories,
            includeStreakStats = includeStats,
            includePatterns = includePatterns,
            includeShieldPlans = includeShieldPlans
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Export Report",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceL)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            // ── Period Selection ──
            Text(
                text = "📅 Select Period",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                PeriodChip("Today", "TODAY", selectedPeriod) {
                    selectedPeriod = it; showCustomRange = false
                }
                PeriodChip("Week", "WEEK", selectedPeriod) {
                    selectedPeriod = it; showCustomRange = false
                }
                PeriodChip("Month", "MONTH", selectedPeriod) {
                    selectedPeriod = it; showCustomRange = false
                }
                PeriodChip("All Time", "ALL", selectedPeriod) {
                    selectedPeriod = it; showCustomRange = false
                }
                PeriodChip("Custom", "CUSTOM", selectedPeriod) {
                    selectedPeriod = it; showCustomRange = true
                }
            }

            // Custom date range inputs
            if (showCustomRange) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Custom Range",
                            style = TaqwaType.cardTitle,
                            color = VanillaCustard
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("From:", style = TaqwaType.captionSmall, color = TextGray)
                                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                                DateSelector(
                                    date = customStartDate,
                                    onPrevious = { customStartDate = customStartDate.minusDays(1) },
                                    onNext = {
                                        if (customStartDate.isBefore(customEndDate)) {
                                            customStartDate = customStartDate.plusDays(1)
                                        }
                                    },
                                    format = displayFormat
                                )
                            }
                            Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("To:", style = TaqwaType.captionSmall, color = TextGray)
                                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                                DateSelector(
                                    date = customEndDate,
                                    onPrevious = {
                                        if (customEndDate.isAfter(customStartDate)) {
                                            customEndDate = customEndDate.minusDays(1)
                                        }
                                    },
                                    onNext = {
                                        if (customEndDate.isBefore(today)) {
                                            customEndDate = customEndDate.plusDays(1)
                                        }
                                    },
                                    format = displayFormat
                                )
                            }
                        }
                    }
                }
            }

            // Period display
            Text(
                text = periodLabel,
                style = TaqwaType.bodySmall,
                color = PrimaryLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // ── What to Include ──
            Text(
                text = "📋 What to Include",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )

            TaqwaCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    ExportToggle("📊 Streak Statistics", includeStats) { includeStats = it }
                    ExportToggle("📈 Pattern Analysis", includePatterns) { includePatterns = it }
                    ExportToggle("📋 Journal Entries", includeJournal) { includeJournal = it }
                    ExportToggle("☀️ Check-In History", includeCheckIns) { includeCheckIns = it }
                    ExportToggle("📉 Relapse History", includeRelapses) { includeRelapses = it }
                    ExportToggle("🧠 Memory Bank", includeMemories) { includeMemories = it }
                    ExportToggle("🛡️ Shield Plans", includeShieldPlans) { includeShieldPlans = it }
                }
            }

            // ── Preview ──
            OutlinedButton(
                onClick = {
                    isLoading = true
                    onPreview(startDate, endDate, periodLabel, options) { text ->
                        previewText = text
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryLight),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = if (isLoading) "Generating..." else "👁️  Preview Report",
                    style = TaqwaType.button,
                    color = PrimaryLight
                )
            }

            // Preview content
            if (previewText != null) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Preview",
                            style = TaqwaType.cardTitle,
                            color = VanillaCustard
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = previewText!!,
                            style = TaqwaType.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 18.sp,
                                fontSize = 11.sp
                            ),
                            color = TextLight,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }

            // ── Export Button ──
            Button(
                onClick = {
                    isLoading = true
                    onExport(startDate, endDate, periodLabel, options) { intent ->
                        isLoading = false
                        context.startActivity(Intent.createChooser(intent, "Share Report"))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "📤  Export & Share",
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            // Privacy note
            Text(
                text = "🔒 The report uses neutral language.\nNo sensitive terms in the file title or content headers.",
                style = TaqwaType.captionSmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }
    }
}

@Composable
private fun PeriodChip(
    label: String,
    value: String,
    selected: String,
    onSelect: (String) -> Unit
) {
    val isSelected = selected == value
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) ChipSelected else ChipUnselected)
            .border(
                width = 1.dp,
                color = if (isSelected) ChipBorder else BackgroundLight,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onSelect(value) }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = TaqwaType.captionSmall.copy(fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal),
            color = if (isSelected) VanillaCustard else TextGray
        )
    }
}

@Composable
private fun ExportToggle(
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!checked) }
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TaqwaType.bodySmall,
            color = if (checked) TextWhite else TextMuted
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onToggle,
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryMedium,
                uncheckedColor = TextMuted,
                checkmarkColor = VanillaCustard
            )
        )
    }
}

@Composable
private fun DateSelector(
    date: LocalDate,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    format: DateTimeFormatter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundLight)
            .padding(vertical = 4.dp)
    ) {
        TextButton(onClick = onPrevious, modifier = Modifier.size(36.dp)) {
            Text("◀", color = TextGray, fontSize = 12.sp)
        }
        Text(
            text = date.format(format),
            style = TaqwaType.captionSmall,
            color = TextWhite,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        TextButton(onClick = onNext, modifier = Modifier.size(36.dp)) {
            Text("▶", color = TextGray, fontSize = 12.sp)
        }
    }
}