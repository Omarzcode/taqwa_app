package com.taqwa.journal.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun RelapseRecoveryScreen(
    currentStreak: Int,
    longestStreak: Int,
    totalRelapses: Int,
    onComplete: (
        trigger: String,
        feeling: String,
        situation: String,
        shieldNote: String,
        letterToSelf: String,
        newIntention: String
    ) -> Unit,
    onBack: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    val totalSteps = 8

    var selectedTrigger by remember { mutableStateOf("") }
    var customTrigger by remember { mutableStateOf("") }
    var selectedFeeling by remember { mutableStateOf("") }
    var customFeeling by remember { mutableStateOf("") }
    var situation by remember { mutableStateOf("") }
    var shieldNote by remember { mutableStateOf("") }
    var letterToSelf by remember { mutableStateOf("") }
    var newIntention by remember { mutableStateOf("") }

    var isCompleted by remember { mutableStateOf(false) }

    if (isCompleted) {
        RecoveryCompletionScreen(
            previousStreak = currentStreak,
            longestStreak = longestStreak,
            totalRelapses = totalRelapses + 1,
            onDone = onBack
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "",
            onBack = {
                if (currentStep > 0) {
                    currentStep--
                } else {
                    onBack()
                }
            }
        )

        LinearProgressIndicator(
            progress = { (currentStep + 1).toFloat() / totalSteps },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = VanillaCustard,
            trackColor = BackgroundLight
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

        Text(
            text = "Step ${currentStep + 1} of $totalSteps",
            style = TaqwaType.captionSmall,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    (fadeIn(tween(300)) + slideInHorizontally { it / 3 }) togetherWith
                            (fadeOut(tween(200)) + slideOutHorizontally { -it / 3 })
                },
                label = "step_transition"
            ) { step ->
                when (step) {
                    0 -> RecoveryStep0_Pause()
                    1 -> RecoveryStep1_Compassion()
                    2 -> RecoveryStep2_Trigger(
                        selectedTrigger = selectedTrigger,
                        customTrigger = customTrigger,
                        onTriggerSelect = { selectedTrigger = it },
                        onCustomTriggerChange = { customTrigger = it }
                    )
                    3 -> RecoveryStep3_Feeling(
                        selectedFeeling = selectedFeeling,
                        customFeeling = customFeeling,
                        onFeelingSelect = { selectedFeeling = it },
                        onCustomFeelingChange = { customFeeling = it }
                    )
                    4 -> RecoveryStep4_Situation(
                        situation = situation,
                        onSituationChange = { situation = it }
                    )
                    5 -> RecoveryStep5_ShieldReview(
                        selectedTrigger = selectedTrigger.ifEmpty { customTrigger },
                        shieldNote = shieldNote,
                        onShieldNoteChange = { shieldNote = it }
                    )
                    6 -> RecoveryStep6_Letter(
                        letterToSelf = letterToSelf,
                        currentStreak = currentStreak,
                        onLetterChange = { letterToSelf = it }
                    )
                    7 -> RecoveryStep7_NewIntention(
                        newIntention = newIntention,
                        onIntentionChange = { newIntention = it }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = TaqwaDimens.screenPaddingHorizontal,
                    vertical = TaqwaDimens.spaceM
                ),
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
        ) {
            if (currentStep > 1) {
                OutlinedButton(
                    onClick = { currentStep-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextLight),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Text("\u2190  Back", style = TaqwaType.button)
                }
            }

            Button(
                onClick = {
                    if (currentStep < totalSteps - 1) {
                        currentStep++
                    } else {
                        val triggerFinal = selectedTrigger.ifEmpty { customTrigger }
                        val feelingFinal = selectedFeeling.ifEmpty { customFeeling }
                        onComplete(
                            triggerFinal,
                            feelingFinal,
                            situation,
                            shieldNote,
                            letterToSelf,
                            newIntention
                        )
                        isCompleted = true
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (currentStep) {
                        0, 1 -> PrimaryMedium
                        7 -> AccentGreen
                        else -> PrimaryMedium
                    }
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = when (currentStep) {
                        0 -> "I'm ready  \u2192"
                        1 -> "Continue  \u2192"
                        7 -> "Begin Fresh  \u2728"
                        else -> "Next  \u2192"
                    },
                    style = TaqwaType.button,
                    color = TextWhite
                )
            }
        }
    }
}

// ════════════════════════════════════════════
// STEP 0: PAUSE
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep0_Pause() {
    val infiniteTransition = rememberInfiniteTransition(label = "breathe")
    val breatheScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83D\uDD4A\uFE0F",
            fontSize = 56.sp,
            modifier = Modifier.scale(breatheScale)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Stop.",
            style = TaqwaType.screenTitle.copy(fontSize = 28.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "Breathe.",
            style = TaqwaType.screenTitle.copy(fontSize = 28.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "You're still here.",
            style = TaqwaType.screenTitle.copy(fontSize = 28.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(
            text = "This moment does not define you.\nWhat you do RIGHT NOW defines you.",
            style = TaqwaType.body.copy(lineHeight = 26.sp),
            color = TextLight,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
            Text(
                text = "The fact that you opened this app instead of running away\nshows incredible courage.\n\nLet's process this together.",
                style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                color = TextLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ════════════════════════════════════════════
// STEP 1: COMPASSION
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep1_Compassion() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "\uD83E\uDD32", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\u0642\u064F\u0644\u0652 \u064A\u064E\u0627 \u0639\u0650\u0628\u064E\u0627\u062F\u0650\u064A\u064E \u0627\u0644\u0651\u064E\u0630\u0650\u064A\u0646\u064E \u0623\u064E\u0633\u0652\u0631\u064E\u0641\u064F\u0648\u0627 \u0639\u064E\u0644\u064E\u0649\u0670 \u0623\u064E\u0646\u0641\u064F\u0633\u0650\u0647\u0650\u0645\u0652 \u0644\u064E\u0627 \u062A\u064E\u0642\u0652\u0646\u064E\u0637\u064F\u0648\u0627 \u0645\u0650\u0646 \u0631\u0651\u064E\u062D\u0652\u0645\u064E\u0629\u0650 \u0627\u0644\u0644\u0651\u064E\u0647\u0650",
                    style = TaqwaType.arabicMedium.copy(lineHeight = 32.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "\"Say: O My servants who have transgressed against themselves,\ndo not despair of the mercy of Allah.\nIndeed, Allah forgives all sins.\"",
                    style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "\u2014 Az-Zumar 39:53",
                    style = TaqwaType.captionSmall,
                    color = TextGray
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Allah called you \"My servants\"\neven while speaking about sin.",
            style = TaqwaType.body.copy(
                fontWeight = FontWeight.Medium,
                lineHeight = 26.sp
            ),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "You belong to Him.\nA slip doesn't change that.\n\nWhat matters is what you do next.",
            style = TaqwaType.body.copy(lineHeight = 26.sp),
            color = TextLight,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "The Prophet (\u0635\u0644\u0649 \u0627\u0644\u0644\u0647 \u0639\u0644\u064A\u0647 \u0648\u0633\u0644\u0645) said:",
                    style = TaqwaType.captionSmall,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "\"Every son of Adam sins, and the best of sinners\nare those who repent.\"",
                    style = TaqwaType.body.copy(
                        fontWeight = FontWeight.Medium,
                        lineHeight = 24.sp
                    ),
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "\u2014 Tirmidhi 2499",
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
            }
        }
    }
}

// ════════════════════════════════════════════
// STEP 2: TRIGGER
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep2_Trigger(
    selectedTrigger: String,
    customTrigger: String,
    onTriggerSelect: (String) -> Unit,
    onCustomTriggerChange: (String) -> Unit
) {
    val triggers = listOf(
        "\uD83D\uDE34" to "Boredom",
        "\uD83D\uDE14" to "Loneliness",
        "\uD83D\uDE29" to "Stress",
        "\uD83D\uDE2B" to "Tiredness",
        "\uD83D\uDCF1" to "Social Media",
        "\uD83C\uDF19" to "Late Night",
        "\uD83D\uDE20" to "Anger/Frustration",
        "\uD83C\uDF89" to "Celebration/Reward",
        "\uD83D\uDE22" to "Sadness",
        "\uD83D\uDC40" to "Accidental Exposure"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\uD83D\uDD0D", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "What Led to This?",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "No judgment. Just understanding.\nThis helps you build stronger defenses.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        triggers.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                row.forEach { (emoji, label) ->
                    val isSelected = selectedTrigger == label
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius))
                            .clickable { onTriggerSelect(if (isSelected) "" else label) },
                        color = if (isSelected) PrimaryMedium else BackgroundCard,
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = TaqwaDimens.spaceM),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = emoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                            Text(
                                text = label,
                                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = if (isSelected) TextWhite else TextLight,
                                maxLines = 1
                            )
                        }
                    }
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        OutlinedTextField(
            value = customTrigger,
            onValueChange = {
                onCustomTriggerChange(it)
                if (it.isNotBlank()) onTriggerSelect("")
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Or describe what happened...",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

// ════════════════════════════════════════════
// STEP 3: FEELING
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep3_Feeling(
    selectedFeeling: String,
    customFeeling: String,
    onFeelingSelect: (String) -> Unit,
    onCustomFeelingChange: (String) -> Unit
) {
    val feelings = listOf(
        "\uD83D\uDE1E" to "Empty inside",
        "\uD83D\uDE10" to "Numb/Disconnected",
        "\uD83D\uDE22" to "Sad/Depressed",
        "\uD83D\uDE30" to "Anxious",
        "\uD83D\uDE24" to "Frustrated",
        "\uD83E\uDD71" to "Exhausted",
        "\uD83D\uDE14" to "Lonely",
        "\uD83D\uDE15" to "Confused",
        "\uD83E\uDD2F" to "Overwhelmed",
        "\uD83D\uDE36" to "Nothing specific"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\u2764\uFE0F", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "What Were You Feeling?",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "The urge is almost never about desire.\nIt's about an unmet emotional need.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        feelings.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                row.forEach { (emoji, label) ->
                    val isSelected = selectedFeeling == label
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius))
                            .clickable { onFeelingSelect(if (isSelected) "" else label) },
                        color = if (isSelected) PrimaryMedium else BackgroundCard,
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = TaqwaDimens.spaceM),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = emoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                            Text(
                                text = label,
                                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = if (isSelected) TextWhite else TextLight,
                                maxLines = 1
                            )
                        }
                    }
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        OutlinedTextField(
            value = customFeeling,
            onValueChange = {
                onCustomFeelingChange(it)
                if (it.isNotBlank()) onFeelingSelect("")
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Or describe in your own words...",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

// ════════════════════════════════════════════
// STEP 4: SITUATION
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep4_Situation(
    situation: String,
    onSituationChange: (String) -> Unit
) {
    val quickChips = listOf(
        "In bed late at night",
        "Alone at home",
        "After a stressful day",
        "In the bathroom",
        "While browsing phone",
        "After an argument"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\uD83D\uDDFA\uFE0F", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Where & When?",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Understanding the situation helps you\navoid it next time.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = situation,
            onValueChange = onSituationChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            placeholder = {
                Text(
                    "Describe the situation...\n\nWhere were you? What time? What were you doing before?",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Quick fill:",
            style = TaqwaType.captionSmall,
            color = TextMuted,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        quickChips.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                row.forEach { chip ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius))
                            .clickable {
                                val newText = if (situation.isBlank()) chip
                                else "$situation. $chip"
                                onSituationChange(newText)
                            },
                        color = BackgroundCard,
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    ) {
                        Text(
                            text = chip,
                            style = TaqwaType.captionSmall,
                            color = TextLight,
                            modifier = Modifier.padding(
                                horizontal = TaqwaDimens.spaceM,
                                vertical = TaqwaDimens.spaceS
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

// ════════════════════════════════════════════
// STEP 5: SHIELD REVIEW
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep5_ShieldReview(
    selectedTrigger: String,
    shieldNote: String,
    onShieldNoteChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\uD83D\uDEE1\uFE0F", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Strengthen Your Shield",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        if (selectedTrigger.isNotBlank()) {
            Text(
                text = "Your trigger was: $selectedTrigger",
                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = AccentOrange,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }

        Text(
            text = "Every relapse reveals a gap in your defenses.\nLet's patch it.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaCard {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "\uD83E\uDD14  Ask yourself:",
                    style = TaqwaType.cardTitle,
                    color = VanillaCustard
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                ReflectionQuestion("What could I have done differently?")
                ReflectionQuestion("At what point could I have stopped?")
                ReflectionQuestion("What warning signs did I ignore?")
                ReflectionQuestion("What tool could have helped? (breathing, dua, calling someone)")
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        OutlinedTextField(
            value = shieldNote,
            onValueChange = onShieldNoteChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            placeholder = {
                Text(
                    "Write your defense plan for next time...\n\ne.g., \"Next time I feel bored at night, I will immediately put my phone in another room and make wudu\"",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

@Composable
private fun ReflectionQuestion(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXXS),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "\u2022",
            style = TaqwaType.body,
            color = VanillaCustard,
            modifier = Modifier.padding(end = TaqwaDimens.spaceS, top = 2.dp)
        )
        Text(
            text = text,
            style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
            color = TextLight
        )
    }
}

// ════════════════════════════════════════════
// STEP 6: LETTER TO SELF
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep6_Letter(
    letterToSelf: String,
    currentStreak: Int,
    onLetterChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\u270D\uFE0F", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Letter to Yourself",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Write something to your future self.\nThis will be saved in your Memory Bank\nand shown during future urges.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        if (currentStreak > 0) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            TaqwaAccentCard(accentColor = AccentRed.copy(alpha = 0.15f), alpha = 0.3f) {
                Text(
                    text = "You just lost a $currentStreak-day streak.\nRemember this feeling. Let it fuel you.",
                    style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                    color = AccentOrange,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = letterToSelf,
            onValueChange = onLetterChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = {
                Text(
                    "Dear future me,\n\nRight now I feel...\nI lost my streak because...\nNext time, remember that...\nYou are stronger than this because...",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "\uD83D\uDCBE  This letter will be saved to your Memory Bank",
            style = TaqwaType.captionSmall,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

// ════════════════════════════════════════════
// STEP 7: NEW INTENTION
// ════════════════════════════════════════════

@Composable
private fun RecoveryStep7_NewIntention(
    newIntention: String,
    onIntentionChange: (String) -> Unit
) {
    val intentionChips = listOf(
        "Sleep early tonight",
        "Pray all 5 salah tomorrow",
        "Make wudu when urge hits",
        "Put phone away at night",
        "Read Quran daily",
        "Exercise daily",
        "Call someone when lonely",
        "Make dua after every prayer"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(text = "\uD83C\uDF05", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Your New Beginning",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Set one concrete intention for your fresh start.\nSmall, specific, achievable.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = newIntention,
            onValueChange = onIntentionChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = {
                Text(
                    "My intention for this fresh start...\n\ne.g., \"I will put my phone in another room every night before bed\"",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Quick pick:",
            style = TaqwaType.captionSmall,
            color = TextMuted,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        intentionChips.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                row.forEach { chip ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius))
                            .clickable { onIntentionChange(chip) },
                        color = BackgroundCard,
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    ) {
                        Text(
                            text = chip,
                            style = TaqwaType.captionSmall,
                            color = TextLight,
                            modifier = Modifier.padding(
                                horizontal = TaqwaDimens.spaceM,
                                vertical = TaqwaDimens.spaceS
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaAccentCard(accentColor = AccentGreen.copy(alpha = 0.15f), alpha = 0.3f) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "\uD83C\uDF31", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "Every expert was once a beginner.\nEvery champion was once a quitter who refused to give up.",
                    style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

// ════════════════════════════════════════════
// COMPLETION SCREEN
// ════════════════════════════════════════════

@Composable
private fun RecoveryCompletionScreen(
    previousStreak: Int,
    longestStreak: Int,
    totalRelapses: Int,
    onDone: () -> Unit
) {
    val scaleAnim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            1f,
            animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f)
        )
        delay(4000)
        onDone()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83C\uDF31",
            fontSize = 64.sp,
            modifier = Modifier.scale(scaleAnim.value)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Fresh Start",
            style = TaqwaType.screenTitle.copy(fontSize = 28.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "Your slate is clean.\nAllah's mercy is greater than any sin.",
            style = TaqwaType.body.copy(lineHeight = 26.sp),
            color = TextLight,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        TaqwaCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (previousStreak > 0) {
                    Text(
                        text = "Previous streak: $previousStreak days",
                        style = TaqwaType.body,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                }
                Text(
                    text = "Your best: $longestStreak days",
                    style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
                    color = VanillaCustard
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "You WILL beat it. In sha Allah.",
                    style = TaqwaType.bodySmall,
                    color = AccentGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\u0627\u0644\u0644\u0651\u064E\u0647\u064F\u0645\u0651\u064E \u0623\u064E\u0639\u0650\u0646\u0651\u0650\u064A \u0639\u064E\u0644\u064E\u0649\u0670 \u0630\u0650\u0643\u0652\u0631\u0650\u0643\u064E \u0648\u064E\u0634\u064F\u0643\u0652\u0631\u0650\u0643\u064E \u0648\u064E\u062D\u064F\u0633\u0652\u0646\u0650 \u0639\u0650\u0628\u064E\u0627\u062F\u064E\u062A\u0650\u0643\u064E",
                    style = TaqwaType.arabicMedium.copy(lineHeight = 30.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "\"O Allah, help me to remember You,\nto thank You, and to worship You well.\"",
                    style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Button(
            onClick = onDone,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = "Begin Day 1  \u2192",
                style = TaqwaType.button.copy(fontSize = 16.sp),
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}