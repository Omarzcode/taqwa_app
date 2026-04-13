package com.taqwa.journal.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.taqwa.journal.data.database.EveningCheckInEntry
import com.taqwa.journal.ui.theme.AccentGreen
import com.taqwa.journal.ui.theme.AccentGreenLight
import com.taqwa.journal.ui.theme.AccentOrange
import com.taqwa.journal.ui.theme.AccentOrangeLight
import com.taqwa.journal.ui.theme.AccentRed
import com.taqwa.journal.ui.theme.BackgroundCard
import com.taqwa.journal.ui.theme.BackgroundDark
import com.taqwa.journal.ui.theme.BackgroundLight
import com.taqwa.journal.ui.theme.PrimaryDark
import com.taqwa.journal.ui.theme.PrimaryLight
import com.taqwa.journal.ui.theme.PrimaryMedium
import com.taqwa.journal.ui.theme.TaqwaDimens
import com.taqwa.journal.ui.theme.TaqwaType
import com.taqwa.journal.ui.theme.TextGray
import com.taqwa.journal.ui.theme.TextLight
import com.taqwa.journal.ui.theme.TextMuted
import com.taqwa.journal.ui.theme.TextWhite
import com.taqwa.journal.ui.theme.VanillaCustard
import com.taqwa.journal.ui.theme.VictoryGreen
import kotlinx.coroutines.delay

@Composable
fun EveningCheckInScreen(
    currentStreak: Int,
    todayMorningCheckIn: CheckInEntry?,
    todayEveningCheckIn: EveningCheckInEntry?,
    alreadyCheckedIn: Boolean,
    onComplete: (
        intentionFollowed: String?,
        intentionNote: String?,
        prayedFive: Boolean,
        morningAdhkar: Boolean,
        eveningAdhkar: Boolean,
        readQuran: Boolean,
        madeIstighfar: Boolean,
        loweredGaze: Boolean,
        hardestMoment: String?,
        hardestTrigger: String?,
        wins: String?,
        tomorrowConcern: String?
    ) -> Unit,
    onBack: () -> Unit
) {
    // If already checked in, show summary
    if (alreadyCheckedIn && todayEveningCheckIn != null) {
        EveningSummaryScreen(
            entry = todayEveningCheckIn,
            currentStreak = currentStreak,
            onBack = onBack
        )
        return
    }

    // Determine steps dynamically
    val hasIntention = todayMorningCheckIn?.intention?.isNotBlank() == true

    // Steps: Greeting, (IntentionReview?), SpiritualChecklist, HardestMoment, Wins, Tomorrow, Done
    val stepIntention = if (hasIntention) 1 else -1
    val stepSpiritual = if (hasIntention) 2 else 1
    val stepHardest = stepSpiritual + 1
    val stepWins = stepHardest + 1
    val stepTomorrow = stepWins + 1
    val stepDone = stepTomorrow + 1
    val totalSteps = stepDone + 1
    val progressSteps = totalSteps - 1

    var currentStep by remember { mutableIntStateOf(0) }

    // Intention review state
    var intentionFollowed by remember { mutableStateOf<String?>(null) }
    var intentionNote by remember { mutableStateOf("") }

    // Spiritual checklist state
    var prayedFive by remember { mutableStateOf(false) }
    var morningAdhkar by remember { mutableStateOf(false) }
    var eveningAdhkar by remember { mutableStateOf(false) }
    var readQuran by remember { mutableStateOf(false) }
    var madeIstighfar by remember { mutableStateOf(false) }
    var loweredGaze by remember { mutableStateOf(false) }

    // Hardest moment state
    var hardestMoment by remember { mutableStateOf("") }
    var hardestTrigger by remember { mutableStateOf<String?>(null) }

    // Wins state
    var wins by remember { mutableStateOf("") }

    // Tomorrow state
    var tomorrowConcern by remember { mutableStateOf("") }

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
        EveningTopBar(
            currentStep = currentStep,
            totalProgress = progressSteps,
            label = if (currentStep < stepDone) "Evening Reflection" else "",
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
                label = "evening_step"
            ) { step ->
                when (step) {
                    0 -> EveningStepGreeting(
                        currentStreak = currentStreak,
                        onContinue = { currentStep = if (hasIntention) 1 else stepSpiritual }
                    )
                    stepIntention -> EveningStepIntentionReview(
                        morningIntention = todayMorningCheckIn?.intention ?: "",
                        followed = intentionFollowed,
                        note = intentionNote,
                        onFollowedChanged = { intentionFollowed = it },
                        onNoteChanged = { intentionNote = it },
                        onContinue = { currentStep = stepSpiritual }
                    )
                    stepSpiritual -> EveningStepSpiritualChecklist(
                        prayedFive = prayedFive,
                        morningAdhkar = morningAdhkar,
                        eveningAdhkar = eveningAdhkar,
                        readQuran = readQuran,
                        madeIstighfar = madeIstighfar,
                        loweredGaze = loweredGaze,
                        onToggle = { item ->
                            when (item) {
                                0 -> prayedFive = !prayedFive
                                1 -> morningAdhkar = !morningAdhkar
                                2 -> eveningAdhkar = !eveningAdhkar
                                3 -> readQuran = !readQuran
                                4 -> madeIstighfar = !madeIstighfar
                                5 -> loweredGaze = !loweredGaze
                            }
                        },
                        onContinue = { currentStep = stepHardest }
                    )
                    stepHardest -> EveningStepHardestMoment(
                        text = hardestMoment,
                        selectedTrigger = hardestTrigger,
                        onTextChanged = { hardestMoment = it },
                        onTriggerSelected = { hardestTrigger = it },
                        onContinue = { currentStep = stepWins }
                    )
                    stepWins -> EveningStepWins(
                        text = wins,
                        onTextChanged = { wins = it },
                        onContinue = { currentStep = stepTomorrow }
                    )
                    stepTomorrow -> EveningStepTomorrow(
                        text = tomorrowConcern,
                        onTextChanged = { tomorrowConcern = it },
                        onContinue = {
                            onComplete(
                                intentionFollowed,
                                intentionNote.ifBlank { null },
                                prayedFive,
                                morningAdhkar,
                                eveningAdhkar,
                                readQuran,
                                madeIstighfar,
                                loweredGaze,
                                hardestMoment.ifBlank { null },
                                hardestTrigger,
                                wins.ifBlank { null },
                                tomorrowConcern.ifBlank { null }
                            )
                            currentStep = stepDone
                            completionTriggered = true
                        }
                    )
                    stepDone -> EveningStepCompletion(currentStreak = currentStreak)
                }
            }
        }
    }
}

// ════════════════════════════════════════════
// EVENING SUMMARY (already checked in)
// ════════════════════════════════════════════

@Composable
private fun EveningSummaryScreen(
    entry: EveningCheckInEntry,
    currentStreak: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal, vertical = TaqwaDimens.spaceL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\u2190",
                style = TaqwaType.sectionTitle,
                color = TextGray,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(TaqwaDimens.spaceS)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Evening Reflection",
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

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VictoryGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("\u2705", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Text(
                text = "Evening reflection complete",
                style = TaqwaType.screenTitle.copy(fontSize = 22.sp),
                color = VanillaCustard
            )

            Text(
                text = "Completed tonight",
                style = TaqwaType.bodySmall,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

            // Spiritual score
            EveningSummaryRow(
                emoji = "\uD83D\uDD4C",
                label = "Spiritual Score",
                value = "${entry.spiritualScore}/6",
                color = when {
                    entry.spiritualScore >= 5 -> AccentGreen
                    entry.spiritualScore >= 3 -> AccentOrange
                    else -> AccentRed
                }
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            // Intention followed
            if (entry.intentionFollowed != null) {
                EveningSummaryRow(
                    emoji = when (entry.intentionFollowed) {
                        EveningCheckInEntry.FOLLOWED_YES -> "\u2705"
                        EveningCheckInEntry.FOLLOWED_PARTIALLY -> "\u26A0\uFE0F"
                        else -> "\u274C"
                    },
                    label = "Intention",
                    value = entry.intentionFollowed!!.lowercase().replaceFirstChar { it.uppercase() },
                    color = when (entry.intentionFollowed) {
                        EveningCheckInEntry.FOLLOWED_YES -> AccentGreen
                        EveningCheckInEntry.FOLLOWED_PARTIALLY -> AccentOrange
                        else -> AccentRed
                    }
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            // Hardest trigger
            if (entry.hardestTrigger != null) {
                EveningSummaryRow(
                    emoji = "\u26A1",
                    label = "Hardest trigger",
                    value = entry.hardestTrigger!!,
                    color = AccentOrange
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            // Hardest moment
            if (entry.hardestMoment != null) {
                EveningSummaryRow(
                    emoji = "\uD83D\uDCAD",
                    label = "Hardest moment",
                    value = entry.hardestMoment!!,
                    color = TextLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            // Wins
            if (entry.wins != null) {
                EveningSummaryRow(
                    emoji = "\uD83C\uDFC6",
                    label = "Wins today",
                    value = entry.wins!!,
                    color = VanillaCustard
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            // Tomorrow
            if (entry.tomorrowConcern != null) {
                EveningSummaryRow(
                    emoji = "\uD83D\uDC41\uFE0F",
                    label = "Watch for tomorrow",
                    value = entry.tomorrowConcern!!,
                    color = PrimaryLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            Text(
                text = "You can only reflect once per evening.\nRest well tonight.",
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
private fun EveningSummaryRow(emoji: String, label: String, value: String, color: Color) {
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
// TOP BAR
// ════════════════════════════════════════════

@Composable
private fun EveningTopBar(
    currentStep: Int,
    totalProgress: Int,
    label: String,
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
                text = "\u2190",
                style = TaqwaType.sectionTitle,
                color = TextGray,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(TaqwaDimens.spaceS)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = label,
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
                        animationSpec = tween(300), label = "edot_$i"
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
// STEP 0: EVENING GREETING
// ════════════════════════════════════════════

@Composable
private fun EveningStepGreeting(
    currentStreak: Int,
    onContinue: () -> Unit
) {
    val fadeIn = remember { Animatable(0f) }
    LaunchedEffect(Unit) { fadeIn.animateTo(1f, tween(600)) }

    val hour = java.time.LocalTime.now().hour
    val eveningAyahs = listOf(
        Triple(
            "\u0648\u064E\u0645\u0650\u0646\u064E \u0627\u0644\u0644\u064E\u0651\u064A\u0652\u0644\u0650 \u0625\u0650\u0630\u064E\u0627 \u0633\u064E\u062C\u064E\u0649\u0670",
            "And by the night when it covers",
            "Al-Layl 92:1"
        ),
        Triple(
            "\u0648\u064E\u0627\u0644\u0636\u064F\u062D\u064E\u0649\u0670 \u0648\u064E\u0627\u0644\u0644\u064E\u0651\u064A\u0652\u0644\u0650 \u0625\u0650\u0630\u064E\u0627 \u0633\u064E\u062C\u064E\u0649\u0670",
            "By the morning brightness, and by the night when it covers with darkness",
            "Ad-Duha 93:1-2"
        ),
        Triple(
            "\u0625\u0650\u0646\u064E\u0651 \u0645\u064E\u0639\u064E \u0627\u0644\u0652\u0639\u064F\u0633\u0652\u0631\u0650 \u064A\u064F\u0633\u0652\u0631\u064B\u0627",
            "Indeed, with hardship comes ease",
            "Ash-Sharh 94:6"
        )
    )
    val ayah = eveningAyahs[currentStreak % eveningAyahs.size]

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
            text = "\u0628\u0650\u0633\u0652\u0645\u0650 \u0627\u0644\u0644\u0651\u064E\u0647\u0650 \u0627\u0644\u0631\u0651\u064E\u062D\u0652\u0645\u064E\u0646\u0650 \u0627\u0644\u0631\u0651\u064E\u062D\u0650\u064A\u0645\u0650",
            style = TaqwaType.arabicMedium.copy(fontSize = 22.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(text = "\uD83C\uDF19", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = if (hour >= 21) "Good Night" else "Good Evening",
            style = TaqwaType.screenTitle.copy(fontSize = 26.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Time to reflect on your day",
            style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
            color = PrimaryLight,
            textAlign = TextAlign.Center
        )

        if (currentStreak > 0) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            val streakEmoji = when {
                currentStreak >= 100 -> "\uD83D\uDC8E"
                currentStreak >= 30 -> "\uD83C\uDFC6"
                currentStreak >= 7 -> "\uD83D\uDD25"
                else -> "\uD83C\uDF31"
            }
            Text(
                text = "$streakEmoji Day $currentStreak \u2014 still going strong",
                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = AccentGreenLight,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        // Evening ayah card
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
                Text(text = "\uD83D\uDCD6", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = ayah.first,
                    style = TaqwaType.arabicMedium.copy(fontSize = 17.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "\"${ayah.second}\"",
                    style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(text = "\u2014 ${ayah.third}", style = TaqwaType.captionSmall, color = TextMuted)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text("Begin Reflection  \u2192", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: INTENTION REVIEW
// ════════════════════════════════════════════

@Composable
private fun EveningStepIntentionReview(
    morningIntention: String,
    followed: String?,
    note: String,
    onFollowedChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
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

        Text(text = "\uD83C\uDFAF", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "This Morning's Intention",
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
                text = "\"$morningIntention\"",
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
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            EveningOptionCard(
                emoji = "\u2705",
                label = "Yes",
                isSelected = followed == EveningCheckInEntry.FOLLOWED_YES,
                color = AccentGreen,
                onClick = { onFollowedChanged(EveningCheckInEntry.FOLLOWED_YES) },
                modifier = Modifier.weight(1f)
            )
            EveningOptionCard(
                emoji = "\u26A0\uFE0F",
                label = "Partially",
                isSelected = followed == EveningCheckInEntry.FOLLOWED_PARTIALLY,
                color = AccentOrange,
                onClick = { onFollowedChanged(EveningCheckInEntry.FOLLOWED_PARTIALLY) },
                modifier = Modifier.weight(1f)
            )
            EveningOptionCard(
                emoji = "\uD83D\uDE14",
                label = "No",
                isSelected = followed == EveningCheckInEntry.FOLLOWED_NO,
                color = AccentRed,
                onClick = { onFollowedChanged(EveningCheckInEntry.FOLLOWED_NO) },
                modifier = Modifier.weight(1f)
            )
        }

        AnimatedVisibility(
            visible = followed != null,
            enter = fadeIn(tween(400)) + expandVertically(tween(400))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                Text(
                    text = when (followed) {
                        EveningCheckInEntry.FOLLOWED_YES -> "MashaAllah! Keeping promises to yourself builds real strength."
                        EveningCheckInEntry.FOLLOWED_PARTIALLY -> "That's still effort. Every step counts."
                        EveningCheckInEntry.FOLLOWED_NO -> "Tomorrow is a fresh start. What matters is you're reflecting now."
                        else -> ""
                    },
                    style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                    color = when (followed) {
                        EveningCheckInEntry.FOLLOWED_YES -> AccentGreenLight
                        EveningCheckInEntry.FOLLOWED_PARTIALLY -> AccentOrangeLight
                        else -> PrimaryLight
                    },
                    textAlign = TextAlign.Center
                )

                if (followed != EveningCheckInEntry.FOLLOWED_YES) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    OutlinedTextField(
                        value = note,
                        onValueChange = onNoteChanged,
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        placeholder = {
                            Text(
                                "What got in the way? (optional)",
                                style = TaqwaType.bodySmall,
                                color = TextMuted.copy(alpha = 0.6f)
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
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = followed != null,
            enter = fadeIn(tween(300)) + slideInVertically { it / 2 }
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text("Continue  \u2192", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: SPIRITUAL CHECKLIST (WIRD)
// ════════════════════════════════════════════

@Composable
private fun EveningStepSpiritualChecklist(
    prayedFive: Boolean,
    morningAdhkar: Boolean,
    eveningAdhkar: Boolean,
    readQuran: Boolean,
    madeIstighfar: Boolean,
    loweredGaze: Boolean,
    onToggle: (Int) -> Unit,
    onContinue: () -> Unit
) {
    val items = listOf(
        Triple("\uD83D\uDD4C", "Prayed all 5 salah", prayedFive),
        Triple("\u2600\uFE0F", "Morning adhkar", morningAdhkar),
        Triple("\uD83C\uDF19", "Evening adhkar", eveningAdhkar),
        Triple("\uD83D\uDCD6", "Read Quran", readQuran),
        Triple("\uD83E\uDEF4", "Made istighfar", madeIstighfar),
        Triple("\uD83D\uDC41\uFE0F", "Lowered gaze / avoided triggers", loweredGaze)
    )
    val score = listOf(prayedFive, morningAdhkar, eveningAdhkar, readQuran, madeIstighfar, loweredGaze).count { it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(text = "\uD83D\uDCFF", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Your Spiritual Day",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Tap what you accomplished today",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        // Score display
        val scoreColor by animateColorAsState(
            targetValue = when {
                score >= 5 -> AccentGreen
                score >= 3 -> AccentOrange
                else -> AccentRed
            },
            animationSpec = tween(300), label = "score_color"
        )
        Text(
            text = "$score / 6",
            style = TaqwaType.screenTitle.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
            color = scoreColor
        )
        Text(
            text = when {
                score == 6 -> "MashaAllah! A full spiritual day"
                score >= 4 -> "Strong day, Alhamdulillah"
                score >= 2 -> "Room to grow \u2014 every deed counts"
                score >= 1 -> "Even one good deed is light"
                else -> "Tomorrow is a new chance"
            },
            style = TaqwaType.bodySmall,
            color = scoreColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        items.forEachIndexed { index, (emoji, label, isChecked) ->
            WirdItem(
                emoji = emoji,
                label = label,
                isChecked = isChecked,
                onClick = { onToggle(index) }
            )
            if (index < items.lastIndex) Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text("Continue  \u2192", style = TaqwaType.button.copy(fontSize = 15.sp), color = TextWhite)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

@Composable
private fun WirdItem(
    emoji: String,
    label: String,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isChecked) AccentGreen.copy(alpha = 0.12f) else BackgroundCard,
        animationSpec = tween(300), label = "wird_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isChecked) AccentGreen.copy(alpha = 0.6f) else BackgroundLight.copy(alpha = 0.5f),
        animationSpec = tween(300), label = "wird_border"
    )
    val itemScale by animateFloatAsState(
        targetValue = if (isChecked) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "wird_scale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(itemScale)
            .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
            .background(bgColor)
            .border(
                width = if (isChecked) 1.5.dp else 0.5.dp,
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
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))
        Text(
            text = label,
            style = TaqwaType.cardTitle,
            color = if (isChecked) AccentGreenLight else TextWhite,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isChecked) AccentGreen else Color.Transparent)
                .border(
                    width = if (isChecked) 0.dp else 1.5.dp,
                    color = if (isChecked) AccentGreen else BackgroundLight,
                    shape = RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Text("\u2713", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ════════════════════════════════════════════
// STEP: HARDEST MOMENT
// ════════════════════════════════════════════

@Composable
private fun EveningStepHardestMoment(
    text: String,
    selectedTrigger: String?,
    onTextChanged: (String) -> Unit,
    onTriggerSelected: (String) -> Unit,
    onContinue: () -> Unit
) {
    val triggers = listOf("Boredom", "Loneliness", "Stress", "Tiredness", "Social Media", "Late Night")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(text = "\u26A1", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Hardest Moment Today",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Capturing triggers while fresh helps you see patterns",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        // Trigger chips
        Text(
            text = "What was the main trigger?",
            style = TaqwaType.caption.copy(fontWeight = FontWeight.Medium),
            color = TextLight,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                triggers.take(3).forEach { trigger ->
                    EveningQuickChip(
                        text = trigger,
                        isSelected = selectedTrigger == trigger,
                        onClick = { onTriggerSelected(trigger) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                triggers.drop(3).forEach { trigger ->
                    EveningQuickChip(
                        text = trigger,
                        isSelected = selectedTrigger == trigger,
                        onClick = { onTriggerSelected(trigger) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            placeholder = {
                Text(
                    "What happened? How did you cope? (optional)",
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = if (text.isBlank() && selectedTrigger == null) "Skip  \u2192" else "Continue  \u2192",
                style = TaqwaType.button.copy(fontSize = 15.sp),
                color = TextWhite
            )
        }

        if (text.isBlank() && selectedTrigger == null) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Text(
                text = "If today was smooth, that's great!",
                style = TaqwaType.captionSmall,
                color = TextMuted.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: WINS
// ════════════════════════════════════════════

@Composable
private fun EveningStepWins(
    text: String,
    onTextChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    val winChips = listOf("Prayed on time", "Avoided trigger", "Helped someone", "Exercised", "Read Quran", "Stayed patient")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(text = "\uD83C\uDFC6", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "What went well today?",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Noticing wins \u2014 even small ones \u2014 rewires your brain for success",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                winChips.take(3).forEach { chip ->
                    EveningQuickChip(
                        text = chip,
                        isSelected = text == chip,
                        onClick = { onTextChanged(chip) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                winChips.drop(3).forEach { chip ->
                    EveningQuickChip(
                        text = chip,
                        isSelected = text == chip,
                        onClick = { onTextChanged(chip) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            placeholder = {
                Text(
                    "e.g., \"Resisted an urge after Asr\"\n\"Helped my mother with groceries\"",
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = if (text.isBlank()) "Skip  \u2192" else "Continue  \u2192",
                style = TaqwaType.button.copy(fontSize = 15.sp),
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
    }
}

// ════════════════════════════════════════════
// STEP: TOMORROW AWARENESS
// ════════════════════════════════════════════

@Composable
private fun EveningStepTomorrow(
    text: String,
    onTextChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    val concernChips = listOf("Staying up late", "Being alone", "Stressful event", "Weekend", "Travel", "No plans")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(text = "\uD83D\uDC41\uFE0F", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Tomorrow Awareness",
            style = TaqwaType.screenTitle.copy(fontSize = 24.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Awareness of risks primes your subconscious to stay alert",
            style = TaqwaType.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Anything to watch out for?",
            style = TaqwaType.caption.copy(fontWeight = FontWeight.Medium),
            color = TextLight,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                concernChips.take(3).forEach { chip ->
                    EveningQuickChip(
                        text = chip,
                        isSelected = text == chip,
                        onClick = { onTextChanged(chip) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
            ) {
                concernChips.drop(3).forEach { chip ->
                    EveningQuickChip(
                        text = chip,
                        isSelected = text == chip,
                        onClick = { onTextChanged(chip) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            placeholder = {
                Text(
                    "e.g., \"Working from home alone\"\n\"Exam stress coming up\"",
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VictoryGreen),
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
        ) {
            Text(
                text = "\uD83C\uDF19  Complete Evening Reflection",
                style = TaqwaType.button.copy(fontSize = 15.sp),
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

        Text(
            text = "All fields are optional \u2014 just showing up matters",
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
private fun EveningStepCompletion(currentStreak: Int) {
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
            Text("\u2713", color = TextWhite, fontSize = 36.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Day complete",
            style = TaqwaType.screenTitle.copy(fontSize = 26.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = if (currentStreak > 0) "Day $currentStreak ends with reflection"
            else "You showed up \u2014 that matters",
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
                    text = "\u0628\u0650\u0627\u0633\u0652\u0645\u0650\u0643\u064E \u0627\u0644\u0644\u0651\u064E\u0647\u064F\u0645\u0651\u064E \u0623\u064E\u0645\u064F\u0648\u062A\u064F \u0648\u064E\u0623\u064E\u062D\u0652\u064A\u064E\u0627",
                    style = TaqwaType.arabicMedium.copy(fontSize = 20.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "\"In Your name, O Allah, I die and I live\"",
                    style = TaqwaType.bodySmall,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "\u2014 Du'a before sleeping (Bukhari)",
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
private fun EveningOptionCard(
    emoji: String,
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) color.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(300), label = "eopt_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) color else BackgroundLight.copy(alpha = 0.5f),
        animationSpec = tween(300), label = "eopt_border"
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
            .padding(vertical = TaqwaDimens.spaceXL, horizontal = TaqwaDimens.spaceS)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = label,
            style = TaqwaType.cardTitle.copy(fontSize = 13.sp),
            color = if (isSelected) color else TextGray
        )
    }
}

@Composable
private fun EveningQuickChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) VanillaCustard.copy(alpha = 0.15f) else BackgroundCard,
        animationSpec = tween(200), label = "echip_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) VanillaCustard.copy(alpha = 0.5f) else BackgroundLight.copy(alpha = 0.3f),
        animationSpec = tween(200), label = "echip_border"
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