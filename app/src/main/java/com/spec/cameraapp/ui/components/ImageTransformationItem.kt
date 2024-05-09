package com.spec.cameraapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spec.cameraapp.utils.transformations.TransformationItem

@Composable
fun ImageTransformationItem(
    transformation: TransformationItem,
    onItemClick: (TransformationItem) -> Unit
) {
    Box(
        Modifier.border(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = transformation.icon),
            contentDescription = "Your image description",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inversePrimary),
            modifier = Modifier
                .clickable {
                    onItemClick.invoke(transformation)
                }
                .size(48.dp)
        )
    }
}
