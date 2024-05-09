package com.spec.cameraapp.ui.components

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.Transformation

@Composable
fun LoadImageFromUri(
    context: Context,
    imageUri: String,
    transformation: List<Transformation>,
    imageSize: Dp,
    scaleType: ContentScale

) {
    val imageRequest = ImageRequest.Builder(context = context).data(
        imageUri
    )

    AsyncImage(
        model = imageRequest.transformations(transformation.toList()).build(),
        contentDescription = "Image Description",
        modifier = Modifier.size(imageSize),
        contentScale = scaleType,
    )
}