package com.taqwa.journal.features.browse.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import com.taqwa.journal.core.components.EmptyStateCard
import com.taqwa.journal.core.components.TaqwaCard
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*
import java.text.SimpleDateFormat
import java.util.*

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
        TaqwaTopBar(
            title = "📖  Entry Details",
            onBack = onBack
        )

        if (entry == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                contentAlignment = Alignment.Center
            ) {
                EmptyStateCard(
                    emoji = "🔍",
                    title = "Entry not found",
                    subtitle = "This entry may have been deleted."
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
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing)
            ) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

                TaqwaCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🏆", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = "Urge Defeated",
                            style = TaqwaType.sectionTitle,
                            color = VictoryGreenLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = dateFormat.format(date),
                            style = TaqwaType.cardTitle,
                            color = TextWhite
                        )
                        Text(
                            text = timeFormat.format(date),
                            style = TaqwaType.bodySmall,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                        val strengthColor = when {
                            entry.urgeStrength <= 3 -> AccentGreen
                            entry.urgeStrength <= 6 -> AccentOrange
                            else -> AccentRed
                        }
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = strengthColor.copy(alpha = 0.15f)
                            ),
                            shape = RoundedCornerShape(TaqwaDimens.spaceS)
                        ) {
                            Text(
                                text = "Urge Strength: ${entry.urgeStrength}/10",
                                style = TaqwaType.body.copy(fontWeight = FontWeight.Bold),
                                color = strengthColor,
                                modifier = Modifier.padding(
                                    horizontal = TaqwaDimens.spaceL,
                                    vertical = TaqwaDimens.spaceS
                                )
                            )
                        }
                    }
                }

                if (entry.situationContext.isNotEmpty()) {
                    DetailSection(
                        icon = "📍",
                        title = "Situation",
                        content = entry.situationContext
                    )
                }

                if (entry.feelings.isNotEmpty()) {
                    DetailSection(
                        icon = "💭",
                        title = "Feelings",
                        content = entry.feelings.replace(",", "\n")
                    )
                }

                if (entry.realNeed.isNotEmpty()) {
                    DetailSection(
                        icon = "🎯",
                        title = "Real Need",
                        content = entry.realNeed.replace(",", "\n")
                    )
                }

                if (entry.alternativeChosen.isNotEmpty()) {
                    DetailSection(
                        icon = "🔄",
                        title = "Alternative Activity",
                        content = entry.alternativeChosen.replace(",", "\n")
                    )
                }

                if (entry.freeText.isNotEmpty()) {
                    DetailSection(
                        icon = "✍️",
                        title = "Message to Self",
                        content = entry.freeText
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
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
    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                Text(
                    text = title,
                    style = TaqwaType.cardTitle,
                    color = PrimaryLight
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = content,
                style = TaqwaType.body.copy(lineHeight = 24.sp),
                color = TextLight
            )
        }
    }
}