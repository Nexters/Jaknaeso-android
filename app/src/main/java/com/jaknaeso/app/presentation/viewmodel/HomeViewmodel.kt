package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.usecase.GetRoundsUseCase
import com.jaknaeso.app.presentation.contract.HomeEffect
import com.jaknaeso.app.presentation.contract.HomeEvent
import com.jaknaeso.app.presentation.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(private val getRoundsUseCase: GetRoundsUseCase) :
    BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        viewModelScope.launch {
            getRounds()
        }
    }

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickRound -> handleQuestionState(event.questionState)
            HomeEvent.TodayRoundButton -> setEffect(HomeEffect.NavigateToRound(currentState.bundleId.toString()))
        }
    }

    suspend fun getRounds() {
        getRoundsUseCase().asResult().collect {
            when (it) {
                is Result.Error -> {
                    Log.e("HomeViewmodel", "getRounds: ${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> {

                    setState {
                        copy(
                            isLoading = false,
                            bundleId = it.data.bundleId,
                            wholeRounds = it.data.wholeRounds,
                            faceRound = it.data.faceRounds,
                            isEnabledTodayRoundButton = !it.data.isTodayRoundCompleted
                        )
                    }
                }
            }

        }
    }

    fun handleQuestionState(questionState: QuestionState) {
        when (questionState) {
            QuestionState.FUTURE -> setEffect(HomeEffect.ShowSnackbar)
            QuestionState.TODAY_LOCKED -> setEffect(HomeEffect.NavigateToRound(currentState.bundleId.toString()))
            QuestionState.PAST -> setEffect(HomeEffect.NavigateToRoundHistory(currentState.bundleId.toString()))
            QuestionState.TODAY_COMPLETED -> setEffect(HomeEffect.NavigateToRoundHistory(currentState.bundleId.toString()))
        }
    }
}
