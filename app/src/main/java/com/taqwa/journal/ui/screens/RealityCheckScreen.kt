package com.taqwa.journal.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.model.QuestionData
import com.taqwa.journal.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun RealityCheckScreen(
    onNext: () -> Unit
) {
    val lines = QuestionData.realityCheckLines
    var visibleCount by remember { mutableIntStateOf(0) }
    var allShown by remember { mutableStateOf(false) }

    // Show lines one by one with delay
    LaunchedEffect(Unit) {
        for (i in lines.indices) {
            delay(3000)
            visibleCount = i + 1
        }
        delay(1000)
        allShown = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top - Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = "❌",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "REALITY CHECK",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AccentRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Read each line slowly...",
                fontSize = 16.sp,
                color = TextGray
            )
        }

        // Middle - Lines appearing one by one
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            lines.forEachIndexed { index, line ->
                AnimatedVisibility(
                    visible = index < visibleCount,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "✦",
                            fontSize = 16.sp,
                            color = AccentOrange,
                            modifier = Modifier.padding(end = 12.dp, top = 2.dp)
                        )
                        Text(
                            text = line,
                            fontSize = 16.sp,
                            color = TextLight,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }

        // Bottom - Next button
        if (allShown) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryMedium
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Next ➜",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}