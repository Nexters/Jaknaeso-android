package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.Round

sealed interface HomeEvent : UiEvent {
    data object ClickRound : HomeEvent
}

data class HomeState(
    val rounds: List<Round>? = null,
    val nextRound: Round? = null,
) : UiState

sealed interface HomeEffect : UiEffect {
    data class NavigateToRound(val roundIndex: String) : HomeEffect
    data object ShowSnackbar : HomeEffect
}
