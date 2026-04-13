package com.taqwa.journal.ui.screens.knowledge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.knowledge.KnowledgeCategory
import com.taqwa.journal.data.knowledge.KnowledgeRepository
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun KnowledgeCategoriesScreen(
    onBack: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    val categories = KnowledgeRepository.getCategories()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "\uD83D\uDCDA Knowledge",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
        ) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            Text(
                text = "Understanding your enemy is the first step to defeating it.",
                style = TaqwaType.body,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            Text(
                text = "\u0645\u064E\u0646\u0652 \u0639\u064E\u0631\u064E\u0641\u064E \u062F\u064E\u0627\u0621\u064E\u0647\u064F \u0623\u064E\u0645\u0652\u0643\u064E\u0646\u064E\u0647\u064F \u0627\u0644\u0634\u0651\u0650\u0641\u064E\u0627\u0621\u064F",
                style = TaqwaType.arabicMedium,
                color = VanillaCustard
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

            categories.forEach { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.id) }
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))
        }
    }
}

@Composable
private fun CategoryCard(
    category: KnowledgeCategory,
    onClick: () -> Unit
) {
    val emoji = when (category.icon) {
        "science" -> "\uD83E\uDDE0"
        "mosque" -> "\uD83D\uDD4C"
        "warning" -> "\uD83D\uDC94"
        "healing" -> "\uD83D\uDEE1\uFE0F"
        "star" -> "\uD83C\uDF1F"
        else -> "\uD83D\uDCDA"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(TaqwaDimens.cardCornerRadius))
            .background(BackgroundCard)
            .clickable(onClick = onClick)
            .padding(TaqwaDimens.cardPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundElevated),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.title,
                    style = TaqwaType.cardTitle,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
                Text(
                    text = category.titleAr,
                    style = TaqwaType.body,
                    color = VanillaCustard
                )
            }

            Text(
                text = "\u203A",
                style = TaqwaType.sectionTitle,
                color = TextMuted
            )
        }

        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

        Text(
            text = category.description,
            style = TaqwaType.bodySmall,
            color = TextGray
        )

        if (category.articleCount > 0) {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Text(
                text = "${category.articleCount} articles",
                style = TaqwaType.caption,
                color = TextMuted
            )
        }
    }
}