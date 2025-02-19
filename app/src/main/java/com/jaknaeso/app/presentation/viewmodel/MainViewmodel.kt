package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.CheckLoginedUserUseCase
import com.jaknaeso.app.presentation.contract.MainEffect
import com.jaknaeso.app.presentation.contract.MainEvent
import com.jaknaeso.app.presentation.contract.MainState
import com.jaknaeso.app.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val checkLoginedUserUseCase: CheckLoginedUserUseCase,
) :
    BaseViewModel<MainEvent, MainState, MainEffect>() {
    override fun createInitialState(): MainState {
        return MainState()
    }

    override fun handleEvent(event: MainEvent) {
    }

    fun branchInitialRoute() {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkLoginedUserUseCase.isLoginedUser()) {
                setState { copy(isInitialRoutingOngoing = false, initialRoute = Route.Home.name) }
            } else {
                setState { copy(isInitialRoutingOngoing = false, initialRoute = Route.Login.name) }
            }
        }
    }
}
