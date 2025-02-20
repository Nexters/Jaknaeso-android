package com.jaknaeso.app.presentation.view

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.ClickableText
import com.jaknaeso.app.designSystem.component.LoopyAssistChip
import com.jaknaeso.app.designSystem.component.LoopyDialog
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.ProfileEffect
import com.jaknaeso.app.presentation.contract.ProfileEvent
import com.jaknaeso.app.presentation.navigation.*
import com.jaknaeso.app.presentation.viewmodel.ProfileViewmodel

@Composable
fun ProfileScreen(
    navigateToPolicy:()->Unit,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToReport: (bundleIndex: String, surveyIndex: String, characterId: String) -> Unit,
    viewModel: ProfileViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(ProfileEvent.GetMemberInfo)
        viewModel.effects.collect { effect ->
            when (effect) {
                ProfileEffect.NavigateToLogin -> navigateToLogin()
                ProfileEffect.NavigateToPolicy -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White)
            .windowInsetsPadding(WindowInsets.statusBars),
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            ProfileContent(
                paddingValues,
                name = uiState.name,
                email = uiState.email,
                onClickLogout = { viewModel.handleEvent(ProfileEvent.LogOutMember) },
                onClickDeleteMember = {
                    showDialog = true
                },
                bottomNavigation = {
                    LoopyBottomNavBar(
                        navigateToHome = { navigateToHome() },
                        navigateToReport = { navigateToReport(NO_BUNDLE_ID, NO_SURVEY_INDEX, NO_CHARACTER_ID) },
                        navigateToProfile = { },
                        currentRoute = Route.Profile
                    )
                },
                onClickPolicy = {navigateToPolicy()}
            )
            LoopyDialog(
                visible = showDialog,
                onDismiss = { showDialog = false }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 32.dp).padding(vertical = 36.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    androidx.compose.material3.Text("정말 탈퇴하실 건가요?", style = TextStyles.title04)
                    Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
                    androidx.compose.material3.Text(
                        "회원 탈퇴 시 지금까지 기록한 정보가\n 전부 삭제되고 복구가 불가능해요.",
                        style = TextStyles.subTitle04,
                        color = ColorPalette.Neautral700,
                        softWrap = true,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.fillMaxWidth(1f).height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                        LoopyFilledButton(
                            "더 써볼게요",
                            onClick = { showDialog = false },
                            modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp),
                            height = 47.dp
                        )
                        LoopyFilledButton(
                            "떠날래요",
                            onClick = { viewModel.handleEvent(ProfileEvent.DeleteMember) },
                            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                            filledColor = ColorPalette.Neautral200,
                            textColor = ColorPalette.Neautral600,
                            borderColor = ColorPalette.Neautral200,
                            height = 47.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    paddingValues: PaddingValues,
    name: String,
    email: String,
    onClickLogout: () -> Unit,
    onClickDeleteMember: () -> Unit,
    bottomNavigation: @Composable () -> Unit,
    onClickPolicy:()->Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    val openLicenses = remember {
        {
            launcher.launch(Intent(context, OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(1f).padding(bottom = paddingValues.calculateBottomPadding()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(top = 78.dp).padding(bottom = 60.dp).padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
            ) {
                Text(name, style = TextStyles.subTitle03, modifier = Modifier.padding(bottom = 8.dp))
                Text(email, style = TextStyles.subTitle04, color = ColorPalette.Neautral700)
            }
            Column(Modifier.padding(horizontal = 20.dp)) {
                Spacer(Modifier.fillMaxWidth(1f).height(10.dp))
                ClickableText(
                    text = "이용약관",
                    textStyle = TextStyles.subTitle03,
                    textColor = ColorPalette.Neautral800,
                    iconColor = ColorPalette.Neautral950,
                    icon = painterResource(R.drawable.ic_next),
                    onClick = {onClickPolicy()}
                )
            }
            Spacer(Modifier.fillMaxWidth(1f).height(10.dp))
            Spacer(modifier = Modifier.background(color = ColorPalette.Neautral300).fillMaxWidth(1f).height(1.dp))
            Column(Modifier.padding(horizontal = 20.dp)) {
                Spacer(Modifier.fillMaxWidth(1f).height(10.dp))
                ClickableText(
                    text = "오픈소스 라이센스",
                    textStyle = TextStyles.subTitle03,
                    textColor = ColorPalette.Neautral800,
                    iconColor = ColorPalette.Neautral950,
                    icon = painterResource(R.drawable.ic_next),
                    onClick = {openLicenses()}
                )
            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(1f).padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "로그아웃",
                    style = TextStyles.subTitle05,
                    color = ColorPalette.Neautral600,
                    modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClickLogout() }
                        .padding(end = 24.dp)
                )
                Text(
                    "회원 탈퇴",
                    style = TextStyles.subTitle05,
                    color = ColorPalette.Neautral600,
                    modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClickDeleteMember() }
                )
            }
            bottomNavigation()
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = { },
                navigateToReport = { },
                navigateToProfile = { },
                currentRoute = Route.Profile
            )
        },
        content = { paddingValues ->
            ProfileContent(paddingValues, "칠가이", "chill777@gmail.com", {}, {}, {},{})
        }
    )

}
