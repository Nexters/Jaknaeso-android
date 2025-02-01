package com.jaknaeso.app.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(navigateToHome: () -> Unit, viewmodel: LoginViewmodel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewmodel.effects.collect { effects ->
            when (effects) {
                LoginEffect.NavigateToHome -> navigateToHome()
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f).height(70.dp).background(color = Color.Cyan)
                .clickable { viewmodel.handleEvent(LoginEvent.ClickKakaoLogin) }) {
            Text(text = "카카오 로그인")
        }

    }
}
