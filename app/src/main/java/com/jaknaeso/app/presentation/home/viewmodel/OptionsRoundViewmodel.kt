package com.jaknaeso.app.presentation.home.viewmodel

import com.jaknaeso.app.presentation.common.BaseViewModel
import com.jaknaeso.app.presentation.home.contract.OptionsRoundEffect
import com.jaknaeso.app.presentation.home.contract.OptionsRoundEvent
import com.jaknaeso.app.presentation.home.contract.OptionsRoundState

class OptionsRoundViewmodel: BaseViewModel<OptionsRoundEvent, OptionsRoundState, OptionsRoundEffect>() {
    override fun createInitialState(): OptionsRoundState {
        return OptionsRoundState
    }

    override fun handleEvent(event: OptionsRoundEvent) {
        when(event){
            OptionsRoundEvent.SelectOption ->
        }
    }
}
