package com.taqwa.journal.ui.navigation.sections

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.taqwa.journal.ui.navigation.Routes
import com.taqwa.journal.ui.screens.HomeScreen
import com.taqwa.journal.ui.screens.MorningCheckInScreen
import com.taqwa.journal.ui.screens.EveningCheckInScreen
import com.taqwa.journal.ui.screens.home.HomeAction
import com.taqwa.journal.ui.screens.home.HomeState
import com.taqwa.journal.ui.viewmodel.JournalViewModel

fun NavGraphBuilder.homeSection(
    navController: NavHostController,
    viewModel: JournalViewModel
) {
    composable(Routes.HOME) {
        val urgesDefeated by viewModel.urgesDefeatedCount.collectAsState(initial = 0)
        val currentStreak by viewModel.currentStreak.collectAsState()
        val longestStreak by viewModel.longestStreak.collectAsState()
        val streakStatus by viewModel.streakStatus.collectAsState()
        val milestoneMessage by viewModel.milestoneMessage.collectAsState()
        val todayCheckInDone by viewModel.todayCheckInDone.collectAsState()
        val checkInLoaded by viewModel.checkInLoaded.collectAsState()
        val todayEveningCheckInDone by viewModel.todayEveningCheckInDone.collectAsState()
        val eveningCheckInLoaded by viewModel.eveningCheckInLoaded.collectAsState()
        val totalRelapses by viewModel.totalRelapses.collectAsState()
        val dailyAyah by viewModel.dailyAyah.collectAsState()
        val memoryCount by viewModel.memoryCount.collectAsState(initial = 0)

        LaunchedEffect(Unit) {
            viewModel.refreshStreakData()
            viewModel.refreshPromiseData()
            viewModel.refreshDailyAyah()
            viewModel.checkTodayCheckIn()
            viewModel.checkTodayEveningCheckIn()
        }

        val homeState = HomeState(
            urgesDefeated = urgesDefeated,
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            streakStatus = streakStatus,
            milestoneMessage = milestoneMessage,
            todayCheckInDone = todayCheckInDone,
            checkInLoaded = checkInLoaded,
            todayEveningCheckInDone = todayEveningCheckInDone,
            eveningCheckInLoaded = eveningCheckInLoaded,
            totalRelapses = totalRelapses,
            dailyAyah = dailyAyah,
            memoryCount = memoryCount
        )

        HomeScreen(
            state = homeState,
            onAction = { action ->
                when (action) {
                    HomeAction.StartUrgeFlow -> {
                        viewModel.resetCurrentEntry()
                        navController.navigate(Routes.BREATHING)
                    }
                    HomeAction.OpenQuickCatch -> {
                        viewModel.loadQuickCatchData()
                        navController.navigate(Routes.QUICK_CATCH)
                    }
                    HomeAction.OpenMorningCheckIn -> {
                        viewModel.loadCheckInMemory()
                        navController.navigate(Routes.MORNING_CHECK_IN)
                    }
                    HomeAction.OpenEveningCheckIn -> {
                        viewModel.loadEveningCheckInData()
                        navController.navigate(Routes.EVENING_CHECK_IN)
                    }
                    HomeAction.DismissMilestone -> {
                        viewModel.dismissMilestone()
                    }
                }
            }
        )
    }

    composable(Routes.MORNING_CHECK_IN) {
        val currentStreak by viewModel.currentStreak.collectAsState()
        val dailyAyah by viewModel.dailyAyah.collectAsState()
        val checkInMemory by viewModel.checkInMemory.collectAsState()
        val yesterdayCheckIn by viewModel.yesterdayCheckIn.collectAsState()
        val todayCheckIn by viewModel.todayCheckIn.collectAsState()
        val todayCheckInDone by viewModel.todayCheckInDone.collectAsState()

        MorningCheckInScreen(
            currentStreak = currentStreak,
            dailyAyah = dailyAyah,
            memoryBankEntry = checkInMemory,
            yesterdayCheckIn = yesterdayCheckIn,
            todayCheckIn = todayCheckIn,
            alreadyCheckedIn = todayCheckInDone,
            onComplete = { mood, riskLevel, intention, sleepQuality, gratitude, yesterdayFollowed ->
                viewModel.saveCheckIn(
                    mood = mood,
                    riskLevel = riskLevel,
                    intention = intention,
                    sleepQuality = sleepQuality,
                    gratitude = gratitude,
                    yesterdayFollowed = yesterdayFollowed
                )
            },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Routes.EVENING_CHECK_IN) {
        val currentStreak by viewModel.currentStreak.collectAsState()
        val todayMorningCheckIn by viewModel.todayCheckIn.collectAsState()
        val todayEveningCheckIn by viewModel.todayEveningCheckIn.collectAsState()
        val todayEveningCheckInDone by viewModel.todayEveningCheckInDone.collectAsState()

        EveningCheckInScreen(
            currentStreak = currentStreak,
            todayMorningCheckIn = todayMorningCheckIn,
            todayEveningCheckIn = todayEveningCheckIn,
            alreadyCheckedIn = todayEveningCheckInDone,
            onComplete = { intentionFollowed, intentionNote, prayedFive, morningAdhkar,
                           eveningAdhkar, readQuran, madeIstighfar, loweredGaze,
                           hardestMoment, hardestTrigger, wins, tomorrowConcern ->
                viewModel.saveEveningCheckIn(
                    intentionFollowed = intentionFollowed,
                    intentionNote = intentionNote,
                    prayedFive = prayedFive,
                    morningAdhkar = morningAdhkar,
                    eveningAdhkar = eveningAdhkar,
                    readQuran = readQuran,
                    madeIstighfar = madeIstighfar,
                    loweredGaze = loweredGaze,
                    hardestMoment = hardestMoment,
                    hardestTrigger = hardestTrigger,
                    wins = wins,
                    tomorrowConcern = tomorrowConcern
                )
            },
            onBack = { navController.popBackStack() }
        )
    }
}