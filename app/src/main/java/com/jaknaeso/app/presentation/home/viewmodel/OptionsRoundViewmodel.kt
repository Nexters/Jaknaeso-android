package com.jaknaeso.app.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.GetBalanceQuestionUseCase
import com.jaknaeso.app.presentation.common.BaseViewModel
import com.jaknaeso.app.presentation.home.contract.OptionsRoundEffect
import com.jaknaeso.app.presentation.home.contract.OptionsRoundEvent
import com.jaknaeso.app.presentation.home.contract.OptionsRoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsRoundViewmodel @Inject constructor(private val getBalanceQuestionUseCase: GetBalanceQuestionUseCase) :
    BaseViewModel<OptionsRoundEvent, OptionsRoundState, OptionsRoundEffect>() {
    override fun createInitialState(): OptionsRoundState {
        return OptionsRoundState()
    }

    override fun handleEvent(event: OptionsRoundEvent) {
        when (event) {
            is OptionsRoundEvent.GetBalanceQuestion -> {
                viewModelScope.launch {
                    val balanceQuestion = getBalanceQuestionUseCase(event.roundIndex.toInt())
                    setState { copy(balanceQuestion) }
                }
            }

            OptionsRoundEvent.SelectOption -> {

            }
        }
    }

}
