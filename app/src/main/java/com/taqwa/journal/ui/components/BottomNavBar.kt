package com.taqwa.journal.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

enum class BottomNavItem(
    val label: String,
    val emoji: String,
    val route: String
) {
    HOME("Home", "🏠", "home"),
    JOURNAL("Journal", "📖", "past_entries"),
    CALENDAR("Calendar", "📅", "calendar"),
    INSIGHTS("Insights", "📊", "pattern_analysis"),
    SETTINGS("Settings", "⚙️", "settings")
}

@Composable
fun TaqwaBottomNavBar(
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            color = DividerColor,
            thickness = TaqwaDimens.dividerThickness
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavBarBackground)
                .padding(
                    top = TaqwaDimens.spaceS,
                    bottom = TaqwaDimens.spaceM
                )
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem.entries.forEach { item ->
                val isSelected = currentRoute == item.route
                TaqwaNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TaqwaNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "nav_scale"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) NavBarSelected else NavBarUnselected,
        label = "nav_color"
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(TaqwaDimens.spaceS))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = TaqwaDimens.spaceXS),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.scale(scale),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(width = 48.dp, height = 28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(NavBarIndicator)
                )
            }
            Text(
                text = item.emoji,
                fontSize = TaqwaDimens.bottomNavIconSize.value.sp
            )
        }
        Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
        Text(
            text = item.label,
            style = TaqwaType.navLabel,
            color = textColor,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}