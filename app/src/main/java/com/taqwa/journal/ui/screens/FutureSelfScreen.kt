package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

@Composable
fun FutureSelfScreen(
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top - Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "📅",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "One Hour From Now...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        // Middle - Two paths side by side
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Path A - Give In
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = AccentRed.copy(alpha = 0.15f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PATH A",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentRed
                    )
                    Text(
                        text = "Give In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentRed
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PathItem(emoji = "😞", text = "5 seconds of pleasure")
                    PathItem(emoji = "😫", text = "Hours of guilt & shame")
                    PathItem(emoji = "📉", text = "Your streak is gone")
                    PathItem(emoji = "🧠", text = "Brain fog for days")
                    PathItem(emoji = "😤", text = "You hate yourself again")
                    PathItem(emoji = "📚", text = "Can't focus on study")
                    PathItem(emoji = "😰", text = "Can't sleep in peace")
                }
            }

            // Path B - Resist
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = AccentGreen.copy(alpha = 0.15f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PATH B",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentGreen
                    )
                    Text(
                        text = "Resist",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentGreen
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PathItem(emoji = "💪", text = "The urge passes")
                    PathItem(emoji = "😊", text = "You feel POWERFUL")
                    PathItem(emoji = "📈", text = "Streak grows stronger")
                    PathItem(emoji = "🧠", text = "Brain heals more")
                    PathItem(emoji = "😌", text = "Self-respect intact")
                    PathItem(emoji = "📚", text = "Study with clear mind")
                    PathItem(emoji = "😴", text = "Sleep in peace")
                }
            }
        }

        // Bottom - Choose Path B button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentGreen
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "I Choose Path B ➜",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }
    }
}

@Composable
private fun PathItem(emoji: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = emoji,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextLight,
            lineHeight = 16.sp
        )
    }
}