package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.BalanceRoundEffect
import com.jaknaeso.app.presentation.contract.BalanceRoundEvent
import com.jaknaeso.app.presentation.viewmodel.BalanceRoundViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BalanceRoundScreen(
    navigateToBalanceRoundComplete: () -> Unit,
    navigateToBack: () -> Unit,
    roundIndex: String,
    viewmodel: BalanceRoundViewmodel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    val question = uiState.value.balanceQuestion
    var isModalExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewmodel.handleEvent(BalanceRoundEvent.GetBalanceQuestion(roundIndex))
        viewmodel.effects.collect { effect ->
            when (effect) {
                BalanceRoundEffect.NavigateToBack -> navigateToBack()
                BalanceRoundEffect.NavigateToBalanceRoundComplete -> navigateToBalanceRoundComplete()
                BalanceRoundEffect.OpenModal -> {
                    isModalExpanded = true
                }
            }
        }

        viewmodel.event.collect { event ->
            when (event) {
                BalanceRoundEvent.ClickBackButton -> {}
                BalanceRoundEvent.ClickSubmitReasonButton -> {
                    isModalExpanded = false
                }

                BalanceRoundEvent.ClickPassEnterReason -> {
                    isModalExpanded = false
                }

                is BalanceRoundEvent.GetBalanceQuestion -> {}
                is BalanceRoundEvent.SelectOption -> {}
            }
        }
    }

    if (question != null) {
        Scaffold(
            modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral100)
        ) { paddingValue ->
            Column(
                Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral100),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (isModalExpanded) {
                    ModalBottomSheet(
                        onDismissRequest = { isModalExpanded = false },
                        modifier = Modifier.background(color = Color.Transparent),
                        sheetState = bottomSheetState,
                        contentColor = Color.White,
                        dragHandle = { DragHandle(onClick = {}) }
                    ) {
                        Column(
                            modifier = Modifier.background(color = Color.White).padding(horizontal = 20.dp)
                                .fillMaxWidth(1f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                            Text(text = "답변을 선택한 이유를 알려주세요", style = TextStyles.title03)
                            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                            LoopyTextField(
                                placeHolderValue = "오늘의 나에게 집중해서 적어보세요",
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth(1f)
                            )
                            Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                LoopyFilledButton(
                                    "작성 완료",
                                    onClick = { viewmodel.handleEvent(BalanceRoundEvent.ClickSubmitReasonButton) },
                                    modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
                                )
                                LoopyFilledButton(
                                    "넘어가기",
                                    onClick = { viewmodel.handleEvent(BalanceRoundEvent.ClickBackButton) },
                                    modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                                    filledColor = ColorPalette.Neautral200,
                                    textColor = ColorPalette.Neautral600
                                )
                            }
                            Spacer(modifier = Modifier.fillMaxWidth().height(62.dp))
                        }
                    }
                }
                LoopyTopBar(
                    title = "오늘의 질문",
                    icon = painterResource(R.drawable.ic_back),
                    onClickIcon = { viewmodel.handleEvent(BalanceRoundEvent.ClickBackButton) })
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
                        LoopySuggestionChip("${question.roundIndex + 1}회차 질문", shape = RoundedCornerShape(8.dp))
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
                        onClick = { viewmodel.handleEvent(BalanceRoundEvent.SelectOption(roundIndex)) },
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }
            }
        }
    }
}
