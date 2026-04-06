package com.taqwa.journal.features.promise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.promise.data.PromiseManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PromiseViewModel(
    application: Application,
    val promiseManager: PromiseManager
) : AndroidViewModel(application) {

    private val _promises = MutableStateFlow<List<String>>(emptyList())
    val promises: StateFlow<List<String>> = _promises.asStateFlow()

    private val _whyQuitting = MutableStateFlow("")
    val whyQuitting: StateFlow<String> = _whyQuitting.asStateFlow()

    private val _duas = MutableStateFlow<List<String>>(emptyList())
    val duas: StateFlow<List<String>> = _duas.asStateFlow()

    private val _personalReminders = MutableStateFlow<List<String>>(emptyList())
    val personalReminders: StateFlow<List<String>> = _personalReminders.asStateFlow()

    private val _hasPromiseContent = MutableStateFlow(false)
    val hasPromiseContent: StateFlow<Boolean> = _hasPromiseContent.asStateFlow()

    init {
        refreshPromiseData()
    }

    fun refreshPromiseData() {
        _promises.value = promiseManager.getPromises()
        _whyQuitting.value = promiseManager.getWhyQuitting()
        _duas.value = promiseManager.getDuas()
        _personalReminders.value = promiseManager.getReminders()
        _hasPromiseContent.value = promiseManager.hasContent()
    }

    fun addPromise(promise: String) {
        promiseManager.addPromise(promise)
        refreshPromiseData()
    }

    fun deletePromise(index: Int) {
        promiseManager.deletePromise(index)
        refreshPromiseData()
    }

    fun setWhyQuitting(why: String) {
        promiseManager.setWhyQuitting(why)
        refreshPromiseData()
    }

    fun addDua(dua: String) {
        promiseManager.addDua(dua)
        refreshPromiseData()
    }

    fun deleteDua(index: Int) {
        promiseManager.deleteDua(index)
        refreshPromiseData()
    }

    fun addReminder(reminder: String) {
        promiseManager.addReminder(reminder)
        refreshPromiseData()
    }

    fun deleteReminder(index: Int) {
        promiseManager.deleteReminder(index)
        refreshPromiseData()
    }
}
