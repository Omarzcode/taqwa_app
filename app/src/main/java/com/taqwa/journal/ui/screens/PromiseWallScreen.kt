package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
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
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "📝  My Promise Wall",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BackgroundDark,
                titleContentColor = TextWhite
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryDark.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Your own words are the most powerful weapon against urges. Write your promises, reasons, and duas here. They will be shown to you during the intervention flow.",
                    fontSize = 13.sp,
                    color = TextLight,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // ========================
            // WHY I'M QUITTING
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "🎯 Why I'm Quitting",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentGold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (editingWhy || whyQuitting.isEmpty()) {
                        OutlinedTextField(
                            value = whyText,
                            onValueChange = { whyText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            placeholder = {
                                Text(
                                    "Why are you quitting? What's your motivation?\n\ne.g., For my relationship with Allah, for my future wife, for my mental health, for my studies...",
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                onSetWhyQuitting(whyText)
                                editingWhy = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryMedium
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Save")
                        }
                    } else {
                        Text(
                            text = "\"$whyQuitting\"",
                            fontSize = 15.sp,
                            color = TextLight,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = {
                                whyText = whyQuitting
                                editingWhy = true
                            }
                        ) {
                            Text("Edit ✏️", color = PrimaryLight)
                        }
                    }
                }
            }

            // ========================
            // MY PROMISES
            // ========================
            SectionCard(
                title = "💪 My Promises",
                emoji = "💪",
                items = promises,
                onAdd = {
                    addDialogType = "promise"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeletePromise,
                placeholder = "I promise myself that...",
                emptyText = "Add promises you've made to yourself"
            )

            // ========================
            // MY DUAS
            // ========================
            SectionCard(
                title = "🤲 My Duas",
                emoji = "🤲",
                items = duas,
                onAdd = {
                    addDialogType = "dua"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeleteDua,
                placeholder = "Ya Allah, help me...",
                emptyText = "Add your personal duas"
            )

            // ========================
            // PERSONAL REMINDERS
            // ========================
            SectionCard(
                title = "📝 Personal Reminders",
                emoji = "📝",
                items = reminders,
                onAdd = {
                    addDialogType = "reminder"
                    addDialogText = ""
                    showAddDialog = true
                },
                onDelete = onDeleteReminder,
                placeholder = "Remember that...",
                emptyText = "Add things you want to remember during urges"
            )

            Spacer(modifier = Modifier.height(16.dp))
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
                    fontWeight = FontWeight.Bold
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
                        Text(dialogPlaceholder, color = TextMuted)
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
                    Text("Cancel")
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun SectionCard(
    title: String,
    emoji: String,
    items: List<String>,
    onAdd: () -> Unit,
    onDelete: (Int) -> Unit,
    placeholder: String,
    emptyText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
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

            Spacer(modifier = Modifier.height(8.dp))

            if (items.isEmpty()) {
                Text(
                    text = emptyText,
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
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
                                fontSize = 14.sp,
                                color = TextLight,
                                lineHeight = 22.sp
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
                            color = BackgroundLight,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}