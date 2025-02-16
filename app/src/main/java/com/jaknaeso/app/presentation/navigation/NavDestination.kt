package com.jaknaeso.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jaknaeso.app.designSystem.component.ErrorInfoView
import com.jaknaeso.app.presentation.view.*

val NO_SURVEY_INDEX = "NO_SURVEY_INDEX"
val NO_BUNDLE_ID = "NO_BUNDLE_ID"

fun NavController.navigateToLogin() = navigate("${Route.Login}")
fun NavController.navigateToHome() = navigate("${Route.Home}")
fun NavController.navigateToRound(bundleId: String, remainingRounds: String) =
    navigate("${Route.Round}/${bundleId}/${remainingRounds}")

fun NavController.navigateToReport(bundleId: String, surveyIndex: String) =
    navigate("${Route.Report}/${bundleId}/${surveyIndex}")

fun NavController.navigateToProfile() = navigate("${Route.Profile}")
fun NavController.navigateToRoundComplete(remainingRounds: String) =
    navigate("${Route.RoundComplete}/${remainingRounds}")

fun NavController.navigateToOnboarding() = navigate("${Route.Onboarding}")

fun NavGraphBuilder.loginScreen(navigateToHome: () -> Unit, navigateToOnboarding: () -> Unit) {
    composable(route = "${Route.Login}") {
        LoginScreen(navigateToHome = navigateToHome, navigateToOnBoarding = navigateToOnboarding)
    }
}

fun NavGraphBuilder.onboardingScreen(navigateToHome: () -> Unit) {
    composable(route = "${Route.Onboarding}") {
        OnBoardingScreen(navigateToHome = navigateToHome)
    }
}

fun NavGraphBuilder.homeScreen(
    navigateToReport: (bundleId: String, surveyIndex: String) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToBalanceRound: (roundIndex: String, remaingRounds: String) -> Unit
) {
    composable(route = "${Route.Home}") {
        HomeScreen(
            navigateToReport = navigateToReport,
            navigateToProfile = navigateToProfile,
            navigateToBalanceRound = navigateToBalanceRound
        )
    }
}

fun NavGraphBuilder.roundScreen(
    navigateToBack: () -> Unit,
    navigateToBalanceRoundComplete: (remaingRounds: String) -> Unit,
) {
    composable(
        route = "${Route.Round}/{bundleId}/{remainingRounds}",
        arguments = listOf(
            navArgument("bundleId") { type = NavType.StringType },
            navArgument("remainingRounds") { type = NavType.StringType })
    ) {
        val bundleId = it.arguments?.getString("bundleId")
        val remainingRounds = it.arguments?.getString("remainingRounds")
        if (bundleId != null && remainingRounds != null) {
            RoundScreen(
                navigateToRoundComplete = { navigateToBalanceRoundComplete(remainingRounds) },
                navigateToBack = navigateToBack,
                bundleIndex = bundleId,
            )
        } else {
            ErrorInfoView(title = "오류가 발생했어요!", message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.", {}, {})
        }
    }
}

fun NavGraphBuilder.RoundCompleteScreen(navigateToHome: () -> Unit) {
    composable(
        route = "${Route.RoundComplete}/{remainingRounds}",
        arguments = listOf(navArgument("remainingRounds") { type = NavType.StringType })
    ) {
        val remainingRounds = it.arguments?.getString("remainingRounds")
        if (remainingRounds != null) {
            RoundCompleteScreen(
                navigateToHome,
                remaingRounds = remainingRounds.toInt() - 1
            )
        } else {
            ErrorInfoView(title = "오류가 발생했어요!", message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.", {}, {})
        }
    }
}

fun NavGraphBuilder.reportScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    composable(
        route = "${Route.Report}/{bundleId}/{surveyIndex}",
        arguments = listOf(
            navArgument("bundleId") { type = NavType.StringType },
            navArgument("surveyIndex") { type = NavType.StringType })
    ) {
        val bundleId = it.arguments?.getString("bundleId")
        val surveyIndex = it.arguments?.getString("surveyIndex")
        if (surveyIndex != null) { //나의 답변 모아보기의 회차를 포커싱해주기
            if (surveyIndex == NO_SURVEY_INDEX) {
                ReportScreen(
                    navigateToHome = navigateToHome,
                    navigateToProfile = navigateToProfile,
                    bundleId = NO_BUNDLE_ID,
                    surveyIndex = surveyIndex
                )
            } else if (bundleId != null) {
                ReportScreen(
                    navigateToHome = navigateToHome,
                    navigateToProfile = navigateToProfile,
                    bundleId = bundleId,
                    surveyIndex = surveyIndex
                )
            } else {
                ErrorInfoView(title = "오류가 발생했어요!", message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.", {}, {})
            }
        }
    }
}

fun NavGraphBuilder.profileScreen(
    navigateToHome: () -> Unit,
    navigateToReport: (bundleId: String, surveyIndex: String) -> Unit,
    navigateToLogin: () -> Unit
) {
    composable(route = "${Route.Profile}") {
        ProfileScreen(
            navigateToHome = navigateToHome,
            navigateToReport = navigateToReport,
            navigateToLogin = navigateToLogin
        )
    }
}

