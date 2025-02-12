package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round

sealed interface HomeEvent : UiEvent {
    data class ClickRound(val questionState: QuestionState) : HomeEvent
    data object TodayRoundButton : HomeEvent
}

data class HomeState(
    val rounds: List<Round>? = null,
    val bundleId: Int? = null,
    val isEnabledTodayRoundButton: Boolean = true
) : UiState

sealed interface HomeEffect : UiEffect {
    data class NavigateToRound(val bundleIndex: String) : HomeEffect
    data object NavigateToRoundHistory : HomeEffect
    data object ShowSnackbar : HomeEffect
}
