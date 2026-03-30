package com.taqwa.journal.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taqwa.journal.ui.theme.*

/**
 * Full-screen loading overlay that blocks interaction.
 * Wrap your screen content in a Box and place this on top.
 *
 * Usage:
 *
 * Box(modifier = Modifier.fillMaxSize()) {
 *     // Your screen content
 *     Column { ... }
 *
 *     // Loading overlay (shows on top when isLoading = true)
 *     LoadingOverlay(
 *         isLoading = uiState.isExporting,
 *         message = "Generating your report..."
 *     )
 * }
 */
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    message: String? = null,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OverlayDark)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* Block clicks */ }
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
                    .background(BackgroundCard)
                    .padding(TaqwaDimens.spaceXXXL),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = VanillaCustard,
                    trackColor = PrimaryDark,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(40.dp)
                )

                if (message != null) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = message,
                        style = TaqwaType.bodySmall,
                        color = TextLight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Inline loading indicator for use inside cards or sections.
 * Use when you don't want to block the entire screen.
 *
 * Usage:
 *
 * TaqwaCard {
 *     if (isLoading) {
 *         InlineLoading(message = "Loading memories...")
 *     } else {
 *         // Content
 *     }
 * }
 */
@Composable
fun InlineLoading(
    message: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = TaqwaDimens.spaceXL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = VanillaCustard,
            trackColor = PrimaryDark,
            strokeWidth = 2.dp,
            modifier = Modifier.size(28.dp)
        )

        if (message != null) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            Text(
                text = message,
                style = TaqwaType.caption,
                color = TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}