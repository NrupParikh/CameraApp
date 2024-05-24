package com.spec.cameraapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.spec.cameraapp.ui.navigation.NavigationGraph
import com.spec.cameraapp.ui.topbar.MyTopAppBar
import com.spec.cameraapp.viewmodels.MainViewModel

/*
*  In this Main Screen we can do below functionality
*   - Handling the Top App Bar
*   - Set up Navigation Graph
*   - If we want to add bottom navigation bar then it will also be done in bottomBar
* */

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            MyTopAppBar(navController = navController, mainViewModel)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                NavigationGraph(navController = navController, mainViewModel)
            }
        }
    )
}

