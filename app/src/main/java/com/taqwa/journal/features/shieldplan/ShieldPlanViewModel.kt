package com.taqwa.journal.features.shieldplan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.taqwa.journal.features.shieldplan.data.ShieldPlan
import com.taqwa.journal.features.shieldplan.data.ShieldPlanManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShieldPlanViewModel(
    application: Application,
    val shieldPlanManager: ShieldPlanManager
) : AndroidViewModel(application) {

    private val _shieldPlans = MutableStateFlow<List<ShieldPlan>>(emptyList())
    val shieldPlans: StateFlow<List<ShieldPlan>> = _shieldPlans.asStateFlow()

    private val _editingPlan = MutableStateFlow<ShieldPlan?>(null)
    val editingPlan: StateFlow<ShieldPlan?> = _editingPlan.asStateFlow()

    init {
        refreshShieldPlans()
    }

    fun refreshShieldPlans() {
        _shieldPlans.value = shieldPlanManager.getShieldPlans()
    }

    fun setEditingPlan(plan: ShieldPlan?) {
        _editingPlan.value = plan
    }

    fun updateShieldPlan(plan: ShieldPlan) {
        shieldPlanManager.updatePlan(plan)
        refreshShieldPlans()
    }

    fun addCustomShieldPlan(
        name: String,
        emoji: String,
        description: String,
        steps: List<String>,
        note: String
    ) {
        shieldPlanManager.addCustomPlan(name, emoji, description, steps, note)
        refreshShieldPlans()
    }

    fun deleteShieldPlan(triggerId: String) {
        shieldPlanManager.deletePlan(triggerId)
        refreshShieldPlans()
    }
}
