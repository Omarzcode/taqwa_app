package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    totalEntries: Int,
    totalRelapses: Int,
    currentStreak: Int,
    longestStreak: Int,
    onClearAllData: () -> Unit,
    onBack: () -> Unit
) {
    var showClearDataDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopAppBar(
            title = {
                Text(text = "⚙️  Settings", fontWeight = FontWeight.Bold)
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Info
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
                    Text(text = "🕌", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Taqwa",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = VanillaCustard
                    )
                    Text(
                        text = "Version 1.0.0",
                        fontSize = 13.sp,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your journey to purity",
                        fontSize = 14.sp,
                        color = TextLight
                    )
                }
            }

            // Statistics Overview
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
                        text = "📊 App Statistics",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(16.dp))

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
                }
            }

            // Privacy Info
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
                        text = "🔒 Privacy",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    PrivacyItem(
                        emoji = "📵",
                        title = "100% Offline",
                        description = "No internet connection needed or used"
                    )
                    PrivacyItem(
                        emoji = "🚫",
                        title = "Zero Permissions",
                        description = "App requests nothing from your phone"
                    )
                    PrivacyItem(
                        emoji = "💾",
                        title = "Local Storage Only",
                        description = "All data stays on your device"
                    )
                    PrivacyItem(
                        emoji = "🔍",
                        title = "No Analytics",
                        description = "No tracking, no data collection"
                    )
                    PrivacyItem(
                        emoji = "👤",
                        title = "No Accounts",
                        description = "No sign-up, no cloud sync"
                    )
                }
            }

            // How It Works
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
                        text = "🧠 How Taqwa Works",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "When an urge hits, your brain enters a 'tunnel vision' state:\n\n" +
                                "🧠 Prefrontal Cortex (rational thinking) → GOES OFFLINE\n" +
                                "🔥 Limbic System (emotions/desires) → TAKES OVER\n" +
                                "⚡ Dopamine System → SCREAMS for the hit\n\n" +
                                "Taqwa uses a 3-phase approach:\n\n" +
                                "Phase 1: INTERRUPT — Stop the autopilot (breathing)\n" +
                                "Phase 2: RECONNECT — Bring back rational thinking (reminders)\n" +
                                "Phase 3: REFLECT — Deep self-understanding (journal)",
                        fontSize = 13.sp,
                        color = TextLight,
                        lineHeight = 22.sp
                    )
                }
            }

            // Danger Zone
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AccentRed.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "⚠️ Danger Zone",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentRed
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "This will permanently delete ALL your data including journal entries, streak history, promises, and everything else. This cannot be undone.",
                        fontSize = 13.sp,
                        color = TextGray,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showClearDataDialog = true },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentRed.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "🗑️  Delete All Data",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentRed
                        )
                    }
                }
            }

            // Credits
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryDark.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ\nفَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ",
                        fontSize = 16.sp,
                        color = VanillaCustard,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"But as for he who feared standing before his Lord\nand restrained the soul from desire —\nthen indeed, Paradise will be his refuge.\"",
                        fontSize = 12.sp,
                        color = TextLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "— An-Nazi'at 79:40-41",
                        fontSize = 11.sp,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Made with ❤️ and Taqwa",
                        fontSize = 13.sp,
                        color = TextGray
                    )
                    Text(
                        text = "github.com/Omarzcode/taqwa_app",
                        fontSize = 11.sp,
                        color = PrimaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Clear data confirmation
    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = {
                Text(
                    text = "⚠️ Delete ALL Data?",
                    fontWeight = FontWeight.Bold
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
                            "This CANNOT be undone!"
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
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = TextGray)
        Text(
            text = value, fontSize = 14.sp,
            fontWeight = FontWeight.Bold, color = TextWhite
        )
    }
}

@Composable
private fun PrivacyItem(emoji: String, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = emoji, fontSize = 16.sp, modifier = Modifier.padding(end = 8.dp))
        Column {
            Text(
                text = title, fontSize = 14.sp,
                fontWeight = FontWeight.Medium, color = TextWhite
            )
            Text(
                text = description, fontSize = 12.sp,
                color = TextGray, lineHeight = 18.sp
            )
        }
    }
}