package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun NotificationSettingsScreen(
    morningEnabled: Boolean,
    morningHour: Int,
    morningMinute: Int,
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
    onMorningToggle: (Boolean) -> Unit,
    onMorningTimeChange: (Int, Int) -> Unit,
    onDangerHourToggle: (Boolean) -> Unit,
    onMemoryResurfaceToggle: (Boolean) -> Unit,
    onInactivityToggle: (Boolean) -> Unit,
    onStreakCelebrationToggle: (Boolean) -> Unit,
    onPostRelapseToggle: (Boolean) -> Unit,
    onTestNotification: (String) -> Unit = {},
    onBack: () -> Unit

) {
    var showTimePicker by remember { mutableStateOf(false) }
    var tempHour by remember { mutableIntStateOf(morningHour) }
    var tempMinute by remember { mutableIntStateOf(morningMinute) }

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

            // Header
            Text(
                text = "Control when and how Taqwa reaches out to you.",
                style = TaqwaType.bodySmall,
                color = TextGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            // ── Morning Reminder ──
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationToggleRow(
                        emoji = "☀️",
                        title = "Morning Reminder",
                        description = "Start your day with strength",
                        enabled = morningEnabled,
                        onToggle = onMorningToggle
                    )

                    if (morningEnabled) {
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        HorizontalDivider(color = DividerColor, thickness = TaqwaDimens.dividerThickness)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(BackgroundLight)
                                .clickable { showTimePicker = true }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Time",
                                style = TaqwaType.body,
                                color = TextLight
                            )
                            Text(
                                text = formatTime(morningHour, morningMinute),
                                style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
                                color = VanillaCustard
                            )
                        }
                    }
                }
            }

            // ── Danger Hour Alert ──
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationToggleRow(
                        emoji = "🛡️",
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
                            text = "Alert fires at: ${formatTime(dangerAlertHour, dangerAlertMinute)}",
                            style = TaqwaType.captionSmall,
                            color = PrimaryLight,
                            modifier = Modifier.padding(start = 40.dp)
                        )
                    }
                }
            }

            // ── Memory Resurface ──
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "🧠",
                    title = "Memory Resurface",
                    description = "A random memory from your bank, once daily",
                    enabled = memoryResurfaceEnabled,
                    onToggle = onMemoryResurfaceToggle
                )
            }

            // ── Inactivity Check ──
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "👋",
                    title = "Inactivity Check",
                    description = "Gentle check-in after 3 days away",
                    enabled = inactivityEnabled,
                    onToggle = onInactivityToggle
                )
            }

            // ── Streak Celebrations ──
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "🏆",
                    title = "Streak Celebrations",
                    description = "Celebrate milestone days (7, 14, 30, 90...)",
                    enabled = streakCelebrationEnabled,
                    onToggle = onStreakCelebrationToggle
                )
            }

            // ── Post-Relapse Follow-up ──
            TaqwaCard {
                NotificationToggleRow(
                    emoji = "💪",
                    title = "Post-Relapse Follow-up",
                    description = "Encouraging message 24hrs after a reset",
                    enabled = postRelapseEnabled,
                    onToggle = onPostRelapseToggle
                )
            }

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
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = {
                Text(
                    text = "Set Morning Reminder Time",
                    style = TaqwaType.cardTitle,
                    color = TextWhite
                )
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

                    // Hour selector
                    Text("Hour", style = TaqwaType.captionSmall, color = TextGray)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = {
                            tempHour = if (tempHour == 0) 23 else tempHour - 1
                        }) {
                            Text("-", color = TextLight, fontSize = 24.sp)
                        }

                        Text(
                            text = "$tempHour",
                            style = TaqwaType.sectionTitle,
                            color = TextWhite,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )

                        TextButton(onClick = {
                            tempHour = if (tempHour == 23) 0 else tempHour + 1
                        }) {
                            Text("+", color = TextLight, fontSize = 24.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    // Minute selector
                    Text("Minute", style = TaqwaType.captionSmall, color = TextGray)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = {
                            tempMinute = if (tempMinute == 0) 55 else tempMinute - 5
                        }) {
                            Text("-", color = TextLight, fontSize = 24.sp)
                        }

                        Text(
                            text = "%02d".format(tempMinute),
                            style = TaqwaType.sectionTitle,
                            color = TextWhite,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )

                        TextButton(onClick = {
                            tempMinute = if (tempMinute >= 55) 0 else tempMinute + 5
                        }) {
                            Text("+", color = TextLight, fontSize = 24.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onMorningTimeChange(tempHour, tempMinute)
                    showTimePicker = false
                }) {
                    Text("Save", color = VanillaCustard)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempHour = morningHour
                    tempMinute = morningMinute
                    showTimePicker = false
                }) {
                    Text("Cancel", color = TextGray)
                }
            },
            containerColor = BackgroundCard
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