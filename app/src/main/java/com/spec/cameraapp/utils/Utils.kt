package com.spec.cameraapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.spec.cameraapp.R
import com.spec.cameraapp.utils.transformations.TransformationItem
import com.spec.cameraapp.utils.transformations.TransformationType

@Composable
fun getScreenNameFromRoute(currentRoute: String): String {
    return when (currentRoute) {
        ROUTE_HOME -> stringResource(id = R.string.title_home)
        ROUTE_CAPTURE_IMAGE -> stringResource(id = R.string.title_capture_image)
        ROUTE_IMAGE_EDIT -> stringResource(id = R.string.title_edit_image)
        else -> ""
    }
}

// List of Transformations
val transformationList: List<TransformationItem> = mutableListOf(
    TransformationItem(
        id = 1,
        icon = R.drawable.ic_rounded_corner,
        TransformationType.ROUNDED_CORNER.name
    ),
    TransformationItem(
        id = 2,
        icon = R.drawable.ic_circle_crop,
        TransformationType.CIRCLE_CROP.name
    ),
    TransformationItem(
        id = 3,
        icon = R.drawable.ic_sqaure_crop,
        TransformationType.RESIZE_IMAGE.name
    ),
    TransformationItem(
        id = 4,
        icon = R.drawable.ic_blur,
        TransformationType.BLUR.name
    ),
    TransformationItem(
        id = 5,
        icon = R.drawable.ic_gray_scale,
        TransformationType.GRAY_SCALE.name
    ),
    TransformationItem(
        id = 6,
        icon = R.drawable.ic_color_filter,
        TransformationType.COLOR_FILTER.name
    ),
    TransformationItem(
        id = 7,
        icon = R.drawable.ic_mask,
        TransformationType.MASK.name
    ),
)
