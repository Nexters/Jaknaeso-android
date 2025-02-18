package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.Option
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyType
import com.jaknaeso.app.presentation.contract.OnBoardingEffect
import com.jaknaeso.app.presentation.contract.OnBoardingEvent
import com.jaknaeso.app.presentation.viewmodel.OnBoardingViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    viewmodel: OnBoardingViewmodel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { uiState.pageCount })
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

    LaunchedEffect(Unit) {
        viewmodel.handleEvent(OnBoardingEvent.GetOnboardingData)
        viewmodel.effects.collect { effect ->
            when (effect) {
                OnBoardingEffect.NavigateToHome -> navigateToHome()
                OnBoardingEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }
    if (uiState.isLoading) {
        Text("로딩 임시", style = TextStyles.title01)
    }
    if (uiState.isError) {
        ErrorInfoView("에러 발생", "불편을 끼쳐죄송", {}, {})
    } else {
        if (uiState.isLoading) {
            LoopyLoadingScreen()
        }
        Scaffold(modifier = Modifier.fillMaxSize().background(Color.White)) { paddingValues ->
            Column(
                Modifier.fillMaxSize().background(Color.White),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false,
                        modifier = Modifier.fillMaxWidth(1f).wrapContentHeight().padding(paddingValues)
                            .background(Color.White)
                    ) { page ->
                        if (page < viewmodel.ONBOARD_INFO_PAGE) { //온보딩 게임 전
                            Column(
                                modifier = Modifier.wrapContentHeight().padding(top=50.dp),
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
                        } else if (page < uiState.pageCount - 1) { //온보딩 게임 중
                            val index = page - viewmodel.ONBOARD_INFO_PAGE
                            val question = uiState.questions[index]
                            Column(
                                modifier = Modifier.wrapContentHeight().padding(top = 50.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OnboardingGameView(
                                    currentPage = index + 1,
                                    totalGamePage = uiState.questions.size,
                                    question = question,
                                    onChangedOption = { optionIndex ->
                                        viewmodel.handleEvent(
                                            OnBoardingEvent.SelectOption(
                                                optionId = optionIndex,
                                                surveyId = question.surveyId
                                            )
                                        )
                                    },
                                )
                            }

                        } else { //온보딩 게임 후 완료 화면
                            OnBoardingCompletedView { viewmodel.handleEvent(OnBoardingEvent.ClickFinkshButton) }
                        }
                    }
                    if (pagerState.currentPage < viewmodel.ONBOARD_INFO_PAGE) { //온보딩 게임 전 인디케이터
                        Spacer(Modifier.height(40.dp))
                        DotIndicator(totalPage = viewmodel.ONBOARD_INFO_PAGE, pagerState.currentPage)
                    }
                }
                if (pagerState.currentPage < viewmodel.ONBOARD_INFO_PAGE) {
                    //버튼들
                    Column(modifier = Modifier.padding(horizontal = 20.dp).padding(28.dp)) {
                        LoopyFilledButton(
                            text = if (pagerState.currentPage < 3) "다음으로" else "시작하기",
                            leadingIconColor = Color.Black,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(1f),
                        )
                    }
                } else if (pagerState.currentPage < uiState.pageCount - 1) { //온보딩 게임 중
                    if (pagerState.currentPage == viewmodel.ONBOARD_INFO_PAGE) {//온보딩의 첫페이지
                        Column(modifier = Modifier.padding(horizontal = 20.dp).padding(28.dp)) {
                            LoopyFilledButton(
                                "다음으로",
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(1f)
                            )
                        }
                    } else { //온보딩 나머지 페이지
                        Row(
                            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 20.dp).padding(bottom = 28.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LoopyFilledButton(
                                "이전으로",
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                    }
                                },
                                filledColor = ColorPalette.Neautral200,
                                textColor = ColorPalette.Neautral600,
                                modifier = Modifier.fillMaxWidth(0.5f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            LoopyFilledButton(
                                "다음으로",
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OnBoardingCompletedView(navigateToHome: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp).padding(top = 120.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "내 가치관 캐릭터가 완성됐어요!", style = TextStyles.title02, softWrap = true)
            Spacer(Modifier.height(20.dp))
            Text(
                text = "홈 화면에서 내 캐릭터를 확인해보세요.",
                style = TextStyles.subTitle02,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Spacer(Modifier.height(40.dp))
            LottieImageView(R.raw.paper_pollen)
        }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoopyFilledButton(
                "완료",
                leadingIconColor = Color.Black,
                onClick = navigateToHome,
                modifier = Modifier.fillMaxWidth(1f),
            )
        }
    }
}

@Composable
fun InfoContentView(content1: String, content2: String, painter: Painter?, lottieRawFile: Int?) {
    Column(
        modifier = Modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(1f))
        Column(
            modifier = Modifier.fillMaxWidth().height(300.dp).background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (painter != null) {
                Image(
                    contentDescription = null,
                    painter = painter,
                    modifier = Modifier.fillMaxWidth(1f).background(Color.White),
                    alignment = Alignment.Center
                )
            }
            if (lottieRawFile != null) {
                LottieImageView(rawFile = lottieRawFile, width = 260.dp, height = 260.dp)
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(26.dp))
        Text(content1, style = TextStyles.title03)
        Text(content2, style = TextStyles.title03)
    }
}

@Composable
fun OnboardingGameView(
    currentPage: Int,
    totalGamePage: Int,
    question: RoundQuestion,
    onChangedOption: (selectedOptionIndex: String) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(0) } //0이 VerticalSliderForm 디폴트 값, 순수 ui인덱스
    Column(
        Modifier.padding(horizontal = 20.dp).fillMaxWidth(1f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.background(
                    color = ColorPalette.PrimaryBlue100,
                    shape = RoundedCornerShape(8.dp)
                ).padding(horizontal = 12.dp).padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${currentPage}",
                    style = TextStyles.subTitle01,
                    color = ColorPalette.PrimaryBlue500
                )
                Text(
                    text = " / ${totalGamePage}",
                    style = TextStyles.subTitle01,
                    color = ColorPalette.PrimaryBlue300
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(1f).height(18.dp))
            androidx.compose.material.Text(
                question.content,
                style = TextStyles.title03,
                color = Color.Black,
                textAlign = TextAlign.Center,
                softWrap = true
            )
        }
        Column(
            Modifier.padding(horizontal = 58.dp).padding(top = 82.dp).wrapContentHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            VerticalSliderForm(
                answerList = question.options.map { it.optionContents },
                onValueChange = { index ->
                    selectedIndex = index
                    val selectedOptionIndex = question.options[index].id
                    onChangedOption(selectedOptionIndex)
                }
            )
        }
    }
}


@Preview
@Composable
fun OnBoardingPreview() {
    Column(Modifier.fillMaxSize()) {
        OnboardingGameView(
            currentPage = 1,
            totalGamePage = 21,
            question = RoundQuestion(
                surveyId = "1",
                surveyType = SurveyType.MULTIPLE_CHOICE,
                content = "짜장 or 짬뽕?",
                options = listOf(
                    Option(id = "0", optionContents = "짜장"),
                    Option(id = "0", optionContents = "짜장"),
                    Option(id = "0", optionContents = "짜장"),
                    Option(id = "0", optionContents = "짜장"),
                    Option(id = "0", optionContents = "짜장")
                )
            ),
            onChangedOption = { optionIndex ->
            },
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            LoopyFilledButton(
                text = "다음으로",
                leadingIconColor = Color.Black,
                onClick = {},
                modifier = Modifier.fillMaxWidth(1f),
            )
        }
    }
}

@Preview
@Composable
fun onBoardingCompletedPreview() {
    OnBoardingCompletedView({})
}

@Preview
@Composable
fun InfoContentPreView() {
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
    val index = 3
    Column(modifier = Modifier.fillMaxHeight(1f), verticalArrangement = Arrangement.SpaceBetween) {
        InfoContentView(
            content1 = infoContents[index].first,
            content2 = infoContents[index].second,
            painter = images[index],
            lottieRawFile = lotties[index]
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            LoopyFilledButton(
                text = "다음으로",
                leadingIconColor = Color.Black,
                onClick = {},
                modifier = Modifier.fillMaxWidth(1f),
            )
        }
    }

}
