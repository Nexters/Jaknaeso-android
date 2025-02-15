package com.jaknaeso.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.view.*

fun NavController.navigateToLogin() = navigate("${Route.Login}")
fun NavController.navigateToHome() = navigate("${Route.Home}")
fun NavController.navigateToRound(roundIndex: String) = navigate("${Route.Round}/${roundIndex}")
fun NavController.navigateToReport(bundleIndex: String) = navigate("${Route.Report}/${bundleIndex}")
fun NavController.navigateToProfile() = navigate("${Route.Profile}")
fun NavController.navigateToRoundComplete() = navigate("${Route.RoundComplete}")
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
    navigateToReport: (bundleIndex: String) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToBalanceRound: (roundIndex: String) -> Unit
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
    navigateToBalanceRoundComplete: () -> Unit,
) {
    composable(
        route = "${Route.Round}/{bundleIndex}",
        arguments = listOf(navArgument("bundleIndex") { type = NavType.StringType })
    ) {
        val bundleIndex = it.arguments?.getString("bundleIndex")
        if (bundleIndex != null) {
            RoundScreen(
                navigateToRoundComplete = navigateToBalanceRoundComplete,
                navigateToBack = navigateToBack,
                bundleIndex = bundleIndex,
            )
        } else {
            Text("유효하지 않은 페이지입니다 :(", style = TextStyles.title01, modifier = Modifier.fillMaxSize())
        }
    }
}

fun NavGraphBuilder.RoundCompleteScreen(navigateToHome: () -> Unit) {
    composable(route = "${Route.RoundComplete}") {
        com.jaknaeso.app.presentation.view.RoundCompleteScreen(navigateToHome)
    }
}

fun NavGraphBuilder.reportScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    composable(
        route = "${Route.Report}/{bundleIndex}",
        arguments = listOf(navArgument("bundleIndex") { type = NavType.StringType })
    ) {
        val bundleIndex = it.arguments?.getString("bundleIndex")
        ReportScreen(
            navigateToHome = navigateToHome,
            navigateToProfile = navigateToProfile,
            bundleId = bundleIndex
        )
    }
}

fun NavGraphBuilder.profileScreen(
    navigateToHome: () -> Unit,
    navigateToReport: (bundleIndex: String) -> Unit,
) {
    composable(route = "${Route.Profile}") {
        ProfileScreen(
            navigateToHome = navigateToHome,
            navigateToReport = navigateToReport,
        )
    }
}

