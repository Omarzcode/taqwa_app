package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class PromiseWallStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): PromiseWallScreenState {
        val promises by viewModel.promises.collectAsState()
        val whyQuitting by viewModel.whyQuitting.collectAsState()
        val duas by viewModel.duas.collectAsState()
        val personalReminders by viewModel.personalReminders.collectAsState()

        return PromiseWallScreenState(
            promises = promises,
            whyQuitting = whyQuitting,
            duas = duas,
            personalReminders = personalReminders
        )
    }

    fun refreshPromiseData() = viewModel.refreshPromiseData()
    fun addPromise(promise: String) = viewModel.addPromise(promise)
    fun deletePromise(index: Int) = viewModel.deletePromise(index)
    fun setWhyQuitting(why: String) = viewModel.setWhyQuitting(why)
    fun addDua(dua: String) = viewModel.addDua(dua)
    fun deleteDua(index: Int) = viewModel.deleteDua(index)
    fun addReminder(reminder: String) = viewModel.addReminder(reminder)
    fun deleteReminder(index: Int) = viewModel.deleteReminder(index)
}

data class PromiseWallScreenState(
    val promises: List<String>,
    val whyQuitting: String,
    val duas: List<String>,
    val personalReminders: List<String>
)