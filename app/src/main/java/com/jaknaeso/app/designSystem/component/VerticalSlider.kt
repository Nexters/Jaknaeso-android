package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun VerticalSliderForm(answerList: List<String>, onValueChange: (index: Int) -> Unit) {
    var value by remember { mutableStateOf(0.5f) }
    var isSelected by remember { mutableStateOf(2) }
    val HEIGHT = 278.dp
    val size = answerList.size
    var valueRanges = remember {
        mutableListOf<ClosedFloatingPointRange<Float>>(
            0f..0.2f,
            0.21f..0.4f,
            0.41f..0.6f,
            0.61f..0.8f,
            0.81f..1f
        )
    }

    LaunchedEffect(value) {
        valueRanges.forEachIndexed { index, closedFloatingPointRange ->
            if (value in closedFloatingPointRange) {
                isSelected = (size - 1) - index
            }
        }
    }

    LaunchedEffect(isSelected) {
        onValueChange(4 - isSelected)
    }

    Row(
        modifier = Modifier.height(HEIGHT).fillMaxWidth().background(color = Color.Transparent),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(0.9f).height(HEIGHT)
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(items = answerList) { index, item ->
                SliderAnswer(isSelected = index == isSelected, answer = item)
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(1f).background(color = Color.Transparent)
        ) {
            VerticalSlider(
                value = value,
                onValueChange = {
                    value = it
                },
                steps = answerList.size,
                valueRanges = valueRanges,
                modifier = Modifier.fillMaxHeight(1f).background(color = Color.Transparent)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SliderAnswer(isSelected: Boolean, answer: String) {
    if (!isSelected) {
        Text(answer, style = TextStyles.subTitle02, color = ColorPalette.Neautral600)
    } else {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    steps: Int,
    valueRanges: List<ClosedFloatingPointRange<Float>>,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var adjustedValue by remember { mutableStateOf(value) }

    Slider(
        colors = SliderDefaults.colors(
            thumbColor = ColorPalette.PrimaryBlue500,
            activeTrackColor = ColorPalette.PrimaryBlue500,
            inactiveTrackColor = ColorPalette.PrimaryBlue500
        ),
        interactionSource = interactionSource,
        onValueChangeFinished = {
            val closestRange = valueRanges.minByOrNull { range ->
                val center = (range.start + range.endInclusive) / 2
                kotlin.math.abs(adjustedValue - center)
            }

            closestRange?.let { range ->
                adjustedValue = when {
                    range.start == 0f && range.endInclusive <= 0.2f -> 0f
                    range.start >= 0.81f && range.endInclusive == 1f -> 1f
                    else -> (range.start + range.endInclusive) / 2
                }
                onValueChange(adjustedValue)
            }
            onValueChangeFinished?.invoke()
        },
        steps = steps,
        value = adjustedValue,
        valueRange = valueRanges.first().start..valueRanges.last().endInclusive,
        enabled = enabled,
        onValueChange = { newValue ->
            val validRange = valueRanges.find { newValue in it }
            if (validRange != null) {
                adjustedValue = newValue
                onValueChange(newValue)
            }
        },
        modifier = Modifier
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxHeight,
                        minHeight = 100,
                        maxHeight = 100,
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .then(modifier),
        track = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(ColorPalette.PrimaryBlue500, shape = RoundedCornerShape(50)) // 둥글게 처리
            )
        },
        thumb = {
            Box(
                modifier = Modifier
                    .size(26.dp) // Thumb 크기 조정
                    .background(ColorPalette.PrimaryBlue500, shape = CircleShape)
                    .border(2.dp, ColorPalette.PrimaryBlue500, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_vertical_side),
                    contentDescription = "Thumb Icon",
                    tint = Color.White, // 아이콘 색상 설정
                    modifier = Modifier.size(24.dp).rotate(90f) // 아이콘 크기 조정
                )
            }
        }
    )
}

@Preview
@Composable
fun SliderPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffdedede)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSliderForm(answerList = listOf("매우 동의해요", "조금 동의해요", "보통이에요", "조금 반대해요", "매우 반대해요"), {})
    }
}
