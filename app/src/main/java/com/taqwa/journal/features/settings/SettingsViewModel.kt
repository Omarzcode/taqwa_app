package com.taqwa.journal.features.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taqwa.journal.core.database.TaqwaDatabase
import com.taqwa.journal.features.streak.data.StreakManager
import com.taqwa.journal.features.notifications.NotificationScheduler
import com.taqwa.journal.features.notifications.NotificationPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val streakManager: StreakManager,
    private val notificationScheduler: NotificationScheduler,
    private val notificationPreferences: NotificationPreferences
) : AndroidViewModel(application) {

    fun clearAllData() {
        viewModelScope.launch {
            val database = TaqwaDatabase.getDatabase(getApplication())
            database.clearAllTables()

            val app = getApplication<Application>()
            app.getSharedPreferences("taqwa_streak", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_promises", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_daily_quran", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_onboarding", 0).edit().clear().apply()
            app.getSharedPreferences("taqwa_shield_plans", 0).edit().clear().apply()

            notificationScheduler.cancelAll()
            notificationPreferences.clearAll()

            streakManager.startNewStreak()
        }
    }
}
