package com.spec.cameraapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.spec.cameraapp.R
import com.spec.cameraapp.utils.transformations.TransformationItem
import com.spec.cameraapp.utils.transformations.TransformationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

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
        icon = R.drawable.ic_resize,
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

// GET BITMAP OF IMAGE LOAD FROM URI OR URL

fun urlToBitmap(
    scope: CoroutineScope,
    imageURL: String,
    context: Context,
    onSuccess: (bitmap: Bitmap) -> Unit,
    onError: (error: Throwable) -> Unit
) {
    var bitmap: Bitmap? = null
    val loadBitmap = scope.launch(Dispatchers.IO) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageURL)
            .allowHardware(false)
            .build()
        val result = loader.execute(request)
        if (result is SuccessResult) {
            bitmap = (result.drawable as BitmapDrawable).bitmap
        } else if (result is ErrorResult) {
            cancel(result.throwable.localizedMessage ?: "ErrorResult", result.throwable)
        }
    }
    loadBitmap.invokeOnCompletion { throwable ->
        bitmap?.let {
            onSuccess(it)
        } ?: throwable?.let {
            onError(it)
        } ?: onError(Throwable("Undefined Error"))
    }
}
