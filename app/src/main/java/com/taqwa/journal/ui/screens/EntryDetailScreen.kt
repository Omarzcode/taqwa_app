package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.JournalEntry
import com.taqwa.journal.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(
    entry: JournalEntry?,
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
                    text = "📖  Entry Details",
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

        if (entry == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Entry not found",
                    color = TextGray,
                    fontSize = 16.sp
                )
            }
        } else {
            val dateFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = Date(entry.timestamp)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Date & Time header
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BackgroundCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🏆 Urge Defeated",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VictoryGreenLight
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dateFormat.format(date),
                            fontSize = 16.sp,
                            color = TextWhite,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = timeFormat.format(date),
                            fontSize = 14.sp,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
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
                                text = "Urge Strength: ${entry.urgeStrength}/10",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    entry.urgeStrength <= 3 -> AccentGreen
                                    entry.urgeStrength <= 6 -> AccentOrange
                                    else -> AccentRed
                                },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                // Q1: Situation
                if (entry.situationContext.isNotEmpty()) {
                    DetailSection(
                        icon = "📍",
                        title = "Situation",
                        content = entry.situationContext
                    )
                }

                // Q2: Feelings
                if (entry.feelings.isNotEmpty()) {
                    DetailSection(
                        icon = "💭",
                        title = "Feelings",
                        content = entry.feelings.replace(",", "\n")
                    )
                }

                // Q3: Real Need
                if (entry.realNeed.isNotEmpty()) {
                    DetailSection(
                        icon = "🎯",
                        title = "Real Need",
                        content = entry.realNeed.replace(",", "\n")
                    )
                }

                // Q4: Alternative Chosen
                if (entry.alternativeChosen.isNotEmpty()) {
                    DetailSection(
                        icon = "🔄",
                        title = "Alternative Activity",
                        content = entry.alternativeChosen.replace(",", "\n")
                    )
                }

                // Q6: Free Text
                if (entry.freeText.isNotEmpty()) {
                    DetailSection(
                        icon = "✍️",
                        title = "Message to Self",
                        content = entry.freeText
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DetailSection(
    icon: String,
    title: String,
    content: String
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryLight
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                fontSize = 15.sp,
                color = TextLight,
                lineHeight = 24.sp
            )
        }
    }
}