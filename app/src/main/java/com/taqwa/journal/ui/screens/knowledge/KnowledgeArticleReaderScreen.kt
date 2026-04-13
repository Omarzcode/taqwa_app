package com.taqwa.journal.ui.screens.knowledge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.knowledge.ContentBlock
import com.taqwa.journal.data.knowledge.KnowledgeArticle
import com.taqwa.journal.data.knowledge.KnowledgeRepository
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun KnowledgeArticleReaderScreen(
    articleId: String,
    onBack: () -> Unit
) {
    val article = KnowledgeRepository.getArticleById(articleId)

    if (article == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Article not found", color = TextGray)
        }
        return
    }

    val direction = if (article.isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top bar always LTR
        TaqwaTopBar(
            title = if (article.isArabic && article.titleAr.isNotEmpty()) article.titleAr else article.title,
            subtitle = if (article.isArabic) article.title else null,
            onBack = onBack
        )

        CompositionLocalProvider(LocalLayoutDirection provides direction) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = TaqwaDimens.screenPaddingHorizontal)
            ) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                // Read time badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (article.isArabic) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(TaqwaDimens.spaceS))
                            .background(BackgroundElevated)
                            .padding(horizontal = TaqwaDimens.spaceM, vertical = TaqwaDimens.spaceXS)
                    ) {
                        Text(
                            text = "\u23F1 ${article.readTimeMinutes} min read",
                            style = TaqwaType.captionSmall,
                            color = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))

                // Content blocks
                article.content.forEach { block ->
                    RenderContentBlock(block, article.isArabic)
                }

                // References section
                if (article.references.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
                    HorizontalDivider(color = DividerColor, thickness = TaqwaDimens.dividerThickness)
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

                    Text(
                        text = if (article.isArabic) "\u0627\u0644\u0645\u0631\u0627\u062C\u0639" else "References",
                        style = TaqwaType.cardTitle,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    article.references.forEach { ref ->
                        Text(
                            text = "\u2022 $ref",
                            style = TaqwaType.captionSmall,
                            color = TextMuted,
                            modifier = Modifier.padding(vertical = TaqwaDimens.spaceXXS)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceHuge))
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceHuge))
            }
        }
    }
}

@Composable
private fun RenderContentBlock(block: ContentBlock, isArabic: Boolean) {
    val textAlign = if (isArabic) TextAlign.Start else TextAlign.Start

    when (block) {
        is ContentBlock.Header -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceXXL))
            Text(
                text = block.text,
                style = if (isArabic) TaqwaType.sectionTitle.copy(fontSize = 20.sp, lineHeight = 34.sp)
                else TaqwaType.sectionTitle,
                color = VanillaCustard,
                textAlign = textAlign,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }

        is ContentBlock.Paragraph -> {
            Text(
                text = block.text,
                style = if (isArabic) TaqwaType.body.copy(fontSize = 17.sp, lineHeight = 30.sp)
                else TaqwaType.body.copy(lineHeight = 26.sp),
                color = TextLight,
                textAlign = textAlign,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.QuranVerse -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundElevated)
                    .padding(TaqwaDimens.cardPadding)
            ) {
                // Quran icon
                Text(
                    text = "\uFDFD",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                // Arabic text always RTL
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = block.arabic,
                        style = TaqwaType.arabicLarge.copy(lineHeight = 40.sp),
                        color = VanillaCustard,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                // Translation
                Text(
                    text = block.translation,
                    style = TaqwaType.body.copy(fontStyle = FontStyle.Italic),
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                // Reference
                Text(
                    text = block.reference,
                    style = TaqwaType.captionSmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.Hadith -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundCard)
                    .padding(TaqwaDimens.cardPadding)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "\u2728", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = if (isArabic) "\u062D\u062F\u064A\u062B" else "Hadith",
                        style = TaqwaType.caption,
                        color = AccentGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                Text(
                    text = block.text,
                    style = if (isArabic) TaqwaType.body.copy(fontSize = 17.sp, lineHeight = 30.sp)
                    else TaqwaType.body.copy(fontStyle = FontStyle.Italic, lineHeight = 26.sp),
                    color = TextLight,
                    textAlign = textAlign,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                Text(
                    text = block.narrator,
                    style = TaqwaType.captionSmall,
                    color = TextMuted,
                    modifier = Modifier.fillMaxWidth()
                )

                if (block.grading.isNotEmpty()) {
                    Text(
                        text = block.grading,
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.ScholarQuote -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundCard)
                    .padding(TaqwaDimens.cardPadding)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "\uD83D\uDCDC", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = block.scholar,
                        style = TaqwaType.cardTitle,
                        color = AccentGold
                    )
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                Text(
                    text = block.text,
                    style = if (isArabic) TaqwaType.body.copy(fontSize = 17.sp, lineHeight = 30.sp)
                    else TaqwaType.body.copy(lineHeight = 26.sp),
                    color = TextLight,
                    textAlign = textAlign,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

                Text(
                    text = block.source,
                    style = TaqwaType.captionSmall,
                    color = TextMuted
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.ScientificFact -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundElevated)
                    .padding(TaqwaDimens.cardPadding)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "\uD83D\uDD2C", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = "Scientific Evidence",
                        style = TaqwaType.caption,
                        color = AccentBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))

                Text(
                    text = block.text,
                    style = TaqwaType.body.copy(lineHeight = 26.sp),
                    color = TextLight,
                    modifier = Modifier.fillMaxWidth()
                )

                if (block.source.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\uD83D\uDCCE ${block.source}",
                        style = TaqwaType.captionSmall,
                        color = TextMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.BulletPoints -> {
            if (block.title.isNotEmpty()) {
                Text(
                    text = block.title,
                    style = if (isArabic) TaqwaType.cardTitle.copy(fontSize = 17.sp)
                    else TaqwaType.cardTitle,
                    color = TextWhite,
                    textAlign = textAlign,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            }

            val bullet = if (isArabic) "\u25CF" else "\u2022"
            block.points.forEach { point ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = TaqwaDimens.spaceXS)
                ) {
                    Text(
                        text = bullet,
                        style = TaqwaType.body,
                        color = VanillaCustard
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text(
                        text = point,
                        style = if (isArabic) TaqwaType.body.copy(fontSize = 16.sp, lineHeight = 28.sp)
                        else TaqwaType.body.copy(lineHeight = 24.sp),
                        color = TextLight,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.Warning -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(AccentRed.copy(alpha = 0.15f))
                    .padding(TaqwaDimens.spaceL)
            ) {
                Text(text = "\u26A0\uFE0F", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                Text(
                    text = block.text,
                    style = if (isArabic) TaqwaType.body.copy(fontSize = 16.sp, lineHeight = 28.sp)
                    else TaqwaType.body.copy(lineHeight = 24.sp),
                    color = AccentRedLight,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.Tip -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(AccentGreen.copy(alpha = 0.15f))
                    .padding(TaqwaDimens.spaceL)
            ) {
                Text(text = "\uD83D\uDCA1", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                Text(
                    text = block.text,
                    style = if (isArabic) TaqwaType.body.copy(fontSize = 16.sp, lineHeight = 28.sp)
                    else TaqwaType.body.copy(lineHeight = 24.sp),
                    color = AccentGreenLight,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.Quote -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(TaqwaDimens.spaceM))
                    .background(BackgroundCard)
                    .padding(TaqwaDimens.cardPadding)
            ) {
                Text(
                    text = "\u201C${block.text}\u201D",
                    style = if (isArabic) TaqwaType.body.copy(fontSize = 17.sp, lineHeight = 30.sp)
                    else TaqwaType.body.copy(fontStyle = FontStyle.Italic, lineHeight = 26.sp),
                    color = TextLight,
                    textAlign = textAlign,
                    modifier = Modifier.fillMaxWidth()
                )
                if (block.author.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                    Text(
                        text = "\u2014 ${block.author}",
                        style = TaqwaType.caption,
                        color = TextMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))
        }

        is ContentBlock.Divider -> {
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
            HorizontalDivider(
                color = DividerColor,
                thickness = TaqwaDimens.dividerThickness,
                modifier = Modifier.padding(horizontal = TaqwaDimens.spaceXXL)
            )
            Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
        }
    }
}