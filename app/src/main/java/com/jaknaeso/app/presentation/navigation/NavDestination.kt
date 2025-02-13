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
fun NavController.navigateToBalanceRound(bundleIndex: String) = navigate("${Route.BalanceRound}/${bundleIndex}")
fun NavController.navigateToSliderRound(bundleindex: String) = navigate("${Route.SliderRound}/${bundleindex}")
fun NavController.navigateToReport() = navigate("${Route.Report}")
fun NavController.navigateToProfile() = navigate("${Route.Profile}")
fun NavController.navigateToRoundComplete() = navigate("${Route.RoundComplete}")

fun NavGraphBuilder.loginScreen(navigateToHome: () -> Unit) {
    composable(route = "${Route.Login}") {
        LoginScreen(navigateToHome = navigateToHome)
    }
}

fun NavGraphBuilder.homeScreen(
    navigateToReport: () -> Unit,
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

fun NavGraphBuilder.balanceRoundScreen(
    navigateToBack: () -> Unit,
    navigateToBalanceRoundComplete: () -> Unit,
) {
    composable(
        route = "${Route.BalanceRound}/{roundIndex}",
        arguments = listOf(navArgument("roundIndex") { type = NavType.StringType })
    ) {
        val roundIndex = it.arguments?.getString("roundIndex")
        if (roundIndex != null) {
            BalanceRoundScreen(
                navigateToBalanceRoundComplete = navigateToBalanceRoundComplete,
                navigateToBack = navigateToBack,
                roundIndex = roundIndex,
            )
        } else {
            Text("유효하지 않은 페이지입니다 :(", style = TextStyles.title01, modifier = Modifier.fillMaxSize())
        }
    }
}

fun NavGraphBuilder.sliderRoundScreen(
    navigateToBack: () -> Unit,
    navigateToRoundComplete: () -> Unit,
) {
    composable(
        route = "${Route.BalanceRound}/{roundIndex}",
        arguments = listOf(navArgument("roundIndex") { type = NavType.StringType })
    ) {
        val roundIndex = it.arguments?.getString("roundIndex")

        if (roundIndex != null) {
            SliderRoundScreen(
                navigateToBalanceRoundComplete = navigateToRoundComplete,
                navigateToBack = navigateToBack,
                roundIndex = roundIndex,
            )
        } else {
            Text("유효하지 않은 페이지입니다 :(", style = TextStyles.title01, modifier = Modifier.fillMaxSize())
        }
    }

}


fun NavGraphBuilder.balanceRoundCompleteScreen(navigateToHome: () -> Unit) {
    composable(route = "${Route.RoundComplete}") {
        RoundCompleteScreen(navigateToHome)
    }
}

fun NavGraphBuilder.reportScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    composable(route = "${Route.Report}") {
        ReportScreen(
            navigateToHome = navigateToHome,
            navigateToProfile = navigateToProfile,
        )
    }
}

fun NavGraphBuilder.profileScreen(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
) {
    composable(route = "${Route.Profile}") {
        ProfileScreen(
            navigateToHome = navigateToHome,
            navigateToReport = navigateToReport,
        )
    }
}

