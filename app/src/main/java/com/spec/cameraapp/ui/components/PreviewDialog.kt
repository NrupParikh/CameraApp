package com.spec.cameraapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.theme.CameraAppTheme

@Composable
fun PreviewDialog(
    imageBitmap: ImageBitmap,
    onPositiveButtonClick: () -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Surface(
                modifier = Modifier.width(250.dp),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier =Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Screenshot"
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(0.dp),
                        onClick = { onPositiveButtonClick(); onDismiss() }) {
                        Text(
                            text = String.format(
                                stringResource(id = R.string.lbl_share)
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewPreviewDialogNight() {
    CameraAppTheme {
        PreviewDialog(
            imageBitmap = ImageBitmap.imageResource(LocalContext.current.resources,R.drawable.ic_color_filter),
            onPositiveButtonClick = { /*TODO*/ },
            showDialog = true
        ) {

        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewPreviewDialogLight() {
    CameraAppTheme {
        PreviewDialog(
            imageBitmap = ImageBitmap.imageResource(LocalContext.current.resources,R.drawable.ic_color_filter),
            onPositiveButtonClick = { /*TODO*/ },
            showDialog = true
        ) {

        }
    }
}
