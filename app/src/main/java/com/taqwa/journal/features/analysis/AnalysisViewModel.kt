package com.taqwa.journal.features.analysis

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.urgeflow.data.UrgeFlowRepository
import com.taqwa.journal.features.checkin.data.CheckInRepository
import kotlinx.coroutines.flow.Flow

class AnalysisViewModel(
    application: Application,
    private val urgeFlowRepository: UrgeFlowRepository,
    private val checkInRepository: CheckInRepository
) : AndroidViewModel(application) {

    fun getAllFeelings(): Flow<List<String>> {
        return urgeFlowRepository.getAllFeelings()
    }

    fun getAllTimestamps(): Flow<List<Long>> {
        return urgeFlowRepository.getAllTimestamps()
    }

    fun getRecentCheckIns() = checkInRepository.recentCheckIns
}
