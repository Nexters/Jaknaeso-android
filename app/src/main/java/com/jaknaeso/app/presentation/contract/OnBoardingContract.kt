package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.OptionId
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyId

sealed interface OnBoardingEvent : UiEvent {
    data object GetOnboardingData : OnBoardingEvent
    data class SelectOption(val optionId: String, val surveyId: String) : OnBoardingEvent
    data object ClickFinkshButton : OnBoardingEvent
}

data class OnBoardingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val questions: List<RoundQuestion> = emptyList(),
    val answersForSubmission: MutableMap<SurveyId, OptionId> = mutableMapOf()//key값을 surveyId로 갖는 해시로 수정하기.
) : UiState

sealed interface OnBoardingEffect : UiEffect {
    data object NavigateToHome : OnBoardingEffect
}
