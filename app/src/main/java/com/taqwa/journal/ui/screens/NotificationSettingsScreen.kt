package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.BackgroundCard
import com.taqwa.journal.ui.theme.BackgroundDark
import com.taqwa.journal.ui.theme.BackgroundLight
import com.taqwa.journal.ui.theme.DividerColor
import com.taqwa.journal.ui.theme.PrimaryLight
import com.taqwa.journal.ui.theme.PrimaryMedium
import com.taqwa.journal.ui.theme.TaqwaDimens
import com.taqwa.journal.ui.theme.TaqwaType
import com.taqwa.journal.ui.theme.TextGray
import com.taqwa.journal.ui.theme.TextLight
import com.taqwa.journal.ui.theme.TextMuted
import com.taqwa.journal.ui.theme.TextWhite
import com.taqwa.journal.ui.theme.VanillaCustard

@Composable
fun NotificationSettingsScreen(
    morningEnabled: Boolean,
    morningHour: Int,
    morningMinute: Int,
    eveningEnabled: Boolean,
    eveningHour: Int,
    eveningMinute: Int,
    dangerHourEnabled: Boolean,
    dangerHourDetected: Boolean,
    dangerHourStart: Int,
    dangerHourEnd: Int,
    dangerAlertHour: Int,
    dangerAlertMinute: Int,
    memoryResurfaceEnabled: Boolean,
    inactivityEnabled: Boolean,
    streakCelebrationEnabled: Boolean,
    postRelapseEnabled: Boolean,
    fridayEnabled: Boolean,
    onMorningToggle: (Boolean) -> Unit,
    onMorningTimeChange: (Int, Int) -> Unit,
    onEveningToggle: (Boolean) -> Unit,
    onEveningTimeChange: (Int, Int) -> Unit,
    onDangerHourToggle: (Boolean) -> Unit,
    onMemoryResurfaceToggle: (Boolean) -> Unit,
    onInactivityToggle: (Boolean) -> Unit,
    onStreakCelebrationToggle: (Boolean) -> Unit,
    onPostRelapseToggle: (Boolean) -> Unit,
    onFridayToggle: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    var showMorningTimePicker by remember { mutableStateOf(false) }
    var showEveningTimePicker by remember { mutableStateOf(false) }
    var tempHour by remember { mutableIntStateOf(morningHour) }
    var tempMinute by remember { mutableIntStateOf(morningMinute) }
    var editingMorning by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Notifications",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            Text(
                text = "Control when and how Taqwa reaches out to you.",
                style = TaqwaType.bodySmall,
                color = TextGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Section: Check-In Reminders
            SectionLabel(text = "CHECK-IN REMINDERS")

            // Morning Reminder
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationToggleRow(
                        emoji = "\u2600\uFE0F",
                        title = "Morning Check-In",
                        description = "Start your day with intention",
                        enabled = morningEnabled,
                        onToggle = onMorningToggle
                    )
                    if (morningEnabled) {
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        HorizontalDivider(color = DividerColor, thickness = TaqwaDimens.dividerThickness)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        TimeRow(
                            label = "Time",
                            hour = morningHour,
                            minute = morningMinute,
                            onClick = {
                                editingMorning = true
                                tempHour = morningHour
                                tempMinute = morningMinute
                                showMorningTimePicker = true
                            }
                        )
                    }
                }
            }

            // Evening Reminder
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationToggleRow(
                        emoji = "\uD83C\uDF19",
                        title = "Evening Reflection",
                        description = "Reflect before you rest",
                        enabled = eveningEnabled,
                        onToggle = onEveningToggle
                    )
                    if (eveningEnabled) {
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        HorizontalDivider(color = DividerColor, thickness = TaqwaDimens.dividerThickness)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        TimeRow(
                            label = "Time",
                            hour = eveningHour,
                            minute = eveningMinute,
                            onClick = {
                                editingMorning = false
                                tempHour = eveningHour
                                tempMinute = eveningMinute
                                showEveningTimePicker = true
                            }
                        )
                    }
                }
            }

            // Section: Protection
            SectionLabel(text = "PROTECTION")

            // Danger Hour
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationToggleRow(
                        emoji = "\uD83D\uDEE1\uFE0F",
                        title = "Danger Hour Alert",
                        description = if (dangerHourDetected) {
                            "Detected peak: ${formatHour(dangerHourStart)} - ${formatHour(dangerHourEnd)}"
                        } else {
                            "Need 5+ journal entries to detect pattern"
                        },
                        enabled = dangerHourEnabled && dangerHourDetected,
                        onToggle = onDangerHourToggle,
                        toggleEnabled = dangerHourDetected
                    )
                    if (dangerHourEnabled && dangerHourDetected) {
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = "Alert at ${formatTime(dangerAlertHour, dangerAlertMinute)} (30 min before)",
                            style = TaqwaType.captionSmall,
                            color = PrimaryLight,
                            modifier = Modifier.padding(start = 40.dp)
                        )
                    }
                }
            }

            // Post-Relapse
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "\uD83D\uDCAA",
                    title = "Post-Relapse Support",
                    description = "Encouraging message 2 hours after a reset",
                    enabled = postRelapseEnabled,
                    onToggle = onPostRelapseToggle
                )
            }

            // Section: Motivation
            SectionLabel(text = "MOTIVATION")

            // Memory Resurface
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "\uD83E\uDDE0",
                    title = "Memory Resurface",
                    description = "A random memory from your bank, once daily",
                    enabled = memoryResurfaceEnabled,
                    onToggle = onMemoryResurfaceToggle
                )
            }

            // Streak Celebrations
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "\uD83C\uDFC6",
                    title = "Streak Celebrations",
                    description = "Celebrate milestones (7, 14, 30, 90...)",
                    enabled = streakCelebrationEnabled,
                    onToggle = onStreakCelebrationToggle
                )
            }

            // Friday Reminder
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "\uD83D\uDD4C",
                    title = "Jumu'ah Reminder",
                    description = "Weekly Friday encouragement at 9:00 AM",
                    enabled = fridayEnabled,
                    onToggle = onFridayToggle
                )
            }

            // Section: Safety Net
            SectionLabel(text = "SAFETY NET")

            // Inactivity
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "\uD83D\uDC4B",
                    title = "Inactivity Check",
                    description = "Gentle check-in after 2 days away",
                    enabled = inactivityEnabled,
                    onToggle = onInactivityToggle
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // Privacy note
            Text(
                text = "All notifications use neutral language.\nNo one can tell what this app is about from a notification.",
                style = TaqwaType.captionSmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }
    }

    // Time Picker Dialog
    if (showMorningTimePicker || showEveningTimePicker) {
        val dialogTitle = if (showMorningTimePicker) "Morning Reminder Time" else "Evening Reminder Time"

        AlertDialog(
            onDismissRequest = {
                showMorningTimePicker = false
                showEveningTimePicker = false
            },
            title = {
                Text(text = dialogTitle, style = TaqwaType.cardTitle, color = TextWhite)
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formatTime(tempHour, tempMinute),
                        style = TaqwaType.screenTitle.copy(fontSize = 36.sp),
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    Text("Hour", style = TaqwaType.captionSmall, color = TextGray)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { tempHour = if (tempHour == 0) 23 else tempHour - 1 }) {
                            Text("-", color = TextLight, fontSize = 24.sp)
                        }
                        Text(
                            text = "$tempHour",
                            style = TaqwaType.sectionTitle,
                            color = TextWhite,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                        TextButton(onClick = { tempHour = if (tempHour == 23) 0 else tempHour + 1 }) {
                            Text("+", color = TextLight, fontSize = 24.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    Text("Minute", style = TaqwaType.captionSmall, color = TextGray)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { tempMinute = if (tempMinute == 0) 55 else tempMinute - 5 }) {
                            Text("-", color = TextLight, fontSize = 24.sp)
                        }
                        Text(
                            text = "%02d".format(tempMinute),
                            style = TaqwaType.sectionTitle,
                            color = TextWhite,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                        TextButton(onClick = { tempMinute = if (tempMinute >= 55) 0 else tempMinute + 5 }) {
                            Text("+", color = TextLight, fontSize = 24.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (showMorningTimePicker) {
                        onMorningTimeChange(tempHour, tempMinute)
                    } else {
                        onEveningTimeChange(tempHour, tempMinute)
                    }
                    showMorningTimePicker = false
                    showEveningTimePicker = false
                }) {
                    Text("Save", color = VanillaCustard)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showMorningTimePicker = false
                    showEveningTimePicker = false
                }) {
                    Text("Cancel", color = TextGray)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = TaqwaType.captionSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        ),
        color = TextMuted,
        modifier = Modifier.padding(top = TaqwaDimens.spaceL, bottom = TaqwaDimens.spaceXS)
    )
}

@Composable
private fun TimeRow(
    label: String,
    hour: Int,
    minute: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundLight)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = TaqwaType.body, color = TextLight)
        Text(
            text = formatTime(hour, minute),
            style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
            color = VanillaCustard
        )
    }
}

@Composable
private fun NotificationToggleRow(
    emoji: String,
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    toggleEnabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = emoji,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp, top = 2.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TaqwaType.body.copy(fontWeight = FontWeight.SemiBold),
                    color = if (enabled) TextWhite else TextGray
                )
                Text(
                    text = description,
                    style = TaqwaType.captionSmall,
                    color = if (enabled) TextGray else TextMuted,
                    lineHeight = 16.sp
                )
            }
        }

        Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            enabled = toggleEnabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = VanillaCustard,
                checkedTrackColor = PrimaryMedium,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = BackgroundLight
            )
        )
    }
}

private fun formatTime(hour: Int, minute: Int): String {
    val period = if (hour < 12) "AM" else "PM"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return "%d:%02d %s".format(displayHour, minute, period)
}

private fun formatHour(hour: Int): String {
    val period = if (hour < 12) "AM" else "PM"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return "%d:00 %s".format(displayHour, period)
}