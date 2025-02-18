package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.FlipAnimation
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LoopyTopBar
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.presentation.contract.RoundEvent

@Composable
fun BalanceRoundScreen(
    question: RoundQuestion?,
    handleEvent: (RoundEvent) -> Unit,
    isModalExpanded: Boolean,
    surveyId: String?,
    enteredComment: String,
    onChangedCommentValue: (value: String) -> Unit
) {
    var isCardFlipped by remember { mutableStateOf(false) }

    if (question != null && surveyId != null) {
        Scaffold(
            modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral0).windowInsetsPadding(WindowInsets.navigationBars)
        ) { paddingValue ->
            if (isModalExpanded) {
                UserCommentModal(
                    enteredComment = enteredComment,
                    onChangedCommentValue = onChangedCommentValue,
                    handleEvent = handleEvent
                )
            }
            Column(
                Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral0),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                LoopyTopBar(
                    title = "오늘의 질문",
                    icon = painterResource(R.drawable.ic_back),
                    onClickIcon = { handleEvent(RoundEvent.ClickBackButton) })
                Column(
                    Modifier.padding(horizontal = 40.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Text(
                            question.content,
                            style = TextStyles.title03,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            softWrap = true
                        )
                        Spacer(Modifier.fillMaxWidth(1f).height(20.dp))
                        FlipAnimation(
                            isCardFlipped = isCardFlipped,
                            forwardColor = Color.White,
                            backwardColor = Color.White,
                            frontContent = {
                                BalanceContent(
                                    title = "첫번째 선택지",
                                    option = question.options[0].optionContents,
                                    onClickFlip = {
                                        isCardFlipped = !isCardFlipped
                                        handleEvent(RoundEvent.SelectOption(question.options[0].id))
                                    }
                                )
                            },
                            backContent = {
                                BalanceContent(
                                    title = "두번째 선택지",
                                    option = question.options[1].optionContents,
                                    onClickFlip = {
                                        isCardFlipped = !isCardFlipped
                                        handleEvent(RoundEvent.SelectOption(question.options[1].id))
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth(1f).aspectRatio(0.94f).fillMaxHeight(1f),
                            onFlipped = {}
                        )
                    }
                    LoopyFilledButton(
                        "작성 완료",
                        onClick = { handleEvent(RoundEvent.OpenModal) },
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceContent(title: String, option: String, onClickFlip: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp).padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp).padding(vertical = 50.dp)
        ) {
            Text(
                title,
                style = TextStyles.subTitle03,
                color = ColorPalette.Neautral600,
                modifier = Modifier.padding(bottom = 28.dp)
            )
            Text(
                text = option,
                style = TextStyles.title03,
                color = Color.Black,
                textAlign = TextAlign.Center,
                softWrap = true
            )
        }
        LoopyFilledButton(
            "카드 뒤집기",
            onClick = onClickFlip,
            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
            height = 50.dp,
            filledColor = Color.White,
            textColor = ColorPalette.Neautral500,
            borderColor = ColorPalette.Neautral400,
            trailingIcon = painterResource(R.drawable.ic_flip),
            trailingIconColor = ColorPalette.Neautral500
        )
    }
}


@Preview
@Composable
private fun BalanceRoundPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral0)
    ) { paddingValue ->
        Column(
            Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral0),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LoopyTopBar(
                title = "오늘의 질문",
                icon = painterResource(R.drawable.ic_back),
                onClickIcon = { })
            Column(
                Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    Text(
                        "독립에 대한 고민이 깊어지는 요즘... 드디어 결정을 내렸다.",
                        style = TextStyles.title03,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        softWrap = true
                    )
                    Spacer(Modifier.fillMaxWidth(1f).height(20.dp))
                    FlipAnimation(
                        isCardFlipped = false,
                        forwardColor = Color.White,
                        backwardColor = ColorPalette.PrimaryBlue100,
                        frontContent = {
                            BalanceContent(title = "첫번째 선택지", option = "주변 사람과 물리적으로 멀어지더라도, 커리어를 선택한다.",{})
                        },
                        backContent = {
                            BalanceContent(title = "두번째 선택지", option = "가족과 함께 살며 따뜻한 식사와 생활비 걱정 없는 일상을 선택한다.",{})
                        },
                        modifier = Modifier.fillMaxWidth(1f).padding(40.dp).aspectRatio(0.94f),
                        onFlipped = {}
                    )
                }
                LoopyFilledButton(
                    "작성 완료",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }

    }
}
