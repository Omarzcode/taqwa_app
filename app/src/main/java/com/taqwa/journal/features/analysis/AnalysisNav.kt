package com.taqwa.journal.features.analysis

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.core.viewmodel.JournalViewModel
import com.taqwa.journal.features.analysis.screens.PatternAnalysisScreen

fun NavGraphBuilder.analysisSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.PATTERN_ANALYSIS) {
        val allEntries = viewModel.allEntries.collectAsState(initial = emptyList()).value

        PatternAnalysisScreen(
            entries = allEntries,
            onBack = { navController.popBackStack() }
        )
    }
}
