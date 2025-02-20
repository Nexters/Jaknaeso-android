package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.DotIndicator
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InformationScreen(navigateToOnBoarding: () -> Unit) {
    val ONBOARD_INFO_PAGE = 4
    val pagerState = rememberPagerState(pageCount = { ONBOARD_INFO_PAGE })
    val scope = rememberCoroutineScope()
    val infoContents = listOf(
        Pair("매일 가치관을 묻는 질문에 답변하고", "나의 하루를 돌아보세요"),
        Pair("15일 동안 답변하면", "나의 가치관 캐릭터를 알 수 있어요"),
        Pair("답변에 대한 나의 생각을 적어", "그날의 나를 돌아볼 수 있어요"),
        Pair("먼저 가치관 테스트를 통해", "나의 가치관 캐릭터를 만들어 볼까요?")
    )
    val images = listOf(
        painterResource(R.drawable.onboard_slider),
        painterResource(R.drawable.onboard_characters),
        painterResource(R.drawable.onboard_enter),
        null
    )
    val lotties = listOf(null, null, null, R.raw.doing_phone)
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.White).windowInsetsPadding(WindowInsets.navigationBars)
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().background(Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f).background(Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    modifier = Modifier.fillMaxWidth(1f).wrapContentHeight().padding(paddingValues)
                        .background(Color.White)
                ) { page ->
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoContentView(
                            content1 = infoContents[page].first,
                            content2 = infoContents[page].second,
                            painter = images[page],
                            lottieRawFile = lotties[page]
                        )
                    }
                }
                if (pagerState.currentPage < ONBOARD_INFO_PAGE) { //온보딩 게임 전 인디케이터
                    Spacer(Modifier.height(40.dp))
                    DotIndicator(totalPage = ONBOARD_INFO_PAGE, pagerState.currentPage)
                }
            }
            //버튼들
            Column(modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 28.dp)) {
                LoopyFilledButton(
                    text = if (pagerState.currentPage < 3) "다음으로" else "시작하기",
                    leadingIconColor = Color.Black,
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < 3) {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            } else {
                                navigateToOnBoarding()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                )
            }
        }
    }
}

@Composable
@Preview
fun InformationPreview(){
    InformationScreen({})
}
