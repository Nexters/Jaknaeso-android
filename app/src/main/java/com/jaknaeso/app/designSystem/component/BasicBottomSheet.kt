package com.jaknaeso.app.designSystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun BasicBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight = screenHeight * 0.65f
    val animatedOffset = remember { Animatable(screenHeight.value) }
    val scope = rememberCoroutineScope()

    // 바텀 시트가 나타날 때 반 정도까지 올라오게 함
    LaunchedEffect(isVisible) {
        animatedOffset.animateTo(
            if (isVisible) screenHeight.value - halfScreenHeight.value else screenHeight.value,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = if (isVisible) 0.4f else 0f))
            .clickable(onClick = onDismiss)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(halfScreenHeight)
                .offset(y = animatedOffset.value.dp)
                .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            animatedOffset.snapTo(
                                (animatedOffset.value + delta)
                                    .coerceIn(screenHeight.value - halfScreenHeight.value, screenHeight.value)
                            )
                        }
                    },
                    onDragStopped = {
                        if (animatedOffset.value > screenHeight.value - halfScreenHeight.value / 2) {
                            onDismiss()
                        } else {
                            animatedOffset.animateTo(screenHeight.value - halfScreenHeight.value)
                        }
                    }
                )
        ) {
            content()
        }
    }
}


@Preview
@Composable
fun BasicBottomSheetPreview() {
    var isSheetVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { isSheetVisible = true }) {
            Text("Show Bottom Sheet")
        }

        if (isSheetVisible) {
            BasicBottomSheet(
                isVisible = isSheetVisible,
                onDismiss = { isSheetVisible = false },
                bottomContent = { Text("Show Bottom Sheet") }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }
        }
    }
}
