package com.taqwa.journal.features.urgeflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.UrgeFlowProgressBar
import com.taqwa.journal.core.theme.*

@Composable
fun PersonalReminderScreen(
    whyQuitting: String,
    promises: List<String>,
    duas: List<String>,
    reminders: List<String>,
    onNext: () -> Unit,
    currentStep: Int = 4,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
            Text(text = "📝", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = "YOUR OWN WORDS",
                style = TaqwaType.screenTitle.copy(
                    fontSize = 22.sp,
                    letterSpacing = 3.sp
                ),
                color = VanillaCustard
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Text(
                text = "You wrote these when you were thinking clearly",
                style = TaqwaType.bodySmall,
                color = TextGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Why quitting
            if (whyQuitting.isNotEmpty()) {
                TaqwaAccentCard(
                    accentColor = VanillaCustard,
                    alpha = 0.08f
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎯  Why You're Doing This",
                            style = TaqwaType.cardTitle,
                            color = VanillaCustard
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "\"$whyQuitting\"",
                            style = TaqwaType.body.copy(
                                fontWeight = FontWeight.Medium,
                                lineHeight = 26.sp
                            ),
                            color = TextWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.cardSpacing))
            }

            // Promises
            if (promises.isNotEmpty()) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "💪  Your Promises",
                            style = TaqwaType.cardTitle,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        promises.forEach { promise ->
                            ReminderItem(text = promise)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.cardSpacing))
            }

            // Duas
            if (duas.isNotEmpty()) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "🤲  Your Duas",
                            style = TaqwaType.cardTitle,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        duas.forEach { dua ->
                            ReminderItem(text = dua)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.cardSpacing))
            }

            // Reminders
            if (reminders.isNotEmpty()) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "📝  Your Reminders",
                            style = TaqwaType.cardTitle,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        reminders.forEach { reminder ->
                            ReminderItem(text = reminder)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Bottom - Next button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TaqwaDimens.buttonHeight)
                    .padding(bottom = TaqwaDimens.spaceL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryMedium
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "Next  ➜",
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }
    }
}

@Composable
private fun ReminderItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXS),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = TaqwaType.body,
            color = TextGray,
            modifier = Modifier.padding(end = TaqwaDimens.spaceS, top = 1.dp)
        )
        Text(
            text = "\"$text\"",
            style = TaqwaType.body.copy(lineHeight = 24.sp),
            color = TextLight
        )
    }
}