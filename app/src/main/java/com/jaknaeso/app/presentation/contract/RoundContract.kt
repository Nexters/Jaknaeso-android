package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.RoundQuestion


sealed interface RoundEvent : UiEvent {
    data class GetQuestion(val bundleIndex: String) : RoundEvent
    data class SelectOption(val optionId: String) : RoundEvent
    data class SaveWord(val comment: String) : RoundEvent
    data object ClickSubmitWordButton : RoundEvent
    data object ClickPassEnterReason : RoundEvent
    data object ClickBackButton : RoundEvent
    data object OpenModal : RoundEvent
    data object CloseModal : RoundEvent
}

data class RoundState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val question: RoundQuestion? = null,
    val isBalanceRound: Boolean = true,
    val selectedOptionId: String? = null,
    val enteredComment: String = "",
    val surveyId: String? = null,
) : UiState

sealed interface RoundEffect : UiEffect {
    data object NavigateToBack : RoundEffect
    data object NavigateToBalanceRoundComplete : RoundEffect
    data object OpenModal : RoundEffect
    data object CloseModal : RoundEffect
}
