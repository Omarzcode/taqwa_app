package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.data.database.MemoryEntry
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class UrgeFlowStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): UrgeFlowState {
        val promises by viewModel.promises.collectAsState()
        val whyQuitting by viewModel.whyQuitting.collectAsState()
        val duas by viewModel.duas.collectAsState()
        val personalReminders by viewModel.personalReminders.collectAsState()
        val hasPromiseContent by viewModel.hasPromiseContent.collectAsState()
        val situationContext by viewModel.currentSituationContext.collectAsState()
        val selectedFeelings by viewModel.currentFeelings.collectAsState()
        val selectedNeeds by viewModel.currentRealNeed.collectAsState()
        val selectedAlternatives by viewModel.currentAlternative.collectAsState()
        val urgeStrength by viewModel.currentUrgeStrength.collectAsState()
        val freeText by viewModel.currentFreeText.collectAsState()
        val urgesDefeated by viewModel.urgesDefeatedCount.collectAsState(initial = 0)

        return UrgeFlowState(
            promises = promises,
            whyQuitting = whyQuitting,
            duas = duas,
            personalReminders = personalReminders,
            hasPromiseContent = hasPromiseContent,
            situationContext = situationContext,
            selectedFeelings = selectedFeelings,
            selectedNeeds = selectedNeeds,
            selectedAlternatives = selectedAlternatives,
            urgeStrength = urgeStrength,
            freeText = freeText,
            urgesDefeated = urgesDefeated
        )
    }

    fun updateSituationContext(value: String) = viewModel.updateSituationContext(value)
    fun toggleFeeling(feeling: String) = viewModel.toggleFeeling(feeling)
    fun toggleRealNeed(need: String) = viewModel.toggleRealNeed(need)
    fun toggleAlternative(alt: String) = viewModel.toggleAlternative(alt)
    fun updateUrgeStrength(strength: Int) = viewModel.updateUrgeStrength(strength)
    fun updateFreeText(text: String) = viewModel.updateFreeText(text)
    fun saveEntry() = viewModel.saveEntry()
    fun resetCurrentEntry() = viewModel.resetCurrentEntry()
}

data class UrgeFlowState(
    val promises: List<String>,
    val whyQuitting: String,
    val duas: List<String>,
    val personalReminders: List<String>,
    val hasPromiseContent: Boolean,
    val situationContext: String,
    val selectedFeelings: List<String>,
    val selectedNeeds: List<String>,
    val selectedAlternatives: List<String>,
    val urgeStrength: Int,
    val freeText: String,
    val urgesDefeated: Int
)