package com.taqwa.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.taqwa.journal.ui.navigation.TaqwaNavGraph
import com.taqwa.journal.ui.screens.OnboardingScreen
import com.taqwa.journal.ui.theme.TaqwaTheme
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaqwaTheme {
                val viewModel: JournalViewModel = viewModel()
                val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()

                if (isOnboardingCompleted) {
                    val navController = rememberNavController()
                    TaqwaNavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                } else {
                    OnboardingScreen(
                        onComplete = { whyQuitting, firstPromise, firstDua ->
                            viewModel.completeOnboarding(whyQuitting, firstPromise, firstDua)
                        }
                    )
                }
            }
        }
    }
}