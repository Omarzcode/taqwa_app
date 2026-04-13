package com.taqwa.journal.ui.screens.knowledge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.knowledge.KnowledgeArticle
import com.taqwa.journal.data.knowledge.KnowledgeRepository
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun KnowledgeArticleListScreen(
    categoryId: String,
    onBack: () -> Unit,
    onArticleClick: (String) -> Unit
) {
    val categories = KnowledgeRepository.getCategories()
    val category = categories.find { it.id == categoryId }
    val articles = KnowledgeRepository.getArticlesForCategory(categoryId)

    val emoji = when (categoryId) {
        "science" -> "\uD83E\uDDE0"
        "islamic" -> "\uD83D\uDD4C"
        "harms" -> "\uD83D\uDC94"
        "recovery" -> "\uD83D\uDEE1\uFE0F"
        "motivation" -> "\uD83C\uDF1F"
        else -> "\uD83D\uDCDA"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "$emoji  ${category?.title ?: "Articles"}",
            subtitle = category?.titleAr,
            onBack = onBack
        )

        if (articles.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(TaqwaDimens.screenPaddingHorizontal),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\uD83D\uDCD6",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
                    Text(
                        text = "Articles coming soon",
                        style = TaqwaType.cardTitle,
                        color = TextLight
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "We\u2019re preparing in-depth content for this category.\nCheck back soon in sha Allah.",
                        style = TaqwaType.body,
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
            ) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                category?.let {
                    Text(
                        text = it.description,
                        style = TaqwaType.body,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
                }

                articles.forEachIndexed { index, article ->
                    ArticleListItem(
                        article = article,
                        index = index + 1,
                        onClick = { onArticleClick(article.id) }
                    )
                    if (index < articles.lastIndex) {
                        HorizontalDivider(
                            color = DividerColor,
                            thickness = TaqwaDimens.dividerThickness,
                            modifier = Modifier.padding(vertical = TaqwaDimens.spaceXS)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXXL))
            }
        }
    }
}

@Composable
private fun ArticleListItem(
    article: KnowledgeArticle,
    index: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(TaqwaDimens.spaceM))
            .clickable(onClick = onClick)
            .padding(
                horizontal = TaqwaDimens.spaceM,
                vertical = TaqwaDimens.spaceL
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Article number
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(TaqwaDimens.spaceS))
                .background(BackgroundElevated),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$index",
                style = TaqwaType.cardTitle,
                color = VanillaCustard
            )
        }

        Spacer(modifier = Modifier.width(TaqwaDimens.spaceL))

        Column(modifier = Modifier.weight(1f)) {
            if (article.isArabic && article.titleAr.isNotEmpty()) {
                Text(
                    text = article.titleAr,
                    style = TaqwaType.cardTitle,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXS))
                Text(
                    text = article.title,
                    style = TaqwaType.caption,
                    color = TextGray
                )
            } else {
                Text(
                    text = article.title,
                    style = TaqwaType.cardTitle,
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            Text(
                text = if (article.isArabic && article.summaryAr.isNotEmpty()) article.summaryAr else article.summary,
                style = TaqwaType.bodySmall,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXS))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "\u23F1 ${article.readTimeMinutes} min read",
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
                if (article.isArabic) {
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = "\u0639\u0631\u0628\u064A",
                        style = TaqwaType.captionSmall,
                        color = AccentGold
                    )
                }
            }
        }

        Text(
            text = "\u203A",
            style = TaqwaType.cardTitle,
            color = TextMuted
        )
    }
}