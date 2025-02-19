package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorPalette.ModalBackground).clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.wrapContentSize()
                    .background(Color.White, shape = RoundedCornerShape(20.dp)).padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun DialogExample() {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Show Dialog")
        }

        LoopyDialog(
            visible = showDialog,
            onDismiss = { showDialog = false }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 32.dp).padding(vertical = 36.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("정말 탈퇴하실 건가요?", style = TextStyles.title04)
                Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
                Text(
                    "회원 탈퇴 시 지금까지 기록한 정보가\n 전부 삭제되고 복구가 불가능해요.",
                    style = TextStyles.subTitle04,
                    color = ColorPalette.Neautral700,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.fillMaxWidth(1f).height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                    LoopyFilledButton(
                        "더 써볼게요",
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp),
                        height = 47.dp
                    )
                    LoopyFilledButton(
                        "떠날래요",
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                        filledColor = ColorPalette.Neautral200,
                        textColor = ColorPalette.Neautral600,
                        borderColor = ColorPalette.Neautral200,
                        height = 47.dp
                    )
                }
            }
        }
    }
}
