package com.taqwa.journal.ui.screens

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
import com.taqwa.journal.ui.theme.*

@Composable
fun QuestionsScreen(
    // Q1
    situationContext: String,
    onSituationContextChange: (String) -> Unit,
    // Q2
    selectedFeelings: List<String>,
    onFeelingToggle: (String) -> Unit,
    // Q3
    selectedNeeds: List<String>,
    onNeedToggle: (String) -> Unit,
    // Q4
    selectedAlternatives: List<String>,
    onAlternativeToggle: (String) -> Unit,
    // Q5
    urgeStrength: Int,
    onUrgeStrengthChange: (Int) -> Unit,
    // Q6
    freeText: String,
    onFreeTextChange: (String) -> Unit,
    // Navigation
    onFinish: () -> Unit
) {
    var currentQuestion by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top - Progress indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Question $currentQuestion of 6",
                fontSize = 14.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { currentQuestion / 6f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryLight,
                trackColor = BackgroundLight
            )
        }

        // Middle - Question content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp)
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

        // Bottom - Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Back button
            if (currentQuestion > 1) {
                OutlinedButton(
                    onClick = { currentQuestion-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextLight
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "← Back",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Next / Finish button
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
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (currentQuestion == 6) "Save & Finish ✅" else "Next ➜",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ========================
// Question 1: Situation Context
// ========================
@Composable
private fun Question1(text: String, onTextChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📍",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Where are you and what were you doing before this urge?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This helps you identify trigger patterns",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            placeholder = {
                Text(
                    "e.g., In my room, was scrolling social media, couldn't sleep...",
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
            shape = RoundedCornerShape(12.dp)
        )
    }
}

// ========================
// Question 2: Feelings
// ========================
@Composable
private fun Question2(selectedFeelings: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "💭",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "What are you feeling right now?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select all that apply. Be honest.",
            fontSize = 14.sp,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Feelings chips in a flow layout
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuestionData.feelings.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { feeling ->
                        SelectableChip(
                            text = feeling,
                            isSelected = selectedFeelings.contains(feeling),
                            onClick = { onToggle(feeling) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty space if odd number
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// ========================
// Question 3: Real Need
// ========================
@Composable
private fun Question3(selectedNeeds: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎯",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "What do you ACTUALLY need right now?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The real need behind the urge",
            fontSize = 14.sp,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
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

// ========================
// Question 4: Alternative Activity
// ========================
@Composable
private fun Question4(selectedAlternatives: List<String>, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🔄",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "What will you do RIGHT NOW instead?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pick at least one activity",
            fontSize = 14.sp,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
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

// ========================
// Question 5: Urge Strength
// ========================
@Composable
private fun Question5(urgeStrength: Int, onStrengthChange: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "💢",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "How strong is this urge?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Big number display
        Text(
            text = "$urgeStrength",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
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
            fontSize = 16.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Slider
        Slider(
            value = urgeStrength.toFloat(),
            onValueChange = { onStrengthChange(it.toInt()) },
            valueRange = 1f..10f,
            steps = 8,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                thumbColor = PrimaryLight,
                activeTrackColor = PrimaryMedium,
                inactiveTrackColor = BackgroundLight
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "1", color = TextGray, fontSize = 14.sp)
            Text(text = "10", color = TextGray, fontSize = 14.sp)
        }
    }
}

// ========================
// Question 6: Free Text
// ========================
@Composable
private fun Question6(text: String, onTextChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "✍️",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Write a message to yourself",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Anything on your mind. Be raw and honest.",
            fontSize = 14.sp,
            color = TextGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            placeholder = {
                Text(
                    "Write freely... this is just for you.",
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
            shape = RoundedCornerShape(12.dp)
        )
    }
}

// ========================
// Reusable Selectable Chip
// ========================
@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) ChipSelected else ChipUnselected
            )
            .border(
                width = 1.dp,
                color = if (isSelected) ChipBorder else BackgroundLight,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (isSelected) TextWhite else TextGray,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}