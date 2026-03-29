package com.taqwa.journal.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.data.preferences.DailyAyah
import com.taqwa.journal.data.preferences.ShieldPlan
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.theme.*

@Composable
fun QuickCatchScreen(
    currentStreak: Int,
    whyQuitting: String,
    relapseLetter: MemoryEntry?,
    victoryNote: MemoryEntry?,
    randomMemory: MemoryEntry?,
    dailyAyah: DailyAyah?,
    activePlans: List<ShieldPlan> = emptyList(),
    onCaughtIt: () -> Unit,
    onNeedFullFlow: () -> Unit,
    onBack: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shield")
    val shieldScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shield_pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // ── Header ──
            Text(
                text = "🛡️",
                fontSize = 48.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = "STOP. READ THIS.",
                style = TaqwaType.screenTitle.copy(
                    fontSize = 22.sp,
                    letterSpacing = 2.sp
                ),
                color = VanillaCustard,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Text(
                text = "This is a thought. Not a command.\nYou have the power right now.",
                style = TaqwaType.bodySmall,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // ── 1. STREAK — What You'll Lose ──
            if (currentStreak > 0) {
                TaqwaAccentCard(accentColor = AccentOrange, alpha = 0.15f) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You will lose",
                            style = TaqwaType.bodySmall,
                            color = AccentOrange
                        )
                        Text(
                            text = "$currentStreak days",
                            style = TaqwaType.streakNumber.copy(fontSize = 48.sp),
                            color = AccentOrangeLight
                        )
                        Text(
                            text = "of healing, strength, and progress.\nIs 5 minutes worth losing all of this?",
                            style = TaqwaType.bodySmall,
                            color = TextLight,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 2. RELAPSE LETTER — Your Own Pain ──
            if (relapseLetter != null) {
                TaqwaAccentCard(accentColor = AccentRed, alpha = 0.12f) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "📝 You wrote this after your last relapse:",
                            style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = AccentRedLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "\"${relapseLetter.message}\"",
                            style = TaqwaType.body.copy(
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            color = TextWhite
                        )
                        if (relapseLetter.streakAtTime > 0) {
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                            Text(
                                text = "You lost a ${relapseLetter.streakAtTime}-day streak that time.",
                                style = TaqwaType.captionSmall,
                                color = TextMuted
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 3. WHY YOU'RE QUITTING — Your Purpose ──
            if (whyQuitting.isNotBlank()) {
                TaqwaCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 Remember why you started:",
                            style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = VanillaCustard
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "\"$whyQuitting\"",
                            style = TaqwaType.body.copy(lineHeight = 24.sp),
                            color = TextWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 4. VICTORY MEMORY — Proof You Can Win ──
            if (victoryNote != null) {
                TaqwaAccentCard(accentColor = VictoryGreen, alpha = 0.12f) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "🏆 After your last victory, you wrote:",
                            style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = VictoryGreenLight
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "\"${victoryNote.message}\"",
                            style = TaqwaType.body.copy(lineHeight = 24.sp),
                            color = TextWhite
                        )
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 5. RANDOM MEMORY — Extra Ammunition ──
            if (randomMemory != null && randomMemory.id != relapseLetter?.id && randomMemory.id != victoryNote?.id) {
                TaqwaCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        val label = when (randomMemory.type) {
                            MemoryEntry.TYPE_RELAPSE_LETTER -> "📝 From your memory bank:"
                            MemoryEntry.TYPE_VICTORY_NOTE -> "🏆 From your memory bank:"
                            else -> "💭 You once wrote:"
                        }
                        Text(
                            text = label,
                            style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "\"${randomMemory.message}\"",
                            style = TaqwaType.body.copy(lineHeight = 24.sp),
                            color = TextLight
                        )
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 6. SHIELD PLAN — Quick Action Steps ──
            if (activePlans.isNotEmpty()) {
                val randomPlan = remember { activePlans.random() }

                TaqwaAccentCard(accentColor = PrimaryMedium, alpha = 0.1f) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "${randomPlan.emoji} Shield: ${randomPlan.triggerName}",
                            style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = VanillaCustard
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                        randomPlan.steps.forEachIndexed { index, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "${index + 1}.",
                                    style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryLight,
                                    modifier = Modifier.width(20.dp)
                                )
                                Text(
                                    text = step,
                                    style = TaqwaType.bodySmall,
                                    color = TextLight,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        if (randomPlan.personalNote.isNotBlank()) {
                            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                            Text(
                                text = "💭 \"${randomPlan.personalNote}\"",
                                style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Light),
                                color = TextGray,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            }

            // ── 7. AYAH — Spiritual Shield ──
            if (dailyAyah != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalDivider(
                        color = DividerColor,
                        modifier = Modifier.padding(horizontal = 48.dp),
                        thickness = TaqwaDimens.dividerThickness
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = dailyAyah.arabic,
                        style = TaqwaType.arabicMedium.copy(fontSize = 18.sp),
                        color = VanillaCustard,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\"${dailyAyah.translation}\"",
                        style = TaqwaType.bodySmall,
                        color = TextLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "— ${dailyAyah.reference}",
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                }
            }

            // ── Final truth ──
            Text(
                text = "This feeling will pass.\nIt always does.\nYou've survived every urge so far.",
                style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
        }

        // ── Bottom Buttons (fixed) ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundDark)
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
                .padding(bottom = TaqwaDimens.spaceXL, top = TaqwaDimens.spaceM)
        ) {
            Button(
                onClick = onCaughtIt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VictoryGreen),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "✅  I caught it — I'm okay",
                    style = TaqwaType.button.copy(fontSize = 16.sp),
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            OutlinedButton(
                onClick = onNeedFullFlow,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentOrange),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        listOf(AccentOrange.copy(alpha = 0.5f), AccentOrange.copy(alpha = 0.3f))
                    )
                ),
                shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
            ) {
                Text(
                    text = "🔴  I need the full flow",
                    style = TaqwaType.button.copy(fontSize = 14.sp),
                    color = AccentOrange
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            TextButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Text(
                    text = "Go back",
                    style = TaqwaType.captionSmall,
                    color = TextMuted.copy(alpha = 0.6f)
                )
            }
        }
    }
}