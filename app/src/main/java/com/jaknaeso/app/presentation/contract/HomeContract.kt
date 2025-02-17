package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round

sealed interface HomeEvent : UiEvent {
    data class ClickRound(val questionState: QuestionState, val questionIndex: Int) : HomeEvent
    data object TodayRoundButton : HomeEvent
}

data class HomeState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val faceRound: List<Round>? = null,
    val wholeRounds: List<Round>? = null,
    val bundleId: Int? = null,
    val isEnabledTodayRoundButton: Boolean = true,
    val remainRounds: Int = 0,
    val characterNo: String = "",
    val characterName: String = "",
    val lottieRawFile: Int? = null
) : UiState

sealed interface HomeEffect : UiEffect {
    data object NavigateToLogin : HomeEffect
    data class NavigateToRound(val bundleIndex: String, val remainingRounds: String) : HomeEffect
    data class NavigateToRoundHistory(val bundleIndex: String, val surveyIndex: String) : HomeEffect
    data object ShowSnackbar : HomeEffect
}
