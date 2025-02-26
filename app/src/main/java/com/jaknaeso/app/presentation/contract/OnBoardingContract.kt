package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.OptionId
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyId

typealias OptionIndex = Int

sealed interface OnBoardingEvent : UiEvent {
    data object GetOnboardingData : OnBoardingEvent
    data class SelectOption(val surveyId: String, val optionId: String, val page: Int, val optionIndex: Int) :
        OnBoardingEvent

    data object SubmitResultButton : OnBoardingEvent
    data object ClickReload : OnBoardingEvent
}

data class OnBoardingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val questions: List<RoundQuestion> = emptyList(),
    val pageCount: Int = 0,
    val answersForSubmission: MutableMap<SurveyId, OptionId> = mutableMapOf(),
    val answersForPresentation: MutableList<OptionIndex> = mutableListOf()
) : UiState

sealed interface OnBoardingEffect : UiEffect {
    data object NavigateToLogin : OnBoardingEffect
    data object NavigateToHome : OnBoardingEffect
}
