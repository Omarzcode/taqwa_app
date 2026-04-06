package com.taqwa.journal.features.tools.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.components.SectionHeader
import com.taqwa.journal.core.components.TaqwaTopBar
import com.taqwa.journal.core.theme.*

sealed interface ToolsAction {
    data object OpenShieldPlans : ToolsAction
    data object OpenMemoryBank : ToolsAction
    data object OpenPromiseWall : ToolsAction
    data object OpenMorningCheckIn : ToolsAction
    data object OpenPastEntries : ToolsAction
    data object OpenCalendar : ToolsAction
    data object OpenPatternAnalysis : ToolsAction
    data object OpenRelapseHistory : ToolsAction
    data object OpenResetStreak : ToolsAction
}

@Composable
fun ToolsScreen(
    todayCheckInDone: Boolean = false,
    memoryCount: Int = 0,
    onAction: (ToolsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(title = "🧰  My Tools")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // ── Defense Tools ──
            SectionHeader(
                emoji = "🛡️",
                title = "Defense Tools",
                subtitle = "Your weapons against urges"
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            ToolItem(
                emoji = "🛡️",
                title = "Shield Plans",
                subtitle = "Pre-written defense strategies for each trigger",
                onClick = { onAction(ToolsAction.OpenShieldPlans) }
            )

            ToolItem(
                emoji = "🧠",
                title = "Memory Bank",
                subtitle = if (memoryCount > 0) "$memoryCount memories saved" else "Save moments of clarity and pain",
                onClick = { onAction(ToolsAction.OpenMemoryBank) }
            )

            ToolItem(
                emoji = "💪",
                title = "Promise Wall",
                subtitle = "Your promises, duas, and reasons to quit",
                onClick = { onAction(ToolsAction.OpenPromiseWall) }
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // ── Daily Ritual ──
            SectionHeader(
                emoji = "☀️",
                title = "Daily Ritual",
                subtitle = "Build your daily shield"
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            ToolItem(
                emoji = "☀️",
                title = "Morning Check-In",
                subtitle = if (todayCheckInDone) "✓ Completed today" else "Start your day with awareness",
                onClick = { onAction(ToolsAction.OpenMorningCheckIn) },
                accentColor = if (!todayCheckInDone) AccentOrangeLight else null
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            // ── Browse & Track ──
            SectionHeader(
                emoji = "📊",
                title = "Browse & Track",
                subtitle = "Your journey data"
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

            ToolItem(
                emoji = "📖",
                title = "Journal Entries",
                subtitle = "Browse your past reflections",
                onClick = { onAction(ToolsAction.OpenPastEntries) }
            )

            ToolItem(
                emoji = "📅",
                title = "Calendar",
                subtitle = "See your journey on a calendar",
                onClick = { onAction(ToolsAction.OpenCalendar) }
            )

            ToolItem(
                emoji = "📊",
                title = "Pattern Analysis",
                subtitle = "Discover your triggers and trends",
                onClick = { onAction(ToolsAction.OpenPatternAnalysis) }
            )

            ToolItem(
                emoji = "📉",
                title = "Relapse History",
                subtitle = "Review past struggles to learn",
                onClick = { onAction(ToolsAction.OpenRelapseHistory) }
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))

            // ── Relapse Section (subtle, at the very bottom) ──
            HorizontalDivider(
                color = DividerColor,
                thickness = TaqwaDimens.dividerThickness
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onAction(ToolsAction.OpenResetStreak) }
                ) {
                    Text(
                        text = "I relapsed...",
                        style = TaqwaType.caption,
                        color = TextMuted.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            Text(
                text = "It's okay. What matters is you come back.",
                style = TaqwaType.captionSmall,
                color = TextMuted.copy(alpha = 0.3f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))
        }
    }
}

@Composable
private fun ToolItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    accentColor: androidx.compose.ui.graphics.Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(TaqwaDimens.spaceM))
            .clickable(onClick = onClick)
            .padding(
                horizontal = TaqwaDimens.spaceM,
                vertical = TaqwaDimens.spaceM
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(TaqwaDimens.spaceS))
                .background(BackgroundElevated),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = TaqwaType.cardTitle,
                color = accentColor ?: TextWhite
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
            Text(
                text = subtitle,
                style = TaqwaType.captionSmall,
                color = if (accentColor != null) accentColor.copy(alpha = 0.7f) else TextMuted
            )
        }

        Text(
            text = "›",
            style = TaqwaType.cardTitle,
            color = TextMuted
        )
    }
}