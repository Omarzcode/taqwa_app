package com.taqwa.journal.core.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.taqwa.journal.core.theme.AccentRed
import com.taqwa.journal.core.theme.BackgroundCard
import com.taqwa.journal.core.theme.VanillaCustard

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
