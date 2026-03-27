package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.UrgeFlowProgressBar
import com.taqwa.journal.ui.theme.*

@Composable
fun FutureSelfScreen(
    onNext: () -> Unit,
    currentStep: Int = 5,
    totalSteps: Int = 7
) {
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top - Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = TaqwaDimens.spaceXXL)
            ) {
                Text(text = "🔮", fontSize = 44.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "ONE HOUR FROM NOW",
                    style = TaqwaType.screenTitle.copy(
                        fontSize = 22.sp,
                        letterSpacing = 2.sp
                    ),
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = "Two paths lie ahead of you",
                    style = TaqwaType.bodySmall,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

            // Two paths side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
            ) {
                // Path A - Give In
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = AccentRed.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(TaqwaDimens.spaceL),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PATH A",
                            style = TaqwaType.captionSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = AccentRed.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Give In",
                            style = TaqwaType.cardTitle,
                            color = AccentRed
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                        PathItem(emoji = "😞", text = "5 seconds of pleasure")
                        PathItem(emoji = "😫", text = "Hours of guilt & shame")
                        PathItem(emoji = "📉", text = "Your streak is gone")
                        PathItem(emoji = "🧠", text = "Brain fog for days")
                        PathItem(emoji = "😤", text = "You hate yourself again")
                        PathItem(emoji = "📚", text = "Can't focus on study")
                        PathItem(emoji = "😰", text = "Can't sleep in peace")
                    }
                }

                // Path B - Resist
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = AccentGreen.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(TaqwaDimens.spaceL),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PATH B",
                            style = TaqwaType.captionSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = AccentGreen.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Resist",
                            style = TaqwaType.cardTitle,
                            color = AccentGreen
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                        PathItem(emoji = "💪", text = "The urge passes")
                        PathItem(emoji = "😊", text = "You feel POWERFUL")
                        PathItem(emoji = "📈", text = "Streak grows stronger")
                        PathItem(emoji = "🧠", text = "Brain heals more")
                        PathItem(emoji = "😌", text = "Self-respect intact")
                        PathItem(emoji = "📚", text = "Study with clear mind")
                        PathItem(emoji = "😴", text = "Sleep in peace")
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Bottom - Choose Path B
            Column(
                modifier = Modifier.padding(bottom = TaqwaDimens.spaceXL)
            ) {
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
                        text = "I Choose Path B  ✓",
                        style = TaqwaType.button.copy(fontSize = 16.sp),
                        color = TextWhite
                    )
                }
            }
        }
    }
}

@Composable
private fun PathItem(emoji: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = emoji,
            fontSize = 13.sp,
            modifier = Modifier.padding(end = TaqwaDimens.spaceXS)
        )
        Text(
            text = text,
            style = TaqwaType.caption,
            color = TextLight,
            lineHeight = 16.sp
        )
    }
}