package com.taqwa.journal.ui.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.data.preferences.ShieldPlan
import com.taqwa.journal.ui.viewmodel.JournalViewModel

class ShieldPlanStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): ShieldPlanScreenState {
        val shieldPlans by viewModel.shieldPlans.collectAsState()
        val editingPlan by viewModel.editingPlan.collectAsState()

        return ShieldPlanScreenState(
            shieldPlans = shieldPlans,
            editingPlan = editingPlan
        )
    }

    fun refreshShieldPlans() = viewModel.refreshShieldPlans()
    fun setEditingPlan(plan: ShieldPlan?) = viewModel.setEditingPlan(plan)
    fun updateShieldPlan(plan: ShieldPlan) = viewModel.updateShieldPlan(plan)
    fun deleteShieldPlan(triggerId: String) = viewModel.deleteShieldPlan(triggerId)
    fun addCustomShieldPlan(
        name: String,
        emoji: String,
        description: String,
        steps: List<String>,
        note: String
    ) = viewModel.addCustomShieldPlan(name, emoji, description, steps, note)
}

data class ShieldPlanScreenState(
    val shieldPlans: List<ShieldPlan>,
    val editingPlan: ShieldPlan?
)