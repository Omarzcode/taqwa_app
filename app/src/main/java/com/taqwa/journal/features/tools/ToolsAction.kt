package com.taqwa.journal.features.tools

sealed class ToolsAction {
    data object OpenShieldPlans : ToolsAction()
    data object OpenMemoryBank : ToolsAction()
    data object OpenPromiseWall : ToolsAction()
    data object OpenMorningCheckIn : ToolsAction()
    data object OpenPastEntries : ToolsAction()
    data object OpenCalendar : ToolsAction()
    data object OpenPatternAnalysis : ToolsAction()
    data object OpenRelapseHistory : ToolsAction()
    data object OpenResetStreak : ToolsAction()
}
