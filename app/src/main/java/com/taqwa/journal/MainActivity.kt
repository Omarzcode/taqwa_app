package com.taqwa.journal

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.taqwa.journal.data.preferences.NotificationPreferences
import com.taqwa.journal.notification.NotificationScheduler
import com.taqwa.journal.notification.TaqwaNotificationManager
import com.taqwa.journal.ui.components.TaqwaBottomNavBar
import com.taqwa.journal.ui.navigation.*
import com.taqwa.journal.ui.screens.OnboardingScreen
import com.taqwa.journal.ui.theme.TaqwaTheme
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class MainActivity : ComponentActivity() {

    private lateinit var notificationPreferences: NotificationPreferences
    private lateinit var notificationScheduler: NotificationScheduler

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("TaqwaNotif", "Notification permission granted: $isGranted")
        if (isGranted) {
            scheduleNotifications()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize notification system
        notificationPreferences = NotificationPreferences(this)
        notificationScheduler = NotificationScheduler(this)

        // Create channels FIRST — must happen before any notification
        val taqwaNotificationManager = TaqwaNotificationManager(this)
        taqwaNotificationManager.createNotificationChannels()
        Log.d("TaqwaNotif", "Notification channels created")

        // Update last app open time
        notificationPreferences.updateLastAppOpen()

        // Request permission then schedule
        if (hasNotificationPermission()) {
            Log.d("TaqwaNotif", "Permission already granted, scheduling notifications")
            scheduleNotifications()
        } else {
            Log.d("TaqwaNotif", "Requesting notification permission")
            requestNotificationPermission()
        }

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

    override fun onResume() {
        super.onResume()
        if (::notificationPreferences.isInitialized) {
            notificationPreferences.updateLastAppOpen()
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Below Android 13, no runtime permission needed
            true
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun scheduleNotifications() {
        val streakManager = com.taqwa.journal.data.preferences.StreakManager(this)
        val currentStreak = streakManager.getCurrentStreak()
        notificationScheduler.scheduleAll(currentStreak)
        Log.d("TaqwaNotif", "All notifications scheduled. Streak: $currentStreak")
    }
}