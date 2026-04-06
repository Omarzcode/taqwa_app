package com.taqwa.journal.features.onboarding.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.theme.*

@Composable
fun OnboardingScreen(
    onComplete: (whyQuitting: String, firstPromise: String, firstDua: String) -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    var whyQuitting by remember { mutableStateOf("") }
    var firstPromise by remember { mutableStateOf("") }
    var firstDua by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .statusBarsPadding()
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Progress dots
        Row(
            modifier = Modifier.padding(top = TaqwaDimens.spaceL),
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            for (i in 0..4) {
                val isActive = i <= currentStep
                val isCurrent = i == currentStep

                Box(
                    modifier = Modifier
                        .size(
                            width = if (isCurrent) 24.dp else if (isActive) 10.dp else 8.dp,
                            height = if (isCurrent) 10.dp else 8.dp
                        )
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            if (isActive) VanillaCustard
                            else BackgroundLight
                        )
                )
            }
        }

        // Content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = TaqwaDimens.spaceL)
        ) {
            when (currentStep) {
                0 -> WelcomeStep()
                1 -> HowItWorksStep()
                2 -> WhyQuittingStep(whyQuitting) { whyQuitting = it }
                3 -> FirstPromiseStep(firstPromise) { firstPromise = it }
                4 -> FirstDuaStep(firstDua) { firstDua = it }
            }
        }

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = TaqwaDimens.spaceL),
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
        ) {
            if (currentStep > 0) {
                OutlinedButton(
                    onClick = { currentStep-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextLight
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Text(
                        "←  Back",
                        style = TaqwaType.button
                    )
                }
            }

            Button(
                onClick = {
                    if (currentStep < 4) {
                        currentStep++
                    } else {
                        onComplete(whyQuitting, firstPromise, firstDua)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentStep == 4) AccentGreen else PrimaryMedium
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = when (currentStep) {
                        0 -> "Let's Begin  ➜"
                        4 -> "Start My Journey  ✨"
                        else -> "Next  ➜"
                    },
                    style = TaqwaType.button.copy(fontSize = 15.sp),
                    color = TextWhite
                )
            }
        }
    }
}

// ========================
// Step 0: Welcome
// ========================
@Composable
private fun WelcomeStep() {
    // Gentle pulse on mosque emoji
    val infiniteTransition = rememberInfiniteTransition(label = "welcome")
    val mosqueScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mosque_scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "﷽",
            style = TaqwaType.arabicLarge.copy(fontSize = 28.sp),
            color = TextLight
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "🕌",
            fontSize = 64.sp,
            modifier = Modifier.scale(mosqueScale)
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Text(
            text = "Welcome to Taqwa",
            style = TaqwaType.screenTitle.copy(fontSize = 26.sp),
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = "Your personal companion in the journey\nto overcome addiction.",
            style = TaqwaType.body.copy(lineHeight = 26.sp),
            color = TextLight,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ\nفَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ",
                    style = TaqwaType.arabicMedium.copy(lineHeight = 30.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "\"But as for he who feared standing before his Lord\nand restrained the soul from desire —\nthen indeed, Paradise will be his refuge.\"",
                    style = TaqwaType.caption.copy(lineHeight = 20.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "— An-Nazi'at 79:40-41",
                    style = TaqwaType.captionSmall,
                    color = TextGray
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        // Privacy badges
        Row(
            horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrivacyBadge("🔒", "Private")
            PrivacyBadge("📵", "Offline")
            PrivacyBadge("👤", "No Accounts")
        }
    }
}

@Composable
private fun PrivacyBadge(emoji: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
        Text(
            text = label,
            style = TaqwaType.captionSmall,
            color = TextMuted
        )
    }
}

// ========================
// Step 1: How It Works
// ========================
@Composable
private fun HowItWorksStep() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🧠", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "How Taqwa Works",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "When an urge hits, your brain goes on autopilot.\nTaqwa interrupts that autopilot.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        FlowStepItem("1", "⏸️", "STOP & BREATHE", "Interrupts the autopilot brain with guided breathing")
        FlowStepItem("2", "❌", "REALITY CHECK", "Reminds you of the truth you forgot in the moment")
        FlowStepItem("3", "🤲", "ISLAMIC REMINDER", "Reconnects you with Allah and your values")
        FlowStepItem("4", "📝", "YOUR OWN WORDS", "Shows your promises, duas, and reasons")
        FlowStepItem("5", "🔮", "FUTURE SELF", "Visualize the consequences of both paths")
        FlowStepItem("6", "✍️", "REFLECT & JOURNAL", "Understand your triggers through guided questions")
        FlowStepItem("7", "🏆", "VICTORY", "Celebrate defeating another urge!")
    }
}

@Composable
private fun FlowStepItem(
    number: String,
    emoji: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXS),
        verticalAlignment = Alignment.Top
    ) {
        // Number circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(PrimaryMedium)
        ) {
            Text(
                text = number,
                style = TaqwaType.caption.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "$emoji  $title",
                style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
            Text(
                text = description,
                style = TaqwaType.bodySmall.copy(lineHeight = 18.sp),
                color = TextGray
            )
        }
    }
}

// ========================
// Step 2: Why Are You Quitting
// ========================
@Composable
private fun WhyQuittingStep(
    text: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🎯", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Why Are You Quitting?",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "This is your anchor. When the storm hits,\nthis will keep you grounded.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            placeholder = {
                Text(
                    "Write from your heart...\n\n" +
                            "Examples:\n" +
                            "• For my relationship with Allah\n" +
                            "• For my future wife and family\n" +
                            "• For my mental health and clarity\n" +
                            "• For my studies and career\n" +
                            "• To be the man I want to be",
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
            text = "💡 You can always edit this later in Promise Wall",
            style = TaqwaType.captionSmall,
            color = TextMuted
        )
    }
}

// ========================
// Step 3: First Promise
// ========================
@Composable
private fun FirstPromiseStep(
    text: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "💪", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Make a Promise",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "Make one promise to yourself right now.\nThis will be shown to you during urges.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            placeholder = {
                Text(
                    "I promise myself that...\n\n" +
                            "Examples:\n" +
                            "• I will never let this control me again\n" +
                            "• I will choose discipline over regret\n" +
                            "• I will become someone I'm proud of",
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
            text = "💡 You can skip this and add later",
            style = TaqwaType.captionSmall,
            color = TextMuted
        )
    }
}

// ========================
// Step 4: First Dua
// ========================
@Composable
private fun FirstDuaStep(
    text: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🤲", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "Write a Personal Dua",
            style = TaqwaType.screenTitle,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        Text(
            text = "A dua from your heart.\nAllah hears you right now.",
            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
            color = TextGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            placeholder = {
                Text(
                    "Ya Allah...\n\n" +
                            "Examples:\n" +
                            "• Ya Allah, give me the strength to resist\n" +
                            "• Ya Allah, purify my heart and my eyes\n" +
                            "• Ya Allah, make me among the patient",
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

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You're about to start a new chapter.",
                    style = TaqwaType.body.copy(fontWeight = FontWeight.Medium),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "Every day you resist is a victory.\nEvery urge you defeat makes you stronger.\nAllah is with you in this battle.",
                    style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                    color = TextGray,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

        Text(
            text = "💡 You can skip this and add later",
            style = TaqwaType.captionSmall,
            color = TextMuted
        )
    }
}