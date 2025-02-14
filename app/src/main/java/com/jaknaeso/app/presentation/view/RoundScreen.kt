package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.designSystem.component.DragHandle
import com.jaknaeso.app.designSystem.component.ErrorInfoView
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LoopyTextField
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.RoundEffect
import com.jaknaeso.app.presentation.contract.RoundEvent
import com.jaknaeso.app.presentation.viewmodel.RoundViewmodel

@Composable
fun RoundScreen(
    navigateToRoundComplete: () -> Unit,
    navigateToBack: () -> Unit,
    bundleIndex: String,
    viewmodel: RoundViewmodel = hiltViewModel()
) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    var isModalExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewmodel.handleEvent(RoundEvent.GetQuestion(bundleIndex))

        viewmodel.effects.collect { effect ->
            when (effect) {
                RoundEffect.NavigateToBack -> navigateToBack()
                RoundEffect.NavigateToBalanceRoundComplete -> navigateToRoundComplete()
                RoundEffect.OpenModal -> isModalExpanded = true
                RoundEffect.CloseModal -> isModalExpanded = false
            }
        }
    }

    if (uiState.value.isLoading) {
        Text(text = "로딩중 임시화면", style = TextStyles.title02, modifier = Modifier.fillMaxSize(1f))
    }
    if (uiState.value.isError) {
        ErrorInfoView(
            title = "오류가 발생했어요!",
            message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.",
            onClickReLoad = {},
            onClickHome = {})
    } else {
        if (uiState.value.isBalanceRound) {
            BalanceRoundScreen(
                question = uiState.value.question,
                handleEvent = { viewmodel.handleEvent(it) },
                isModalExpanded = isModalExpanded,
                surveyId = uiState.value.question?.id,
                enteredComment = uiState.value.enteredComment,
                onChangedCommentValue = { viewmodel.handleEvent(RoundEvent.SaveWord(it)) }
            )
        } else {
            SliderRoundScreen(
                question = uiState.value.question,
                handleEvent = { viewmodel.handleEvent(it) },
                isModalExpanded = isModalExpanded,
                surveyId = uiState.value.question?.id,
                enteredComment = uiState.value.enteredComment,
                onChangedCommentValue = { viewmodel.handleEvent(RoundEvent.SaveWord(it)) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCommentModal(
    enteredComment: String,
    onChangedCommentValue: (value: String) -> Unit,
    handleEvent: (RoundEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { handleEvent(RoundEvent.CloseModal) },
        modifier = Modifier.background(color = Color.Transparent),
        sheetState = sheetState,
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
                value = enteredComment,
                placeHolderValue = "오늘의 나에게 집중해서 적어보세요",
                onValueChange = { onChangedCommentValue(it) },
                modifier = Modifier.fillMaxWidth(1f)
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                LoopyFilledButton(
                    "작성 완료",
                    onClick = { handleEvent(RoundEvent.ClickSubmitWordButton) },
                    modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
                )
                LoopyFilledButton(
                    "넘어가기",
                    onClick = { handleEvent(RoundEvent.ClickBackButton) },
                    modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                    filledColor = ColorPalette.Neautral200,
                    textColor = ColorPalette.Neautral600
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(62.dp))
        }
    }
}
