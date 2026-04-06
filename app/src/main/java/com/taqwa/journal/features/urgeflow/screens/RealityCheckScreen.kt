package com.taqwa.journal.features.urgeflow.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.core.components.UrgeFlowProgressBar
import com.taqwa.journal.core.theme.*
import kotlinx.coroutines.delay

@Composable
fun RealityCheckScreen(
    onNext: () -> Unit,
    currentStep: Int = 2,
    totalSteps: Int = 7
) {
    val lines = QuestionData.realityCheckLines
    var visibleCount by remember { mutableIntStateOf(0) }
    var allShown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        for (i in lines.indices) {
            delay(3000)
            visibleCount = i + 1
        }
        delay(1000)
        allShown = true
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
                    text = "REALITY CHECK",
                    style = TaqwaType.screenTitle.copy(
                        fontSize = 26.sp,
                        letterSpacing = 3.sp
                    ),
                    color = AccentRed
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "Read each line slowly...",
                    style = TaqwaType.body,
                    color = TextGray
                )
            }

            // Middle - Lines appearing one by one
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = TaqwaDimens.spaceXL),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceL)
            ) {
                lines.forEachIndexed { index, line ->
                    AnimatedVisibility(
                        visible = index < visibleCount,
                        enter = fadeIn(
                            animationSpec = tween(600)
                        ) + slideInVertically(
                            animationSpec = tween(600),
                            initialOffsetY = { it / 2 }
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "✦",
                                fontSize = 14.sp,
                                color = AccentOrange,
                                modifier = Modifier.padding(
                                    end = TaqwaDimens.spaceM,
                                    top = 3.dp
                                )
                            )
                            Text(
                                text = line,
                                style = TaqwaType.body.copy(
                                    lineHeight = 22.sp
                                ),
                                color = TextLight
                            )
                        }
                    }
                }
            }

            // Bottom - Next button
            AnimatedVisibility(
                visible = allShown,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it })
            ) {
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
                        text = "I hear you  ➜",
                        style = TaqwaType.button.copy(fontSize = 16.sp),
                        color = TextWhite
                    )
                }
            }

            if (!allShown) {
                Spacer(modifier = Modifier.height(TaqwaDimens.buttonHeight))
            }
        }
    }
}