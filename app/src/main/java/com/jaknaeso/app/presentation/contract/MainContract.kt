package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.presentation.navigation.Route

sealed interface MainEvent : UiEvent

data class MainState(
    val initialRoute: String = Route.Login.name
) : UiState

sealed interface MainEffect : UiEffect
