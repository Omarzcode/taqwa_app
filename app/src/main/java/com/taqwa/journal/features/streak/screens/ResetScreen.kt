package com.taqwa.journal.features.streak.screens

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
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*

@Composable
fun ResetScreen(
    currentStreak: Int,
    onReset: (reason: String) -> Unit,
    onResetWithLetter: (reason: String, letter: String) -> Unit,
    onBack: () -> Unit
) {
    var reason by remember { mutableStateOf("") }
    var letterToSelf by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showLetterSection by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Reset Streak",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceXL)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            // Compassionate message
            TaqwaCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🤲", fontSize = 44.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = "It's okay. Everyone falls.",
                        style = TaqwaType.sectionTitle,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    Text(
                        text = "What matters is you're here again.\nYou didn't give up. That takes courage.",
                        style = TaqwaType.body.copy(lineHeight = 24.sp),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    HorizontalDivider(
                        color = DividerColor,
                        modifier = Modifier.padding(horizontal = 32.dp),
                        thickness = TaqwaDimens.dividerThickness
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    Text(
                        text = "إِنَّ اللَّهَ يُحِبُّ التَّوَّابِينَ",
                        style = TaqwaType.arabicLarge,
                        color = VanillaCustard,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\"Indeed, Allah loves those\nwho repent repeatedly.\"",
                        style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "— Al-Baqarah 2:222",
                        style = TaqwaType.captionSmall,
                        color = TextGray
                    )
                }
            }

            // Streak info
            if (currentStreak > 0) {
                TaqwaAccentCard(accentColor = AccentOrange, alpha = 0.1f) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You had a streak of $currentStreak days",
                            style = TaqwaType.cardTitle,
                            color = AccentOrange
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                        Text(
                            text = "That progress is NOT lost. Your brain healed during those days.",
                            style = TaqwaType.bodySmall,
                            color = TextGray,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Reflection — What went wrong
            Text(
                text = "What went wrong? What can you learn?",
                style = TaqwaType.sectionTitle,
                color = TextWhite,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text(
                        "Be honest with yourself...\n\nWhat triggered it?",
                        style = TaqwaType.bodySmall,
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
                shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
            )

            // ── NEW: Letter to Future Self ──
            if (!showLetterSection) {
                TaqwaAccentCard(accentColor = AccentRed, alpha = 0.08f) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📝 Your strongest weapon",
                            style = TaqwaType.cardTitle,
                            color = AccentRedLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = "Right now you feel the pain and regret.\nNext time, you'll forget.\n\nWrite a message that will stop your future self.",
                            style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                            color = TextGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                        OutlinedButton(
                            onClick = { showLetterSection = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = AccentRedLight
                            ),
                            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                        ) {
                            Text(
                                "✍️  Write to Future Self",
                                style = TaqwaType.button,
                                color = AccentRedLight
                            )
                        }
                    }
                }
            } else {
                // Letter input section
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📝", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                        Text(
                            text = "Letter to your future self",
                            style = TaqwaType.cardTitle,
                            color = AccentRedLight
                        )
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(
                        text = "This will appear the next time you're about to relapse.",
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    OutlinedTextField(
                        value = letterToSelf,
                        onValueChange = { letterToSelf = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp, max = 250.dp),
                        placeholder = {
                            Text(
                                "How do you feel right now?\n\nWhat would you say to stop yourself next time?\n\n\"Remember this feeling. It wasn't worth it...\"",
                                style = TaqwaType.bodySmall,
                                color = TextMuted,
                                lineHeight = 20.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentRed,
                            unfocusedBorderColor = BackgroundLight,
                            cursorColor = AccentRed,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    )
                }
            }

            // Reset button
            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentOrange
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "Reset & Start Fresh  🔄",
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            // Hadith
            Text(
                text = "\"A believer is not stung from\nthe same hole twice.\"\n— Prophet Muhammad ﷺ (Bukhari)",
                style = TaqwaType.bodySmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }
    }

    // Confirm dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = "Reset Streak?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )
            },
            text = {
                Text(
                    "Your streak will reset to Day 0.\nThis is a fresh start, not a failure.",
                    style = TaqwaType.body,
                    color = TextGray
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (letterToSelf.isNotBlank()) {
                            onResetWithLetter(reason, letterToSelf)
                        } else {
                            onReset(reason)
                        }
                        showConfirmDialog = false
                    }
                ) {
                    Text("Reset & Start Fresh", color = AccentOrange)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}