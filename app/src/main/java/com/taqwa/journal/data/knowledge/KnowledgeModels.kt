package com.taqwa.journal.data.knowledge

data class KnowledgeCategory(
    val id: String,
    val title: String,
    val titleAr: String,
    val description: String,
    val descriptionAr: String,
    val icon: String,
    val articleCount: Int = 0
)

data class KnowledgeArticle(
    val id: String,
    val categoryId: String,
    val title: String,
    val titleAr: String = "",
    val summary: String,
    val summaryAr: String = "",
    val content: List<ContentBlock>,
    val readTimeMinutes: Int,
    val isArabic: Boolean = false,
    val references: List<String> = emptyList()
)

sealed class ContentBlock {
    data class Header(val text: String) : ContentBlock()
    data class Paragraph(val text: String) : ContentBlock()
    data class QuranVerse(
        val arabic: String,
        val translation: String,
        val reference: String
    ) : ContentBlock()
    data class Hadith(
        val text: String,
        val narrator: String,
        val grading: String = ""
    ) : ContentBlock()
    data class ScholarQuote(
        val text: String,
        val scholar: String,
        val source: String
    ) : ContentBlock()
    data class ScientificFact(
        val text: String,
        val source: String = ""
    ) : ContentBlock()
    data class BulletPoints(
        val title: String = "",
        val points: List<String>
    ) : ContentBlock()
    data class Warning(val text: String) : ContentBlock()
    data class Tip(val text: String) : ContentBlock()
    data class Quote(
        val text: String,
        val author: String = ""
    ) : ContentBlock()
    data class Divider(val nothing: Unit = Unit) : ContentBlock()
}