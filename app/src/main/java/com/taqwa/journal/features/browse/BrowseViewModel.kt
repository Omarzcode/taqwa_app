package com.taqwa.journal.features.browse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import com.taqwa.journal.features.urgeflow.data.UrgeFlowRepository
import kotlinx.coroutines.flow.Flow

class BrowseViewModel(
    application: Application,
    private val repository: UrgeFlowRepository
) : AndroidViewModel(application) {

    val allEntries: Flow<List<JournalEntry>> = repository.allEntries

    fun getEntryById(entryId: Int): Flow<JournalEntry?> {
        return repository.getEntryById(entryId)
    }

    fun deleteEntry(entry: JournalEntry) {
        android.content.ContextCompat
    }

    fun getAllFeelings(): Flow<List<String>> {
        return repository.getAllFeelings()
    }

    fun getAllTimestamps(): Flow<List<Long>> {
        return repository.getAllTimestamps()
    }
}
