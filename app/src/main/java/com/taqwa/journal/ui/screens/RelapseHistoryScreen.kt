package com.taqwa.journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.preferences.RelapseRecord
import com.taqwa.journal.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
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
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "📉  Relapse History",
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

        if (relapseHistory.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏆",
                        fontSize = 64.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No relapses recorded",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Keep going strong!",
                        fontSize = 14.sp,
                        color = TextGray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                // Summary card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = BackgroundCard
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Relapses: $totalRelapses",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentOrange
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Every relapse is a lesson.\nStudy your patterns. Learn. Grow.",
                                fontSize = 13.sp,
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

                // Bottom encouragement
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = PrimaryDark.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "وَلَا تَيْأَسُوا مِن رَّوْحِ اللَّهِ",
                                fontSize = 18.sp,
                                color = AccentGold,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "\"And do not despair of the mercy of Allah.\"",
                                fontSize = 14.sp,
                                color = TextLight,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "— Surah Yusuf 12:87",
                                fontSize = 12.sp,
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

    Card(
        modifier = Modifier.fillMaxWidth(),
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
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Relapse #$number",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentOrange
                )
                Text(
                    text = formattedDate,
                    fontSize = 13.sp,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Streak lost
            if (record.streakLost > 0) {
                Text(
                    text = "📉 Lost a ${record.streakLost}-day streak",
                    fontSize = 14.sp,
                    color = TextLight
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Reason
            if (record.reason.isNotEmpty()) {
                Text(
                    text = "💭 Reflection:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = record.reason,
                    fontSize = 14.sp,
                    color = TextGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}