package com.taqwa.journal.features.home.ui

sealed interface HomeAction {
    data object StartUrgeFlow : HomeAction
    data object OpenQuickCatch : HomeAction
    data object OpenMorningCheckIn : HomeAction
    data object DismissMilestone : HomeAction
}