package com.taqwa.journal.features.browse

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.core.navigation.Routes
import com.taqwa.journal.features.browse.screens.PastEntriesScreen
import com.taqwa.journal.features.browse.screens.EntryDetailScreen
import com.taqwa.journal.features.browse.screens.CalendarScreen

fun NavGraphBuilder.browseSection(navController: NavHostController) {
    composable(Routes.BROWSE_MAIN) {
        PastEntriesScreen(navController = navController)
    }

    composable(Routes.PAST_ENTRIES_WITH_TYPE) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?: ""
        PastEntriesScreen(
            navController = navController,
            entryType = type
        )
    }

    composable(Routes.ENTRY_DETAIL) { backStackEntry ->
        val entryId = backStackEntry.arguments?.getString("entryId") ?: ""
        EntryDetailScreen(
            navController = navController,
            entryId = entryId
        )
    }

    composable(Routes.CALENDAR) {
        CalendarScreen(navController = navController)
    }
}
