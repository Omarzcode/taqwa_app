package com.taqwa.journal.features.memory.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.core.components.TaqwaAccentCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MemoryBankScreen(
    memories: List<MemoryEntry>,
    onAddMemory: () -> Unit,
    onTogglePin: (MemoryEntry) -> Unit,
    onDeleteMemory: (MemoryEntry) -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    var memoryToDelete by remember { mutableStateOf<MemoryEntry?>(null) }

    val filteredMemories = remember(memories, selectedFilter) {
        when (selectedFilter) {
            MemoryEntry.TYPE_RELAPSE_LETTER -> memories.filter { it.type == MemoryEntry.TYPE_RELAPSE_LETTER }
            MemoryEntry.TYPE_VICTORY_NOTE -> memories.filter { it.type == MemoryEntry.TYPE_VICTORY_NOTE }
            MemoryEntry.TYPE_MANUAL -> memories.filter { it.type == MemoryEntry.TYPE_MANUAL }
            else -> memories
        }
    }

    val relapseCount = remember(memories) { memories.count { it.type == MemoryEntry.TYPE_RELAPSE_LETTER } }
    val victoryCount = remember(memories) { memories.count { it.type == MemoryEntry.TYPE_VICTORY_NOTE } }
    val manualCount = remember(memories) { memories.count { it.type == MemoryEntry.TYPE_MANUAL } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Memory Bank",
            onBack = onBack
        )

        if (memories.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "🧠", fontSize = 64.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                Text(
                    text = "Your Memory Bank is empty",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "Memories are created automatically:\n\n" +
                            "📝 After a relapse — your pain becomes your weapon\n\n" +
                            "🏆 After a victory — your pride becomes your shield\n\n" +
                            "Or add one manually now.",
                    style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                    color = TextGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
                Button(
                    onClick = onAddMemory,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryMedium),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text("Write a Memory", style = TaqwaType.button, color = TextWhite)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = TaqwaDimens.screenPaddingHorizontal,
                    vertical = TaqwaDimens.spaceM
                ),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceM)
            ) {
                // Stats header
                item {
                    TaqwaCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            MemoryStat("📝", "$relapseCount", "Pain")
                            MemoryStat("🏆", "$victoryCount", "Pride")
                            MemoryStat("💭", "$manualCount", "Notes")
                            MemoryStat("📌", "${memories.count { it.isPinned }}", "Pinned")
                        }
                    }
                }

                // Filter chips
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
                    ) {
                        FilterChipItem("All", "ALL", selectedFilter) { selectedFilter = it }
                        FilterChipItem("Pain", MemoryEntry.TYPE_RELAPSE_LETTER, selectedFilter) { selectedFilter = it }
                        FilterChipItem("Pride", MemoryEntry.TYPE_VICTORY_NOTE, selectedFilter) { selectedFilter = it }
                        FilterChipItem("Notes", MemoryEntry.TYPE_MANUAL, selectedFilter) { selectedFilter = it }
                    }
                }

                // Memory entries
                items(filteredMemories, key = { it.id }) { memory ->
                    MemoryCard(
                        memory = memory,
                        onTogglePin = { onTogglePin(memory) },
                        onDelete = { memoryToDelete = memory }
                    )
                }

                // Add button at bottom
                item {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    OutlinedButton(
                        onClick = onAddMemory,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryLight),
                        shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                        Text("Add Memory", style = TaqwaType.button, color = PrimaryLight)
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

    // Delete confirmation
    if (memoryToDelete != null) {
        AlertDialog(
            onDismissRequest = { memoryToDelete = null },
            title = {
                Text(
                    "Delete Memory?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )
            },
            text = {
                Text(
                    "This memory will be permanently removed from your bank.",
                    style = TaqwaType.body,
                    color = TextGray
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    memoryToDelete?.let { onDeleteMemory(it) }
                    memoryToDelete = null
                }) {
                    Text("Delete", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { memoryToDelete = null }) {
                    Text("Keep", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun MemoryCard(
    memory: MemoryEntry,
    onTogglePin: () -> Unit,
    onDelete: () -> Unit
) {
    val accentColor = when (memory.type) {
        MemoryEntry.TYPE_RELAPSE_LETTER -> AccentRed
        MemoryEntry.TYPE_VICTORY_NOTE -> VictoryGreen
        else -> PrimaryMedium
    }

    val typeEmoji = when (memory.type) {
        MemoryEntry.TYPE_RELAPSE_LETTER -> "📝"
        MemoryEntry.TYPE_VICTORY_NOTE -> "🏆"
        else -> "💭"
    }

    val typeLabel = when (memory.type) {
        MemoryEntry.TYPE_RELAPSE_LETTER -> "Relapse Letter"
        MemoryEntry.TYPE_VICTORY_NOTE -> "Victory Note"
        else -> "Personal Note"
    }

    val dateFormatted = remember(memory.timestamp) {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(memory.timestamp))
    }

    TaqwaAccentCard(accentColor = accentColor, alpha = 0.1f) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = typeEmoji, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = typeLabel,
                        style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = accentColor
                    )
                }
                Row {
                    // Pin button
                    IconButton(
                        onClick = onTogglePin,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = if (memory.isPinned) "📌" else "📍",
                            fontSize = 14.sp
                        )
                    }
                    // Delete button
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            // Message
            Text(
                text = "\"${memory.message}\"",
                style = TaqwaType.body.copy(lineHeight = 24.sp),
                color = TextWhite,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateFormatted,
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
                if (memory.streakAtTime > 0) {
                    Text(
                        text = "Day ${memory.streakAtTime}",
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun MemoryStat(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
        Text(text = value, style = TaqwaType.statValue, color = TextWhite)
        Text(text = label, style = TaqwaType.statLabel, color = TextGray)
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    value: String,
    selected: String,
    onSelect: (String) -> Unit
) {
    FilterChip(
        selected = selected == value,
        onClick = { onSelect(value) },
        label = {
            Text(
                text = label,
                style = TaqwaType.captionSmall,
                fontSize = 12.sp
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = ChipSelected,
            selectedLabelColor = VanillaCustard,
            containerColor = ChipUnselected,
            labelColor = TextGray
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = BackgroundLight,
            selectedBorderColor = ChipBorder,
            enabled = true,
            selected = selected == value
        )
    )
}