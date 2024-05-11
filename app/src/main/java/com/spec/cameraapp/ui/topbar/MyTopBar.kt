package com.spec.cameraapp.ui.topbar

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.spec.cameraapp.ui.components.currentRoute
import com.spec.cameraapp.ui.navigation.Route
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.utils.getScreenNameFromRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController) {

    val currentRoute = currentRoute(navController = navController)

    TopAppBar(
        title = {
            Text(
                text = getScreenNameFromRoute(currentRoute.toString()),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        navigationIcon = {
            if (!currentRoute.equals(Route.Home.route)) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "",
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    )

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyTopAppBarNight() {
    CameraAppTheme {
        MyTopAppBar(navController = NavController(LocalContext.current))
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMyTopAppBarLight() {
    CameraAppTheme {
        MyTopAppBar(navController = NavController(LocalContext.current))
    }
}