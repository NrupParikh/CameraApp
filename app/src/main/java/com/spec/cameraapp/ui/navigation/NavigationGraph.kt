package com.spec.cameraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spec.cameraapp.ui.fragment.capture_image.CaptureImageScreen
import com.spec.cameraapp.ui.fragment.home.HomeScreen
import com.spec.cameraapp.ui.fragment.image_editing.ImageEditingScreen
import com.spec.cameraapp.viewmodels.MainViewModel

/*
*  In this Navigation Graph we can do below functionality
*   - Set the starting screen
*   - Handle the navigation of various screens
* */

@Composable
fun NavigationGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(mainViewModel, navController)
        }
        composable(route = Route.CaptureImage.route) {
            CaptureImageScreen(mainViewModel, navController)
        }
        composable(route = Route.EditImage.route) {
            ImageEditingScreen(mainViewModel, navController)
        }
    }
}