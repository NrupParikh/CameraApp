package com.spec.cameraapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.spec.cameraapp.R

@Composable
fun getScreenNameFromRoute(currentRoute: String): String {
    return when (currentRoute) {
        ROUTE_HOME -> stringResource(id = R.string.title_home)
        ROUTE_CAPTURE_IMAGE -> stringResource(id = R.string.title_capture_image)
        ROUTE_IMAGE_EDIT -> stringResource(id = R.string.title_edit_image)
        else -> ""
    }
}