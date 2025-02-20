package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.*
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.Character
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.model.RoundResult
import com.jaknaeso.app.presentation.contract.ReportEffect
import com.jaknaeso.app.presentation.contract.ReportEvent
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar
import com.jaknaeso.app.presentation.navigation.NO_BUNDLE_ID
import com.jaknaeso.app.presentation.navigation.NO_SURVEY_INDEX
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.viewmodel.ReportViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    viewmodel: ReportViewmodel = hiltViewModel(),
    bundleId: String, //클릭해서 들어오는 라운드의 bundleId
    characterId: String, //최신 캐릭터의 결과값을 볼 수 있는 characterId.
    surveyIndex: String //클릭해서 들어오는 라운드 index
) {
    val initialTabPage by remember { mutableStateOf(if (surveyIndex != NO_SURVEY_INDEX && bundleId != NO_BUNDLE_ID) 1 else 0) }
    val uistate = viewmodel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { 2 })
    var isModalExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewmodel.handleEvent(ReportEvent.GetCharactersList(bundleId)) //캐릭터리스트 먼저 호출하고 나면 CompletedLoadCharacterList에서 캐릭터가 얻어짐

        if (surveyIndex != NO_SURVEY_INDEX && bundleId != NO_BUNDLE_ID) { //캐릭터리스트가 로드된 후에 호출할 필요가 없음
            pagerState.animateScrollToPage(1)
            viewmodel.handleEvent(
                ReportEvent.GetParticularCharacterData(bundleId)
            )
        }
        viewmodel.effects.collectLatest { effect ->
            when (effect) {
                ReportEffect.NavigateToHome -> navigateToHome()
                ReportEffect.NavigateToProfile -> navigateToProfile()
                ReportEffect.NavigateToLogin -> navigateToLogin()
                ReportEffect.CompletedLoadCharacterList -> viewmodel.handleEvent(ReportEvent.GetFirstCharacterData)
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = ColorPalette.Neautral0)
            .windowInsetsPadding(WindowInsets.statusBars),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = navigateToHome,
                navigateToReport = {},
                navigateToProfile = navigateToProfile,
                currentRoute = Route.Report
            )
        },
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(color = ColorPalette.Neautral0).padding(paddingValues),
                    verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
                ) {
                    if (isModalExpanded) {
                        BasicBottomSheet(
                            isVisible = isModalExpanded,
                            onDismiss = { isModalExpanded = false }
                        ) {
                            CharacterSelectModalContent(
                                onModalTitleClick = { isModalExpanded = false },
                                onSelectionChanged = { characterNo, characterId, bundleId ->
                                    viewmodel.handleEvent(
                                        ReportEvent.SelectCharacterData(
                                            characterNo = characterNo,
                                            characterId = characterId.toString(),
                                            bundleId = bundleId.toString()
                                        )
                                    )
                                    isModalExpanded = !isModalExpanded
                                },
                                characters = uistate.value.characters ?: emptyList()
                            )
                        }
                    }
                    Column {
                        Spacer(Modifier.fillMaxWidth().height(54.dp))
                        Column(Modifier.padding(horizontal = 20.dp)) {
                            LoopyAssistChip(
                                onClick = { isModalExpanded = !isModalExpanded },
                                label = uistate.value.reportTitle,
                                labelStyle = TextStyles.title03,
                                filledColor = Color.Transparent,
                                labelColor = Color.Black,
                                shape = RoundedCornerShape(8.dp),
                                trailingIcon = painterResource(R.drawable.ic_arrow_down),
                                trailingIconColor = ColorPalette.Neautral600
                            )
                            Spacer(modifier = Modifier.fillMaxWidth(1f).height(20.dp))
                        }
                        LoopyTabBar(
                            initialPage = initialTabPage,
                            tabBarTitles = listOf("캐릭터 분석", "나의 답변 모아보기"),
                            onPage = { index ->
                                scope.launch {
                                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % 2)
                                }
                            })
                        if (uistate.value.isLoading) {
                            LoopyLoadingScreen()
                        } else {
                            HorizontalPager(state = pagerState, userScrollEnabled = false) { page ->
                                when (page) {
                                    0 -> {
                                        if (uistate.value.isNoCharacterToShow) {
                                            NoCharacterToShow(characterNo = uistate.value.reportTitle)
                                        } else {
                                            CharacterAnalysisView(
                                                report = uistate.value.report,
                                                uistate.value.submissionsResult ?: emptyList()
                                            )
                                        }
                                    }

                                    1 -> MyAnswersView(uistate.value.submissionsResult ?: emptyList(), surveyIndex)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MyAnswersView(submissionResults: List<RoundResult>, expandedSurveyItemAtInitialized: String) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val index = if (submissionResults.size > 1) 10 else 1 //왜 안 되지
            listState.animateScrollToItem(index)
        }
    }

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.fillMaxWidth().height(40.dp))
        LazyColumn(Modifier.padding(horizontal = 20.dp)) {
            itemsIndexed(items = submissionResults, key = { index, item -> item.index }) { index, data ->
                DropdownCard(
                    initialExpanded = expandedSurveyItemAtInitialized == (index + 1).toString(),
                    shellContent = {
                        DropdownCardShellContent(index)
                    },
                    mainContent = {
                        DropdownCardMainContent(data)
                    })
                Spacer(modifier = Modifier.fillMaxWidth().height(18.dp))
            }
        }
    }
}

@Composable
fun NoCharacterToShow(characterNo: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieImageView(rawFile = R.raw.doing_phone, width = 200.dp, height = 200.dp)
            Spacer(Modifier.height(24.dp))
            Text(
                "${characterNo}를\n만드는 중이에요",
                textAlign = TextAlign.Center,
                style = TextStyles.title02,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "15일 간의 가치관 질문에\n응답하면 캐릭터가 완성돼요.",
                textAlign = TextAlign.Center,
                style = TextStyles.subTitle03,
                color = ColorPalette.Neautral600
            )
        }
    }
}

@Composable
fun CharacterSelectModalContent(
    onModalTitleClick: () -> Unit,
    onSelectionChanged: (characterNo: String, characterId: Int, bundleId: Int) -> Unit,
    characters: List<Character>
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(1f).padding(bottom = 28.dp).padding(horizontal = 20.dp).padding(top = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "가치관 캐릭터 선택하기", style = TextStyles.title03)
            IconButton(
                enabled = true,
                onClick = onModalTitleClick,
                modifier = Modifier.background(color = Color.Transparent).size(15.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
        RadioButtons(
            characters = characters,
            onChanged = { characterNo, characterId, bundleId ->
                onSelectionChanged(
                    characterNo,
                    characterId,
                    bundleId
                )
            })
    }
}

@Composable
fun RadioButtons(
    characters: List<Character>,
    onChanged: (characterNo: String, characterId: Int, bundleId: Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(-1) } // 하나만 선택 가능하도록 변경

    LazyColumn(modifier = Modifier.padding(start = 4.dp, end = 12.dp)) {
        itemsIndexed(characters) { index, item ->
            SelectionFilterChip(
                isSelected = (index == selectedIndex),
                label = { Text(text = "${item.characterNo}", style = TextStyles.subTitle01) },
                shape = RoundedCornerShape(10.dp),
                onClick = { isSelected ->
                    if (!isSelected) { // 선택되지 않은 경우만 변경
                        selectedIndex = index
                        onChanged(item.characterNo, item.characterId, item.bundleId) // 선택 변경 이벤트 전달
                    }
                },
                trailingIcon = painterResource(R.drawable.ic_check),
                selectedIconColor = ColorPalette.PrimaryBlue500,
                modifier = Modifier.fillMaxWidth(1f),
                filledColor = Color.White
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
        }
    }
}

@Composable
fun CharacterAnalysisView(report: CharacterReport, submissionResults: List<RoundResult>) {
    Column(
        modifier = Modifier
            .background(color = ColorPalette.Neautral0).fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
    ) {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(bottom = 40.dp).padding(horizontal = 20.dp)
                    ) {
                        LottieImageView(report.lottieRawFile, width = 280.dp, height = 280.dp)
                        Text(
                            text = report.name ?: "",
                            style = TextStyles.title03,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp),
                            softWrap = true
                        )
                        LoopySuggestionChip(
                            enabled = false,
                            label = report.duration ?: "",
                            labelStyle = TextStyles.body02,
                            labelColor = ColorPalette.Neautral700,
                            shape = RoundedCornerShape(5.dp),
                            height = 26.dp
                        )
                        Text(
                            report.description ?: "",
                            style = TextStyles.subTitle04,
                            color = ColorPalette.Neautral700,
                            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 60.dp),
                            softWrap = true,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(color = ColorPalette.Neautral300)
                    )
                    Column(Modifier.padding(horizontal = 20.dp).padding(top = 48.dp, bottom = 46.dp)) {
                        DotBoard(titleContent = "주요 특징", contents = report.mainTraits)
                    }
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(color = ColorPalette.Neautral300)
                    )
                    Column(Modifier.padding(horizontal = 20.dp).padding(top = 48.dp, bottom = 46.dp)) {
                        DotBoard(titleContent = "강점", contents = report.strengths)
                    }
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(color = ColorPalette.Neautral300)
                    )
                    Column(Modifier.padding(horizontal = 20.dp).padding(top = 48.dp, bottom = 46.dp)) {
                        DotBoard(titleContent = "단점", contents = report.weaknesses)
                    }
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(color = ColorPalette.Neautral300)
                    )
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(vertical = 48.dp).padding(horizontal = 20.dp)
                    ) {
                        Text("가치관 선택 비율", style = TextStyles.title03, modifier = Modifier.padding(bottom = 8.dp))
                        Text(
                            text = report.keywordStrenthDescription,
                            style = TextStyles.subTitle04,
                            color = ColorPalette.Neautral700,
                            softWrap = true,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                        Column(
                            Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            RadarChart(values = report.keywordPercentage, extraHorizontalPadding = 20.dp)
                        }
                    }
//                    Spacer(
//                        modifier = Modifier.fillMaxWidth().height(1.dp).background(color = ColorPalette.Neautral300)
//                    )
//                    Column(modifier = Modifier.padding(top = 40.dp, bottom = 24.dp).padding(horizontal = 20.dp)) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(
//                                text = "회고를 자주 남긴 주제는 ",
//                                style = TextStyles.title03,
//                                color = Color.Black,
//                                modifier = Modifier.padding(end = 9.dp)
//                            )
//                            LoopyAssistChip(
//                                enabled = false,
//                                label = "{value}",
//                                labelStyle = TextStyles.subTitle03,
//                                filledColor = ColorPalette.PrimaryBlue100,
//                                labelColor = ColorPalette.PrimaryBlue500,
//                                shape = RoundedCornerShape(5.dp),
//                            )
//                            Text(
//                                text = " 예요.",
//                                style = TextStyles.title03,
//                                color = Color.Black,
//                                modifier = Modifier.padding(start = 7.dp)
//                            )
//                        }
//                    }
                }
            }
//            itemsIndexed(items = submissionResults) { index, item ->
//                Column(Modifier.padding(horizontal = 20.dp)) {
//                    DropdownCard(
//                        shellContent = {
//                            DropdownCardShellContent(index = index)
//                        },
//                        initialExpanded = false,
//                        mainContent = {
//                            DropdownCardMainContent(item)
//                        }
//                    )
//                }
//                Spacer(modifier = Modifier.fillMaxWidth().height(18.dp))
//            }
        }
    }
}

@Composable
fun DropdownCardShellContent(index: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${index + 1}회차", style = TextStyles.subTitle03)
        Icon(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun DropdownCardMainContent(data: RoundResult) {
    Column {
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
        LoopyAssistChip(
            label = data.submittedAt,
            labelStyle = TextStyles.body02,
            filledColor = ColorPalette.Neautral200,
            labelColor = ColorPalette.Neautral700,
            shape = RoundedCornerShape(6.dp),
            enabled = false
        )
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Q.", style = TextStyles.body01, modifier = Modifier.padding(end = 8.dp))
            Text(
                data.question,
                style = TextStyles.body01,
                softWrap = true
            )
        }
        Spacer(
            modifier = Modifier.fillMaxWidth().height(1.dp)
                .background(color = ColorPalette.Neautral300)
        )
        Row(
            verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        ) {
            Text("A.", style = TextStyles.body01, modifier = Modifier.padding(end = 8.dp))
            Text(
                data.answer,
                style = TextStyles.body01,
                softWrap = true
            )
        }
        Column(
            modifier = Modifier.background(
                color = ColorPalette.Neautral100,
                shape = RoundedCornerShape(12.dp)
            ).padding(vertical = 12.dp).padding(horizontal = 16.dp).fillMaxWidth(1f)
        ) {
            Text(
                "회고",
                style = TextStyles.body01,
                color = ColorPalette.Neautral800,
                modifier = Modifier.padding(bottom = 8.dp),
                softWrap = true
            )
            Text(
                data.word,
                style = TextStyles.body01, color = ColorPalette.Neautral700, softWrap = true
            )
        }
    }
}

@Preview
@Composable
fun CharacterAnalysisPreview() {
    CharacterAnalysisView(
        report = CharacterReport(
            0,
            "첫번째 캐릭터",
            "잔다르크",
            R.raw.benevolence,
            "성장을 중요시 여기는 모함가 타입은 새로운 즐거움을 발굴하는 것을 가장 중요시 여기는 유형이에요",
            "2024.10.10 - 2024.10.25",
            mainTraits = emptyList(),
            strengths = emptyList(),
            emptyList(),
            keywordStrenthDescription = "",
            keywordPercentage = listOf(0.3f, 0.5f, 0.6f, 0.7f, 0.5f, 0.6f, 0.7f)
        ),
        emptyList()
    )
}

@Preview
@Composable
fun MyAnswersReportPreview() {
    MyAnswersView(emptyList(), "1")
}

@Preview
@Composable
fun NoCharacterToShowPreview() {
    NoCharacterToShow("두번째 캐릭터")
}
