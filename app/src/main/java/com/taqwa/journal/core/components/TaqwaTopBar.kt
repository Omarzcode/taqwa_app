package com.taqwa.journal.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taqwa.journal.core.theme.*

/**
 * Enhanced top bar with optional subtitle, breadcrumb, and action icons.
 *
 * Usage examples:
 *
 * // Simple (backward compatible)
 * TaqwaTopBar(title = "Settings", onBack = { navController.popBackStack() })
 *
 * // With subtitle
 * TaqwaTopBar(title = "Edit Shield Plan", subtitle = "Boredom Trigger", onBack = { ... })
 *
 * // With breadcrumb
 * TaqwaTopBar(title = "Notifications", breadcrumb = "Settings", onBack = { ... })
 *
 * // With action icon
 * TaqwaTopBar(
 *     title = "Memory Bank",
 *     onBack = { ... },
 *     actionIcon = Icons.Default.Add,
 *     actionDescription = "Add Memory",
 *     onAction = { ... }
 * )
 *
 * // With two actions
 * TaqwaTopBar(
 *     title = "Journal",
 *     onBack = { ... },
 *     actionIcon = Icons.Default.FilterList,
 *     actionDescription = "Filter",
 *     onAction = { ... },
 *     secondaryActionIcon = Icons.Default.Search,
 *     secondaryActionDescription = "Search",
 *     onSecondaryAction = { ... }
 * )
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaqwaTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    subtitle: String? = null,
    breadcrumb: String? = null,
    actionIcon: ImageVector? = null,
    actionDescription: String = "Action",
    onAction: (() -> Unit)? = null,
    secondaryActionIcon: ImageVector? = null,
    secondaryActionDescription: String = "Action",
    onSecondaryAction: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Column {
                // Breadcrumb (e.g., "Settings > Notifications")
                if (breadcrumb != null) {
                    Text(
                        text = breadcrumb,
                        style = TaqwaType.captionSmall,
                        color = TextMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // Title
                Text(
                    text = title,
                    style = TaqwaType.sectionTitle,
                    color = TextWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Subtitle (e.g., editing context)
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = TaqwaType.caption,
                        color = TextGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }
            }
        },
        actions = {
            if (secondaryActionIcon != null && onSecondaryAction != null) {
                IconButton(onClick = onSecondaryAction) {
                    Icon(
                        imageVector = secondaryActionIcon,
                        contentDescription = secondaryActionDescription,
                        tint = TextLight
                    )
                }
            }
            if (actionIcon != null && onAction != null) {
                IconButton(onClick = onAction) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionDescription,
                        tint = VanillaCustard
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundDark,
            titleContentColor = TextWhite
        )
    )
}
