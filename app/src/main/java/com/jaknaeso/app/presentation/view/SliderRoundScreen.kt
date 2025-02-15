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
import com.jaknaeso.app.designSystem.component.LoopyTopBar
import com.jaknaeso.app.designSystem.component.VerticalSliderForm
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.Option
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyType
import com.jaknaeso.app.presentation.contract.RoundEvent

@Composable
fun SliderRoundScreen(
    headerContent: @Composable () -> Unit,
    footerContent: @Composable () -> Unit,
    question: RoundQuestion?,
    handleEvent: (RoundEvent) -> Unit,
    isModalExpanded: Boolean,
    surveyId: String?,
    enteredComment: String,
    onChangedCommentValue: (value: String) -> Unit
) {
    if (question != null && surveyId != null) {
        Scaffold(
            modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral50)
        ) { paddingValue ->
            if (isModalExpanded) {
                UserCommentModal(
                    enteredComment = enteredComment,
                    onChangedCommentValue = onChangedCommentValue,
                    handleEvent = handleEvent
                )
            }
            Column(
                Modifier.fillMaxSize(1f).padding(paddingValue).background(color = ColorPalette.Neautral50),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                headerContent()
                SliderContent(question = question, onChangedOption = { roundIndex ->
                    val selectedOption = question.options[roundIndex]
                    handleEvent(RoundEvent.SelectOption(selectedOption.id))
                }, handleEvent = handleEvent, footerContent = footerContent)
            }
        }
    }
}

@Composable
fun SliderContent(
    question: RoundQuestion,
    onChangedOption: (roundIndex: Int) -> Unit,
    handleEvent: (RoundEvent) -> Unit,
    footerContent: @Composable () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) } //0이 VerticalSliderForm 디폴트 값
    Column(
        Modifier.padding(horizontal = 20.dp).padding(top = 54.dp, bottom = 28.dp).fillMaxSize(1f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            question.content,
            style = TextStyles.title03,
            color = Color.Black,
            textAlign = TextAlign.Center,
            softWrap = true
        )
        Spacer(Modifier.fillMaxWidth(1f).height(82.dp))
        Column(Modifier.padding(horizontal = 58.dp).fillMaxHeight(0.8f), verticalArrangement = Arrangement.Top) {
            VerticalSliderForm(
                answerList = question.options.map { it.optionContents },
                onValueChange = { index ->
                    selectedIndex = index
                    onChangedOption(index)
                })
        }
        footerContent()
    }
}

@Preview
@Composable
fun SliderRoundPreview() {
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
                onClickIcon = {})
            SliderContent(
                question = RoundQuestion(
                    surveyId = "0",
                    surveyType = SurveyType.MULTIPLE_CHOICE,
                    content = "짜장면과 짬뽕 뭐가 더 좋아?",
                    options = listOf(
                        Option("0", "짜장"),
                        Option("1", "짬뽕"),
                        Option("0", "짜장"),
                        Option("1", "짬뽕"),
                        Option("0", "짜장")
                    )
                ), onChangedOption = {}, handleEvent = {}, footerContent = {})
        }
    }
}
