package com.taqwa.journal.core.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taqwa.journal.core.theme.*

/**
 * Reusable wizard/stepper container for multi-step flows.
 * Handles progress display, navigation buttons, step transitions.
 */
@Composable
fun WizardContainer(
    currentStep: Int,
    totalSteps: Int,
    onBack: () -> Unit,
    onPrevious: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onFinish: () -> Unit = {},
    canProgress: Boolean = true,
    stepTitles: List<String>? = null,
    title: String? = null,
    nextLabel: String = "Next",
    previousLabel: String = "Back",
    finishLabel: String = "Done",
    showProgress: Boolean = true,
    content: @Composable (step: Int) -> Unit
) {
    val isFirstStep = currentStep == 0
    val isLastStep = currentStep == totalSteps - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top bar
        TaqwaTopBar(
            title = title ?: stepTitles?.getOrNull(currentStep) ?: "Step ${currentStep + 1}",
            onBack = onBack,
            subtitle = if (title != null && stepTitles != null) {
                stepTitles.getOrNull(currentStep)
            } else null
        )

        // Progress indicator
        if (showProgress) {
            WizardProgressBar(
                currentStep = currentStep,
                totalSteps = totalSteps,
                stepTitles = stepTitles
            )
        }

        // Step content (scrollable, fills remaining space)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300)
                        ) + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeOut(tween(200))
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300)
                        ) + fadeOut(tween(200))
                    }
                },
                label = "wizard_step"
            ) { step ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            horizontal = TaqwaDimens.screenPaddingHorizontal,
                            vertical = TaqwaDimens.screenPaddingVertical
                        )
                ) {
                    content(step)
                }
            }
        }

        // Navigation buttons
        WizardNavButtons(
            isFirstStep = isFirstStep,
            isLastStep = isLastStep,
            canProgress = canProgress,
            onPrevious = onPrevious ?: {},
            onNext = onNext ?: {},
            onFinish = onFinish,
            nextLabel = nextLabel,
            previousLabel = previousLabel,
            finishLabel = finishLabel
        )
    }
}

@Composable
fun WizardProgressBar(
    currentStep: Int,
    totalSteps: Int,
    stepTitles: List<String>? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = TaqwaDimens.screenPaddingHorizontal,
                vertical = TaqwaDimens.spaceM
            )
    ) {
        // Dot indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until totalSteps) {
                val isActive = i == currentStep
                val isCompleted = i < currentStep

                Box(
                    modifier = Modifier
                        .size(
                            if (isActive) TaqwaDimens.progressDotSizeActive
                            else TaqwaDimens.progressDotSize
                        )
                        .clip(CircleShape)
                        .background(
                            when {
                                isActive -> VanillaCustard
                                isCompleted -> AccentGreen
                                else -> PrimaryDark
                            }
                        )
                )

                // Line between dots
                if (i < totalSteps - 1) {
                    Box(
                        modifier = Modifier
                            .width(TaqwaDimens.spaceXL)
                            .height(TaqwaDimens.progressLineHeight)
                            .background(
                                if (i < currentStep) AccentGreen.copy(alpha = 0.6f)
                                else PrimaryDark
                            )
                    )
                }
            }
        }

        // Step label
        if (stepTitles != null) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Text(
                text = "Step ${currentStep + 1} of $totalSteps",
                style = TaqwaType.captionSmall,
                color = TextMuted,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun WizardNavButtons(
    isFirstStep: Boolean,
    isLastStep: Boolean,
    canProgress: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
    nextLabel: String,
    previousLabel: String,
    finishLabel: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundDark.copy(alpha = 0f),
                        BackgroundDark
                    )
                )
            )
            .padding(
                horizontal = TaqwaDimens.screenPaddingHorizontal,
                vertical = TaqwaDimens.spaceL
            ),
        horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceM)
    ) {
        // Previous button
        if (!isFirstStep) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier
                    .weight(1f)
                    .height(TaqwaDimens.buttonHeight),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextLight
                )
            ) {
                Text(
                    text = previousLabel,
                    style = TaqwaType.button
                )
            }
        }

        // Next / Finish button
        Button(
            onClick = if (isLastStep) onFinish else onNext,
            modifier = Modifier
                .weight(if (isFirstStep) 1f else 1.5f)
                .height(TaqwaDimens.buttonHeight),
            enabled = canProgress,
            shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLastStep) AccentGreen else PrimaryMedium,
                contentColor = TextWhite,
                disabledContainerColor = PrimaryDark.copy(alpha = 0.5f),
                disabledContentColor = TextMuted
            )
        ) {
            Text(
                text = if (isLastStep) finishLabel else nextLabel,
                style = TaqwaType.button
            )
        }
    }
}
