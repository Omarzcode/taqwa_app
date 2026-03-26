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
import com.taqwa.journal.ui.theme.*

@Composable
fun PersonalReminderScreen(
    whyQuitting: String,
    promises: List<String>,
    duas: List<String>,
    reminders: List<String>,
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
        // Top
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "📝",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "YOUR OWN WORDS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "You wrote these when you were thinking clearly",
                fontSize = 14.sp,
                color = TextGray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Content
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Why quitting
            if (whyQuitting.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AccentGold.copy(alpha = 0.1f)
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
                            text = "🎯 Why You're Doing This",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "\"$whyQuitting\"",
                            fontSize = 16.sp,
                            color = TextWhite,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Promises
            if (promises.isNotEmpty()) {
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
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "💪 Your Promises",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        promises.forEach { promise ->
                            Text(
                                text = "• \"$promise\"",
                                fontSize = 15.sp,
                                color = TextLight,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Duas
            if (duas.isNotEmpty()) {
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
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "🤲 Your Duas",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        duas.forEach { dua ->
                            Text(
                                text = "• \"$dua\"",
                                fontSize = 15.sp,
                                color = TextLight,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Reminders
            if (reminders.isNotEmpty()) {
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
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "📝 Your Reminders",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        reminders.forEach { reminder ->
                            Text(
                                text = "• \"$reminder\"",
                                fontSize = 15.sp,
                                color = TextLight,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom - Next button
        Button(
            onClick = onNext,
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
                text = "Next ➜",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}