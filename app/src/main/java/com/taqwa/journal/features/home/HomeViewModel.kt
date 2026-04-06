package com.taqwa.journal.features.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.urgeflow.data.UrgeFlowRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    application: Application,
    private val urgeFlowRepository: UrgeFlowRepository
) : AndroidViewModel(application) {

    val urgesDefeatedCount: Flow<Int> = urgeFlowRepository.urgesDefeatedCount
}
