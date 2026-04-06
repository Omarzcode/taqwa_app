package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.core.viewmodel.JournalViewModel

class PromiseWallStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): PromiseWallScreenState {
        val promises = viewModel.promises.collectAsState().value
        val whyQuitting = viewModel.whyQuitting.collectAsState().value
        val duas = viewModel.duas.collectAsState().value
        val personalReminders = viewModel.personalReminders.collectAsState().value

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
