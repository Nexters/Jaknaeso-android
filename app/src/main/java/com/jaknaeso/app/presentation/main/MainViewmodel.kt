package com.jaknaeso.app.presentation.main

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.CheckLoginedUserUseCase
import com.jaknaeso.app.presentation.common.BaseViewModel
import com.jaknaeso.app.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(private val checkLoginedUserUseCase: CheckLoginedUserUseCase) :
    BaseViewModel<MainEvent, MainState, MainEffect>() {

    override fun createInitialState(): MainState {
        branchInitialRoute()
        return MainState()
    }

    override fun handleEvent(event: MainEvent) {
    }

    fun branchInitialRoute() {
        viewModelScope.launch {
            if (checkLoginedUserUseCase.isLoginedUser()) {
                setState { copy(isLoginedUser = isLoginedUser, initialRoute = Route.Home.name) }
            } else {
                setState { copy(isLoginedUser = isLoginedUser, initialRoute = Route.Login.name) }
            }
        }
    }

}
