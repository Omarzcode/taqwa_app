package com.taqwa.journal.features.shieldplan.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.features.shieldplan.data.ShieldPlanManager.ShieldPlan
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*

/**
 * Edit Shield Plan Screen
 *
 * Used for both editing existing plans and creating custom ones.
 * When isNewCustom = true, shows additional fields for trigger name/emoji.
 */
@Composable
fun EditShieldPlanScreen(
    plan: ShieldPlan?,
    isNewCustom: Boolean = false,
    onSave: (ShieldPlan) -> Unit,
    onSaveCustom: (name: String, emoji: String, description: String, steps: List<String>, note: String) -> Unit,
    onBack: () -> Unit
) {
    // State for existing plan editing
    var steps by remember {
        mutableStateOf(plan?.steps?.toMutableList() ?: mutableListOf("", "", ""))
    }
    var personalNote by remember { mutableStateOf(plan?.personalNote ?: "") }

    // State for custom trigger creation
    var customName by remember { mutableStateOf("") }
    var customEmoji by remember { mutableStateOf("⚡") }
    var customDescription by remember { mutableStateOf("") }

    val title = when {
        isNewCustom -> "New Shield Plan"
        plan != null -> "Edit: ${plan.triggerName}"
        else -> "Shield Plan"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = title,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceL)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            // Header for existing plan
            if (!isNewCustom && plan != null) {
                TaqwaCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = plan.emoji, fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = plan.triggerName,
                            style = TaqwaType.sectionTitle,
                            color = VanillaCustard,
                            textAlign = TextAlign.Center
                        )
                        if (plan.triggerNameAr.isNotBlank()) {
                            Text(
                                text = plan.triggerNameAr,
                                style = TaqwaType.bodySmall,
                                color = TextMuted
                            )
                        }
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = plan.description,
                            style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                            color = TextGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Custom trigger fields
            if (isNewCustom) {
                Text(
                    text = "Define Your Trigger",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )

                // Emoji selector (simple text field for now)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = customEmoji,
                        onValueChange = { if (it.length <= 2) customEmoji = it },
                        modifier = Modifier.width(70.dp),
                        label = { Text("Emoji", style = TaqwaType.captionSmall, color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryLight,
                            unfocusedBorderColor = BackgroundLight,
                            cursorColor = PrimaryLight,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius),
                        textStyle = TaqwaType.body.copy(fontSize = 24.sp, textAlign = TextAlign.Center)
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                    OutlinedTextField(
                        value = customName,
                        onValueChange = { customName = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Trigger Name", style = TaqwaType.captionSmall, color = TextMuted) },
                        placeholder = { Text("e.g., After an argument", style = TaqwaType.bodySmall, color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryLight,
                            unfocusedBorderColor = BackgroundLight,
                            cursorColor = PrimaryLight,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                    )
                }

                OutlinedTextField(
                    value = customDescription,
                    onValueChange = { customDescription = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("When does this trigger happen?", style = TaqwaType.captionSmall, color = TextMuted) },
                    placeholder = {
                        Text(
                            "Describe the situation...",
                            style = TaqwaType.bodySmall, color = TextMuted
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryLight,
                        unfocusedBorderColor = BackgroundLight,
                        cursorColor = PrimaryLight,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius),
                    minLines = 2
                )
            }

            // Steps section
            Text(
                text = "Action Steps",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )
            Text(
                text = "What will you do when this trigger hits?\nWrite steps your future self can follow without thinking.",
                style = TaqwaType.captionSmall,
                color = TextMuted,
                lineHeight = 18.sp
            )

            // Step fields
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = TaqwaType.cardTitle,
                        color = PrimaryLight,
                        modifier = Modifier.width(28.dp)
                    )
                    OutlinedTextField(
                        value = step,
                        onValueChange = { newValue ->
                            steps = steps.toMutableList().also { it[index] = newValue }
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                "Step ${index + 1}...",
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
                        shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius),
                        singleLine = true
                    )
                    if (steps.size > 1) {
                        IconButton(
                            onClick = {
                                steps = steps.toMutableList().also { it.removeAt(index) }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                tint = TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Add step button
            if (steps.size < 7) {
                TextButton(
                    onClick = {
                        steps = steps.toMutableList().also { it.add("") }
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = PrimaryLight
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceXS))
                    Text("Add Step", style = TaqwaType.captionSmall, color = PrimaryLight)
                }
            }

            // Personal note
            Text(
                text = "Personal Note",
                style = TaqwaType.sectionTitle,
                color = TextWhite
            )
            Text(
                text = "Write a message to yourself for when this trigger hits.",
                style = TaqwaType.captionSmall,
                color = TextMuted
            )

            OutlinedTextField(
                value = personalNote,
                onValueChange = { personalNote = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                placeholder = {
                    Text(
                        "e.g., \"Don't touch the phone when you're lying in bed alone\"",
                        style = TaqwaType.bodySmall,
                        color = TextMuted,
                        lineHeight = 20.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryLight,
                    unfocusedBorderColor = BackgroundLight,
                    cursorColor = PrimaryLight,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            // Save button
            Button(
                onClick = {
                    val cleanSteps = steps.filter { it.isNotBlank() }
                    if (isNewCustom) {
                        if (customName.isNotBlank() && cleanSteps.isNotEmpty()) {
                            onSaveCustom(customName, customEmoji, customDescription, cleanSteps, personalNote)
                        }
                    } else if (plan != null) {
                        onSave(
                            plan.copy(
                                steps = cleanSteps,
                                personalNote = personalNote
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = if (isNewCustom) {
                    customName.isNotBlank() && steps.any { it.isNotBlank() }
                } else {
                    steps.any { it.isNotBlank() }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryMedium,
                    disabledContainerColor = PrimaryMedium.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "💾  Save Shield Plan",
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }
    }
}