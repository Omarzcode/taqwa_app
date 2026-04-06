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
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.UrgeFlowProgressBar
import com.taqwa.journal.core.theme.*

@Composable
fun IslamicReminderScreen(
    onNext: () -> Unit,
    currentStep: Int = 3,
    totalSteps: Int = 7
) {
    val reminder = remember {
        QuestionData.islamicReminders.random()
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top - Icon & Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = TaqwaDimens.spaceXXL)
            ) {
                Text(text = "🤲", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "REMEMBER ALLAH",
                    style = TaqwaType.screenTitle.copy(
                        fontSize = 24.sp,
                        letterSpacing = 3.sp
                    ),
                    color = VanillaCustard
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Middle - Ayah Card
            TaqwaCard(
                containerColor = BackgroundCard
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Arabic
                    Text(
                        text = reminder.arabic,
                        style = TaqwaType.arabicLarge,
                        color = AccentGold,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    // Divider
                    HorizontalDivider(
                        color = DividerColor,
                        modifier = Modifier.padding(horizontal = 32.dp),
                        thickness = TaqwaDimens.dividerThickness
                    )

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    // Translation
                    Text(
                        text = "\"${reminder.translation}\"",
                        style = TaqwaType.body.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = 24.sp
                        ),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                    // Reference
                    Text(
                        text = "— ${reminder.reference}",
                        style = TaqwaType.bodySmall,
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Reflection
            Text(
                text = reminder.reflection,
                style = TaqwaType.body.copy(
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Light
                ),
                color = TextLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = TaqwaDimens.spaceS)
            )

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
        }
    }
}