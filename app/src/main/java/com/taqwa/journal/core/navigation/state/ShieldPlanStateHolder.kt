package com.taqwa.journal.core.navigation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taqwa.journal.features.shieldplan.data.ShieldPlan
import com.taqwa.journal.core.viewmodel.JournalViewModel

class ShieldPlanStateHolder(private val viewModel: JournalViewModel) {

    @Composable
    fun collectState(): ShieldPlanScreenState {
        val shieldPlans = viewModel.shieldPlans.collectAsState().value
        val editingPlan = viewModel.editingPlan.collectAsState().value

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
