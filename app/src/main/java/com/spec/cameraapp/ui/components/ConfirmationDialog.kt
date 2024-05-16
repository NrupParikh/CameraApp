package com.spec.cameraapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.theme.CameraAppTheme

@Composable
fun ConfirmationDialog(
    title: String,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(16.dp),
                        style = TextStyle(
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            onClick = { onPositiveButtonClick(); onDismiss() }) {
                            Text(
                                text = String.format(
                                    stringResource(id = R.string.lbl_ok)
                                ),
                            )
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            onClick = { onNegativeButtonClick(); onDismiss() }) {
                            Text(
                                text = String.format(
                                    stringResource(id = R.string.lbl_cancel)
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewConfirmationDialogNight() {
    CameraAppTheme {
        ConfirmationDialog(
            title = "Are you sure you want to delete this project?",
            onPositiveButtonClick = { /*TODO*/ },
            onNegativeButtonClick = { /*TODO*/ },
            showDialog = true
        ) {

        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewConfirmationDialogLight() {
    CameraAppTheme {
        ConfirmationDialog(
            title = "Are you sure you want to delete this project?",
            onPositiveButtonClick = { /*TODO*/ },
            onNegativeButtonClick = { /*TODO*/ },
            showDialog = true
        ) {

        }
    }
}
