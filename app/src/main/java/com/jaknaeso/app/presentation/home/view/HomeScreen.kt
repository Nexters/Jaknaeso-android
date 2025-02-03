package com.jaknaeso.app.presentation.home.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.DragHandle
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LoopyShapeFilledButton
import com.jaknaeso.app.designSystem.component.LoopySuggestionChip
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.entity.Round
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(navigateToHome: () -> Unit, navigateToReport: () -> Unit, navigateToProfile: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = ColorPalette.Neautral100),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = navigateToHome,
                navigateToReport = navigateToReport,
                navigateToProfile = navigateToProfile
            )
        }) {
        Column(
            modifier = Modifier.background(color = ColorPalette.Neautral100).fillMaxSize(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                LoopySuggestionChip(
                    "1번째 캐릭터",
                    labelStyle = TextStyles.subTitle04,
                    filledColor = ColorPalette.Neautral200,
                    labelColor = ColorPalette.Neautral700,
                    shape = RoundedCornerShape(8.dp)
                )
                Text("{valueType}\n두 줄인 경우", style = TextStyles.title01)
            }
            Column {
                BottomSheetContent(
                    onClick = { isExpanded = !isExpanded },
                    isExpanded = isExpanded,
                    rounds = emptyList<Round>().toImmutableList()
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(isExpanded: Boolean, onClick: () -> Unit, rounds: ImmutableList<Round>) {
    val ROW = 5
    val HIDDEN_COLUMN = 2
    val chunkedRounds = rounds.chunked(ROW)
    val firstRowRound = chunkedRounds[0]
    val hiddedRowRounds = chunkedRounds.subList(1, chunkedRounds.size)
    Column(
        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(20.dp)).fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        DragHandle(onClick = onClick)
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(firstRowRound) { round ->
                QuestionItem(round)
            }
        }
        // 추가 LazyRow (애니메이션 적용)
        AnimatedVisibility(visible = isExpanded) {
            Column {
                repeat(HIDDEN_COLUMN) { index ->
                    val animatedOffset by animateDpAsState(
                        targetValue = if (isExpanded) (-20 * (index + 1)).dp else 0.dp, // 위로 이동
                        animationSpec = tween(durationMillis = 300)
                    )
                    LazyColumn {
                        items(hiddedRowRounds) { rowRound ->
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = animatedOffset)
                                    .padding(vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                items(rowRound) { round ->
                                    QuestionItem(round)
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(34.dp))
        LoopyFilledButton(
            text = "오늘의 질문 답변하기",
            textStyle = TextStyles.subTitle01,
            onClick = {},
            modifier = Modifier.fillMaxWidth(1f).offset(y = if (isExpanded) (-50).dp else 0.dp).padding(bottom = 28.dp),
        )
    }
}

@Composable
fun QuestionItem(round: Round) {
    val ROW = 5
    val size = (LocalConfiguration.current.screenWidthDp - (6 * 20)).div(ROW)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LoopyShapeFilledButton(
            enabled = true,
            icon = painterResource(R.drawable.ic_lock),
            shape = CircleShape,
            modifier = Modifier.sizeIn(minWidth = size.dp, minHeight = size.dp)
        )
        Text("n회차", style = TextStyles.subTitle03, modifier = Modifier.padding(top = 6.dp))
    }
}
