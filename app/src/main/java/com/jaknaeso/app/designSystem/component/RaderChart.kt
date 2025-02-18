package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarChart(
    values: List<Float>,  // 8개의 값 (0~1 범위 가정)
    maxRadius: Float = 250f, // 차트 크기
    labels: List<String> = listOf(
        "성장", "평화", "사회", "안전", "인정", "성취", "개인", "자유"
    )
) {
    val numAxes = values.size
    val angleStep = (2 * PI / numAxes).toFloat()
    val fontColors = mutableListOf<Color>()

    Box(modifier = Modifier.size((maxRadius * 2).dp), contentAlignment = Alignment.TopStart) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val points = mutableListOf<Offset>()

            // 거미줄 그리기
            drawRadarLayer(center, numAxes, angleStep, maxRadius, ColorPalette.Neautral600)

            // 축 그리기
            for (i in 0 until numAxes) {
                val angle = angleStep * i - PI.toFloat() / 2
                val endX = center.x + maxRadius * cos(angle)
                val endY = center.y + maxRadius * sin(angle)
                drawLine(ColorPalette.Neautral600, center, Offset(endX, endY), strokeWidth = 1.16f)
            }

            // 데이터 그리기
            val path = Path()
            values.forEachIndexed { index, value ->
                val angle = angleStep * index - PI.toFloat() / 2
                val radius = maxRadius * value
                val x = center.x + radius * cos(angle)
                val y = center.y + radius * sin(angle)
                val fontColor = if (value > 0.5f) Color.Black else ColorPalette.Neautral600
                fontColors.add(fontColor)
                points.add(Offset(x, y))
            }

            path.moveTo(points[0].x, points[0].y)
            points.forEach { path.lineTo(it.x, it.y) }
            path.close()
            drawPath(path, ColorPalette.PrimaryBlue400.copy(alpha = 0.5f))
            drawPath(path, ColorPalette.PrimaryBlue500, style = Stroke(width = 2.9f))
        }

        // 축 레이블 추가
        labels.forEachIndexed { index, label ->
            val angle = angleStep * index - PI.toFloat() / 2
            val radius = maxRadius * 0.48f // 레이블 위치 조정
            val x = maxRadius + radius * cos(angle) - 64
            val y = maxRadius + radius * sin(angle) - 7
            Box(
                modifier = Modifier.offset(x.dp, y.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = TextStyles.body02,
                    color = fontColors[index],
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// 방사형 격자 그리기
private fun DrawScope.drawRadarLayer(
    center: Offset,
    numAxes: Int,
    angleStep: Float,
    radius: Float,
    color: Color
) {
    val path = Path()
    for (i in 0 until numAxes) {
        val angle = angleStep * i - PI.toFloat() / 2
        val x = center.x + radius * cos(angle)
        val y = center.y + radius * sin(angle)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color, style = Stroke(width = 1.2f))
}


@Preview
@Composable
fun RaderChartPreview() {
    RadarChart(
        values = listOf(0.6f, 1f, 0.7f, 0.3f, 0.5f, 0.3f, 0.45f, 0.4f),  // 8개의 값 (0~1 범위 가정)
    )
}
