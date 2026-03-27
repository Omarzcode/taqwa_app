package com.taqwa.journal.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Progress dots
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0..4) {
                Box(
                    modifier = Modifier
                        .size(if (i == currentStep) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (i <= currentStep) VanillaCustard
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
                .padding(vertical = 16.dp)
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
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("← Back", fontSize = 15.sp, fontWeight = FontWeight.Medium)
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
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = when (currentStep) {
                        0 -> "Let's Begin ➜"
                        4 -> "Start My Journey ✨"
                        else -> "Next ➜"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "﷽", fontSize = 36.sp, color = TextWhite)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "🕌", fontSize = 72.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to Taqwa",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Your personal companion in the journey\nto overcome porn addiction.",
            fontSize = 16.sp,
            color = TextLight,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = PrimaryDark.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ فَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ\"",
                    fontSize = 16.sp,
                    color = VanillaCustard,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "\"But as for he who feared standing before his Lord\nand restrained the soul from desire —\nthen indeed, Paradise will be his refuge.\"",
                    fontSize = 13.sp,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Text(
                    text = "— An-Nazi'at 79:40-41",
                    fontSize = 11.sp,
                    color = TextGray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "🔒 100% Private • Offline • No Accounts",
            fontSize = 12.sp,
            color = TextGray
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
        Text(text = "🧠", fontSize = 56.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How Taqwa Works",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "When an urge hits, your brain goes on autopilot.\nTaqwa interrupts that autopilot.",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Steps
        FlowStepItem(
            number = "1",
            emoji = "⏸️",
            title = "STOP & BREATHE",
            description = "Interrupts the autopilot brain with guided breathing"
        )

        FlowStepItem(
            number = "2",
            emoji = "❌",
            title = "REALITY CHECK",
            description = "Reminds you of the truth you forgot in the moment"
        )

        FlowStepItem(
            number = "3",
            emoji = "🤲",
            title = "ISLAMIC REMINDER",
            description = "Reconnects you with Allah and your values"
        )

        FlowStepItem(
            number = "4",
            emoji = "📝",
            title = "YOUR OWN WORDS",
            description = "Shows your promises, duas, and reasons"
        )

        FlowStepItem(
            number = "5",
            emoji = "📅",
            title = "FUTURE SELF",
            description = "Visualize the consequences of both paths"
        )

        FlowStepItem(
            number = "6",
            emoji = "📋",
            title = "REFLECT & JOURNAL",
            description = "Understand your triggers through guided questions"
        )

        FlowStepItem(
            number = "7",
            emoji = "🏆",
            title = "VICTORY",
            description = "Celebrate defeating another urge!"
        )
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Number circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(PrimaryMedium)
        ) {
            Text(
                text = number,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "$emoji $title",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = description,
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 18.sp
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
        Text(text = "🎯", fontSize = 56.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Why Are You Quitting?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This is your anchor. When the storm hits,\nthis will keep you grounded.",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "💡 You can always edit this later in Promise Wall",
            fontSize = 12.sp,
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
        Text(text = "💪", fontSize = 56.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Make a Promise",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Make one promise to yourself right now.\nThis will be shown to you during urges.",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "💡 You can skip this and add later",
            fontSize = 12.sp,
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
        Text(text = "🤲", fontSize = 56.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Write a Personal Dua",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VanillaCustard,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "A dua from your heart.\nAllah hears you right now.",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = PrimaryDark.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You're about to start a new chapter.",
                    fontSize = 15.sp,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Every day you resist is a victory.\nEvery urge you defeat makes you stronger.\nAllah is with you in this battle.",
                    fontSize = 13.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "💡 You can skip this and add later",
            fontSize = 12.sp,
            color = TextMuted
        )
    }
}