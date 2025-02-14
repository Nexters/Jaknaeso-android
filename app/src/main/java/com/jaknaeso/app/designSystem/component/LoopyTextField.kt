package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyTextField(value: String, onValueChange: (value: String) -> Unit, placeHolderValue: String, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyles.subTitle04,
        modifier = Modifier.height(200.dp).then(modifier)
            .background(color = ColorPalette.Neautral100, shape = RoundedCornerShape(20.dp))
            .border(
                border = BorderStroke(width = 1.dp, color = ColorPalette.Neautral300),
                shape = RoundedCornerShape(20.dp)
            ),
        placeholder = {
            Text(
                text = placeHolderValue,
                style = TextStyles.subTitle04,
                color = ColorPalette.Neautral600
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            errorTextColor = Color.Black,
            focusedContainerColor = ColorPalette.Neautral100,
            unfocusedContainerColor = ColorPalette.Neautral100,
            disabledContainerColor = ColorPalette.Neautral100,
            errorContainerColor = ColorPalette.Neautral100,
            cursorColor = ColorPalette.Neautral300,
            errorCursorColor = ColorPalette.Neautral300,
            textSelectionColors = TextSelectionColors(
                handleColor = ColorPalette.PrimaryBlue400,
                backgroundColor = Color.Transparent
            ),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            disabledLeadingIconColor = Color.Black,
            errorLeadingIconColor = Color.Black,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Black,
            disabledTrailingIconColor = Color.Black,
            errorTrailingIconColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            disabledLabelColor = Color.Black,
            errorLabelColor = Color.Black,
            focusedPlaceholderColor = ColorPalette.Neautral600,
            unfocusedPlaceholderColor = ColorPalette.Neautral600,
            disabledPlaceholderColor = ColorPalette.Neautral600,
            errorPlaceholderColor = ColorPalette.Neautral600,
            focusedSupportingTextColor = Color.Black,
            unfocusedSupportingTextColor = Color.Black,
            disabledSupportingTextColor = Color.Black,
            errorSupportingTextColor = Color.Black,
            focusedPrefixColor = Color.Black,
            unfocusedPrefixColor = Color.Black,
            disabledPrefixColor = Color.Black,
            errorPrefixColor = Color.Black,
            focusedSuffixColor = Color.Black,
            unfocusedSuffixColor = Color.Black,
            disabledSuffixColor = Color.Black,
            errorSuffixColor = Color.Black,
        )
    )
}

@Preview
@Composable
fun PreviewLoopyTextField() {
    Column(Modifier.fillMaxWidth(1f)){
        LoopyTextField(
            value = "",
            placeHolderValue = "오늘의 나에게 집중해서 적어보세요",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(1f)
        )
    }
}
