package com.jaknaeso.app.presentation.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Home")
    }
}
