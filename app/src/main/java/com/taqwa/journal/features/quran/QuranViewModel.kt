package com.taqwa.journal.features.quran

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.quran.data.DailyAyah
import com.taqwa.journal.features.quran.data.DailyQuranManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuranViewModel(
    application: Application,
    private val dailyQuranManager: DailyQuranManager
) : AndroidViewModel(application) {

    private val _dailyAyah = MutableStateFlow<DailyAyah?>(null)
    val dailyAyah: StateFlow<DailyAyah?> = _dailyAyah.asStateFlow()

    init {
        refreshDailyAyah()
    }

    fun refreshDailyAyah() {
        _dailyAyah.value = dailyQuranManager.getTodaysAyah()
    }

    fun getTodaysAyah(): DailyAyah? = _dailyAyah.value
}
