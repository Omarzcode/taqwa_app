package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.core.viewmodel.JournalViewModel

class UrgeFlowStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): UrgeFlowState {
        val promises = viewModel.promises.collectAsState().value
        val whyQuitting = viewModel.whyQuitting.collectAsState().value
        val duas = viewModel.duas.collectAsState().value
        val personalReminders = viewModel.personalReminders.collectAsState().value
        val hasPromiseContent = viewModel.hasPromiseContent.collectAsState().value
        val situationContext = viewModel.currentSituationContext.collectAsState().value
        val selectedFeelings = viewModel.currentFeelings.collectAsState().value
        val selectedNeeds = viewModel.currentRealNeed.collectAsState().value
        val selectedAlternatives = viewModel.currentAlternative.collectAsState().value
        val urgeStrength = viewModel.currentUrgeStrength.collectAsState().value
        val freeText = viewModel.currentFreeText.collectAsState().value
        val urgesDefeated = viewModel.urgesDefeatedCount.collectAsState(initial = 0).value

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
