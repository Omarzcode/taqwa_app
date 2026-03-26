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
fun ResetScreen(
    currentStreak: Int,
    onReset: (String) -> Unit,
    onBack: () -> Unit
) {
    var reason by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Reset Streak",
                    fontWeight = FontWeight.Bold
                )
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Compassionate message
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🤲",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "It's okay. Everyone falls.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "What matters is you're here again.\nYou didn't give up. That takes courage.",
                        fontSize = 15.sp,
                        color = TextLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Ayah
                    Divider(
                        color = PrimaryMedium.copy(alpha = 0.3f),
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "إِنَّ اللَّهَ يُحِبُّ التَّوَّابِينَ",
                        fontSize = 22.sp,
                        color = AccentGold,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"Indeed, Allah loves those\nwho repent repeatedly.\"",
                        fontSize = 14.sp,
                        color = TextLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                    Text(
                        text = "— Al-Baqarah 2:222",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
            }

            // Current streak info
            if (currentStreak > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AccentOrange.copy(alpha = 0.1f)
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
                            text = "You had a streak of $currentStreak days",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccentOrange
                        )
                        Text(
                            text = "That progress is NOT lost. Your brain healed during those days.",
                            fontSize = 13.sp,
                            color = TextGray,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Reflection question
            Text(
                text = "What went wrong? What can you learn?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                placeholder = {
                    Text(
                        "Be honest with yourself...\n\nWhat triggered it?\nWhat could you do differently next time?",
                        color = TextMuted
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryLight,
                    unfocusedBorderColor = BackgroundLight,
                    cursorColor = PrimaryLight,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Reset button
            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentOrange
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Reset & Start Fresh 🔄",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            // Encouragement
            Text(
                text = "\"A believer is not stung from\nthe same hole twice.\"\n— Prophet Muhammad ﷺ (Bukhari)",
                fontSize = 13.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }

    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = "Reset Streak?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Your streak will reset to Day 0.\nThis is a fresh start, not a failure."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onReset(reason)
                        showConfirmDialog = false
                    }
                ) {
                    Text("Reset & Start Fresh", color = AccentOrange)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            },
            containerColor = BackgroundCard
        )
    }
}