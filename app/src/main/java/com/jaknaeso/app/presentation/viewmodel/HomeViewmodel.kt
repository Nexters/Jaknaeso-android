package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.usecase.GetBriefLatestCharacterUseCase
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
    private val getLatestCharacterUseCase: GetBriefLatestCharacterUseCase
) :
    BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        initializeData()
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

            HomeEvent.ClickReload -> {
                setState { copy(isLoading = true, isError = false, null, null, null, null, true, 0, ",", "", null) }
                initializeData()
            }
        }
    }

    fun initializeData() {
        viewModelScope.launch(Dispatchers.IO) {
            getRounds()
            getLatestCharacter()
        }
    }

    suspend fun getRounds() {
        getRoundsUseCase().asResult().collect {
            when (it) {
                is Result.Error -> {
                    if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                        setEffect(HomeEffect.NavigateToLogin)
                    }
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
                    Log.e("HomeViewmodel", "getLatestCharacter: ${it.exception.message}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> {
                    setState {
                        copy(
                            characterNo = it.data.characterNo ?: "",
                            characterName = it.data.characterName ?: "",
                            lottieRawFile = it.data.lottieRawFile,
                            characterId = it.data.characterId
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
                    questionIndex,
                    currentState.characterId.toString()
                )
            )

            QuestionState.TODAY_COMPLETED -> setEffect(
                HomeEffect.NavigateToRoundHistory(
                    currentState.bundleId.toString(),
                    questionIndex,
                    currentState.characterId.toString()
                )
            )
        }
    }
}
