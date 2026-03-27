package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.ui.theme.*

@Composable
fun HomeScreen(
    urgesDefeated: Int,
    currentStreak: Int,
    longestStreak: Int,
    streakStatus: String,
    milestoneMessage: String?,
    totalRelapses: Int,
    dailyAyah: DailyAyah?,
    onDismissMilestone: () -> Unit,
    onUrgeClick: () -> Unit,
    onPastEntriesClick: () -> Unit,
    onResetStreakClick: () -> Unit,
    onRelapseHistoryClick: () -> Unit,
    onPatternAnalysisClick: () -> Unit,
    onPromiseWallClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    if (milestoneMessage != null) {
        AlertDialog(
            onDismissRequest = onDismissMilestone,
            title = {
                Text(
                    text = "🎉 Milestone Reached!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = milestoneMessage, fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(), lineHeight = 24.sp
                )
            },
            confirmButton = {
                TextButton(onClick = onDismissMilestone) {
                    Text("Alhamdulillah! 🤲", color = VanillaCustard)
                }
            },
            containerColor = BackgroundCard
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "﷽", fontSize = 32.sp, color = TextWhite, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Taqwa", fontSize = 28.sp,
                fontWeight = FontWeight.Bold, color = VanillaCustard
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Ayah Card
        if (dailyAyah != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryDark.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "📖 Ayah of the Day", fontSize = 12.sp, color = TextGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dailyAyah.arabic, fontSize = 18.sp, color = VanillaCustard,
                        textAlign = TextAlign.Center, lineHeight = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${dailyAyah.translation}\"", fontSize = 13.sp,
                        color = TextLight, textAlign = TextAlign.Center, lineHeight = 20.sp
                    )
                    Text(
                        text = "— ${dailyAyah.reference}",
                        fontSize = 11.sp, color = TextGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Streak Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BackgroundCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🔥 Current Streak", fontSize = 14.sp, color = TextGray)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$currentStreak", fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentStreak > 0) VanillaCustard else TextGray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentStreak == 1) "day" else "days",
                        fontSize = 16.sp, color = TextGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Text(text = streakStatus, fontSize = 13.sp, color = PrimaryLight)

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    color = BackgroundLight,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("Defeated", "$urgesDefeated", "🛡️")
                    StatItem("Best", "$longestStreak days", "👑")
                    StatItem("Relapses", "$totalRelapses", "📉")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Big Urge Button
        Button(
            onClick = onUrgeClick,
            modifier = Modifier.size(170.dp).clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = UrgeButtonRed),
            shape = CircleShape,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🔴", fontSize = 26.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "I'M HAVING\nAN URGE", fontSize = 15.sp,
                    fontWeight = FontWeight.Bold, color = TextWhite,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bottom buttons
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onPastEntriesClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryLight),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("📖  My Past Entries", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = onCalendarClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentGreen),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("📅  My Calendar", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = onPatternAnalysisClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = VanillaCustard),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("📊  My Patterns", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = onPromiseWallClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentBlue),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("📝  My Promise Wall", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            if (totalRelapses > 0) {
                OutlinedButton(
                    onClick = onRelapseHistoryClick,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentOrange),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "📉  Relapse History ($totalRelapses)",
                        fontSize = 14.sp, fontWeight = FontWeight.Medium
                    )
                }
            }

            OutlinedButton(
                onClick = onSettingsClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGray),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("⚙️  Settings", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            TextButton(
                onClick = onResetStreakClick,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {
                Text("😔  I relapsed... Reset streak", fontSize = 12.sp, color = TextMuted)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun StatItem(label: String, value: String, emoji: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Text(text = label, fontSize = 10.sp, color = TextGray)
    }
}