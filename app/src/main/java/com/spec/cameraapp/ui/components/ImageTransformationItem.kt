package com.spec.cameraapp.ui.components

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.utils.transformations.GrayscaleTransformation
import com.spec.cameraapp.utils.transformations.TransformationItem
import com.spec.cameraapp.utils.transformations.TransformationType

@Composable
fun ImageTransformationItem(
    transformation: TransformationItem,
    isClick:Boolean,
    onItemClick: (TransformationItem,Boolean) -> Unit
) {
    var isClick by remember { mutableStateOf(isClick) }
    Box(
        Modifier.border(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = transformation.icon),
            contentDescription = "Your image description",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inversePrimary),
            modifier = Modifier
                .clickable {
                    //Toast.makeText(LocalContext.current,"",Toast.LENGTH_SHORT).show()
                    Log.d("JBK", "ImageTransformationItem: ${isClick}")
                    isClick = !isClick
                    Log.d("JBK", "ImageTransformationItem after : ${isClick}")

                    onItemClick.invoke(transformation,isClick)
                }
                .size(48.dp)
                .background(
                    color = if (isClick) MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewImageTransformationItemNight() {
    CameraAppTheme {
        ImageTransformationItem(
            transformation =
            TransformationItem(
                id = 1, icon = R.drawable.ic_rounded_corner,
                TransformationType.ROUNDED_CORNER.name
            ),true,
            onItemClick = {
                    selectedTransformation,isClick ->

            }
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewImageTransformationItemLight() {
    CameraAppTheme {
        ImageTransformationItem(
            transformation =
            TransformationItem(
                id = 1, icon = R.drawable.ic_rounded_corner,
                TransformationType.ROUNDED_CORNER.name
            ),true,
            onItemClick =   {
                    selectedTransformation,isClick ->}
        )
    }
}