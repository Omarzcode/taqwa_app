package com.taqwa.journal.ui.screens.home

sealed interface HomeAction {
    data object StartUrgeFlow : HomeAction
    data object OpenQuickCatch : HomeAction
    data object OpenMorningCheckIn : HomeAction
    data object OpenEveningCheckIn : HomeAction
    data object DismissMilestone : HomeAction
}