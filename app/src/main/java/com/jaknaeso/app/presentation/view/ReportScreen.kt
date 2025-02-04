package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.ReportEffect
import com.jaknaeso.app.presentation.contract.ReportEvent
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.viewmodel.ReportViewmodel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReportScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    viewmodel: ReportViewmodel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uistate = viewmodel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.effects.collectLatest { effect ->
            when (effect) {
                ReportEffect.NavigateToHome -> navigateToHome()
                ReportEffect.NavigateToProfile -> navigateToProfile()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = { viewmodel.handleEvent(ReportEvent.ClickHome) },
                navigateToReport = {},
                navigateToProfile = { viewmodel.handleEvent(ReportEvent.ClickProfile) },
                currentRoute = Route.Report
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(color = Color.White).fillMaxSize(1f)
                    .padding(vertical = 54.dp).padding(horizontal = 40.dp).padding(paddingValues),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "캐릭터 분석",
                    style = TextStyles.title03,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(80.dp))
                if (uistate.value.isReportExisted) {
                    Text(text = "F1멋쟁이 f1 드라이버", style = TextStyles.subTitle03, color = Color.Black)
                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight(1f).verticalScroll(scrollState)
                    ) {
                        Text(
                            text = "당신의 가치관을 분석한 결과, 멋쟁이 F1 드라이버 라는 별명이 가장 잘 어울립니다. 당신은 도전을 두려워하지 않으며, 한계를 뛰어넘는 데서 진정한 성취감을 느끼는 사람입니다. 경쟁을 피하기보다는 오히려 그 속에서 더 성장하고 발전하는 타입으로, 주어진 상황에서 최상의 결과를 만들어내는 능력을 가지고 있습니다. 위험을 감수하더라도 새로운 기회를 잡기 위해 가속 페달을 밟는 스타일이며, 정체된 환경보다는 끊임없이 변화하는 무대를 더 선호합니다.",
                            style = TextStyles.subTitle04,
                            color = ColorPalette.Neautral700
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                        Text(
                            text = "당신은 철저한 전략가이면서도 직관적인 승부사입니다. INTJ적인 치밀한 계획성과 ESTP적인 즉흥적 판단력이 공존하는 독특한 성향을 가지고 있어, 장기적인 목표를 세우면서도 기회가 찾아왔을 때는 빠른 결단력으로 행동할 줄 압니다. 계획을 중요하게 여기지만, 때로는 본능적으로 즉각적인 움직임을 보여주기도 합니다. 레이싱 트랙 위에서 치밀한 분석을 바탕으로 최적의 코스를 계산하면서도, 순간적인 판단으로 추월을 시도하는 드라이버처럼, 당신은 판을 읽고 빠르게 대응하는 능력이 탁월합니다.",
                            style = TextStyles.subTitle04,
                            color = ColorPalette.Neautral700
                        )
                    }
                } else {
                    Text(
                        text = "아직 결과가 안 나왔어요..!\n질문에 모두 답하면 알 수 있을 거에요!",
                        style = TextStyles.subTitle03,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                    Text(
                        text = "보편적 가치이론(Universal Theory of Values) 에 기반하여, 개인이 중요하게 여기는 가치관을 분석하고 해석해줘요. 이를 통해 당신이 어떤 가치를 중시하며, 어떤 동기와 신념이 행동에 영향을 미치는지에 대한 인사이트를 제공하고 싶어요. 저희 서비스는 자신을 더 깊이 이해하고, 개인적 성장과 의사결정에 도움이 되었으면 해요 :)",
                        style = TextStyles.subTitle04,
                        color = ColorPalette.Neautral700
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun ReportScreenPreview() {
    ReportScreen({}, {})
}
