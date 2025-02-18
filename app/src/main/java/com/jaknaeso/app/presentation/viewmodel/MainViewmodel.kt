package com.jaknaeso.app.presentation.viewmodel

import com.jaknaeso.app.presentation.contract.MainEffect
import com.jaknaeso.app.presentation.contract.MainEvent
import com.jaknaeso.app.presentation.contract.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor() :
    BaseViewModel<MainEvent, MainState, MainEffect>() {
    override fun createInitialState(): MainState {
        return MainState
    }

    override fun handleEvent(event: MainEvent) {
    }

}
