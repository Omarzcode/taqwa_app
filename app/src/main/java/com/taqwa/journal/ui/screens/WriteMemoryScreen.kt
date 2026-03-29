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
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

/**
 * Write Memory Screen
 *
 * Used in three contexts:
 * 1. After relapse (type = RELAPSE_LETTER) — "Write to your future self"
 * 2. After victory (type = VICTORY_NOTE) — "What do you want to remember?"
 * 3. Manual from Memory Bank (type = MANUAL) — "Add a memory"
 *
 * The context determines the prompts, tone, and visual style.
 */
@Composable
fun WriteMemoryScreen(
    memoryType: String,
    currentStreak: Int = 0,
    onSave: (message: String, trigger: String) -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    var trigger by remember { mutableStateOf("") }

    val isRelapseLetter = memoryType == MemoryEntry.TYPE_RELAPSE_LETTER
    val isVictoryNote = memoryType == MemoryEntry.TYPE_VICTORY_NOTE
    val isManual = memoryType == MemoryEntry.TYPE_MANUAL

    val accentColor = when {
        isRelapseLetter -> AccentRed
        isVictoryNote -> VictoryGreen
        else -> PrimaryLight
    }

    val title = when {
        isRelapseLetter -> "Letter to Future Self"
        isVictoryNote -> "Victory Note"
        else -> "New Memory"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = title,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceL)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            // Context-specific header
            TaqwaCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when {
                            isRelapseLetter -> "📝"
                            isVictoryNote -> "🏆"
                            else -> "💭"
                        },
                        fontSize = 40.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    when {
                        isRelapseLetter -> {
                            Text(
                                text = "Write to your future self.",
                                style = TaqwaType.sectionTitle,
                                color = TextWhite,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "Right now you feel the pain, the regret, the disappointment.\n\n" +
                                        "Next time an urge comes, you'll forget this feeling.\n\n" +
                                        "Write something that will stop your future self.",
                                style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                                color = TextGray,
                                textAlign = TextAlign.Center
                            )
                        }

                        isVictoryNote -> {
                            Text(
                                text = "Capture this moment.",
                                style = TaqwaType.sectionTitle,
                                color = TextWhite,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "You just defeated an urge. You feel strong.\n\n" +
                                        "Write what helped. Write how you feel.\n" +
                                        "This becomes proof that you CAN win.",
                                style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                                color = TextGray,
                                textAlign = TextAlign.Center
                            )
                        }

                        else -> {
                            Text(
                                text = "Add to your memory bank.",
                                style = TaqwaType.sectionTitle,
                                color = TextWhite,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "Write something you want to remember\nwhen an urge comes.\n\nYour own words are your strongest weapon.",
                                style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                                color = TextGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Trigger field (only for relapse letters)
            if (isRelapseLetter) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "What triggered the relapse?",
                        style = TaqwaType.cardTitle,
                        color = TextLight
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    OutlinedTextField(
                        value = trigger,
                        onValueChange = { trigger = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        placeholder = {
                            Text(
                                "Was alone at night, feeling lonely...",
                                style = TaqwaType.bodySmall,
                                color = TextMuted
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = BackgroundLight,
                            cursorColor = accentColor,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    )
                }
            }

            // Main message field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = when {
                        isRelapseLetter -> "Your message to future self:"
                        isVictoryNote -> "What do you want to remember?"
                        else -> "Your memory:"
                    },
                    style = TaqwaType.cardTitle,
                    color = TextLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                // Prompt hints
                if (isRelapseLetter) {
                    Text(
                        text = "💡 Ideas: How do you feel right now? What would you say to stop yourself? What did you lose?",
                        style = TaqwaType.captionSmall,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                }

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp, max = 300.dp),
                    placeholder = {
                        Text(
                            text = when {
                                isRelapseLetter -> "Write from the heart. Be honest. Be raw.\n\nThe next time you want to relapse, you'll read these words..."
                                isVictoryNote -> "How does it feel to win?\nWhat helped you the most?\nWhat would you tell someone struggling right now?"
                                else -> "Write something meaningful to you...\n\nThis will appear when you need it most."
                            },
                            style = TaqwaType.bodySmall,
                            color = TextMuted,
                            lineHeight = 20.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = BackgroundLight,
                        cursorColor = accentColor,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            // Save button
            Button(
                onClick = { onSave(message, trigger) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = message.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    disabledContainerColor = accentColor.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = when {
                        isRelapseLetter -> "💾  Save Letter"
                        isVictoryNote -> "💾  Save Victory Note"
                        else -> "💾  Save Memory"
                    },
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            // Skip option (for relapse and victory — not manual)
            if (!isManual) {
                TextButton(onClick = onSkip) {
                    Text(
                        text = "Skip for now",
                        style = TaqwaType.captionSmall,
                        color = TextMuted.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }
    }
}