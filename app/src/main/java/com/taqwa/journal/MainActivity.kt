package com.taqwa.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.taqwa.journal.ui.components.TaqwaBottomNavBar
import com.taqwa.journal.ui.navigation.*
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
                    val showBottomNav = shouldShowBottomNav(navController)
                    val route = currentRoute(navController)

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            AnimatedVisibility(
                                visible = showBottomNav,
                                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                            ) {
                                TaqwaBottomNavBar(
                                    currentRoute = route,
                                    onItemClick = { item ->
                                        navController.navigateToBottomNavItem(item)
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = innerPadding.calculateTopPadding(),
                                    bottom = innerPadding.calculateBottomPadding()
                                )
                        ) {
                            TaqwaNavGraph(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
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