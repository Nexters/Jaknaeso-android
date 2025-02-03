package com.jaknaeso.app.presentation.home.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LoopySuggestionChip
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.entity.VersusQuestion
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OptionsRoundScreen(navigateToHome: () -> Unit, roundIndex: String) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val question = VersusQuestion(
        roundIndex = 0,
        question = "상황주고 어떤 선택할래? A? B?",
        options = listOf("주변 사람과 물리적으로 먹어지더라도, 커리어를 선택한다.", "주변 사람들을 지키고, 커리어를 포기한다.")
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = ColorPalette.Neautral100)
    ) { paddingValue ->
        Column(
            Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral100),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 30.dp).padding(horizontal = 32.dp)
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoopySuggestionChip("${question.roundIndex}회차 질문", shape = RoundedCornerShape(8.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "${question.question}",
                        style = TextStyles.title03,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalPager(state = pagerState, userScrollEnabled = false) { page ->
                    Column(
                        modifier = Modifier.fillMaxWidth(1f).sizeIn(minHeight = 350.dp, maxHeight = 430.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)).padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.fillMaxWidth(1f).height(20.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${page + 1}번째 선택지",
                                style = TextStyles.subTitle03,
                                color = ColorPalette.Neautral600,
                                modifier = Modifier.padding(bottom = 28.dp)
                            )
                            Text(
                                "${question.options[page]}",
                                style = TextStyles.title03,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                        }
                        Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.End) {
                            LoopyFilledButton(
                                "다음 답변 보기",
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(0.5f),
                                filledColor = Color.Transparent,
                                textColor = ColorPalette.Neautral500
                            )
                        }
                    }
                }
                LoopyFilledButton(
                    "작성 완료",
                    onClick = { navigateToHome() },
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }

    }
}
