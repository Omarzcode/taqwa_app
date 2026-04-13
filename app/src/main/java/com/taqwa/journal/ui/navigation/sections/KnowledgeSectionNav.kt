package com.taqwa.journal.ui.navigation.sections

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.screens.knowledge.KnowledgeArticleListScreen
import com.taqwa.journal.ui.screens.knowledge.KnowledgeArticleReaderScreen
import com.taqwa.journal.ui.screens.knowledge.KnowledgeCategoriesScreen
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.knowledgeSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.KNOWLEDGE_CATEGORIES) {
        KnowledgeCategoriesScreen(
            onBack = { navController.popBackStack() },
            onCategoryClick = { categoryId ->
                navController.navigate(Routes.knowledgeArticleList(categoryId))
            }
        )
    }

    composable(
        route = Routes.KNOWLEDGE_ARTICLE_LIST,
        arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
    ) { backStackEntry ->
        val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
        KnowledgeArticleListScreen(
            categoryId = categoryId,
            onBack = { navController.popBackStack() },
            onArticleClick = { articleId ->
                navController.navigate(Routes.knowledgeArticleReader(articleId))
            }
        )
    }

    composable(
        route = Routes.KNOWLEDGE_ARTICLE_READER,
        arguments = listOf(navArgument("articleId") { type = NavType.StringType })
    ) { backStackEntry ->
        val articleId = backStackEntry.arguments?.getString("articleId") ?: return@composable
        KnowledgeArticleReaderScreen(
            articleId = articleId,
            onBack = { navController.popBackStack() }
        )
    }
}