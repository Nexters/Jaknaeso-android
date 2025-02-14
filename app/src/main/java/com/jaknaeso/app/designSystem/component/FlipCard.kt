package com.jaknaeso.app.designSystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun FlipAnimation(
    forwardColor: Color = Color.White,
    backwardColor: Color = ColorPalette.Neautral50,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    modifier: Modifier,
    onFlipped : (isCardFlipped:Boolean)->Unit
) {
    var isCardFlipped by remember { mutableStateOf(false) }
    val animDuration = 900
    val zAxisDistance = 200f // distance between camera and Card

    val rotateCardY by animateFloatAsState(
        targetValue = if (isCardFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = animDuration, easing = EaseInOut),
        label = ""
    )

    Column(
        modifier = Modifier.then(modifier)
            .graphicsLayer {
                rotationY = rotateCardY
                cameraDistance = zAxisDistance
            }
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onFlipped(!isCardFlipped)
                isCardFlipped = !isCardFlipped
            }
            .background(if (isCardFlipped) backwardColor else forwardColor),
    ) {
        if (rotateCardY <= 90f) {
            frontContent() // 앞면 콘텐츠
        } else {
            // 회전이 90도를 초과하면 뒷면을 보여주되, 좌우 반전 없이 정방향 표시
            Box(Modifier.graphicsLayer { rotationY = 180f }) {
                backContent()
            }
        }
    }
}

@Preview
@Composable
fun FlipCardPreview() {
    Column(
        Modifier
            .background(ColorPalette.Neautral0)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlipAnimation(
            forwardColor = Color.White,
            backwardColor = ColorPalette.PrimaryBlue100,
            frontContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("앞면 내용", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            },
            backContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("뒷면 내용", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            modifier = Modifier.size(300.dp, 300.dp),
            onFlipped = {}
        )
    }
}

