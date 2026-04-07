package com.taqwa.journal.features.urgeflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.core.components.UrgeFlowProgressBar
import com.taqwa.journal.core.theme.*

@Composable
fun QuestionsScreen(
    situationContext: String,
    onSituationContextChange: (String) -> Unit,
    selectedFeelings: List<String>,
    onFeelingToggle: (String) -> Unit,
    selectedNeeds: List<String>,
    onNeedToggle: (String) -> Unit,
    selectedAlternatives: List<String>,
    onAlternativeToggle: (String) -> Unit,
    urgeStrength: Int,
    onUrgeStrengthChange: (Int) -> Unit,
    freeText: String,
    onFreeTextChange: (String) -> Unit,
    onFinish: () -> Unit,
    currentStep: Int = 6,
    totalSteps: Int = 7
) {
    var currentQuestion by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        UrgeFlowProgressBar(
            currentStep = currentStep,
            totalSteps = totalSteps
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = TaqwaDimens.spaceL)
            ) {
                Text(
                    text = "✍️  JOURNAL",
                    style = TaqwaType.captionSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = "Question $currentQuestion of 6",
                    style = TaqwaType.bodySmall,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                LinearProgressIndicator(
                    progress = { currentQuestion / 6f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = PrimaryLight,
                    trackColor = BackgroundLight
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = TaqwaDimens.spaceL)
            ) {
                when (currentQuestion) {
                    1 -> Question1(situationContext, onSituationContextChange)
                    2 -> Question2(selectedFeelings, onFeelingToggle)
                    3 -> Question3(selectedNeeds, onNeedToggle)
                    4 -> Question4(selectedAlternatives, onAlternativeToggle)
                    5 -> Question5(urgeStrength, onUrgeStrengthChange)
                    6 -> Question6(freeText, onFreeTextChange)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = TaqwaDimens.spaceXL),
                horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
            ) {
                if (currentQuestion > 1) {
                    OutlinedButton(
                        onClick = { currentQuestion-- },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextLight
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Text(
                            text = "←  Back",
                            style = TaqwaType.button
                        )
                    }
                }

                Button(
                    onClick = {
                        if (currentQuestion < 6) {
                            currentQuestion++
                        } else {
                            onFinish()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentQuestion == 6) AccentGreen else PrimaryMedium
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Text(
                        text = if (currentQuestion == 6) "Save & Finish  ✓" else "Next  ➜",
                        style = TaqwaType.button.copy(fontSize = 15.sp),
                        color = TextWhite
                    )
                }
            }
        }
    }
}

@Composable
private fun Question1(text: String, onTextChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📍", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "Where are you and what were you doing before this urge?",
            style = TaqwaType.sectionTitle.copy(lineHeight = 28.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = "This helps you identify trigger patterns",
            style = TaqwaType.bodySmall,
            color = TextGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            placeholder = {
                Text(
                    "e.g., In my room, was scrolling social media, couldn't sleep...",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryLight,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = PrimaryLight,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )
    }
}

@Composable
private fun Question2(selectedFeelings: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "💭", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "What are you feeling right now?",
            style = TaqwaType.sectionTitle,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = "Select all that apply. Be honest.",
            style = TaqwaType.bodySmall,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Column(
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            QuestionData.feelings.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
                ) {
                    row.forEach { feeling ->
                        SelectableChip(
                            text = feeling,
                            isSelected = selectedFeelings.contains(feeling),
                            onClick = { onToggle(feeling) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun Question3(selectedNeeds: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🎯", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "What do you ACTUALLY need right now?",
            style = TaqwaType.sectionTitle.copy(lineHeight = 28.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = "The real need behind the urge",
            style = TaqwaType.bodySmall,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Column(
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            QuestionData.realNeeds.forEach { need ->
                SelectableChip(
                    text = need,
                    isSelected = selectedNeeds.contains(need),
                    onClick = { onToggle(need) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Question4(selectedAlternatives: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🔄", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "What will you do RIGHT NOW instead?",
            style = TaqwaType.sectionTitle.copy(lineHeight = 28.sp),
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = "Pick at least one activity",
            style = TaqwaType.bodySmall,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        Column(
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
        ) {
            QuestionData.alternatives.forEach { alt ->
                SelectableChip(
                    text = alt,
                    isSelected = selectedAlternatives.contains(alt),
                    onClick = { onToggle(alt) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Question5(urgeStrength: Int, onStrengthChange: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "💢", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "How strong is this urge?",
            style = TaqwaType.sectionTitle,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Text(
            text = "$urgeStrength",
            style = TaqwaType.streakNumber.copy(fontSize = 72.sp),
            color = when {
                urgeStrength <= 3 -> AccentGreen
                urgeStrength <= 6 -> AccentOrange
                else -> AccentRed
            }
        )
        Text(
            text = when {
                urgeStrength <= 3 -> "Mild"
                urgeStrength <= 6 -> "Moderate"
                urgeStrength <= 8 -> "Strong"
                else -> "Overwhelming"
            },
            style = TaqwaType.body,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        Slider(
            value = urgeStrength.toFloat(),
            onValueChange = { onStrengthChange(it.toInt()) },
            valueRange = 1f..10f,
            steps = 8,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.spaceL),
            colors = SliderDefaults.colors(
                thumbColor = PrimaryLight,
                activeTrackColor = PrimaryMedium,
                inactiveTrackColor = BackgroundLight
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TaqwaDimens.spaceL),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "1", style = TaqwaType.bodySmall, color = TextGray)
            Text(text = "10", style = TaqwaType.bodySmall, color = TextGray)
        }
    }
}

@Composable
private fun Question6(text: String, onTextChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "✍️", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        Text(
            text = "Write a message to yourself",
            style = TaqwaType.sectionTitle,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        Text(
            text = "Anything on your mind. Be raw and honest.",
            style = TaqwaType.bodySmall,
            color = TextGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            placeholder = {
                Text(
                    "Write freely... this is just for you.",
                    style = TaqwaType.bodySmall,
                    color = TextMuted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryLight,
                unfocusedBorderColor = BackgroundLight,
                cursorColor = PrimaryLight,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
        )
    }
}

@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius))
            .background(
                if (isSelected) ChipSelected else ChipUnselected
            )
            .border(
                width = 1.dp,
                color = if (isSelected) ChipBorder else BackgroundLight,
                shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = TaqwaDimens.spaceL, vertical = TaqwaDimens.spaceM),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = TaqwaType.body,
            color = if (isSelected) TextWhite else TextGray,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}
