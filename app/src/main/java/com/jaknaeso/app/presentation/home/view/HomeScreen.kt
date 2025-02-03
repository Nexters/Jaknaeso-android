package com.jaknaeso.app.presentation.home.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.DragHandle
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LoopyShapeFilledButton
import com.jaknaeso.app.designSystem.component.LoopySuggestionChip
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.entity.Round
import com.jaknaeso.app.presentation.home.viewmodel.HomeViewmodel
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar

@Composable
fun HomeScreen(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToProfile: () -> Unit,
    viewmodel: HomeViewmodel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = ColorPalette.Neautral100),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = navigateToHome,
                navigateToReport = navigateToReport,
                navigateToProfile = navigateToProfile
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.background(color = ColorPalette.Neautral100).fillMaxSize(1f).padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
                    LoopySuggestionChip(
                        "나의 캐릭터",
                        labelStyle = TextStyles.subTitle04,
                        filledColor = ColorPalette.Neautral200,
                        labelColor = ColorPalette.Neautral700,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Text("순서대로\n질문에 답해보세요", style = TextStyles.title01, modifier = Modifier.padding(top = 10.dp))
                }
                Column(
                    modifier = Modifier.background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                ) {
                    BottomSheetContent(
                        onClick = { isExpanded = !isExpanded },
                        isExpanded = isExpanded,
                        rounds = uiState.value.rounds
                    )
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    LoopyFilledButton(
                        text = "오늘의 질문 답변하기",
                        textStyle = TextStyles.subTitle01,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(1f)
                            .padding(bottom = 28.dp),
                    )
                }
            }
        }
    )
}

@Composable
fun BottomSheetContent(isExpanded: Boolean, onClick: () -> Unit, rounds: List<Round>?) {
    if (rounds?.size ?: 0 > 0) {
        val ROW = 5
        val chunkedRounds = rounds!!.chunked(ROW)
        val firstRowRound = chunkedRounds[0]
        val hiddedRowRounds = chunkedRounds.subList(1, chunkedRounds.size)
        Column(
            modifier = Modifier.background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ).fillMaxWidth()
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
                LazyColumn {
                    itemsIndexed(hiddedRowRounds) { index, rowRound ->
                        val animatedOffset by animateDpAsState(
                            targetValue = if (isExpanded) (-10 * (index + 1)).dp else 0.dp, // 위로 이동
                            animationSpec = tween(durationMillis = 300)
                        )
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
}

data class QuestionItemState(
    val isEnabled: Boolean,
    val icon: Painter,
    val iconColor: Color,
    val filledColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

@Composable
fun QuestionItem(round: Round) {
    val ROW = 5
    val size = (LocalConfiguration.current.screenWidthDp - (6 * 20)).div(ROW)
    val item = round.let {
        if (it.isCompleted && it.isLocked) {
            QuestionItemState(
                isEnabled = false,
                icon = painterResource(R.drawable.ic_check),
                iconColor = ColorPalette.Neautral600,
                filledColor = ColorPalette.Neautral200,
                textColor = ColorPalette.Neautral600,
                textStyle = TextStyles.subTitle04
            )
        } else if (!it.isCompleted && it.isLocked) {
            QuestionItemState(
                isEnabled = false,
                icon = painterResource(R.drawable.ic_lock),
                iconColor = ColorPalette.Neautral400,
                filledColor = ColorPalette.Neautral100,
                textColor = ColorPalette.Neautral400,
                textStyle = TextStyles.subTitle04
            )
        } else {
            QuestionItemState(
                isEnabled = true,
                icon = painterResource(R.drawable.ic_lock),
                iconColor = ColorPalette.PrimaryBlue500,
                filledColor = ColorPalette.PrimaryBlue100,
                textColor = ColorPalette.Neautral900,
                textStyle = TextStyles.subTitle03
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LoopyShapeFilledButton(
            enabled = item.isEnabled,
            icon = item.icon,
            shape = CircleShape,
            modifier = Modifier.sizeIn(minWidth = size.dp, minHeight = size.dp),
            iconColor = item.iconColor,
            disabledIconColor = item.iconColor,
            disabledColor = item.filledColor,
            filledColor = item.filledColor,
        )
        Text(
            "${round.roundIndex + 1}회차",
            style = item.textStyle,
            modifier = Modifier.padding(top = 6.dp),
            color = item.textColor
        )
    }
}
