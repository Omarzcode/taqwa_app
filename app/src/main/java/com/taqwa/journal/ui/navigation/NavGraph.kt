package com.taqwa.journal.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.taqwa.journal.ui.components.BottomNavItem
import com.taqwa.journal.ui.navigation.sections.*
import com.taqwa.journal.ui.theme.AccentRed
import com.taqwa.journal.ui.theme.BackgroundCard
import com.taqwa.journal.ui.theme.VanillaCustard
import com.taqwa.journal.ui.viewmodel.JournalViewModel

@Composable
fun shouldShowBottomNav(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute in Routes.bottomNavRoutes
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun NavHostController.navigateToBottomNavItem(item: BottomNavItem) {
    navigate(item.route) {
        popUpTo(Routes.HOME) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun FlowBackHandler(navController: NavHostController) {
    var showQuitDialog by remember { mutableStateOf(false) }
    BackHandler { showQuitDialog = true }
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("⚠️ Don't Give Up!", fontWeight = FontWeight.Bold) },
            text = {
                Text("You're in the middle of fighting an urge.\n\nThe urge WANTS you to close this app.\nDon't let it win.\n\nStay strong. Keep going.")
            },
            confirmButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("💪 Keep Going", color = VanillaCustard)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showQuitDialog = false
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }) { Text("Quit Flow", color = AccentRed) }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
fun TaqwaNavGraph(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        homeSection(navController, viewModel)
        toolsSection(navController, viewModel)
        urgeFlowSection(navController, viewModel)
        memorySection(navController, viewModel)
        shieldPlanSection(navController, viewModel)
        settingsSection(navController, viewModel)
        browseSection(navController, viewModel)
    }
}