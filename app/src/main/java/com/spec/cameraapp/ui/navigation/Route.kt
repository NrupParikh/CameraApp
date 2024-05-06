package com.spec.cameraapp.ui.navigation

import com.spec.cameraapp.ui.utils.ROUTE_CAPTURE_IMAGE
import com.spec.cameraapp.ui.utils.ROUTE_HOME
import com.spec.cameraapp.ui.utils.ROUTE_IMAGE_EDIT

sealed class Route(val route: String) {
    data object Home : Route(ROUTE_HOME)
    data object CaptureImage : Route(ROUTE_CAPTURE_IMAGE)
    data object EditImage : Route(ROUTE_IMAGE_EDIT)
}