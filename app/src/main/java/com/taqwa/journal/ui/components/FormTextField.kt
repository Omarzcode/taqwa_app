package com.taqwa.journal.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.taqwa.journal.ui.theme.*

/**
 * Standardized text field matching Taqwa design language.
 * Replaces inconsistent OutlinedTextField usage across screens.
 *
 * Usage:
 *
 * // Simple single-line
 * FormTextField(
 *     value = name,
 *     onValueChange = { name = it },
 *     label = "Plan Name",
 *     placeholder = "e.g., Late Night Boredom"
 * )
 *
 * // Multi-line with character count
 * FormTextField(
 *     value = description,
 *     onValueChange = { description = it },
 *     label = "Description",
 *     placeholder = "Describe your plan...",
 *     singleLine = false,
 *     maxLines = 4,
 *     maxLength = 300,
 *     showCharCount = true
 * )
 *
 * // With error
 * FormTextField(
 *     value = email,
 *     onValueChange = { email = it },
 *     label = "Email",
 *     error = if (emailInvalid) "Please enter a valid email" else null
 * )
 *
 * // With emoji label
 * FormTextField(
 *     value = dua,
 *     onValueChange = { dua = it },
 *     label = "Your Dua",
 *     emoji = "🤲",
 *     placeholder = "Write a personal dua..."
 * )
 */
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    emoji: String? = null,
    placeholder: String? = null,
    error: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 5,
    minLines: Int = if (singleLine) 1 else 3,
    maxLength: Int? = null,
    showCharCount: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = if (singleLine) ImeAction.Done else ImeAction.Default,
    onImeAction: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label row
        if (label != null) {
            Row {
                if (emoji != null) {
                    Text(
                        text = emoji,
                        style = TaqwaType.caption
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceXS))
                }
                Text(
                    text = label,
                    style = TaqwaType.caption,
                    color = if (error != null) AccentRed else TextGray
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
        }

        // Text field
        OutlinedTextField(
            value = if (maxLength != null) value.take(maxLength) else value,
            onValueChange = { newValue ->
                if (maxLength != null) {
                    if (newValue.length <= maxLength) onValueChange(newValue)
                } else {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (error != null) {
                        Modifier.border(
                            width = 1.dp,
                            color = AccentRed.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius)
                        )
                    } else Modifier
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = TaqwaType.body.copy(color = TextWhite),
            placeholder = if (placeholder != null) {
                {
                    Text(
                        text = placeholder,
                        style = TaqwaType.body,
                        color = TextMuted
                    )
                }
            } else null,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            shape = RoundedCornerShape(TaqwaDimens.buttonSmallCornerRadius),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VanillaCustard.copy(alpha = 0.6f),
                unfocusedBorderColor = PrimaryDark,
                disabledBorderColor = PrimaryDark.copy(alpha = 0.5f),
                errorBorderColor = AccentRed,
                focusedContainerColor = BackgroundElevated,
                unfocusedContainerColor = BackgroundElevated,
                disabledContainerColor = BackgroundElevated.copy(alpha = 0.5f),
                cursorColor = VanillaCustard,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                disabledTextColor = TextMuted
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction?.invoke() },
                onNext = { onImeAction?.invoke() }
            )
        )

        // Bottom row: error + character count
        if (error != null || (showCharCount && maxLength != null)) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Error message
                if (error != null) {
                    Text(
                        text = error,
                        style = TaqwaType.captionSmall,
                        color = AccentRed,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                // Character count
                if (showCharCount && maxLength != null) {
                    Text(
                        text = "${value.length}/$maxLength",
                        style = TaqwaType.captionSmall,
                        color = if (value.length >= maxLength) AccentOrange else TextMuted
                    )
                }
            }
        }
    }
}