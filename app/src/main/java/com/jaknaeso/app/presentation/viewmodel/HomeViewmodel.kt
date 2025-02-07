package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.roomDB.RoundDatabase
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
                updateNextQuestion()
            }
        }
    }

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickRound -> {
                val nextRound = uiState.value.nextRound
                if (nextRound != null) {
                    setEffect(HomeEffect.NavigateToRound(nextRound.roundIndex.toString()))
                }
            }
        }
    }

    suspend fun getRounds() {
        getRoundsUseCase().collect { rounds ->
            setState { copy(rounds) }
        }
    }

    fun updateNextQuestion() {
        setState { copy(nextRound = uiState.value.rounds?.find { !it.isLocked && !it.isCompleted }) }
    }
}
