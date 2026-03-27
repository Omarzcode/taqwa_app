package com.taqwa.journal.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.ui.components.*
import com.taqwa.journal.ui.theme.*

@Composable
fun HomeScreen(
    urgesDefeated: Int,
    currentStreak: Int,
    longestStreak: Int,
    streakStatus: String,
    milestoneMessage: String?,
    totalRelapses: Int,
    dailyAyah: DailyAyah?,
    onDismissMilestone: () -> Unit,
    onUrgeClick: () -> Unit,
    onResetStreakClick: () -> Unit,
    onPastEntriesClick: () -> Unit = {},
    onRelapseHistoryClick: () -> Unit = {},
    onPatternAnalysisClick: () -> Unit = {},
    onPromiseWallClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    // Milestone dialog
    if (milestoneMessage != null) {
        AlertDialog(
            onDismissRequest = onDismissMilestone,
            title = {
                Text(
                    text = "🎉 Milestone Reached!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = milestoneMessage,
                    style = TaqwaType.body.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 24.sp
                )
            },
            confirmButton = {
                TextButton(onClick = onDismissMilestone) {
                    Text("Alhamdulillah! 🤲", color = VanillaCustard)
                }
            },
            containerColor = BackgroundCard
        )
    }

    // Subtle breathing animation for urge button
    val infiniteTransition = rememberInfiniteTransition(label = "breathe")
    val breatheScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe_scale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

        // ── Header ──
        Text(
            text = "Taqwa",
            style = TaqwaType.screenTitle.copy(
                fontSize = 22.sp,
                letterSpacing = 2.sp
            ),
            color = VanillaCustard
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))

        // ── Daily Ayah (TOP — Spiritual Grounding) ──
        if (dailyAyah != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dailyAyah.arabic,
                    style = TaqwaType.arabicMedium.copy(fontSize = 20.sp),
                    color = VanillaCustard,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "\"${dailyAyah.translation}\"",
                    style = TaqwaType.bodySmall.copy(
                        fontWeight = FontWeight.Light,
                        lineHeight = 22.sp
                    ),
                    color = TextLight,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = dailyAyah.reference,
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // Subtle divider
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(PrimaryDark)
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }

        // ── Streak Display ──
        TaqwaCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Streak emoji based on progress
                val streakEmoji = when {
                    currentStreak >= 100 -> "💎"
                    currentStreak >= 30 -> "🏆"
                    currentStreak >= 7 -> "🔥"
                    currentStreak >= 1 -> "🌱"
                    else -> "🤲"
                }

                Text(text = streakEmoji, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                Text(
                    text = "$currentStreak",
                    style = TaqwaType.streakNumber.copy(fontSize = 60.sp),
                    color = if (currentStreak > 0) VanillaCustard else TextGray
                )
                Text(
                    text = when (currentStreak) {
                        0 -> "Start your journey"
                        1 -> "day strong"
                        else -> "days strong"
                    },
                    style = TaqwaType.body,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                Text(
                    text = streakStatus,
                    style = TaqwaType.bodySmall,
                    color = PrimaryLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                HorizontalDivider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    thickness = TaqwaDimens.dividerThickness
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MiniStat(emoji = "🛡️", value = "$urgesDefeated", label = "Defeated")
                    MiniStat(emoji = "👑", value = "$longestStreak", label = "Best Streak")
                    MiniStat(emoji = "📉", value = "$totalRelapses", label = "Relapses")
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        // ── Urge Button — Modern Pill Design ──
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Outer glow ring
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(68.dp)
                    .scale(breatheScale)
                    .clip(RoundedCornerShape(34.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                UrgeButtonGlow.copy(alpha = glowAlpha),
                                AccentOrange.copy(alpha = glowAlpha * 0.6f),
                                UrgeButtonGlow.copy(alpha = glowAlpha)
                            )
                        )
                    )
            )

            // Main button
            Button(
                onClick = onUrgeClick,
                modifier = Modifier
                    .width(200.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(28.dp),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    UrgeButtonRed,
                                    UrgeButtonRedDark
                                )
                            ),
                            RoundedCornerShape(28.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    UrgeButtonGlow.copy(alpha = 0.3f),
                                    UrgeButtonGlow.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Pulsing dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(AccentRed)
                        )
                        Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                        Text(
                            text = "I need help",
                            style = TaqwaType.button.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp
                            ),
                            color = TextWhite
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        // Subtitle under button
        Text(
            text = "Tap when you feel an urge",
            style = TaqwaType.caption,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

        // ── Relapse (extremely subtle) ──
        TextButton(
            onClick = onResetStreakClick,
            modifier = Modifier.height(TaqwaDimens.buttonSmallHeight)
        ) {
            Text(
                text = "I relapsed...",
                style = TaqwaType.captionSmall,
                color = TextMuted.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
    }
}

@Composable
private fun MiniStat(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
        Text(
            text = value,
            style = TaqwaType.statValue,
            color = TextWhite
        )
        Text(
            text = label,
            style = TaqwaType.statLabel,
            color = TextGray
        )
    }
}