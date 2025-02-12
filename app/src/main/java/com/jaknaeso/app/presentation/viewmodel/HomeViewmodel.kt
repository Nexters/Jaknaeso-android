package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.roomDB.RoundDatabase
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.usecase.GetRoundsUseCase
import com.jaknaeso.app.presentation.contract.HomeEffect
import com.jaknaeso.app.presentation.contract.HomeEvent
import com.jaknaeso.app.presentation.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getRoundsUseCase: GetRoundsUseCase,
    private val databaseCallback: RoundDatabase.DatabaseCallback
) :
    BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        viewModelScope.launch {
            databaseCallback.isDatabaseInitialized.collect { isInitialized ->
                getRounds()
            }
        }
    }

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickRound -> handleQuestionState(event.questionState)

            HomeEvent.TodayRoundButton -> {}
        }
    }

    suspend fun getRounds() {
        getRoundsUseCase().collect { rounds ->
            setState {
                copy(
                    bundleId = rounds?.bundleId,
                    rounds = rounds?.rounds,
                    isEnabledTodayRoundButton = rounds?.isTodayRoundCompleted ?: false
                )
            }
        }
    }

    fun handleQuestionState(questionState: QuestionState) {
        when (questionState) {
            QuestionState.FUTURE -> setEffect(HomeEffect.ShowSnackbar)
            QuestionState.TODAY_LOCKED -> setEffect(HomeEffect.NavigateToRound(currentState.bundleId.toString()))
            QuestionState.PAST -> setEffect(HomeEffect.NavigateToRoundHistory)
            QuestionState.TODAY_COMPLETED -> setEffect(HomeEffect.NavigateToRoundHistory)
        }
    }
}
