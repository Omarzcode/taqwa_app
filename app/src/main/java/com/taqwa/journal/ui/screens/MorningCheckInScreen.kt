package com.taqwa.journal.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.CheckInEntry
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun MorningCheckInScreen(
    currentStreak: Int,
    dailyAyah: DailyAyah?,
    memoryBankEntry: MemoryEntry?,
    yesterdayCheckIn: CheckInEntry?,
    todayCheckIn: CheckInEntry?,
    alreadyCheckedIn: Boolean,
    onComplete: (mood: String, riskLevel: String, intention: String, sleepQuality: String, gratitude: String, yesterdayFollowed: Boolean) -> Unit,
    onBack: () -> Unit
) {
    // If already checked in, show summary
    if (alreadyCheckedIn && todayCheckIn != null) {
        CheckInSummaryScreen(
            checkIn = todayCheckIn,
            currentStreak = currentStreak,
            onBack = onBack
        )
        return
    }

    // Determine total steps dynamically
    val hasYesterdayIntention = yesterdayCheckIn?.intention?.isNotBlank() == true
    val hour = java.time.LocalTime.now().hour
    val isMorning = hour in 4..11
    val timeLabel = when {
        hour in 4..11 -> "Morning"
        hour in 12..16 -> "Afternoon"
        hour in 17..20 -> "Evening"
        else -> "Night"
    }

    // Steps: Greeting, (Yesterday?), Sleep, Mood, Gratitude, Risk, Intention+Complete, Done
    val stepYesterday = if (hasYesterdayIntention) 1 else -1
    val stepSleep = if (hasYesterdayIntention) 2 else 1
    val stepMood = stepSleep + 1
    val stepGratitude = stepMood + 1
    val stepRisk = stepGratitude + 1
    val stepIntention = stepRisk + 1
    val stepDone = stepIntention + 1
    val totalSteps = stepDone + 1
    val progressSteps = totalSteps - 1 // exclude done page from progress

    var currentStep by remember { mutableIntStateOf(0) }
    var selectedSleep by remember { mutableStateOf<String?>(null) }
    var selectedMood by remember { mutableStateOf<String?>(null) }
    var selectedRisk by remember { mutableStateOf<String?>(null) }
    var gratitude by remember { mutableStateOf("") }
    var intention by remember { mutableStateOf("") }
    var yesterdayFollowed by remember { mutableStateOf(false) }
    var completionTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(completionTriggered) {
        if (completionTriggered) {
            delay(2800)
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top bar
        CheckInTopBar(
            currentStep = currentStep,
            totalProgress = progressSteps,
            timeLabel = if (currentStep < stepDone) "$timeLabel Check-In" else "",
            onBack = {
                if (currentStep > 0) currentStep-- else onBack()
            }
        )

        // Step content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { it / 3 } + fadeIn(tween(300))) togetherWith
                                (slideOutHorizontally { -it / 3 } + fadeOut(tween(200)))
                    } else {
                        (slideInHorizontally { -it / 3 } + fadeIn(tween(300))) togetherWith
                                (slideOutHorizontally { it / 3 } + fadeOut(tween(200)))
                    }
                },
                label = "step"
            ) { step ->
                when (step) {
                    0 -> StepGreeting(
                        currentStreak = currentStreak,
                        dailyAyah = dailyAyah,
                        isMorning = isMorning,
                        timeLabel = timeLabel,
                        onContinue = { currentStep = 1 }
                    )
                    stepYesterday -> StepYesterdayAccountability(
                        yesterdayIntention = yesterdayCheckIn?.intention ?: "",
                        followed = yesterdayFollowed,
                        onFollowedChanged = { yesterdayFollowed = it },
                        onContinue = { currentStep = stepSleep }
                    )
                    stepSleep -> StepSleep(
                        selectedSleep = selectedSleep,
                        onSleepSelected = { selectedSleep = it },
                        onContinue = { currentStep = stepMood }
                    )
                    stepMood -> StepMood(
                        selectedMood = selectedMood,
                        onMoodSelected = { selectedMood = it },
                        onContinue = { currentStep = stepGratitude }
                    )
                    stepGratitude -> StepGratitude(
                        gratitude = gratitude,
                        onGratitudeChanged = { gratitude = it },
                        onContinue = { currentStep = stepRisk }
                    )
                    stepRisk -> StepRisk(
                        selectedRisk = selectedRisk,
                        sleepQuality = selectedSleep,
                        onRiskSelected = { selectedRisk = it },
                        onContinue = { currentStep = stepIntention }
                    )
                    stepIntention -> StepIntention(
                        intention = intention,
                        memoryBankEntry = memoryBankEntry,
                        onIntentionChanged = { intention = it },
                        onContinue = {
                            onComplete(
                                selectedMood ?: CheckInEntry.MOOD_OKAY,
                                selectedRisk ?: CheckInEntry.RISK_MEDIUM,
                                intention,
                                selectedSleep ?: "",
                                gratitude,
                                yesterdayFollowed
                            )
                            currentStep = stepDone
                            completionTriggered = true
                        }
                    )
                    stepDone -> StepCompletion(
                        currentStreak = currentStreak,
                        isMorning = isMorning
                    )
                }
            }
        }
    }
}

// ════════════════════════════════════════════
// ALREADY CHECKED IN — SUMMARY VIEW
// ════════════════════════════════════════════

@Composable
private fun CheckInSummaryScreen(
    checkIn: CheckInEntry,
    currentStreak: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal, vertical = TaqwaDimens.spaceL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                style = TaqwaType.sectionTitle,
                color = TextGray,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(TaqwaDimens.spaceS)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Today's Check-In",
                style = TaqwaType.caption.copy(fontWeight = FontWeight.Medium, letterSpacing = 1.sp),
                color = TextMuted
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.size(32.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Completed badge
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VictoryGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("✅", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Text(
                text = "Check-in complete",
                style = TaqwaType.screenTitle.copy(fontSize = 22.sp),
                color = VanillaCustard
            )

            Text(
                text = if (checkIn.isMorning) "Completed this morning" else "Completed today",
                style = TaqwaType.bodySmall,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

            // Summary cards
            if (checkIn.sleepQuality.isNotEmpty()) {
                SummaryRow(
                    emoji = when (checkIn.sleepQuality) {
                        CheckInEntry.SLEEP_GOOD -> "😴"
                        CheckInEntry.SLEEP_OKAY -> "😐"
                        else -> "😫"
                    },
                    label = "Sleep",
                    value = checkIn.sleepQuality.lowercase().replaceFirstChar { it.uppercase() },
                    color = when (checkIn.sleepQuality) {
                        CheckInEntry.SLEEP_GOOD -> AccentGreen
                        CheckInEntry.SLEEP_OKAY -> AccentOrange
                        else -> AccentRed
                    }
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            SummaryRow(
                emoji = when (checkIn.mood) {
                    CheckInEntry.MOOD_GOOD -> "😊"
                    CheckInEntry.MOOD_OKAY -> "😐"
                    else -> "😔"
                },
                label = "Mood",
                value = checkIn.mood.lowercase().replaceFirstChar { it.uppercase() },
                color = when (checkIn.mood) {
                    CheckInEntry.MOOD_GOOD -> AccentGreen
                    CheckInEntry.MOOD_OKAY -> AccentOrange
                    else -> AccentRed
                }
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            SummaryRow(
                emoji = when (checkIn.riskLevel) {
                    CheckInEntry.RISK_LOW -> "🟢"
                    CheckInEntry.RISK_MEDIUM -> "🟡"
                    else -> "🔴"
                },
                label = "Risk Level",
                value = checkIn.riskLevel.lowercase().replaceFirstChar { it.uppercase() },
                color = when (checkIn.riskLevel) {
                    CheckInEntry.RISK_LOW -> AccentGreen
                    CheckInEntry.RISK_MEDIUM -> AccentOrange
                    else -> AccentRed
                }
            )

            if (checkIn.gratitude.isNotBlank()) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                SummaryRow(emoji = "🤲", label = "Grateful for", value = checkIn.gratitude, color = VanillaCustard)
            }

            if (checkIn.intention.isNotBlank()) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                SummaryRow(emoji = "🎯", label = "Intention", value = checkIn.intention, color = PrimaryLight)
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

            Text(
                text = "You can only check in once per day.\nCome back tomorrow for your next check-in.",
                style = TaqwaType.bodySmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }
    }
}

@Composable
private fun SummaryRow(emoji: String, label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundCard)
            .padding(horizontal = TaqwaDimens.cardPadding, vertical = TaqwaDimens.spaceL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = TaqwaType.captionSmall, color = TextMuted)
            Text(text = value, style = TaqwaType.cardTitle, color = color, maxLines = 2)
        }
    }
}

// ════════════════════════════════════════════
// TOP BAR WITH PROGRESS
// ════════════════════════════════════════════

@Composable
private fun CheckInTopBar(
    currentStep: Int,
    totalProgress: Int,
    timeLabel: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundDark)
            .padding(top = TaqwaDimens.spaceL)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                style = TaqwaType.sectionTitle,
                color = TextGray,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(TaqwaDimens.spaceS)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = timeLabel,
                style = TaqwaType.caption.copy(fontWeight = FontWeight.Medium, letterSpacing = 1.sp),
                color = TextMuted
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.size(32.dp))
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        if (currentStep < totalProgress) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = TaqwaDimens.spaceHuge),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until totalProgress) {
                    val isActive = i <= currentStep
                    val dotColor by animateColorAsState(
                        targetValue = if (isActive) VanillaCustard else BackgroundLight,
                        animationSpec = tween(300), label = "dot_$i"
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(dotColor)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
    }
}

// ════════════════════════════════════════════
// STEP 0: GREETING
// ════════════════════════════════════════════

@Composable
private fun StepGreeting(
    currentStreak: Int,
    dailyAyah: DailyAyah?,
    isMorning: Boolean,
    timeLabel: String,
    onContinue: () -> Unit
) {
    val fadeIn = remember { Animatable(0f) }
    LaunchedEffect(Unit) { fadeIn.animateTo(1f, tween(600)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
            .graphicsLayer { alpha = fadeIn.value },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(
            text = "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ",
            style = TaqwaType.arabicMedium.copy(fontSize = 22.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        val greetingEmoji = when {
            !isMorning && java.time.LocalTime.now().hour > 20 -> "🌙"
            !isMorning -> "🌅"
            else -> "☀️"
        }

        Text(text = greetingEmoji, fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "Good $timeLabel",
            style = TaqwaType.screenTitle.copy(fontSize = 26.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        if (!isMorning) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Text(
                text = "It's not morning, but checking in is always good.\nAllah sees your effort.",
                style = TaqwaType.bodySmall,
                color = AccentOrangeLight,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        if (currentStreak > 0) {
            val streakEmoji = when {
                currentStreak >= 100 -> "💎"
                currentStreak >= 30 -> "🏆"
                currentStreak >= 7 -> "🔥"
                else -> "🌱"
            }
            Text(
                text = "$streakEmoji Day $currentStreak — Alhamdulillah",
                style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
                color = AccentGreenLight,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "A new beginning, with Allah's help",
                style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
                color = PrimaryLight,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        if (dailyAyah != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
                    .background(BackgroundCard)
                    .border(0.5.dp, PrimaryDark.copy(alpha = 0.5f), RoundedCornerShape(TaqwaDimens.cardCornerRadius))
                    .padding(TaqwaDimens.cardPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "📖", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    Text(
                        text = dailyAyah.arabic,
                        style = TaqwaType.arabicMedium.copy(fontSize = 17.sp),
                        color = VanillaCustard,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\"${dailyAyah.translation}\"",
                        style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(text = "— ${dailyAyah.reference}", style = TaqwaType.captionSmall, color = TextMuted)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text("Begin Check-In  →", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: YESTERDAY ACCOUNTABILITY
// ════════════════════════════════════════════

@Composable
private fun StepYesterdayAccountability(
    yesterdayIntention: String,
    followed: Boolean,
    onFollowedChanged: (Boolean) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        Text(text = "📋", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Yesterday's Intention",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundCard)
                .padding(TaqwaDimens.cardPadding)
        ) {
            Text(
                text = "\"$yesterdayIntention\"",
                style = TaqwaType.body.copy(lineHeight = 22.sp),
                color = VanillaCustard,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Did you follow through?",
            style = TaqwaType.sectionTitle,
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceM)
        ) {
            AccountabilityOption(
                emoji = "✅",
                label = "Yes, I did",
                isSelected = followed,
                color = AccentGreen,
                onClick = { onFollowedChanged(true) },
                modifier = Modifier.weight(1f)
            )
            AccountabilityOption(
                emoji = "😔",
                label = "Not fully",
                isSelected = !followed,
                color = AccentOrange,
                onClick = { onFollowedChanged(false) },
                modifier = Modifier.weight(1f)
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(400))
        ) {
            Text(
                text = if (followed)
                    "MashaAllah! Consistency builds character."
                else
                    "That's okay. Every day is a new chance.\nWhat matters is you're here now.",
                style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                color = if (followed) AccentGreenLight else PrimaryLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TaqwaDimens.spaceL)
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text("Continue  →", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

@Composable
private fun AccountabilityOption(
    emoji: String,
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) color.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(300), label = "acc_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) color else BackgroundLight.copy(alpha = 0.5f),
        animationSpec = tween(300), label = "acc_border"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = TaqwaDimens.spaceXL, horizontal = TaqwaDimens.spaceM)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = label,
            style = TaqwaType.cardTitle,
            color = if (isSelected) color else TextGray
        )
    }
}

// ════════════════════════════════════════════
// STEP: SLEEP QUALITY
// ════════════════════════════════════════════

@Composable
private fun StepSleep(
    selectedSleep: String?,
    onSleepSelected: (String) -> Unit,
    onContinue: () -> Unit
) {
    val sleepOptions = listOf(
        Triple("😴", "Good Sleep", CheckInEntry.SLEEP_GOOD) to AccentGreen,
        Triple("😐", "Okay Sleep", CheckInEntry.SLEEP_OKAY) to AccentOrange,
        Triple("😫", "Bad Sleep", CheckInEntry.SLEEP_BAD) to AccentRed
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        Text(
            text = "How did you sleep?",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Sleep quality is the #1 predictor of your strength today",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        sleepOptions.forEachIndexed { index, (info, color) ->
            val (emoji, label, value) = info
            SelectionCard(
                emoji = emoji,
                label = label,
                sublabel = when (value) {
                    CheckInEntry.SLEEP_GOOD -> "6+ hours, rested"
                    CheckInEntry.SLEEP_OKAY -> "Some rest, not great"
                    else -> "Barely slept, exhausted"
                },
                isSelected = selectedSleep == value,
                color = color,
                onClick = { onSleepSelected(value) }
            )
            if (index < sleepOptions.lastIndex) Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }

        AnimatedVisibility(
            visible = selectedSleep == CheckInEntry.SLEEP_BAD,
            enter = fadeIn(tween(400)) + expandVertically(tween(400))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = TaqwaDimens.spaceL)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AccentRed.copy(alpha = 0.1f))
                    .padding(TaqwaDimens.spaceL)
            ) {
                Row {
                    Text(text = "⚠️", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                    Text(
                        text = "Poor sleep weakens willpower. Be extra vigilant today — keep your shield up.",
                        style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                        color = AccentRedLight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.2f))

        AnimatedVisibility(
            visible = selectedSleep != null,
            enter = fadeIn(tween(300)) + slideInVertically { it / 2 }
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text("Continue  →", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: MOOD
// ════════════════════════════════════════════

@Composable
private fun StepMood(
    selectedMood: String?,
    onMoodSelected: (String) -> Unit,
    onContinue: () -> Unit
) {
    val moods = listOf(
        Triple("😊", "Good", CheckInEntry.MOOD_GOOD) to AccentGreen,
        Triple("😐", "Okay", CheckInEntry.MOOD_OKAY) to AccentOrange,
        Triple("😔", "Low", CheckInEntry.MOOD_LOW) to AccentRed
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        Text(
            text = "How are you feeling?",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Be honest — this is between you and Allah",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        moods.forEachIndexed { index, (info, color) ->
            val (emoji, label, value) = info
            SelectionCard(
                emoji = emoji,
                label = label,
                sublabel = when (value) {
                    CheckInEntry.MOOD_GOOD -> "Feeling positive and hopeful"
                    CheckInEntry.MOOD_OKAY -> "Neutral, manageable"
                    else -> "Struggling, feeling down"
                },
                isSelected = selectedMood == value,
                color = color,
                onClick = { onMoodSelected(value) }
            )
            if (index < moods.lastIndex) Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }

        AnimatedVisibility(
            visible = selectedMood != null,
            enter = fadeIn(tween(400)) + expandVertically(tween(400))
        ) {
            val message = when (selectedMood) {
                CheckInEntry.MOOD_GOOD -> "Alhamdulillah! Channel this energy into good deeds."
                CheckInEntry.MOOD_OKAY -> "A steady heart is a strong heart. Keep going."
                CheckInEntry.MOOD_LOW -> "\"Allah does not burden a soul beyond what it can bear.\" — 2:286"
                else -> ""
            }
            val msgColor = when (selectedMood) {
                CheckInEntry.MOOD_GOOD -> AccentGreenLight
                CheckInEntry.MOOD_LOW -> AccentRedLight
                else -> PrimaryLight
            }
            Text(
                text = message,
                style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                color = msgColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TaqwaDimens.spaceXL, start = TaqwaDimens.spaceL, end = TaqwaDimens.spaceL)
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))

        AnimatedVisibility(
            visible = selectedMood != null,
            enter = fadeIn(tween(300)) + slideInVertically { it / 2 }
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text("Continue  →", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: GRATITUDE
// ════════════════════════════════════════════

@Composable
private fun StepGratitude(
    gratitude: String,
    onGratitudeChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        Text(text = "🤲", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "One thing you're\ngrateful for",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Gratitude rewires the brain away from craving",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = gratitude,
            onValueChange = onGratitudeChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            placeholder = {
                Text(
                    "e.g., \"My family's support\"\n\"Waking up healthy today\"\n\"Another chance to be better\"",
                    style = TaqwaType.bodySmall,
                    color = TextMuted.copy(alpha = 0.6f),
                    lineHeight = 20.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard.copy(alpha = 0.5f),
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedContainerColor = BackgroundCard,
                unfocusedContainerColor = BackgroundCard
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        // Quick gratitude chips
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        val quickGratitudes = listOf("Family", "Health", "Iman", "Another day", "Food & shelter")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            quickGratitudes.forEach { text ->
                QuickChip(
                    text = text,
                    isSelected = gratitude == text,
                    onClick = { onGratitudeChanged(text) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.2f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = if (gratitude.isBlank()) "Skip  →" else "Continue  →",
                style = TaqwaType.button.copy(fontSize = 15.sp),
                color = TextWhite
            )
        }

        if (gratitude.isBlank()) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Text(
                text = "It's okay to skip, but try to find something",
                style = TaqwaType.captionSmall,
                color = TextMuted.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: RISK LEVEL
// ════════════════════════════════════════════

@Composable
private fun StepRisk(
    selectedRisk: String?,
    sleepQuality: String?,
    onRiskSelected: (String) -> Unit,
    onContinue: () -> Unit
) {
    val risks = listOf(
        Triple("🟢", "Low Risk", CheckInEntry.RISK_LOW) to AccentGreen,
        Triple("🟡", "Medium Risk", CheckInEntry.RISK_MEDIUM) to AccentOrange,
        Triple("🔴", "High Risk", CheckInEntry.RISK_HIGH) to AccentRed
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        Text(
            text = "How vulnerable\ndo you feel today?",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Honesty here builds your shield",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center
        )

        // Warning if bad sleep
        if (sleepQuality == CheckInEntry.SLEEP_BAD) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = "⚠️ Poor sleep detected — consider marking higher risk",
                style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.Medium),
                color = AccentOrangeLight,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        risks.forEachIndexed { index, (info, color) ->
            val (emoji, label, value) = info
            SelectionCard(
                emoji = emoji,
                label = label,
                sublabel = when (value) {
                    CheckInEntry.RISK_LOW -> "Feeling strong and focused"
                    CheckInEntry.RISK_MEDIUM -> "Could go either way today"
                    else -> "Feeling vulnerable and weak"
                },
                isSelected = selectedRisk == value,
                color = color,
                onClick = { onRiskSelected(value) }
            )
            if (index < risks.lastIndex) Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }

        AnimatedVisibility(
            visible = selectedRisk != null,
            enter = fadeIn(tween(400)) + expandVertically(tween(400))
        ) {
            val (adviceEmoji, adviceText, adviceColor) = when (selectedRisk) {
                CheckInEntry.RISK_LOW -> Triple("💪", "Alhamdulillah! Build good habits on strong days — they protect you on weak ones.", AccentGreenLight)
                CheckInEntry.RISK_MEDIUM -> Triple("🛡️", "Stay aware. Plan around your triggers. Keep this app close.", AccentOrangeLight)
                CheckInEntry.RISK_HIGH -> Triple("⚠️", "Extra vigilance today. Stay busy, stay praying, stay connected. Open this app at the first sign of an urge.", AccentRedLight)
                else -> Triple("", "", TextMuted)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = TaqwaDimens.spaceL)
                    .clip(RoundedCornerShape(12.dp))
                    .background(adviceColor.copy(alpha = 0.1f))
                    .padding(TaqwaDimens.spaceL)
            ) {
                Row {
                    Text(text = adviceEmoji, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                    Text(text = adviceText, style = TaqwaType.bodySmall.copy(lineHeight = 20.sp), color = adviceColor)
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.2f))

        AnimatedVisibility(
            visible = selectedRisk != null,
            enter = fadeIn(tween(300)) + slideInVertically { it / 2 }
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text("Continue  →", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: INTENTION + MEMORY
// ════════════════════════════════════════════

@Composable
private fun StepIntention(
    intention: String,
    memoryBankEntry: MemoryEntry?,
    onIntentionChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(
            text = "Set your intention",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "\"If you intend to do something, plan it\" — implementation intentions increase follow-through by 2-3x",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = intention,
            onValueChange = onIntentionChanged,
            modifier = Modifier.fillMaxWidth().height(120.dp),
            placeholder = {
                Text(
                    "e.g., \"I will pray all 5 prayers on time\"\n\"I will not be alone with my phone at night\"\n\"I will exercise when I feel restless\"",
                    style = TaqwaType.bodySmall,
                    color = TextMuted.copy(alpha = 0.6f),
                    lineHeight = 20.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard.copy(alpha = 0.5f),
                unfocusedBorderColor = BackgroundLight,
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedContainerColor = BackgroundCard,
                unfocusedContainerColor = BackgroundCard
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        val quickIntentions = listOf(
            "Pray all 5 on time",
            "No phone alone at night",
            "Read Quran today",
            "Exercise when restless",
            "Call a friend if lonely"
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                quickIntentions.take(3).forEach { text ->
                    QuickChip(
                        text = text,
                        isSelected = intention == text,
                        onClick = { onIntentionChanged(text) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                quickIntentions.drop(3).forEach { text ->
                    QuickChip(
                        text = text,
                        isSelected = intention == text,
                        onClick = { onIntentionChanged(text) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (memoryBankEntry != null) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

            val memColor = when (memoryBankEntry.type) {
                MemoryEntry.TYPE_RELAPSE_LETTER -> AccentRed
                MemoryEntry.TYPE_VICTORY_NOTE -> AccentGreen
                else -> PrimaryLight
            }
            val memLabel = when (memoryBankEntry.type) {
                MemoryEntry.TYPE_RELAPSE_LETTER -> "📝 Remember this:"
                MemoryEntry.TYPE_VICTORY_NOTE -> "🏆 You wrote after a victory:"
                else -> "💭 From your memory bank:"
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(memColor.copy(alpha = 0.08f))
                    .border(0.5.dp, memColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(TaqwaDimens.spaceL)
            ) {
                Column {
                    Text(
                        text = memLabel,
                        style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = memColor
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\"${memoryBankEntry.message}\"",
                        style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                        color = TextLight,
                        maxLines = 3
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VictoryGreen),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = "☀️  Start My Day With Strength",
                style = TaqwaType.button.copy(fontSize = 15.sp),
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

        Text(
            text = "Intention is optional — mood & risk are enough",
            style = TaqwaType.captionSmall,
            color = TextMuted.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: COMPLETION
// ════════════════════════════════════════════

@Composable
private fun StepCompletion(
    currentStreak: Int,
    isMorning: Boolean
) {
    val scaleAnim = remember { Animatable(0.5f) }
    val fadeAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }
    LaunchedEffect(Unit) {
        fadeAnim.animateTo(1f, tween(800))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
            .graphicsLayer { alpha = fadeAnim.value },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.3f))

        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(scaleAnim.value)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(VictoryGreen, VictoryGreen.copy(alpha = 0.7f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", color = TextWhite, fontSize = 36.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "You're ready",
            style = TaqwaType.screenTitle.copy(fontSize = 26.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = if (currentStreak > 0) "Day ${currentStreak + 1} begins with strength"
            else "A new chapter begins now",
            style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundCard)
                .padding(TaqwaDimens.cardPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "رَبِّ أَعِنِّي وَلَا تُعِنْ عَلَيَّ",
                    style = TaqwaType.arabicMedium.copy(fontSize = 20.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "\"My Lord, help me and do not help against me\"",
                    style = TaqwaType.bodySmall,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "— Du'a of Prophet Ibrahim (AS)",
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.3f))

        Text(
            text = "Returning to home...",
            style = TaqwaType.captionSmall,
            color = TextMuted.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// REUSABLE COMPONENTS
// ════════════════════════════════════════════

@Composable
private fun SelectionCard(
    emoji: String,
    label: String,
    sublabel: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) color.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(300), label = "sel_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) color else BackgroundLight.copy(alpha = 0.5f),
        animationSpec = tween(300), label = "sel_border"
    )
    val cardScale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "sel_scale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = TaqwaDimens.cardPadding, vertical = TaqwaDimens.spaceL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = TaqwaType.cardTitle,
                color = if (isSelected) color else TextWhite
            )
            Text(text = sublabel, style = TaqwaType.captionSmall, color = TextMuted)
        }
        if (isSelected) {
            Box(
                modifier = Modifier.size(24.dp).clip(CircleShape).background(color),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Box(
                modifier = Modifier.size(24.dp).clip(CircleShape).border(1.dp, BackgroundLight, CircleShape)
            )
        }
    }
}

@Composable
private fun QuickChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) VanillaCustard.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(200), label = "chip_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) VanillaCustard.copy(alpha = 0.5f) else BackgroundLight.copy(alpha = 0.3f),
        animationSpec = tween(200), label = "chip_border"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(0.5.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = TaqwaDimens.spaceS, vertical = TaqwaDimens.spaceS),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TaqwaType.captionSmall,
            color = if (isSelected) VanillaCustard else TextGray,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}