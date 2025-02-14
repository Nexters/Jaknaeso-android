package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.BalanceRoundEvent
import com.jaknaeso.app.presentation.viewmodel.BalanceRoundViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderRoundScreen(
    navigateToRoundComplete: () -> Unit,
    navigateToBack: () -> Unit,
    roundIndex: String,
    viewmodel: BalanceRoundViewmodel = hiltViewModel()
) {
    var isModalExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
            SliderContent(onClickFinishButton = {roundIndex ->  })
        }
    }
}

@Composable
fun SliderContent(onClickFinishButton: (roundIndex:Int) -> Unit) {
    var selectedIndex by remember { mutableStateOf(2) } //2가 VerticalSliderForm 보통 디폴트 값
    Column(
        Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(Modifier.padding(horizontal = 58.dp)) {
            Text(
                "독립에 대한 고민이 깊어지는 요즘... 드디어 결정을 내렸다.",
                style = TextStyles.title03,
                color = Color.Black,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Spacer(Modifier.fillMaxWidth(1f).height(82.dp))
            VerticalSliderForm(
                answerList = listOf("매우 동의해요", "조금 동의해요", "보통이에요", "조금 반대해요", "매우 반대해요"),
                onValueChange = { index -> selectedIndex = index})
        }
        LoopyFilledButton(
            "작성 완료",
            onClick = { onClickFinishButton(selectedIndex) },
            modifier = Modifier.fillMaxWidth(1f)
        )
    }
}

@Preview
@Composable
fun SliderRoundPreview() {
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
                onClickIcon = {})
            SliderContent(onClickFinishButton = {roundIndex ->  })
        }
    }
}
