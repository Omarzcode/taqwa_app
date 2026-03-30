package com.taqwa.journal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

/**
 * Standardized dialog matching Taqwa design language.
 * Replaces inconsistent AlertDialog usage across screens.
 */
@Composable
fun TaqwaDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    emoji: String? = null,
    title: String,
    message: String? = null,
    confirmLabel: String = "Confirm",
    confirmColor: Color = VanillaCustard,
    onConfirm: () -> Unit,
    dismissLabel: String? = "Cancel",
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    if (!isVisible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (emoji != null) {
                    Text(
                        text = emoji,
                        style = TaqwaType.emojiMedium
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                }
                Text(
                    text = title,
                    style = TaqwaType.cardTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (message != null) {
                    Text(
                        text = message,
                        style = TaqwaType.body,
                        color = TextLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
                content?.invoke(this)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmLabel,
                    style = TaqwaType.button,
                    color = confirmColor
                )
            }
        },
        dismissButton = if (dismissLabel != null) {
            {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = dismissLabel,
                        style = TaqwaType.button,
                        color = TextMuted
                    )
                }
            }
        } else null,
        containerColor = BackgroundCard,
        shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
    )
}

/**
 * Dialog with a text input field. Used for adding items to lists
 * (promises, duas, reminders, etc.)
 */
@Composable
fun TaqwaInputDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    emoji: String? = null,
    title: String,
    placeholder: String = "",
    initialValue: String = "",
    maxLength: Int? = 200,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 4,
    confirmLabel: String = "Add",
    confirmColor: Color = VanillaCustard,
    onConfirm: (String) -> Unit
) {
    if (!isVisible) return

    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (emoji != null) {
                    Text(
                        text = emoji,
                        style = TaqwaType.emojiMedium
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                }
                Text(
                    text = title,
                    style = TaqwaType.cardTitle,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            FormTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = placeholder,
                singleLine = singleLine,
                maxLines = maxLines,
                maxLength = maxLength,
                showCharCount = maxLength != null
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (text.isNotBlank()) {
                        onConfirm(text.trim())
                        text = ""
                    }
                },
                enabled = text.isNotBlank()
            ) {
                Text(
                    text = confirmLabel,
                    style = TaqwaType.button,
                    color = if (text.isNotBlank()) confirmColor else TextMuted
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                text = ""
                onDismiss()
            }) {
                Text(
                    text = "Cancel",
                    style = TaqwaType.button,
                    color = TextMuted
                )
            }
        },
        containerColor = BackgroundCard,
        shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius)
    )
}