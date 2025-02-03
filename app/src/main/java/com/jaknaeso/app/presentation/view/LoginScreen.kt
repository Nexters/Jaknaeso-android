package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.presentation.contract.LoginEffect
import com.jaknaeso.app.presentation.contract.LoginEvent
import com.jaknaeso.app.presentation.viewmodel.LoginViewmodel

@Composable
fun LoginScreen(navigateToHome: () -> Unit, viewmodel: LoginViewmodel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewmodel.effects.collect { effects ->
            when (effects) {
                LoginEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoopyFilledButton(
            text = "카카오로 시작하기",
            icon = painterResource(R.drawable.ic_kakao),
            iconColor = Color.Black,
            filledColor = ColorPalette.Kakao,
            onClick = { viewmodel.handleEvent(LoginEvent.ClickKakaoLogin) },
            textColor = Color.Black,
            modifier = Modifier.fillMaxWidth(1f)
        )

    }
}
