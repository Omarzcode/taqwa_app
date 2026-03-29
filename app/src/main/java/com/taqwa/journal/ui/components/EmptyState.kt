package com.taqwa.journal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

@Composable
fun EmptyState(
    emoji: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    extra: @Composable ColumnScope.() -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = TaqwaDimens.spaceXXXL)
        ) {
            Text(text = emoji, fontSize = 56.sp)
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
            Text(
                text = title,
                style = TaqwaType.sectionTitle,
                color = TextWhite,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Text(
                text = description,
                style = TaqwaType.bodySmall,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            extra()
        }
    }
}