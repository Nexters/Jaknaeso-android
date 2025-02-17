package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.usecase.GetLatestCharacterUseCase
import com.jaknaeso.app.domain.usecase.GetRoundsUseCase
import com.jaknaeso.app.presentation.contract.HomeEffect
import com.jaknaeso.app.presentation.contract.HomeEvent
import com.jaknaeso.app.presentation.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getRoundsUseCase: GetRoundsUseCase,
    private val getLatestCharacterUseCase: GetLatestCharacterUseCase
) :
    BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRounds()
            //getLatestCharacter()
        }
    }

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickRound -> handleQuestionState(event.questionState, event.questionIndex.toString())
            HomeEvent.TodayRoundButton -> setEffect(
                HomeEffect.NavigateToRound(
                    currentState.bundleId.toString(),
                    currentState.remainRounds.toString()
                )
            )
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
                            isEnabledTodayRoundButton = !it.data.isTodayRoundCompleted,
                            remainRounds = it.data.remainRound
                        )
                    }
                }
            }

        }
    }

    suspend fun getLatestCharacter() {
        getLatestCharacterUseCase().asResult().collect {
            when (it) {
                is Result.Error -> {
                    Log.e("HomeViewmodel", "getLatestCharacter: ${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> {
                    setState {
                        copy(
                            characterNo = it.data.characterNo,
                            characterType = it.data.characterType,
                            lottieRawFile = it.data.lottieRawFile
                        )
                    }
                }
            }
        }
    }

    fun handleQuestionState(questionState: QuestionState, questionIndex: String) {
        when (questionState) {
            QuestionState.FUTURE -> setEffect(HomeEffect.ShowSnackbar)
            QuestionState.TODAY_LOCKED -> setEffect(
                HomeEffect.NavigateToRound(
                    currentState.bundleId.toString(),
                    currentState.remainRounds.toString()
                )
            )

            QuestionState.PAST -> setEffect(
                HomeEffect.NavigateToRoundHistory(
                    currentState.bundleId.toString(),
                    questionIndex
                )
            )

            QuestionState.TODAY_COMPLETED -> setEffect(
                HomeEffect.NavigateToRoundHistory(
                    currentState.bundleId.toString(),
                    questionIndex
                )
            )
        }
    }
}
