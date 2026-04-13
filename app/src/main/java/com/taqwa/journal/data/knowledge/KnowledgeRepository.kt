package com.taqwa.journal.data.knowledge

object KnowledgeRepository {

    fun getCategories(): List<KnowledgeCategory> = listOf(
        KnowledgeCategory(
            id = "science",
            title = "The Science of Addiction",
            titleAr = "\u0639\u0644\u0645 \u0627\u0644\u0625\u062F\u0645\u0627\u0646",
            description = "How pornography hijacks your brain\u2019s reward system \u2014 dopamine, neural pathways, and the neuroscience of addiction",
            descriptionAr = "\u0643\u064A\u0641 \u064A\u062E\u0637\u0641 \u0627\u0644\u0625\u0628\u0627\u062D\u064A\u0629 \u0646\u0638\u0627\u0645 \u0627\u0644\u0645\u0643\u0627\u0641\u0623\u0629 \u0641\u064A \u0627\u0644\u062F\u0645\u0627\u063A",
            icon = "science",
            articleCount = getArticlesForCategory("science").size
        ),
        KnowledgeCategory(
            id = "islamic",
            title = "Islamic Perspective",
            titleAr = "\u0627\u0644\u0645\u0646\u0638\u0648\u0631 \u0627\u0644\u0625\u0633\u0644\u0627\u0645\u064A",
            description = "What the scholars of Ahl as-Sunnah taught about guarding the gaze, the disease of desires, and purifying the soul",
            descriptionAr = "\u0645\u0627 \u0642\u0627\u0644\u0647 \u0639\u0644\u0645\u0627\u0621 \u0623\u0647\u0644 \u0627\u0644\u0633\u0646\u0629 \u0641\u064A \u063A\u0636 \u0627\u0644\u0628\u0635\u0631 \u0648\u0645\u062C\u0627\u0647\u062F\u0629 \u0627\u0644\u0646\u0641\u0633 \u0648\u062A\u0632\u0643\u064A\u0629 \u0627\u0644\u0642\u0644\u0628",
            icon = "mosque",
            articleCount = getArticlesForCategory("islamic").size
        ),
        KnowledgeCategory(
            id = "harms",
            title = "The Harms",
            titleAr = "\u0627\u0644\u0623\u0636\u0631\u0627\u0631",
            description = "The real damage to your mind, body, soul, and relationships",
            descriptionAr = "\u0627\u0644\u0623\u0636\u0631\u0627\u0631 \u0627\u0644\u062D\u0642\u064A\u0642\u064A\u0629 \u0639\u0644\u0649 \u0627\u0644\u0639\u0642\u0644 \u0648\u0627\u0644\u062C\u0633\u062F \u0648\u0627\u0644\u0631\u0648\u062D \u0648\u0627\u0644\u0639\u0644\u0627\u0642\u0627\u062A",
            icon = "warning",
            articleCount = getArticlesForCategory("harms").size
        ),
        KnowledgeCategory(
            id = "recovery",
            title = "Recovery Science",
            titleAr = "\u0639\u0644\u0645 \u0627\u0644\u062A\u0639\u0627\u0641\u064A",
            description = "Your brain can heal \u2014 neuroplasticity, withdrawal timelines, and the path to recovery",
            descriptionAr = "\u062F\u0645\u0627\u063A\u0643 \u064A\u0633\u062A\u0637\u064A\u0639 \u0627\u0644\u0634\u0641\u0627\u0621 \u2014 \u0627\u0644\u0645\u0631\u0648\u0646\u0629 \u0627\u0644\u0639\u0635\u0628\u064A\u0629 \u0648\u0637\u0631\u064A\u0642 \u0627\u0644\u062A\u0639\u0627\u0641\u064A",
            icon = "healing",
            articleCount = getArticlesForCategory("recovery").size
        ),
        KnowledgeCategory(
            id = "motivation",
            title = "Success & Motivation",
            titleAr = "\u0627\u0644\u0646\u062C\u0627\u062D \u0648\u0627\u0644\u062A\u062D\u0641\u064A\u0632",
            description = "The rewards of patience, benefits of quitting, and fuel for your journey",
            descriptionAr = "\u062B\u0645\u0631\u0627\u062A \u0627\u0644\u0635\u0628\u0631 \u0648\u0641\u0648\u0627\u0626\u062F \u0627\u0644\u0625\u0642\u0644\u0627\u0639 \u0648\u0627\u0644\u062F\u0627\u0641\u0639 \u0644\u0644\u0627\u0633\u062A\u0645\u0631\u0627\u0631",
            icon = "star",
            articleCount = getArticlesForCategory("motivation").size
        )
    )

    fun getArticlesForCategory(categoryId: String): List<KnowledgeArticle> {
        return when (categoryId) {
            "science" -> ScienceArticles.getAll()
            "islamic" -> IslamicArticles.getAll()
            "harms" -> HarmsArticles.getAll()
            "recovery" -> RecoveryArticles.getAll()
            "motivation" -> MotivationArticles.getAll()
            else -> emptyList()
        }
    }

    fun getArticleById(articleId: String): KnowledgeArticle? {
        return getCategories().flatMap { getArticlesForCategory(it.id) }
            .find { it.id == articleId }
    }
}