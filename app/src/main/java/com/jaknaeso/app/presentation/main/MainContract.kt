package com.jaknaeso.app.presentation.main

import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState
import com.jaknaeso.app.presentation.navigation.Route

sealed interface MainEvent : UiEvent

data class MainState(
    val initialRoute: String = Route.Login.name
) : UiState

sealed interface MainEffect : UiEffect
