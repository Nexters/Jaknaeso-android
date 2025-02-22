package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.RoundEffect
import com.jaknaeso.app.presentation.contract.RoundEvent
import com.jaknaeso.app.presentation.viewmodel.RoundViewmodel
import kotlinx.coroutines.launch

@Composable
fun RoundScreen(
    navigateToLogin: () -> Unit,
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
                RoundEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    if (uiState.value.isError) {
        ErrorInfoView(
            title = "오류가 발생했어요!",
            message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.",
            onClickReLoad = {},
            onClickHome = {})
    } else {
        if (uiState.value.isLoading) {
            LoopyLoadingScreen()
        }
        if (uiState.value.isBalanceRound) {
            BalanceRoundScreen(
                question = uiState.value.question,
                handleEvent = { viewmodel.handleEvent(it) },
                isModalExpanded = isModalExpanded,
                surveyId = uiState.value.question?.surveyId,
                enteredComment = uiState.value.enteredComment,
                onChangedCommentValue = { viewmodel.handleEvent(RoundEvent.SaveWord(it)) }
            )
        } else {
            SliderRoundScreen(
                headerContent = {
                    LoopyTopBar(
                        title = "오늘의 질문",
                        icon = painterResource(R.drawable.ic_back),
                        onClickIcon = { viewmodel.handleEvent(RoundEvent.ClickBackButton) })
                },
                footerContent = {
                    LoopyFilledButton(
                        "작성 완료",
                        onClick = { viewmodel.handleEvent(RoundEvent.OpenModal) },
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                },
                question = uiState.value.question,
                handleEvent = { viewmodel.handleEvent(it) },
                isModalExpanded = isModalExpanded,
                surveyId = uiState.value.question?.surveyId,
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
    val density = LocalDensity.current
    val keyboardDensity = WindowInsets.ime.getBottom(density)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
    val isImeVisible by remember {
        derivedStateOf { keyboardDensity > 0 }
    }

    LaunchedEffect(isImeVisible) {
        if (isImeVisible) {
            sheetState.expand()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { handleEvent(RoundEvent.CloseModal) },
        modifier = Modifier.background(color = Color.Transparent).fillMaxWidth(),
        sheetState = sheetState,
        contentColor = Color.White,
        dragHandle = { DragHandle(onClick = {}) },
        windowInsets = WindowInsets.ime
    ) {
        Column(
            modifier = Modifier.background(color = Color.White).padding(horizontal = 20.dp)
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("답변을 선택한 이유를 알려주세요", style = TextStyles.title03, color = Color.Black)
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.clickable { handleEvent(RoundEvent.CloseModal) })
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
            LoopyTextField(
                value = enteredComment,
                placeHolderValue = "오늘의 나에게 집중해서 적어보세요",
                onValueChange = { onChangedCommentValue(it) },
                modifier = Modifier.fillMaxWidth()
                    .onFocusEvent {
                        scope.launch { sheetState.expand() }
                    }
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
            ) {
                LoopyFilledButton(
                    "작성 완료",
                    onClick = {
                        handleEvent(RoundEvent.CloseModal)
                        handleEvent(RoundEvent.ClickSubmitAnswer)
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                )
            }
        }
    }
}
