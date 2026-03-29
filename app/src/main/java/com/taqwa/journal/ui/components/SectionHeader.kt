package com.taqwa.journal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.taqwa.journal.ui.theme.*

@Composable
fun SectionHeader(
    emoji: String,
    title: String,
    modifier: Modifier = Modifier,
    color: Color = VanillaCustard,
    subtitle: String? = null
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = emoji,
                style = TaqwaType.emojiMedium.copy(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
            Text(
                text = title,
                style = TaqwaType.sectionTitle,
                color = color
            )
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))
            Text(
                text = subtitle,
                style = TaqwaType.caption,
                color = TextGray
            )
        }
    }
}