package com.jaknaeso.app.presentation.home.contract

import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState


sealed interface OptionsRoundEvent : UiEvent {
    data object SelectOption : OptionsRoundEvent
}

data object OptionsRoundState : UiState

sealed interface OptionsRoundEffect : UiEffect {
    data object NavigateToHome : OptionsRoundEffect
}
