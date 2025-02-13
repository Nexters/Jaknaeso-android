package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopySuggestionChip(
    label: String,
    shape: Shape,
    enabled: Boolean = true,
    icon: Painter?= null,
    height: Dp = 33.dp,
    labelStyle: TextStyle = TextStyles.subTitle03,
    labelIconColor: Color = ColorPalette.PrimaryBlue500,
    labelColor: Color = ColorPalette.PrimaryBlue500,
    filledColor: Color = ColorPalette.PrimaryBlue100,
    disabledLabelColor: Color = ColorPalette.Neautral700,
    disabledIconColor: Color = ColorPalette.Neautral700,
    disabledColor: Color = ColorPalette.Neautral200,
    modifier: Modifier? = null,
) {
    val borderColor = remember { mutableStateOf(if (enabled) filledColor else disabledColor) }
    SuggestionChip(
        modifier = Modifier.height(height).then(modifier ?: Modifier),
        label = { Text(label, style = labelStyle, color = labelColor) },
        onClick = {},
        icon = {if(icon != null){Icon(painter = icon, contentDescription = null)}},
        colors = ChipColors(
            containerColor = filledColor,
            labelColor = filledColor,
            leadingIconContentColor = labelIconColor,
            trailingIconContentColor = labelIconColor,
            disabledContainerColor = disabledColor,
            disabledLabelColor = disabledLabelColor,
            disabledLeadingIconContentColor = disabledIconColor,
            disabledTrailingIconContentColor = disabledLabelColor
        ),
        enabled = enabled,
        border = BorderStroke(width = 0.dp, color = borderColor.value),
        shape = shape
    )
}

@Preview
@Composable
fun PreviewLoopChip() {
    Column {
        LoopySuggestionChip("N회차 질문", labelStyle = TextStyles.subTitle03, shape = RoundedCornerShape(8.dp))
        LoopySuggestionChip(
            "1번째 캐릭터",
            labelStyle = TextStyles.subTitle04,
            filledColor = ColorPalette.Neautral200,
            labelColor = ColorPalette.Neautral700,
            shape = RoundedCornerShape(8.dp),
        )
    }
}
