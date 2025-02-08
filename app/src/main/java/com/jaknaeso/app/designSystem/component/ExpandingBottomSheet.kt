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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ExpandingBottomSheet(
    floatingContent: @Composable () -> Unit,
    faceContent: @Composable () -> Unit,
    wholeContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
) {
    val PADDING = 40.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxSheetHeight = screenHeight * 1f // 최대 높이 (화면의 90%)
    val minSheetHeight = (screenHeight * 0.3f + PADDING) // 최소 높이 (화면의 20%)
    val sheetHeight = remember { mutableStateOf(minSheetHeight.value) } // 시트의 높이 (초기: 최소 높이)
    val coroutineScope = rememberCoroutineScope()
    var isModalOpen by remember { mutableStateOf(false) }

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
                        animateToClose(sheetHeight, minSheetHeight)
                    }
                }
        ) {
            // 바텀 시트 (항상 아래에 고정됨)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 항상 아래쪽 고정
                    .fillMaxWidth()
                    .height(sheetHeight.value.dp)
                    .background(Color.Transparent)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            val newHeight =
                                (sheetHeight.value - delta).coerceIn(minSheetHeight.value, maxSheetHeight.value)
                            sheetHeight.value = newHeight
                        },
                        onDragStopped = {
                            coroutineScope.launch {
                                if (sheetHeight.value < (maxSheetHeight.value + minSheetHeight.value) / 2) {
                                    animateToClose(sheetHeight, minSheetHeight)
                                } else {
                                    animateToOpen(sheetHeight, maxSheetHeight)
                                }
                            }
                        },
                        onDragStarted = { isModalOpen = !isModalOpen }
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
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom
                ) {
                    // 드래그 핸들러
                    DragHandle {
                        coroutineScope.launch {
                            if (isModalOpen) {
                                animateToClose(sheetHeight, minSheetHeight)
                                isModalOpen = !isModalOpen
                            } else {
                                animateToOpen(sheetHeight, maxSheetHeight)
                                isModalOpen = !isModalOpen
                            }
                        }
                    }
                    if (isModalOpen) {
                        // 스크롤 가능한 콘텐츠 영역
                        Box(
                            modifier = Modifier
                                .weight(1f) // 상단 콘텐츠가 스크롤되도록 설정
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()) // 스크롤 가능
                        ) {
                            wholeContent() // 사용자가 넣을 콘텐츠
                        }
                        bottomContent()
                    } else {
                        faceContent()
                        Spacer(modifier = Modifier.fillMaxWidth(1f).height(PADDING))
                        bottomContent()
                    }
                }
            }
        }
    }
}

// 애니메이션으로 바텀 시트를 열기 (위로 확장)
private suspend fun animateToOpen(sheetHeight: MutableState<Float>, maxSheetHeight: Dp) {
    animate(
        initialValue = sheetHeight.value,
        targetValue = maxSheetHeight.value,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    ) { value, _ ->
        sheetHeight.value = value
    }
}

// 애니메이션으로 바텀 시트를 닫기 (아래로 축소)
private suspend fun animateToClose(sheetHeight: MutableState<Float>, minSheetHeight: Dp) {
    animate(
        initialValue = sheetHeight.value,
        targetValue = minSheetHeight.value,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    ) { value, _ ->
        sheetHeight.value = value
    }
}
