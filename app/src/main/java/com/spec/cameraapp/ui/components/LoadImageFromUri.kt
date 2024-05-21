package com.spec.cameraapp.ui.components

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.theme.CameraAppTheme

@Composable
fun LoadImageFromUri(
    context: Context,
    imageUri: String,
    transformation: List<Transformation>,
    imageSize: Dp,
    scaleType: ContentScale,
    blurRadius: Dp,
    roundedCornerRadius: Dp,
    isCircleShape: Boolean

) {
    val imageRequest = ImageRequest.Builder(context = context).data(
        imageUri
    )

    AsyncImage(
        model = imageRequest.transformations(transformation.toList()).build(),
        contentDescription = "Image Description",
        modifier = Modifier
            // For Rounded Corner
            .clip(
                if (isCircleShape) {
                    CircleShape
                } else {
                    RoundedCornerShape(roundedCornerRadius)
                }
            )
            // For Blur
            .blur(blurRadius)
            // For Image Resize
            .size(imageSize),
        contentScale = scaleType,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewLoadImageFromUriNight() {
    CameraAppTheme {
        LoadImageFromUri(
            context = LocalContext.current,
            imageUri = "",
            transformation = listOf(),
            imageSize = 48.dp,
            scaleType = ContentScale.Crop,
            roundedCornerRadius = 0.dp,
            blurRadius = 0.dp,
            isCircleShape = true
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewLoadImageFromUriLight() {
    CameraAppTheme {
        LoadImageFromUri(
            context = LocalContext.current,
            imageUri = "",
            transformation = listOf(),
            imageSize = 48.dp,
            scaleType = ContentScale.Crop,
            roundedCornerRadius = 0.dp,
            blurRadius = 0.dp,
            isCircleShape = true
        )
    }
}