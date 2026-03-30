package com.taqwa.journal.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.ui.components.EmptyStateCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

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
        TaqwaTopBar(
            title = "📖  My Journal",
            onBack = onBack
        )

        if (entries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                contentAlignment = Alignment.Center
            ) {
                EmptyStateCard(
                    emoji = "📝",
                    title = "No entries yet",
                    subtitle = "Your journal entries will appear here\nafter you complete an urge flow."
                )
            }
        } else {
            Text(
                text = "${entries.size} ${if (entries.size == 1) "entry" else "entries"}",
                style = TaqwaType.caption,
                color = TextMuted,
                modifier = Modifier.padding(
                    horizontal = TaqwaDimens.screenPaddingHorizontal,
                    vertical = TaqwaDimens.spaceS
                )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing),
                contentPadding = PaddingValues(bottom = TaqwaDimens.spaceL)
            ) {
                itemsIndexed(entries) { index, entry ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 4 }
                        )
                    ) {
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
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TaqwaDimens.spaceL)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormat.format(date),
                        style = TaqwaType.cardTitle,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
                    Text(
                        text = timeFormat.format(date),
                        style = TaqwaType.bodySmall,
                        color = TextGray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceS)
                ) {
                    UrgeStrengthBadge(strength = entry.urgeStrength)

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

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            if (entry.feelings.isNotEmpty()) {
                Text(
                    text = entry.feelings.replace(",", "  •  "),
                    style = TaqwaType.bodySmall,
                    color = PrimaryLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            }

            if (entry.freeText.isNotEmpty()) {
                Text(
                    text = entry.freeText,
                    style = TaqwaType.bodySmall,
                    color = TextGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Entry?",
                    style = TaqwaType.sectionTitle,
                    color = TextWhite
                )
            },
            text = {
                Text(
                    "This entry will be permanently deleted.",
                    style = TaqwaType.body,
                    color = TextGray
                )
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
                    Text("Cancel", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun UrgeStrengthBadge(strength: Int) {
    val color = when {
        strength <= 3 -> AccentGreen
        strength <= 6 -> AccentOrange
        else -> AccentRed
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(TaqwaDimens.spaceS)
    ) {
        Text(
            text = "💢 $strength/10",
            style = TaqwaType.caption.copy(fontWeight = FontWeight.Bold),
            color = color,
            modifier = Modifier.padding(
                horizontal = TaqwaDimens.spaceS,
                vertical = TaqwaDimens.spaceXS
            )
        )
    }
}