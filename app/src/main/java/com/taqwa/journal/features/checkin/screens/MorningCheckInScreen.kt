package com.taqwa.journal.features.checkin.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.features.checkin.data.CheckInEntry
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.quran.data.DailyQuranManager.DailyAyah
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*

@Composable
fun MorningCheckInScreen(
    currentStreak: Int,
    dailyAyah: DailyAyah?,
    memoryBankEntry: MemoryEntry?,
    onComplete: (mood: String, riskLevel: String, intention: String) -> Unit,
    onBack: () -> Unit
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }
    var selectedRisk by remember { mutableStateOf<String?>(null) }
    var intention by remember { mutableStateOf("") }
    var currentSection by remember { mutableIntStateOf(0) }
    // 0 = greeting + ayah + memory
    // 1 = mood
    // 2 = risk
    // 3 = intention
    // 4 = complete

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Morning Check-In",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // ── Section 0: Greeting + Spiritual Grounding ──
            if (currentSection >= 0) {
                // Greeting
                Text(text = "☀️", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                val streakEmoji = when {
                    currentStreak >= 30 -> "🏆"
                    currentStreak >= 7 -> "🔥"
                    currentStreak >= 1 -> "🌱"
                    else -> "🤲"
                }

                Text(
                    text = if (currentStreak > 0)
                        "$streakEmoji Day $currentStreak — Alhamdulillah"
                    else
                        "🤲 A new day, a new chance",
                    style = TaqwaType.sectionTitle,
                    color = VanillaCustard,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

                Text(
                    text = "Start your day with awareness and strength.",
                    style = TaqwaType.bodySmall,
                    color = TextGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

                // Daily Ayah
                if (dailyAyah != null) {
                    TaqwaCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "📖 Today's Verse",
                                style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = TextGray
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                            Text(
                                text = dailyAyah.arabic,
                                style = TaqwaType.arabicMedium.copy(fontSize = 18.sp),
                                color = VanillaCustard,
                                textAlign = TextAlign.Center,
                                lineHeight = 32.sp
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "\"${dailyAyah.translation}\"",
                                style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "— ${dailyAyah.reference}",
                                style = TaqwaType.captionSmall,
                                color = TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                }

                // Memory Bank Entry — Fight the forgetting
                if (memoryBankEntry != null) {
                    val accentColor = when (memoryBankEntry.type) {
                        MemoryEntry.TYPE_RELAPSE_LETTER -> AccentRed
                        MemoryEntry.TYPE_VICTORY_NOTE -> VictoryGreen
                        else -> PrimaryLight
                    }
                    val label = when (memoryBankEntry.type) {
                        MemoryEntry.TYPE_RELAPSE_LETTER -> "📝 Remember this:"
                        MemoryEntry.TYPE_VICTORY_NOTE -> "🏆 You wrote after a victory:"
                        else -> "💭 From your memory bank:"
                    }

                    TaqwaAccentCard(accentColor = accentColor, alpha = 0.1f) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = label,
                                style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = accentColor
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                            Text(
                                text = "\"${memoryBankEntry.message}\"",
                                style = TaqwaType.body.copy(lineHeight = 24.sp),
                                color = TextWhite
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                }

                if (currentSection == 0) {
                    Button(
                        onClick = { currentSection = 1 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text(
                            "Continue →",
                            style = TaqwaType.button,
                            color = TextWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
                }
            }

            // ── Section 1: Mood ──
            if (currentSection >= 1) {
                HorizontalDivider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = 48.dp, vertical = TaqwaDimens.spaceL),
                    thickness = TaqwaDimens.dividerThickness
                )

                Text(
                    text = "How are you feeling this morning?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MoodOption(
                        emoji = "😊",
                        label = "Good",
                        value = CheckInEntry.MOOD_GOOD,
                        selected = selectedMood == CheckInEntry.MOOD_GOOD,
                        color = AccentGreen,
                        onClick = { selectedMood = CheckInEntry.MOOD_GOOD }
                    )
                    MoodOption(
                        emoji = "😐",
                        label = "Okay",
                        value = CheckInEntry.MOOD_OKAY,
                        selected = selectedMood == CheckInEntry.MOOD_OKAY,
                        color = AccentOrange,
                        onClick = { selectedMood = CheckInEntry.MOOD_OKAY }
                    )
                    MoodOption(
                        emoji = "😔",
                        label = "Low",
                        value = CheckInEntry.MOOD_LOW,
                        selected = selectedMood == CheckInEntry.MOOD_LOW,
                        color = AccentRed,
                        onClick = { selectedMood = CheckInEntry.MOOD_LOW }
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                if (currentSection == 1 && selectedMood != null) {
                    Button(
                        onClick = { currentSection = 2 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text("Continue →", style = TaqwaType.button, color = TextWhite)
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                }
            }

            // ── Section 2: Risk Level ──
            if (currentSection >= 2) {
                HorizontalDivider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = 48.dp, vertical = TaqwaDimens.spaceL),
                    thickness = TaqwaDimens.dividerThickness
                )

                Text(
                    text = "How vulnerable do you feel today?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "Be honest. This helps build your shield.",
                    style = TaqwaType.captionSmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    RiskOption(
                        emoji = "🟢",
                        label = "Low",
                        sublabel = "Feeling strong",
                        value = CheckInEntry.RISK_LOW,
                        selected = selectedRisk == CheckInEntry.RISK_LOW,
                        color = AccentGreen,
                        onClick = { selectedRisk = CheckInEntry.RISK_LOW }
                    )
                    RiskOption(
                        emoji = "🟡",
                        label = "Medium",
                        sublabel = "Could go either way",
                        value = CheckInEntry.RISK_MEDIUM,
                        selected = selectedRisk == CheckInEntry.RISK_MEDIUM,
                        color = AccentOrange,
                        onClick = { selectedRisk = CheckInEntry.RISK_MEDIUM }
                    )
                    RiskOption(
                        emoji = "🔴",
                        label = "High",
                        sublabel = "Feeling weak",
                        value = CheckInEntry.RISK_HIGH,
                        selected = selectedRisk == CheckInEntry.RISK_HIGH,
                        color = AccentRed,
                        onClick = { selectedRisk = CheckInEntry.RISK_HIGH }
                    )
                }

                // Warning for high risk
                if (selectedRisk == CheckInEntry.RISK_HIGH) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    TaqwaAccentCard(accentColor = AccentRed, alpha = 0.1f) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🛡️ Shield Up",
                                style = TaqwaType.cardTitle,
                                color = AccentRedLight
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "Today requires extra vigilance.\nStay busy. Stay connected. Stay praying.\nIf an urge comes, open the app immediately.",
                                style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Encouragement for low risk
                if (selectedRisk == CheckInEntry.RISK_LOW) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    TaqwaAccentCard(accentColor = AccentGreen, alpha = 0.1f) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "💪 Strong Day Ahead",
                                style = TaqwaType.cardTitle,
                                color = AccentGreenLight
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "Alhamdulillah. Use this strength wisely.\nBuild good habits today — they'll protect you\non harder days.",
                                style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                if (currentSection == 2 && selectedRisk != null) {
                    Button(
                        onClick = { currentSection = 3 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text("Continue →", style = TaqwaType.button, color = TextWhite)
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                }
            }

            // ── Section 3: Intention ──
            if (currentSection >= 3) {
                HorizontalDivider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = 48.dp, vertical = TaqwaDimens.spaceL),
                    thickness = TaqwaDimens.dividerThickness
                )

                Text(
                    text = "Set one intention for today",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "What will you commit to today?",
                    style = TaqwaType.captionSmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                OutlinedTextField(
                    value = intention,
                    onValueChange = { intention = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = {
                        Text(
                            "e.g., \"I will pray all 5 prayers on time\"\n\"I will not be alone with my phone tonight\"\n\"I will call a friend if I feel lonely\"",
                            style = TaqwaType.bodySmall,
                            color = TextMuted,
                            lineHeight = 20.sp
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

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

                // Complete button
                Button(
                    onClick = {
                        if (selectedMood != null && selectedRisk != null) {
                            onComplete(selectedMood!!, selectedRisk!!, intention)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = selectedMood != null && selectedRisk != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VictoryGreen,
                        disabledContainerColor = VictoryGreen.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Text(
                        text = "☀️  Start My Day With Strength",
                        style = TaqwaType.button.copy(fontSize = 16.sp),
                        color = TextWhite
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
            }
        }
    }
}

@Composable
private fun MoodOption(
    emoji: String,
    label: String,
    value: String,
    selected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) color.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(300),
        label = "mood_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) color else BackgroundLight,
        animationSpec = tween(300),
        label = "mood_border"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = TaqwaDimens.spaceL, horizontal = TaqwaDimens.spaceM)
    ) {
        Text(text = emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = label,
            style = TaqwaType.cardTitle,
            color = if (selected) color else TextGray
        )
    }
}

@Composable
private fun RiskOption(
    emoji: String,
    label: String,
    sublabel: String,
    value: String,
    selected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) color.copy(alpha = 0.12f) else BackgroundCard,
        animationSpec = tween(300),
        label = "risk_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) color else BackgroundLight,
        animationSpec = tween(300),
        label = "risk_border"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(105.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = TaqwaDimens.spaceL, horizontal = TaqwaDimens.spaceS)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = label,
            style = TaqwaType.cardTitle,
            color = if (selected) color else TextGray
        )
        Text(
            text = sublabel,
            style = TaqwaType.captionSmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}