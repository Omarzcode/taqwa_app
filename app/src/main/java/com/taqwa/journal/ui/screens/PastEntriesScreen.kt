package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastEntriesScreen(
    entries: List<JournalEntry>,
    onEntryClick: (Int) -> Unit,
    onDeleteEntry: (JournalEntry) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "📖  My Journal",
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

        if (entries.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📝",
                        fontSize = 64.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No entries yet",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your journal entries will appear here\nafter you complete an urge flow.",
                        fontSize = 14.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }
        } else {
            // Entries list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(entries) { entry ->
                    EntryCard(
                        entry = entry,
                        onClick = { onEntryClick(entry.id) },
                        onDelete = { onDeleteEntry(entry) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EntryCard(
    entry: JournalEntry,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(entry.timestamp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundCard
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Date and time row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormat.format(date),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = timeFormat.format(date),
                        fontSize = 13.sp,
                        color = TextGray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Urge strength badge
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                entry.urgeStrength <= 3 -> AccentGreen.copy(alpha = 0.2f)
                                entry.urgeStrength <= 6 -> AccentOrange.copy(alpha = 0.2f)
                                else -> AccentRed.copy(alpha = 0.2f)
                            }
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "💢 ${entry.urgeStrength}/10",
                            fontSize = 12.sp,
                            color = when {
                                entry.urgeStrength <= 3 -> AccentGreen
                                entry.urgeStrength <= 6 -> AccentOrange
                                else -> AccentRed
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Delete button
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Feelings
            if (entry.feelings.isNotEmpty()) {
                Text(
                    text = entry.feelings.replace(",", "  •  "),
                    fontSize = 13.sp,
                    color = PrimaryLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Free text preview
            if (entry.freeText.isNotEmpty()) {
                Text(
                    text = entry.freeText,
                    fontSize = 13.sp,
                    color = TextGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Entry?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("This entry will be permanently deleted.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            containerColor = BackgroundCard
        )
    }
}