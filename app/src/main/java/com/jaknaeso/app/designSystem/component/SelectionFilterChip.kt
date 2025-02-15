package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun SelectionFilterChip(
    isSelected: Boolean,
    shape: Shape,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    onClick: (isSelected: Boolean) -> Unit = {},
    selectedContainerColor: Color = Color.White,
    selectedIconColor: Color = ColorPalette.PrimaryBlue500,
    selectedLabelColor: Color = Color.Black,
    trailingIcon: Painter,
    trailingIconSize: Dp = 15.dp,
    labelIconColor: Color = ColorPalette.PrimaryBlue500,
    labelColor: Color = ColorPalette.PrimaryBlue500,
    filledColor: Color = ColorPalette.PrimaryBlue100,
    disabledLabelColor: Color = ColorPalette.Neautral700,
    disabledIconColor: Color = ColorPalette.Neautral700,
    disabledColor: Color = ColorPalette.Neautral200,
    modifier: Modifier? = null,
) {
    val borderColor = remember { mutableStateOf(if (enabled) filledColor else disabledColor) }
    FilterChip(
        modifier = Modifier.height(33.dp).then(modifier ?: Modifier),
        label = label,
        onClick = { onClick(isSelected) },
        trailingIcon = {
            if (isSelected) {
                Icon(painter = trailingIcon, contentDescription = null, modifier = Modifier.size(trailingIconSize))
            }
        },
        colors = SelectableChipColors(
            containerColor = filledColor,
            labelColor = labelColor,
            disabledContainerColor = filledColor,
            disabledLabelColor = disabledLabelColor,
            leadingIconColor = labelIconColor,
            trailingIconColor = labelColor,
            disabledLeadingIconColor = disabledIconColor,
            disabledTrailingIconColor = disabledIconColor,
            selectedContainerColor = selectedContainerColor,
            disabledSelectedContainerColor = disabledColor,
            selectedLabelColor = selectedLabelColor,
            selectedLeadingIconColor = selectedIconColor,
            selectedTrailingIconColor = selectedIconColor,
        ),
        enabled = enabled,
        border = BorderStroke(width = 0.dp, color = borderColor.value),
        shape = shape,
        selected = isSelected,
        leadingIcon = null,
        elevation = null,
    )
}

@Preview
@Composable
fun SelectionFilterChipPreview() {
    Column {
        SelectionFilterChip(
            isSelected = false, label = { Text(text = "첫번째 캐릭터") },
            shape = RoundedCornerShape(10.dp),
            onClick = {},
            trailingIcon = painterResource(R.drawable.ic_check),
            selectedIconColor = ColorPalette.PrimaryBlue500
        )
        SelectionFilterChip(
            isSelected = true, label = { Text(text = "두번째 캐릭터") },
            shape = RoundedCornerShape(10.dp),
            onClick = {},
            trailingIcon = painterResource(R.drawable.ic_check),
            selectedIconColor = ColorPalette.PrimaryBlue500,
            modifier = Modifier.fillMaxWidth(1f),
            filledColor = Color.White
        )
    }
}
