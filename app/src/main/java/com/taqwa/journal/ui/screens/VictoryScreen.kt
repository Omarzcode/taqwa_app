package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
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
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VictoryScreen(
    urgesDefeated: Int,
    onGoHome: () -> Unit
) {
    val victoryAyah = remember {
        QuestionData.victoryAyahs.random()
    }

    val currentTime = remember {
        SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
            .format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top - Trophy
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = "🏆",
                fontSize = 72.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "URGE DEFEATED",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = VictoryGreenLight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You just proved you're stronger\nthan your desires.",
                fontSize = 16.sp,
                color = TextLight,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        // Middle - Ayah
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = VictoryGreen.copy(alpha = 0.15f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = victoryAyah.arabic,
                    fontSize = 20.sp,
                    color = AccentGold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "\"${victoryAyah.translation}\"",
                    fontSize = 14.sp,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "— ${victoryAyah.reference}",
                    fontSize = 12.sp,
                    color = TextGray
                )
            }
        }

        // Stats
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundCard
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔥",
                            fontSize = 24.sp
                        )
                        Text(
                            text = "$urgesDefeated",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold
                        )
                        Text(
                            text = "Total Defeated",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📅",
                            fontSize = 24.sp
                        )
                        Text(
                            text = currentTime,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextLight,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Entry Saved",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                }
            }
        }

        // Bottom - Home button
        Button(
            onClick = onGoHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryMedium
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🏠  Back to Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}