package com.taqwa.journal.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.theme.*

data class UrgeFlowStep(
    val label: String,
    val emoji: String
)

object UrgeFlowSteps {
    val steps = listOf(
        UrgeFlowStep("Breathe", "🫁"),
        UrgeFlowStep("Reality", "❌"),
        UrgeFlowStep("Remember", "🤲"),
        UrgeFlowStep("Remind", "📝"),
        UrgeFlowStep("Future", "🔮"),
        UrgeFlowStep("Journal", "✍️"),
        UrgeFlowStep("Victory", "🏆")
    )

    // When PersonalReminder is skipped
    val stepsWithoutPersonal = listOf(
        UrgeFlowStep("Breathe", "🫁"),
        UrgeFlowStep("Reality", "❌"),
        UrgeFlowStep("Remember", "🤲"),
        UrgeFlowStep("Future", "🔮"),
        UrgeFlowStep("Journal", "✍️"),
        UrgeFlowStep("Victory", "🏆")
    )
}

@Composable
fun UrgeFlowProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = TaqwaDimens.spaceL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Step counter text
        Text(
            text = "Step $currentStep of $totalSteps",
            style = TaqwaType.captionSmall,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        // Progress bar track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(BackgroundLight)
        ) {
            // Animated fill
            val fraction = currentStep.toFloat() / totalSteps.toFloat()
            val animatedWidth by animateDpAsState(
                targetValue = (fraction * 1000).dp, // will be constrained by fillMaxWidth
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "progress_width"
            )

            val progressColor by animateColorAsState(
                targetValue = when {
                    fraction >= 1f -> AccentGreen
                    fraction >= 0.7f -> VanillaCustard
                    else -> PrimaryLight
                },
                label = "progress_color"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(2.dp))
                    .background(progressColor)
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        // Dot indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..totalSteps) {
                val isCompleted = i < currentStep
                val isCurrent = i == currentStep

                val dotSize by animateDpAsState(
                    targetValue = when {
                        isCurrent -> 10.dp
                        isCompleted -> 8.dp
                        else -> 6.dp
                    },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    ),
                    label = "dot_size_$i"
                )

                val dotColor by animateColorAsState(
                    targetValue = when {
                        isCurrent -> VanillaCustard
                        isCompleted -> PrimaryLight
                        else -> BackgroundLight
                    },
                    label = "dot_color_$i"
                )

                Box(
                    modifier = Modifier
                        .size(dotSize)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
    }
}

@Composable
fun UrgeFlowScaffold(
    currentStep: Int,
    totalSteps: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Progress bar at top
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        UrgeFlowProgressBar(
            currentStep = currentStep,
            totalSteps = totalSteps
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

        // Screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            content = content
        )
    }
}
