package com.jaknaeso.app.designSystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.jaknaeso.app.R

object TextStyles {
    val bold = FontFamily(Font(R.font.pretendard_bold))
    val semiBold = FontFamily(Font(R.font.pretendard_semi_bold))
    val medium = FontFamily(Font(R.font.pretendard_medium))
    val regular = FontFamily(Font(R.font.pretendard_regular))

    val title01 = TextStyle(fontFamily = bold, fontSize = 32.sp)
    val title02 = TextStyle(fontFamily = bold, fontSize = 24.sp)
    val title03 = TextStyle(fontFamily = bold, fontSize = 20.sp)
    val title04 = TextStyle(fontFamily = bold, fontSize = 18.sp)
    val subTitle01 = TextStyle(fontFamily = semiBold, fontSize = 18.sp)
    val subTitle02 = TextStyle(fontFamily = regular, fontSize = 18.sp)
    val subTitle03 = TextStyle(fontFamily = semiBold, fontSize = 16.sp)
    val subTitle04 = TextStyle(fontFamily = regular, fontSize = 16.sp)
    val subTitle05 = TextStyle(fontFamily = semiBold, fontSize = 14.sp)
    val body01 = TextStyle(fontFamily = medium, fontSize = 14.sp)
    val body02 = TextStyle(fontFamily = medium, fontSize = 12.sp)
    val caption = TextStyle(fontFamily = medium, fontSize = 10.sp)
}
