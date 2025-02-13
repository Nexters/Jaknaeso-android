package com.jaknaeso.app.designSystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import kotlinx.coroutines.launch

@Composable
fun ExpandingBottomSheet(
    floatingContent: @Composable () -> Unit,
    faceContent: @Composable () -> Unit,
    wholeContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
) {
    val PADDING = 40.dp
    val ULTIMATE_GAP = 140.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val localDensity = LocalDensity.current
    var faceContentHeight by remember {
        mutableStateOf(0.dp)
    }
    var wholeContentHeight by remember {
        mutableStateOf(screenHeight)
    }
    var maxSheetHeight = remember { mutableStateOf(screenHeight * 0.9f) } // 최대 높이 (화면의 90%)
    var minSheetHeight = remember { mutableStateOf(screenHeight * 0.35f) } // 최소 높이 (화면의 30%)
    val sheetHeight = remember { mutableStateOf(minSheetHeight.value) } // 시트의 높이 (초기: 최소 높이)
    val coroutineScope = rememberCoroutineScope()
    var isModalOpen by remember { mutableStateOf(false) }

    LaunchedEffect(faceContentHeight, wholeContentHeight) {
        minSheetHeight.value = faceContentHeight.value.dp
        maxSheetHeight.value = wholeContentHeight.value.dp
    }

    Column(
        Modifier
            .fillMaxSize(1f),
        verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isModalOpen) {
            floatingContent()
            Spacer(Modifier.fillMaxWidth().height(20.dp))
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Transparent)
                .clickable {
                    coroutineScope.launch {
                        animateToClose(sheetHeight, minSheetHeight.value)
                    }
                }
        ) {
            // 바텀 시트 (항상 아래에 고정됨)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 항상 아래쪽 고정
                    .fillMaxWidth()
                    .height(sheetHeight.value)
                    .background(Color.Transparent)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            val newHeight =
                                (sheetHeight.value - delta.dp).coerceIn(
                                    minimumValue = minSheetHeight.value,
                                    maximumValue = maxSheetHeight.value
                                )
                            sheetHeight.value = newHeight
                        },
                        onDragStopped = {
                            coroutineScope.launch {
                                if (sheetHeight.value < (maxSheetHeight.value.value.dp + minSheetHeight.value) / 2) {
                                    animateToClose(sheetHeight, minSheetHeight.value)
                                } else {
                                    animateToOpen(sheetHeight, maxSheetHeight.value)
                                }
                            }
                        },
                        onDragStarted = { isModalOpen = !isModalOpen },
                        reverseDirection = true
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(top = 8.dp).padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // 드래그 핸들러
                    DragHandle(icon = painterResource(R.drawable.ic_arrow_up)) {
                        coroutineScope.launch {
                            if (isModalOpen) {
                                animateToClose(sheetHeight, minSheetHeight.value)
                                isModalOpen = !isModalOpen
                            } else {
                                animateToOpen(sheetHeight, maxSheetHeight.value)
                                isModalOpen = !isModalOpen
                            }
                        }
                    }
                    if (isModalOpen) {
                        Column(modifier = Modifier.onGloballyPositioned { coordinates ->
                            wholeContentHeight =
                                with(localDensity) { coordinates.size.height.toDp() + ULTIMATE_GAP + PADDING }
                        }) {
                            // 스크롤 가능한 콘텐츠 영역
                            Box(
                                modifier = Modifier
                                    .weight(1f) // 상단 콘텐츠가 스크롤되도록 설정
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()) // 스크롤 가능
                            ) {
                                wholeContent()
                                Spacer(modifier = Modifier.fillMaxWidth(1f).height(PADDING))
                            }
                            bottomContent()
                        }
                    } else {
                        Column(modifier = Modifier.onGloballyPositioned { coordinates ->
                            faceContentHeight =
                                with(localDensity) { coordinates.size.height.toDp() + PADDING + PADDING }
                        }) {
                            faceContent()
                            Spacer(modifier = Modifier.fillMaxWidth(1f).height(PADDING))
                            bottomContent()
                        }
                    }
                }
            }
        }
    }
}

// 애니메이션으로 바텀 시트를 열기 (위로 확장)
private suspend fun animateToOpen(sheetHeight: MutableState<Dp>, maxSheetHeight: Dp) {
    animate(
        initialValue = sheetHeight.value.value,
        targetValue = maxSheetHeight.value,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    ) { value, _ ->
        sheetHeight.value = value.dp
    }
}

// 애니메이션으로 바텀 시트를 닫기 (아래로 축소)
private suspend fun animateToClose(sheetHeight: MutableState<Dp>, minSheetHeight: Dp) {
    animate(
        initialValue = sheetHeight.value.value,
        targetValue = minSheetHeight.value,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    ) { value, _ ->
        sheetHeight.value = value.dp
    }
}
