package com.taqwa.journal.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taqwa.journal.core.theme.*

@Composable
fun TaqwaCard(
    modifier: Modifier = Modifier,
    containerColor: Color = BackgroundCard,
    contentPadding: PaddingValues = PaddingValues(TaqwaDimens.cardPadding),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(TaqwaDimens.cardCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = TaqwaDimens.cardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun TaqwaAccentCard(
    modifier: Modifier = Modifier,
    accentColor: Color = PrimaryDark,
    alpha: Float = 0.3f,
    contentPadding: PaddingValues = PaddingValues(TaqwaDimens.cardPadding),
    content: @Composable ColumnScope.() -> Unit
) {
    TaqwaCard(
        modifier = modifier,
        containerColor = accentColor.copy(alpha = alpha),
        contentPadding = contentPadding,
        content = content
    )
}
