package com.taqwa.journal.features.urgeflow.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.UrgeFlowProgressBar
import com.taqwa.journal.core.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VictoryScreen(
    urgesDefeated: Int,
    onGoHome: () -> Unit,
    onWriteVictoryNote: () -> Unit,
    currentStep: Int = 7,
    totalSteps: Int = 7
) {
    val victoryAyah = remember {
        QuestionData.victoryAyahs.random()
    }

    val currentTime = remember {
        SimpleDateFormat("MMM dd, yyyy — hh:mm a", Locale.getDefault())
            .format(Date())
    }

    // Trophy bounce animation
    val infiniteTransition = rememberInfiniteTransition(label = "victory")
    val trophyScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "trophy_scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Progress bar — completed!
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        UrgeFlowProgressBar(
            currentStep = currentStep,
            totalSteps = totalSteps
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top - Trophy
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = TaqwaDimens.spaceXXL)
            ) {
                Text(
                    text = "🏆",
                    fontSize = 64.sp,
                    modifier = Modifier.scale(trophyScale)
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                Text(
                    text = "URGE DEFEATED",
                    style = TaqwaType.screenTitle.copy(
                        fontSize = 26.sp,
                        letterSpacing = 3.sp
                    ),
                    color = VictoryGreenLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "You just proved you're stronger\nthan your desires.",
                    style = TaqwaType.body,
                    color = TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Ayah Card
            TaqwaAccentCard(
                accentColor = VictoryGreen,
                alpha = 0.15f
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = victoryAyah.arabic,
                        style = TaqwaType.arabicMedium.copy(fontSize = 20.sp),
                        color = AccentGold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = "\"${victoryAyah.translation}\"",
                        style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "— ${victoryAyah.reference}",
                        style = TaqwaType.caption,
                        color = TextGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // Stats Card
            TaqwaCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🛡️", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                        Text(
                            text = "$urgesDefeated",
                            style = TaqwaType.screenTitle,
                            color = VanillaCustard
                        )
                        Text(
                            text = "Total Defeated",
                            style = TaqwaType.captionSmall,
                            color = TextGray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(TaqwaDimens.dividerThickness)
                            .height(60.dp)
                            .background(DividerColor)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📝", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                        Text(
                            text = "Saved",
                            style = TaqwaType.cardTitle,
                            color = AccentGreen
                        )
                        Text(
                            text = currentTime,
                            style = TaqwaType.captionSmall,
                            color = TextGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

            // ── NEW: Write Victory Note prompt ──
            TaqwaAccentCard(accentColor = VictoryGreen, alpha = 0.08f) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🧠 Save this feeling",
                        style = TaqwaType.cardTitle,
                        color = VictoryGreenLight
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "Write a note to yourself for the next time\nan urge comes. Your own words are your\nstrongest weapon.",
                        style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    OutlinedButton(
                        onClick = onWriteVictoryNote,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = VictoryGreenLight
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text(
                            "✍️  Write Victory Note",
                            style = TaqwaType.button,
                            color = VictoryGreenLight
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

            // Bottom - Home button
            Column(
                modifier = Modifier.padding(bottom = TaqwaDimens.spaceXL)
            ) {
                Button(
                    onClick = onGoHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryMedium
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Text(
                        text = "🏠  Back to Home",
                        style = TaqwaType.button.copy(fontSize = 16.sp),
                        color = TextWhite
                    )
                }
            }
        }
    }
}