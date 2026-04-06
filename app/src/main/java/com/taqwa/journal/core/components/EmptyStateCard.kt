package com.taqwa.journal.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.taqwa.journal.core.theme.*

/**
 * Standardized empty state display for screens with no data.
 */
@Composable
fun EmptyStateCard(
    emoji: String,
    title: String,
    subtitle: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TaqwaCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = TaqwaDimens.spaceXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                style = TaqwaType.emojiLarge
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Text(
                text = title,
                style = TaqwaType.cardTitle,
                color = TextLight,
                textAlign = TextAlign.Center
            )

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                Text(
                    text = subtitle,
                    style = TaqwaType.bodySmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            if (actionLabel != null && onAction != null) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXL))
                TextButton(onClick = onAction) {
                    Text(
                        text = actionLabel,
                        style = TaqwaType.button,
                        color = VanillaCustard
                    )
                }
            }
        }
    }
}
