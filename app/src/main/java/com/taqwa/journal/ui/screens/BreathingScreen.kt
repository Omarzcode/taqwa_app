package com.taqwa.journal.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun BreathingScreen(
    onNext: () -> Unit
) {
    // Breath counter
    var breathCount by remember { mutableIntStateOf(0) }
    var breathPhase by remember { mutableStateOf("GET READY") }
    var isBreathingStarted by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // Animation for the breathing circle
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top - Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = "⏸️",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "STOP.",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = AccentRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You are on autopilot right now.\nYour brain is lying to you.\nThis urge will pass.",
                fontSize = 16.sp,
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
                // Breathing circle with animation
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .scale(scale)
                        .background(
                            color = PrimaryMedium.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(140.dp)
                            .background(
                                color = PrimaryMedium.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = breathPhase,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Breath counter
                Text(
                    text = "Breath: $breathCount / 5",
                    fontSize = 18.sp,
                    color = TextLight,
                    fontWeight = FontWeight.Medium
                )
            } else {
                // Start button
                Button(
                    onClick = { isBreathingStarted = true },
                    modifier = Modifier
                        .size(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryMedium
                    ),
                    shape = CircleShape
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🫁",
                            fontSize = 40.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "START\nBREATHING",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Bottom - Next button (only after completion)
        if (isCompleted) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryMedium
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "I took my breaths ➜",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}