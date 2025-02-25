package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import kotlin.math.roundToInt

@Composable
fun SliderOptions(options: List<String>, onValueChanged: (index: Int) -> Unit) {
    val minOffset = -400f
    val maxOffset = 400f // 필요에 따라 dp -> px 변환 적용
    var selectedOption by remember { mutableStateOf(0f) }
    val answerRange = mutableListOf<ClosedFloatingPointRange<Float>>(
        -400f..-300f,
        -301f..-100f,
        -100f..100f,
        101f..300f,
        301f..400f
    )

    var selectedIndex by remember { mutableStateOf(2) }

    LaunchedEffect(selectedIndex) {
        onValueChanged(selectedIndex)
    }

    Box(
        modifier = Modifier
            .size(400.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.height(300.dp).fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
            LazyColumn(Modifier.fillMaxHeight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                itemsIndexed(options) { index, item ->
                    SliderAnswer(isSelected = selectedOption in answerRange[index], answer = item, onSelected = {
                        selectedIndex = index
                    })
                }
            }
            Slider(minOffset = minOffset, maxOffset = maxOffset, { selectedOption = it })
        }
    }
}

@Composable
fun SliderAnswer(isSelected: Boolean, answer: String, onSelected: () -> Unit) {
    if (!isSelected) {
        Text(answer, style = TextStyles.subTitle02, color = ColorPalette.Neautral600)
    } else {
        onSelected()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.background(color = ColorPalette.PrimaryBlue500, shape = CircleShape).size(26.dp)
                    .padding(5.dp)
            )
            Text(
                answer,
                style = TextStyles.subTitle01,
                color = Color.Black,
                modifier = Modifier.padding(start = 5.5.dp)
            )
        }
    }
}


@Composable
fun Slider(minOffset: Float, maxOffset: Float, onOffsetChanged: (value: Float) -> Unit) {
    var offsetY by remember { mutableStateOf(0f) }
    Box(contentAlignment = Alignment.Center) {
        // 범위를 픽셀 단위로 지정 (예: 0 ~ 300px)
        Box(
            modifier = Modifier
                .height(300.dp)
                .width(8.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = ColorPalette.PrimaryBlue500)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .size(20.dp)
                .background(ColorPalette.PrimaryBlue500, shape = CircleShape)
                .clip(shape = CircleShape)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        val nextOffSet = (offsetY + dragAmount).coerceIn(minOffset, maxOffset)
                        offsetY = nextOffSet
                        onOffsetChanged(nextOffSet)
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_vertical_side),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun SliderOptionsPreview() {
    SliderOptions(listOf("Aaaaa", "Bbbbb", "Ccccc", "Ddddd", "Eeeee"), {})
}
