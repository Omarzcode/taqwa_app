package com.taqwa.journal.features.home.screens

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
import com.taqwa.journal.core.components.*
import com.taqwa.journal.features.home.ui.HomeAction
import com.taqwa.journal.features.home.ui.HomeState
import com.taqwa.journal.core.theme.*

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    // Morning Check-In overlay
    var checkInDismissed by remember { mutableStateOf(false) }
    val showCheckInOverlay = state.showMorningCheckIn && !checkInDismissed

    Box(modifier = Modifier.fillMaxSize()) {

        // ── Main Content ──
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

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // ── Daily Ayah ──
            if (state.dailyAyah != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.dailyAyah.arabic,
                        style = TaqwaType.arabicMedium.copy(fontSize = 20.sp),
                        color = VanillaCustard,
                        textAlign = TextAlign.Center,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                    Text(
                        text = "\"${state.dailyAyah.translation}\"",
                        style = TaqwaType.bodySmall.copy(
                            fontWeight = FontWeight.Light,
                            lineHeight = 22.sp
                        ),
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(
                        text = state.dailyAyah.reference,
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(PrimaryDark)
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
            }

            // ── Streak Card ──
            TaqwaCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.streakEmoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                    Text(
                        text = "${state.currentStreak}",
                        style = TaqwaType.streakNumber.copy(fontSize = 60.sp),
                        color = if (state.currentStreak > 0) VanillaCustard else TextGray
                    )
                    Text(
                        text = state.streakLabel,
                        style = TaqwaType.body,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
                    Text(
                        text = state.streakStatus,
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
                        MiniStat(emoji = "🛡️", value = "${state.urgesDefeated}", label = "Defeated")
                        MiniStat(emoji = "👑", value = "${state.longestStreak}", label = "Best Streak")
                        MiniStat(emoji = "📉", value = "${state.totalRelapses}", label = "Relapses")
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

            // ── "I Need Help" Button ──
            UrgeButton(onClick = { onAction(HomeAction.StartUrgeFlow) })

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            Text(
                text = "Tap when you feel an urge",
                style = TaqwaType.caption,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // ── Quick Catch Button ──
            OutlinedButton(
                onClick = { onAction(HomeAction.OpenQuickCatch) },
                modifier = Modifier
                    .width(200.dp)
                    .height(44.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = VanillaCustard),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        listOf(
                            PrimaryLight.copy(alpha = 0.4f),
                            PrimaryLight.copy(alpha = 0.2f)
                        )
                    )
                ),
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = "🛡️  Quick Catch",
                    style = TaqwaType.button.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = VanillaCustard.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))

            Text(
                text = "Just a thought? Catch it fast",
                style = TaqwaType.captionSmall,
                color = TextMuted.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))
        }

        // ── Morning Check-In Overlay ──
        if (showCheckInOverlay) {
            MorningCheckInOverlay(
                onStartCheckIn = {
                    checkInDismissed = true
                    onAction(HomeAction.OpenMorningCheckIn)
                },
                onDismiss = { checkInDismissed = true }
            )
        }

        // ── Milestone Dialog ──
        if (state.hasMilestone) {
            TaqwaDialog(
                isVisible = true,
                onDismiss = { onAction(HomeAction.DismissMilestone) },
                emoji = "🎉",
                title = "Milestone Reached!",
                message = state.milestoneMessage,
                confirmLabel = "Alhamdulillah! 🤲",
                confirmColor = VanillaCustard,
                onConfirm = { onAction(HomeAction.DismissMilestone) },
                dismissLabel = null
            )
        }
    }
}

// ════════════════════════════════════════════
// MORNING CHECK-IN OVERLAY
// ════════════════════════════════════════════

@Composable
private fun MorningCheckInOverlay(
    onStartCheckIn: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OverlayDark),
        contentAlignment = Alignment.Center
    ) {
        TaqwaCard(
            modifier = Modifier
                .padding(horizontal = TaqwaDimens.spaceXXXL)
                .widthIn(max = 320.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "☀️", fontSize = 40.sp)

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                Text(
                    text = "Good Morning",
                    style = TaqwaType.sectionTitle,
                    color = VanillaCustard,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                Text(
                    text = "Start your day with awareness\nand set your intention.",
                    style = TaqwaType.bodySmall,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

                // Start Check-In button
                Button(
                    onClick = onStartCheckIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TaqwaDimens.buttonHeight),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentOrange,
                        contentColor = TextWhite
                    )
                ) {
                    Text(
                        text = "☀️  Begin Check-In",
                        style = TaqwaType.button
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                // Dismiss
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Maybe later",
                        style = TaqwaType.caption,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

// ════════════════════════════════════════════
// URGE BUTTON
// ════════════════════════════════════════════

@Composable
private fun UrgeButton(onClick: () -> Unit) {
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
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

        Button(
            onClick = onClick,
            modifier = Modifier
                .width(200.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(28.dp),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(UrgeButtonRed, UrgeButtonRedDark)
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
}

// ════════════════════════════════════════════
// HELPERS
// ════════════════════════════════════════════

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