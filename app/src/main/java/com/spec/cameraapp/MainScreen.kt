package com.spec.cameraapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.spec.cameraapp.ui.navigation.NavigationGraph
import com.spec.cameraapp.ui.topbar.MyTopAppBar

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            MyTopAppBar(navController = navController)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                NavigationGraph(navController = navController)
            }
        }
    )
}