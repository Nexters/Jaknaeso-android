package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.ErrorInfoView
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LottieImageView
import com.jaknaeso.app.designSystem.component.VerticalSliderForm
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
fun OnBoardingScreen(navigateToHome: () -> Unit, viewmodel: OnBoardingViewmodel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { uiState.pageCount })
    val infoContents = listOf(
        Pair("매일 가치관을 묻는 질문에 답변하고", "나의 하루를 돌아보세요"),
        Pair("15일 동안 답변하면", "나의 가치관 캐릭터를 알 수 있어요"),
        Pair("답변에 대한 나의 생각을 적어", "그날의 나를 돌아볼 수 있어요"),
        Pair("먼저 가치관 테스트를 통해", "나의 가치관 캐릭터를 만들어 볼까요?")
    )

    LaunchedEffect(Unit) {
        viewmodel.handleEvent(OnBoardingEvent.GetOnboardingData)
        viewmodel.effects.collect { effect ->
            when (effect) {
                OnBoardingEffect.NavigateToHome -> navigateToHome()
            }
        }
    }
    if (uiState.isLoading) {
        Text("로딩 임시", style = TextStyles.title01)
    }
    if (uiState.isError) {
        ErrorInfoView("에러 발생", "불편을 끼쳐죄송", {}, {})
    } else {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(bottom = 28.dp)
            ) { page ->
                if (page < viewmodel.ONBOARD_INFO_PAGE) { //온보딩 게임 전
                    Column(modifier = Modifier.fillMaxHeight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                        InfoContentView(content1 = infoContents[page].first, content2 = infoContents[page].second)
                        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                            LoopyFilledButton(
                                text = if (page < 3) "다음으로" else "시작하기",
                                leadingIconColor = Color.Black,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(1f),
                            )
                        }
                    }
                } else if (page < uiState.pageCount - 1) { //온보딩 게임 중
                    val index = page - viewmodel.ONBOARD_INFO_PAGE
                    val question = uiState.questions[index]
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
                        footContent = {
                            if (page == viewmodel.ONBOARD_INFO_PAGE) {//온보딩의 첫페이지
                                LoopyFilledButton(
                                    "다음으로",
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(1f)
                                )
                            } else { //온보딩 나머지 페이지
                                Row(
                                    modifier = Modifier.fillMaxWidth(1f),
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
                    )

                } else { //온보딩 게임 후 완료 화면
                    OnBoardingCompletedView { viewmodel.handleEvent(OnBoardingEvent.ClickFinkshButton) }
                }
            }
        }
    }
}


@Composable
fun OnBoardingCompletedView(navigateToHome: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp).padding(top = 28.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.fillMaxHeight(0.8f),
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
fun InfoContentView(content1: String, content2: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(110.dp))
        Column(
            modifier = Modifier.fillMaxWidth().height(300.dp).background(color = ColorPalette.PrimaryBlue100),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("로띠 예정", style = TextStyles.body02)
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
    footContent: @Composable () -> Unit,
) {
    androidx.compose.material.Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White)
    ) { paddingValue ->
        Column(
            Modifier.fillMaxWidth(1f).padding(paddingValue).background(color = Color.White),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var selectedIndex by remember { mutableStateOf(0) } //0이 VerticalSliderForm 디폴트 값, 순수 ui인덱스
            Column(
                Modifier.padding(horizontal = 20.dp).padding(top = 54.dp).fillMaxSize(1f),
                verticalArrangement = Arrangement.SpaceBetween,
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
                    Modifier.padding(horizontal = 58.dp).fillMaxHeight(0.8f),
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
                footContent()
            }
        }
    }
}


@Preview
@Composable
fun OnBoardingPreview() {
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
        footContent = {
            LoopyFilledButton(
                "다음으로",
                onClick = {
                },
                modifier = Modifier.fillMaxWidth(1f)
            )
        }
    )
}

@Preview
@Composable
fun onBoardingCompletedPreview() {
    OnBoardingCompletedView({})
}
