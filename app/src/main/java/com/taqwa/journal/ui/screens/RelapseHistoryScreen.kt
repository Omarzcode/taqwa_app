package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.ui.components.EmptyState
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RelapseHistoryScreen(
    relapseHistory: List<RelapseRecord>,
    totalRelapses: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "📉  Relapse History",
            onBack = onBack
        )

        if (relapseHistory.isEmpty()) {
            EmptyState(
                emoji = "🏆",
                title = "No relapses recorded",
                description = "Keep going strong!\nMay Allah keep you steadfast."
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(TaqwaDimens.cardSpacing),
                contentPadding = PaddingValues(
                    top = TaqwaDimens.spaceS,
                    bottom = TaqwaDimens.spaceL
                )
            ) {
                // Summary
                item {
                    TaqwaCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Relapses: $totalRelapses",
                                style = TaqwaType.sectionTitle,
                                color = AccentOrange
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "Every relapse is a lesson.\nStudy your patterns. Learn. Grow.",
                                style = TaqwaType.bodySmall,
                                color = TextGray,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                // Relapse entries
                itemsIndexed(relapseHistory) { index, record ->
                    RelapseCard(
                        number = index + 1,
                        record = record
                    )
                }

                // Encouragement
                item {
                    TaqwaAccentCard(accentColor = PrimaryDark, alpha = 0.3f) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "وَلَا تَيْأَسُوا مِن رَّوْحِ اللَّهِ",
                                style = TaqwaType.arabicMedium,
                                color = VanillaCustard,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "\"And do not despair of the mercy of Allah.\"",
                                style = TaqwaType.bodySmall,
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "— Surah Yusuf 12:87",
                                style = TaqwaType.captionSmall,
                                color = TextGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RelapseCard(
    number: Int,
    record: RelapseRecord
) {
    val formattedDate = try {
        val date = LocalDate.parse(record.date)
        date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e: Exception) {
        record.date
    }

    TaqwaCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Relapse #$number",
                    style = TaqwaType.cardTitle,
                    color = AccentOrange
                )
                Text(
                    text = formattedDate,
                    style = TaqwaType.bodySmall,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            // Streak lost
            if (record.streakLost > 0) {
                Text(
                    text = "📉 Lost a ${record.streakLost}-day streak",
                    style = TaqwaType.body,
                    color = TextLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            }

            // Reason
            if (record.reason.isNotEmpty()) {
                Text(
                    text = "💭 Reflection:",
                    style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = PrimaryLight
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
                Text(
                    text = record.reason,
                    style = TaqwaType.body.copy(lineHeight = 20.sp),
                    color = TextGray
                )
            }
        }
    }
}