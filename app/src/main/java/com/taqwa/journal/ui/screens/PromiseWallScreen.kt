package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun PromiseWallScreen(
    promises: List<String>,
    whyQuitting: String,
    duas: List<String>,
    reminders: List<String>,
    onAddPromise: (String) -> Unit,
    onDeletePromise: (Int) -> Unit,
    onSetWhyQuitting: (String) -> Unit,
    onAddDua: (String) -> Unit,
    onDeleteDua: (Int) -> Unit,
    onAddReminder: (String) -> Unit,
    onDeleteReminder: (Int) -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var addDialogType by remember { mutableStateOf("") }
    var addDialogText by remember { mutableStateOf("") }
    var editingWhy by remember { mutableStateOf(false) }
    var whyText by remember { mutableStateOf(whyQuitting) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "📝  My Promise Wall",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            // Info card
            TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
                Text(
                    text = "Your own words are the most powerful weapon against urges. Write your promises, reasons, and duas here. They will be shown to you during the intervention flow.",
                    style = TaqwaType.bodySmall.copy(lineHeight = 20.sp),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
            }

            // Why I'm Quitting
            TaqwaCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "🎯  Why I'm Quitting",
                        style = TaqwaType.sectionTitle,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                    if (editingWhy || whyQuitting.isEmpty()) {
                        OutlinedTextField(
                            value = whyText,
                            onValueChange = { whyText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            placeholder = {
                                Text(
                                    "Why are you quitting? What's your motivation?\n\ne.g., For my relationship with Allah, for my future wife, for my mental health...",
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
                            shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Button(
                            onClick = {
                                onSetWhyQuitting(whyText)
                                editingWhy = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryMedium
                            ),
                            shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                        ) {
                            Text("Save", style = TaqwaType.button, color = TextWhite)
                        }
                    } else {
                        Text(
                            text = "\"$whyQuitting\"",
                            style = TaqwaType.body.copy(lineHeight = 24.sp),
                            color = TextLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        TextButton(
                            onClick = {
                                whyText = whyQuitting
                                editingWhy = true
                            }
                        ) {
                            Text(
                                "Edit ✏️",
                                style = TaqwaType.button,
                                color = PrimaryLight
                            )
                        }
                    }
                }
            }

            // Promises
            PromiseSectionCard(
                title = "💪  My Promises",
                emoji = "💪",
                items = promises,
                onAdd = {
                    addDialogType = "promise"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeletePromise,
                emptyText = "Add promises you've made to yourself"
            )

            // Duas
            PromiseSectionCard(
                title = "🤲  My Duas",
                emoji = "🤲",
                items = duas,
                onAdd = {
                    addDialogType = "dua"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeleteDua,
                emptyText = "Add your personal duas"
            )

            // Reminders
            PromiseSectionCard(
                title = "📝  Personal Reminders",
                emoji = "📝",
                items = reminders,
                onAdd = {
                    addDialogType = "reminder"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeleteReminder,
                emptyText = "Add things you want to remember during urges"
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }
    }

    // Add dialog
    if (showAddDialog) {
        val dialogTitle = when (addDialogType) {
            "promise" -> "Add a Promise 💪"
            "dua" -> "Add a Dua 🤲"
            "reminder" -> "Add a Reminder 📝"
            else -> "Add"
        }
        val dialogPlaceholder = when (addDialogType) {
            "promise" -> "I promise myself that..."
            "dua" -> "Ya Allah, help me..."
            "reminder" -> "Remember that..."
            else -> ""
        }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = dialogTitle,
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )
            },
            text = {
                OutlinedTextField(
                    value = addDialogText,
                    onValueChange = { addDialogText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = {
                        Text(dialogPlaceholder, style = TaqwaType.bodySmall, color = TextMuted)
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
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (addDialogText.isNotBlank()) {
                            when (addDialogType) {
                                "promise" -> onAddPromise(addDialogText.trim())
                                "dua" -> onAddDua(addDialogText.trim())
                                "reminder" -> onAddReminder(addDialogText.trim())
                            }
                            showAddDialog = false
                            addDialogText = ""
                        }
                    }
                ) {
                    Text("Add", color = PrimaryLight)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun PromiseSectionCard(
    title: String,
    emoji: String,
    items: List<String>,
    onAdd: () -> Unit,
    onDelete: (Int) -> Unit,
    emptyText: String
) {
    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = TaqwaType.sectionTitle,
                    color = VanillaCustard
                )
                IconButton(
                    onClick = onAdd,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = PrimaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            if (items.isEmpty()) {
                Text(
                    text = emptyText,
                    style = TaqwaType.bodySmall,
                    color = TextMuted,
                    modifier = Modifier.padding(vertical = TaqwaDimens.spaceS)
                )
            } else {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = TaqwaDimens.spaceXS),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "$emoji ",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "\"$item\"",
                                style = TaqwaType.body.copy(lineHeight = 22.sp),
                                color = TextLight
                            )
                        }
                        IconButton(
                            onClick = { onDelete(index) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    if (index < items.size - 1) {
                        HorizontalDivider(
                            color = DividerColor,
                            modifier = Modifier.padding(vertical = TaqwaDimens.spaceXS),
                            thickness = TaqwaDimens.dividerThickness
                        )
                    }
                }
            }
        }
    }
}