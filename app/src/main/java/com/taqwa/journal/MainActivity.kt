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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.taqwa.journal.widget.WidgetUpdater

class MainActivity : ComponentActivity() {

    private lateinit var notificationPreferences: NotificationPreferences
    private lateinit var notificationScheduler: NotificationScheduler

    companion object {
        const val EXTRA_NAVIGATE_TO = "navigate_to"
    }

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

        notificationPreferences = NotificationPreferences(this)
        notificationScheduler = NotificationScheduler(this)

        val taqwaNotificationManager = TaqwaNotificationManager(this)
        taqwaNotificationManager.createNotificationChannels()
        Log.d("TaqwaNotif", "Notification channels created")

        notificationPreferences.updateLastAppOpen()

        if (hasNotificationPermission()) {
            Log.d("TaqwaNotif", "Permission already granted, scheduling notifications")
            scheduleNotifications()
        } else {
            Log.d("TaqwaNotif", "Requesting notification permission")
            requestNotificationPermission()
        }

        // Refresh all widgets on every app open
        WidgetUpdater.updateAllWidgets(this)
        // Ensure midnight refresh is always scheduled
        WidgetUpdater.scheduleMidnightRefresh(this)

        // Read and consume the deep link extra
        val navigateTo = intent?.getStringExtra(EXTRA_NAVIGATE_TO)
        intent?.removeExtra(EXTRA_NAVIGATE_TO)

        setContent {
            TaqwaTheme {
                val viewModel: JournalViewModel = viewModel()
                val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()

                if (isOnboardingCompleted) {
                    val navController = rememberNavController()
                    val showBottomNav = shouldShowBottomNav(navController)
                    val route = currentRoute(navController)

                    // Track if deep link was already consumed
                    var deepLinkConsumed by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        if (navigateTo != null && !deepLinkConsumed) {
                            deepLinkConsumed = true
                            when (navigateTo) {
                                Routes.MORNING_CHECK_IN -> {
                                    viewModel.loadCheckInMemory()
                                    navController.navigate(Routes.MORNING_CHECK_IN) {
                                        popUpTo(Routes.HOME)
                                    }
                                }
                                Routes.SHIELD_PLANS -> {
                                    navController.navigate(Routes.SHIELD_PLANS) {
                                        popUpTo(Routes.HOME)
                                    }
                                }
                                Routes.BREATHING -> {
                                    viewModel.resetCurrentEntry()
                                    navController.navigate(Routes.BREATHING) {
                                        popUpTo(Routes.HOME)
                                    }
                                }
                            }
                        }
                    }

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

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        if (::notificationPreferences.isInitialized) {
            notificationPreferences.updateLastAppOpen()
        }
        // Refresh widgets whenever user returns to app
        WidgetUpdater.updateAllWidgets(this)
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
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