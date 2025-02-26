package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.usecase.GetOnBoardingQuestionUseCase
import com.jaknaeso.app.domain.usecase.PostOnBoardingRoundUseCase
import com.jaknaeso.app.presentation.contract.OnBoardingEffect
import com.jaknaeso.app.presentation.contract.OnBoardingEvent
import com.jaknaeso.app.presentation.contract.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewmodel @Inject constructor(
    private val getOnBoardingQuestionUseCase: GetOnBoardingQuestionUseCase,
    private val postOnBoardingRoundUseCase: PostOnBoardingRoundUseCase,
    private val loginRepository: LoginRepository
) : BaseViewModel<OnBoardingEvent, OnBoardingState, OnBoardingEffect>() {
    val ONBOARD_COMPLETED_PAGE = 1

    override fun createInitialState(): OnBoardingState {
        return OnBoardingState()
    }

    override fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.GetOnboardingData -> getOnBoardingQuestion()
            is OnBoardingEvent.SelectOption -> updateAnswers(
                surveyId = event.surveyId,
                optionId = event.optionId,
                page = event.page,
                optionIndex = event.optionIndex
            )

            OnBoardingEvent.SubmitResultButton -> postAnswers()
            OnBoardingEvent.ClickReload -> {
                setState {
                    copy(
                        isLoading = true,
                        isError = false,
                        questions = emptyList(),
                        pageCount = 0,
                        answersForSubmission = mutableMapOf(),
                        answersForPresentation = mutableListOf()
                    )
                }
                getOnBoardingQuestion()
            }
        }
    }

    fun getOnBoardingQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            getOnBoardingQuestionUseCase().asResult().collect {
                when (it) {
                    is Result.Error -> {
                        if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                            setEffect(OnBoardingEffect.NavigateToLogin)
                        }
                        setState { copy(isLoading = false, isError = true) }
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        setState {
                            copy(
                                isLoading = false,
                                questions = it.data,
                                pageCount = it.data.size + ONBOARD_COMPLETED_PAGE,
                                answersForPresentation = MutableList(it.data.size) { 2 } // 슬라이더의 '보통' 위치의 인덱스 값으로 초기화
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateAnswers(surveyId: String, optionId: String, page: Int, optionIndex: Int) {
        currentState.answersForSubmission[surveyId] = optionId
        currentState.answersForPresentation[page] = optionIndex
        setState {
            copy(
                answersForSubmission = currentState.answersForSubmission,
                answersForPresentation = currentState.answersForPresentation
            )
        }
    }

    fun postAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            postOnBoardingRoundUseCase(currentState.answersForSubmission.toMap()).asResult().collect {
                when (it) {
                    is Result.Error -> {
                        if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                            setEffect(OnBoardingEffect.NavigateToLogin)
                        }
                        setState { copy(isLoading = false, isError = true) }
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        loginRepository.saveIsOnBoardingCompleted(true)
                    }
                }
            }
        }
    }

}
