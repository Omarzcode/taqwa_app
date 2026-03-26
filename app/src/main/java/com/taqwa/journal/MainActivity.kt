package com.taqwa.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.taqwa.journal.ui.navigation.TaqwaNavGraph
import com.taqwa.journal.ui.theme.TaqwaTheme
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaqwaTheme {
                val navController = rememberNavController()
                val viewModel: JournalViewModel = viewModel()

                TaqwaNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}