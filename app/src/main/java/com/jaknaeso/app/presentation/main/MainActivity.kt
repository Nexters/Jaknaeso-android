package com.jaknaeso.app.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.jaknaeso.app.presentation.navigation.Route
import com.jaknaeso.app.presentation.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewmodel: MainViewmodel = hiltViewModel()
            val navHostController = rememberNavController()
            SetUpNavGraph(
                navController = navHostController,
                startDestination = Route.Home.name,
            )
        }
    }
}
