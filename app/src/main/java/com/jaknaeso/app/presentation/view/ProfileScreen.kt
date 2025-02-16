package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.ProfileEffect
import com.jaknaeso.app.presentation.contract.ProfileEvent
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar
import com.jaknaeso.app.presentation.navigation.NO_BUNDLE_ID
import com.jaknaeso.app.presentation.navigation.NO_SURVEY_INDEX
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.viewmodel.ProfileViewmodel

@Composable
fun ProfileScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToReport: (bundleIndex: String, surveyIndex:String) -> Unit,
    viewModel: ProfileViewmodel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ProfileEffect.NavigateToLogin -> navigateToLogin()
                ProfileEffect.NavigateToPolicy -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = { navigateToHome() },
                navigateToReport = { navigateToReport(NO_BUNDLE_ID, NO_SURVEY_INDEX) },
                navigateToProfile = { },
                currentRoute = Route.Profile
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(color = Color.White).fillMaxSize(1f)
                    .padding(vertical = 54.dp).padding(paddingValues),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
            ) {
                Text("이름")
                Text("메일")
                Text(
                    "회원 탈퇴",
                    style = TextStyles.subTitle05,
                    color = ColorPalette.Neautral600,
                    modifier = Modifier.clickable { viewModel.handleEvent(ProfileEvent.DeleteMember) })
            }
        }
    )
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen({}, {}, {bundleIndex, surveyIndex ->  })
}
