package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round
import com.jaknaeso.app.presentation.contract.HomeEffect
import com.jaknaeso.app.presentation.contract.HomeEvent
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar
import com.jaknaeso.app.presentation.navigation.NO_BUNDLE_ID
import com.jaknaeso.app.presentation.navigation.NO_SURVEY_INDEX
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.viewmodel.HomeViewmodel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    navigateToReport: (bundleId: String, surveyIndex: String) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToBalanceRound: (bundleIndex: String, remainingRounds: String) -> Unit,
    viewmodel: HomeViewmodel = hiltViewModel(),
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewmodel.effects.collectLatest { effect ->
            when (effect) {
                is HomeEffect.NavigateToRound -> {
                    navigateToBalanceRound(effect.bundleIndex, effect.remainingRounds)
                }

                HomeEffect.ShowSnackbar -> snackbarHostState.showSnackbar(
                    message = "",
                    duration = SnackbarDuration.Short
                )

                is HomeEffect.NavigateToRoundHistory -> navigateToReport(effect.bundleIndex, effect.surveyIndex)
                HomeEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    if (uiState.isError) {
        Column(modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral50)) { }
    } else {
        if (uiState.isLoading) {
            LoopyLoadingScreen()
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(1f).background(color = ColorPalette.Neautral50),
            bottomBar = {
                LoopyBottomNavBar(
                    navigateToHome = {},
                    navigateToReport = { navigateToReport(NO_BUNDLE_ID, NO_SURVEY_INDEX) }, //캐릭터 분석으로 넘어감
                    navigateToProfile = navigateToProfile,
                    currentRoute = Route.Home
                )
            },
            snackbarHost = {
                Loopysnackbar(snackbarHostState = snackbarHostState) {
                    Text(
                        "하루에 한 회차씩 답변할 수 있어요",
                        style = TextStyles.subTitle04,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 25.dp).fillMaxWidth(1f),
                        textAlign = TextAlign.Center
                    )
                }
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier.background(color = ColorPalette.Neautral100).fillMaxSize(1f)
                        .padding(paddingValues),
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(34.dp))
                        LoopySuggestionChip(
                            "${uiState.characterNo} 캐릭터",
                            labelStyle = TextStyles.subTitle04,
                            filledColor = ColorPalette.PrimaryBlue100,
                            labelColor = ColorPalette.PrimaryBlue500,
                            shape = RoundedCornerShape(8.dp)
                        )
                        Text(
                            uiState.characterName,
                            style = TextStyles.title01,
                            modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.5f),
                            softWrap = true
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) { LottieImageView(uiState.lottieRawFile) }
                    }
                    Column(
                        modifier = Modifier.background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                    ) {
                        ExpandingBottomSheet(
                            floatingContent = { RestRoundsUntilCharacter(uiState.remainRounds) },
                            faceContent = {
                                FaceContent(
                                    uiState.faceRound,
                                    onClickRound = { state, questionIndex ->
                                        viewmodel.handleEvent(
                                            HomeEvent.ClickRound(
                                                state,
                                                questionIndex
                                            )
                                        )
                                    })
                            },
                            wholeContent = {
                                WholeContent(
                                    uiState.wholeRounds,
                                    { state, questionIndex ->
                                        viewmodel.handleEvent(
                                            HomeEvent.ClickRound(
                                                state,
                                                questionIndex
                                            )
                                        )
                                    })
                            },
                            bottomContent = {
                                LoopyFilledButton(
                                    enabled = uiState.isEnabledTodayRoundButton,
                                    text = "오늘의 질문 답변하기",
                                    textStyle = TextStyles.subTitle01,
                                    onClick = { viewmodel.handleEvent(HomeEvent.TodayRoundButton) },
                                    modifier = Modifier.fillMaxWidth(1f),
                                )
                            },
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun RestRoundsUntilCharacter(count: Int) {
    Row(
        modifier = Modifier.background(color = ColorPalette.Neautral200, shape = RoundedCornerShape(40.dp))
            .padding(horizontal = 26.dp).padding(top = 16.dp, bottom = 15.dp)
    ) {
        Text(
            text = "캐릭터 완성까지",
            style = TextStyles.subTitle01,
            color = ColorPalette.Neautral600,
            modifier = Modifier.padding(end = 10.dp)
        )
        Spacer(
            modifier = Modifier.background(color = ColorPalette.Neautral500).width(1.dp).height(20.dp)
        )
        Text(
            text = "${count}회차",
            style = TextStyles.subTitle01,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun FaceContent(
    rounds: List<Round>?,
    onClickRound: (state: QuestionState, questionIndex: Int) -> Unit
) {
    val faceRounds = rounds?.subList(0, 5)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(faceRounds ?: emptyList()) { round ->
            QuestionItem(round = round, index = round.index, onClickItem = { onClickRound(it, round.index) })
        }
    }
}

@Composable
fun WholeContent(
    rounds: List<Round>?,
    onClickRound: (state: QuestionState, questionIndex: Int) -> Unit
) {
    if (!rounds.isNullOrEmpty()) {
        val ROW = 5
        val chunkedRounds = rounds.chunked(ROW)
        Column(
            modifier = Modifier.background(
                color = Color.White,
            ).fillMaxWidth(1f)
        ) {
            chunkedRounds.forEach { rounds ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    items(rounds) { round ->
                        QuestionItem(
                            round = round,
                            index = round.index,
                            onClickItem = { onClickRound(it, round.index) })
                    }
                }
            }
        }
    }
}

