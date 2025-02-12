package com.jaknaeso.app.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.jaknaeso.app.presentation.contract.MainEffect
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.navigation.SetUpNavGraph
import com.jaknaeso.app.presentation.navigation.navigateToLogin
import com.jaknaeso.app.presentation.viewmodel.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewmodel: MainViewmodel = hiltViewModel()
            val navHostController = rememberNavController()

            LaunchedEffect(Unit) {
                viewmodel.effects.collect { effect ->
                    when (effect) {
                        MainEffect.NavigateToLogin -> navHostController.navigateToLogin()
                    }
                }
            }

            SetUpNavGraph(
                navController = navHostController,
                startDestination = Route.Home.name,
            )
        }
    }
}
