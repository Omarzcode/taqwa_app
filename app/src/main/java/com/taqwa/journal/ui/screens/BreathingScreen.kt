package com.taqwa.journal.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.UrgeFlowProgressBar
import com.taqwa.journal.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun BreathingScreen(
    onNext: () -> Unit,
    currentStep: Int = 1,
    totalSteps: Int = 7
) {
    var breathCount by remember { mutableIntStateOf(0) }
    var breathPhase by remember { mutableStateOf("GET READY") }
    var isBreathingStarted by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // Breathing circle animation
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Phase-based color
    val circleColor by animateColorAsState(
        targetValue = when (breathPhase) {
            "BREATHE IN..." -> AccentBlue.copy(alpha = 0.4f)
            "HOLD..." -> VanillaCustard.copy(alpha = 0.3f)
            "BREATHE OUT..." -> AccentGreen.copy(alpha = 0.4f)
            "WELL DONE" -> AccentGreen.copy(alpha = 0.5f)
            else -> PrimaryMedium.copy(alpha = 0.3f)
        },
        label = "circle_color"
    )

    val innerCircleColor by animateColorAsState(
        targetValue = when (breathPhase) {
            "BREATHE IN..." -> AccentBlue.copy(alpha = 0.6f)
            "HOLD..." -> VanillaCustard.copy(alpha = 0.4f)
            "BREATHE OUT..." -> AccentGreen.copy(alpha = 0.6f)
            "WELL DONE" -> AccentGreen.copy(alpha = 0.7f)
            else -> PrimaryMedium.copy(alpha = 0.5f)
        },
        label = "inner_circle_color"
    )

    // Breathing cycle logic
    LaunchedEffect(isBreathingStarted) {
        if (isBreathingStarted) {
            for (i in 1..5) {
                breathPhase = "BREATHE IN..."
                delay(4000)
                breathPhase = "HOLD..."
                delay(4000)
                breathPhase = "BREATHE OUT..."
                delay(4000)
                breathCount = i
            }
            breathPhase = "WELL DONE"
            isCompleted = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Progress bar
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        UrgeFlowProgressBar(
            currentStep = currentStep,
            totalSteps = totalSteps
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top - Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = TaqwaDimens.spaceXXL)
            ) {
                Text(
                    text = "STOP.",
                    style = TaqwaType.screenTitle.copy(
                        fontSize = 32.sp,
                        letterSpacing = 4.sp
                    ),
                    color = AccentRed
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "You are on autopilot right now.\nYour brain is lying to you.\nThis urge will pass.",
                    style = TaqwaType.body,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            // Middle - Breathing Circle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isBreathingStarted) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(200.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(circleColor)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(innerCircleColor)
                        ) {
                            Text(
                                text = breathPhase,
                                style = TaqwaType.button,
                                color = TextWhite,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

                    // Progress dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
                    ) {
                        for (i in 1..5) {
                            Box(
                                modifier = Modifier
                                    .size(if (i <= breathCount) 12.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (i <= breathCount) AccentGreen
                                        else BackgroundLight
                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "$breathCount / 5 breaths",
                        style = TaqwaType.bodySmall,
                        color = TextGray
                    )
                } else {
                    // Start button
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            PrimaryMedium.copy(alpha = 0.2f),
                                            PrimaryMedium.copy(alpha = 0.0f)
                                        )
                                    )
                                )
                        )
                        Button(
                            onClick = { isBreathingStarted = true },
                            modifier = Modifier.size(180.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryMedium
                            ),
                            shape = CircleShape
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "🫁", fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                                Text(
                                    text = "START\nBREATHING",
                                    style = TaqwaType.button,
                                    color = TextWhite,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Bottom - Next button
            Column(
                modifier = Modifier.padding(bottom = TaqwaDimens.spaceXXL)
            ) {
                if (isCompleted) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGreen
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text(
                            text = "I took my breaths  ✓",
                            style = TaqwaType.button.copy(fontSize = 16.sp),
                            color = TextWhite
                        )
                    }
                } else {
                    // Invisible spacer same height as button
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}