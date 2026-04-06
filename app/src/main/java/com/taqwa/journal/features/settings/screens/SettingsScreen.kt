package com.taqwa.journal.features.settings.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*

@Composable
fun SettingsScreen(
    totalEntries: Int,
    totalRelapses: Int,
    currentStreak: Int,
    longestStreak: Int,
    onClearAllData: () -> Unit,
    onRelapseHistoryClick: () -> Unit,
    onExportClick: () -> Unit = {},
    onNotificationSettingsClick: () -> Unit = {},
    onBack: () -> Unit
) {
    var showClearDataDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "⚙️  Settings",
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

            // App Info
            TaqwaCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🕌", fontSize = 44.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "Taqwa",
                        style = TaqwaType.screenTitle,
                        color = VanillaCustard
                    )
                    Text(
                        text = "Version 1.0.0",
                        style = TaqwaType.bodySmall,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "Your journey to purity",
                        style = TaqwaType.body,
                        color = TextLight
                    )
                }
            }

            // ── Notification Settings ──
            TaqwaCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onNotificationSettingsClick),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🔔", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "Smart Notifications",
                        style = TaqwaType.cardTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(
                        text = "Morning reminders, danger alerts, memory resurface & more",
                        style = TaqwaType.captionSmall,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // ── Export Report ──
            TaqwaCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onExportClick),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "📤", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "Export Report",
                        style = TaqwaType.cardTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(
                        text = "Export your journal data as a shareable report",
                        style = TaqwaType.captionSmall,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Statistics
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "📊  App Statistics",
                        style = TaqwaType.sectionTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    StatsRow(label = "Total Journal Entries", value = "$totalEntries")
                    StatsRow(label = "Current Streak", value = "$currentStreak days")
                    StatsRow(label = "Longest Streak", value = "$longestStreak days")
                    StatsRow(label = "Total Relapses", value = "$totalRelapses")
                    StatsRow(
                        label = "Win Rate",
                        value = if (totalEntries + totalRelapses > 0) {
                            val rate = (totalEntries.toFloat() / (totalEntries + totalRelapses) * 100).toInt()
                            "$rate%"
                        } else "N/A"
                    )

                    // Relapse History button
                    if (totalRelapses > 0) {
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                        HorizontalDivider(
                            color = DividerColor,
                            thickness = TaqwaDimens.dividerThickness
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                        OutlinedButton(
                            onClick = onRelapseHistoryClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(TaqwaDimens.buttonHeight),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = AccentOrange
                            ),
                            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                        ) {
                            Text(
                                text = "📉  View Relapse History ($totalRelapses)",
                                style = TaqwaType.button,
                                color = AccentOrange
                            )
                        }
                    }
                }
            }

            // Privacy
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "🔒  Privacy",
                        style = TaqwaType.sectionTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    PrivacyItem("📵", "100% Offline", "No internet connection needed or used")
                    PrivacyItem("🚫", "Zero Permissions", "App requests nothing from your phone")
                    PrivacyItem("💾", "Local Storage Only", "All data stays on your device")
                    PrivacyItem("🔍", "No Analytics", "No tracking, no data collection")
                    PrivacyItem("👤", "No Accounts", "No sign-up, no cloud sync")
                }
            }

            // How It Works
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "🧠  How Taqwa Works",
                        style = TaqwaType.sectionTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    Text(
                        text = "When an urge hits, your brain enters a 'tunnel vision' state:\n\n" +
                                "🧠 Prefrontal Cortex (rational thinking) → GOES OFFLINE\n" +
                                "🔥 Limbic System (emotions/desires) → TAKES OVER\n" +
                                "⚡ Dopamine System → SCREAMS for the hit\n\n" +
                                "Taqwa uses a 3-phase approach:\n\n" +
                                "Phase 1: INTERRUPT — Stop the autopilot (breathing)\n" +
                                "Phase 2: RECONNECT — Bring back rational thinking (reminders)\n" +
                                "Phase 3: REFLECT — Deep self-understanding (journal)",
                        style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                        color = TextLight
                    )
                }
            }

            // Danger Zone
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AccentRed.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TaqwaDimens.cardPadding)
                ) {
                    Text(
                        text = "⚠️  Danger Zone",
                        style = TaqwaType.sectionTitle,
                        color = AccentRed
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    Text(
                        text = "This will permanently delete ALL your data including journal entries, streak history, promises, and everything else. This cannot be undone.",
                        style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Button(
                        onClick = { showClearDataDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(TaqwaDimens.buttonHeight),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentRed.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    ) {
                        Text(
                            text = "🗑️  Delete All Data",
                            style = TaqwaType.button,
                            color = AccentRed
                        )
                    }
                }
            }

            // Credits
            TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ\nفَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ",
                        style = TaqwaType.arabicMedium.copy(lineHeight = 30.sp),
                        color = VanillaCustard,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\"But as for he who feared standing before his Lord\nand restrained the soul from desire —\nthen indeed, Paradise will be his refuge.\"",
                        style = TaqwaType.caption.copy(lineHeight = 20.sp),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "— An-Nazi'at 79:40-41",
                        style = TaqwaType.captionSmall,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = "Made with ❤️ and Taqwa",
                        style = TaqwaType.bodySmall,
                        color = TextGray
                    )
                    Text(
                        text = "github.com/Omarzcode/taqwa_app",
                        style = TaqwaType.captionSmall,
                        color = PrimaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }
    }

    // Clear data dialog
    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = {
                Text(
                    text = "⚠️ Delete ALL Data?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )
            },
            text = {
                Text(
                    "This will permanently delete:\n\n" +
                            "• All journal entries\n" +
                            "• Streak history\n" +
                            "• Relapse history\n" +
                            "• Promises, duas, reminders\n" +
                            "• All settings\n\n" +
                            "This CANNOT be undone!",
                    style = TaqwaType.body,
                    color = TextGray
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllData()
                        showClearDataDialog = false
                    }
                ) {
                    Text("Delete Everything", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("Cancel", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun StatsRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TaqwaType.body,
            color = TextGray
        )
        Text(
            text = value,
            style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
            color = TextWhite
        )
    }
}

@Composable
private fun PrivacyItem(emoji: String, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXS),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = emoji,
            fontSize = 16.sp,
            modifier = Modifier.padding(end = TaqwaDimens.spaceS)
        )
        Column {
            Text(
                text = title,
                style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
                color = TextWhite
            )
            Text(
                text = description,
                style = TaqwaType.caption.copy(lineHeight = 18.sp),
                color = TextGray
            )
        }
    }
}