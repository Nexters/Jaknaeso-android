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
import com.jaknaeso.app.presentation.view.BalanceRoundScreen
import com.jaknaeso.app.presentation.view.HomeScreen
import com.jaknaeso.app.presentation.view.LoginScreen
import com.jaknaeso.app.presentation.view.ReportScreen

fun NavController.navigateToLogin() = navigate("${Route.Login}")
fun NavController.navigateToHome() = navigate("${Route.Home}")
fun NavController.navigateToBalanceRound(roundIndex: String) = navigate("${Route.BalanceRound}/${roundIndex}")
fun NavController.navigateToReport() = navigate("${Route.Report}")

fun NavGraphBuilder.loginScreen(navigateToHome: () -> Unit) {
    composable(route = "${Route.Login}") {
        LoginScreen(navigateToHome = navigateToHome)
    }
}

fun NavGraphBuilder.homeScreen(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToBalanceRound: (roundIndex: String) -> Unit
) {
    composable(route = "${Route.Home}") {
        HomeScreen(
            navigateToHome = navigateToHome,
            navigateToReport = navigateToReport,
            navigateToProfile = navigateToProfile,
            navigateToBalanceRound = navigateToBalanceRound
        )
    }
}

fun NavGraphBuilder.balanceRoundScreen(
    navigateToHome: () -> Unit
) {
    composable(
        route = "${Route.BalanceRound}/{roundIndex}",
        arguments = listOf(navArgument("roundIndex") { type = NavType.StringType })
    ) {
        val roundIndex = it.arguments?.getString("roundIndex")
        if (roundIndex != null) {
            BalanceRoundScreen(navigateToHome, roundIndex = roundIndex)
        } else {
            Text("유효하지 않은 페이지입니다 :(", style = TextStyles.title01, modifier = Modifier.fillMaxSize())
        }
    }
}

fun NavGraphBuilder.reportScreen(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    composable(route = "${Route.Report}") {
        ReportScreen(
            navigateToHome = navigateToHome,
            navigateToReport = navigateToReport,
            navigateToProfile = navigateToProfile,
        )
    }
}
