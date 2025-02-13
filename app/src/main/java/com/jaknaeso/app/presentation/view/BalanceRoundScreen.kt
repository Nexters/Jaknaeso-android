package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.tooling.preview.Preview
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
            modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral0)
        ) { paddingValue ->
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
            Column(
                Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral0),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                LoopyTopBar(
                    title = "오늘의 질문",
                    icon = painterResource(R.drawable.ic_back),
                    onClickIcon = { viewmodel.handleEvent(BalanceRoundEvent.ClickBackButton) })
                Column(
                    Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Text(
                            "독립에 대한 고민이 깊어지는 요즘... 드디어 결정을 내렸다.",
                            style = TextStyles.title03,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            softWrap = true
                        )
                        Spacer(Modifier.fillMaxWidth(1f).height(20.dp))
                        FlipAnimation(
                            forwardColor = Color.White,
                            backwardColor = ColorPalette.PrimaryBlue100,
                            frontContent = {
                                BalanceContent(title = "첫번째 선택지", option = "주변 사람과 물리적으로 멀어지더라도, 커리어를 선택한다.")
                            },
                            backContent = {
                                BalanceContent(title = "두번째 선택지", option = "가족과 함께 살며 따뜻한 식사와 생활비 걱정 없는 일상을 선택한다.")
                            },
                            modifier = Modifier.fillMaxWidth(1f).padding(40.dp).aspectRatio(0.94f)
                        )
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

@Composable
fun BalanceContent(title: String, option: String) {
    Column(
        modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp).padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp).padding(vertical = 50.dp)
        ) {
            Text(
                title,
                style = TextStyles.subTitle03,
                color = ColorPalette.Neautral600,
                modifier = Modifier.padding(bottom = 28.dp)
            )
            Text(
                text = option,
                style = TextStyles.title03,
                color = Color.Black,
                textAlign = TextAlign.Center,
                softWrap = true
            )
        }
        LoopyFilledButton(
            "카드 뒤집기",
            onClick = {},
            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
            height = 50.dp,
            filledColor = Color.White,
            textColor = ColorPalette.Neautral500,
            borderColor = ColorPalette.Neautral400,
            trailingIcon = painterResource(R.drawable.ic_flip),
            trailingIconColor = ColorPalette.Neautral500
        )
    }
}


@Preview
@Composable
private fun BalanceRoundPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral0)
    ) { paddingValue ->
        Column(
            Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral0),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LoopyTopBar(
                title = "오늘의 질문",
                icon = painterResource(R.drawable.ic_back),
                onClickIcon = { })
            Column(
                Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    Text(
                        "독립에 대한 고민이 깊어지는 요즘... 드디어 결정을 내렸다.",
                        style = TextStyles.title03,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        softWrap = true
                    )
                    Spacer(Modifier.fillMaxWidth(1f).height(20.dp))
                    FlipAnimation(
                        forwardColor = Color.White,
                        backwardColor = ColorPalette.PrimaryBlue100,
                        frontContent = {
                            BalanceContent(title = "첫번째 선택지", option = "주변 사람과 물리적으로 멀어지더라도, 커리어를 선택한다.")
                        },
                        backContent = {
                            BalanceContent(title = "두번째 선택지", option = "가족과 함께 살며 따뜻한 식사와 생활비 걱정 없는 일상을 선택한다.")
                        },
                        modifier = Modifier.fillMaxWidth(1f).padding(40.dp).aspectRatio(0.94f)
                    )
                }
                LoopyFilledButton(
                    "작성 완료",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }

    }
}
