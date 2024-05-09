package com.spec.cameraapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.spec.cameraapp.R

@Composable
fun SelectImagePopUp(
    onClickCamera: () -> Unit,
    onClickGallery: () -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit,

    ) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Surface(
                modifier = Modifier.width(250.dp), // Customize your width here

                shadowElevation = 8.dp,
                shape = MaterialTheme.shapes.extraLarge,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text =String.format(
                        stringResource(id =  R.string.lbl_title_popup_select_image)),
                        style = TextStyle(fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onClickCamera(); onDismiss() }) {
                        Text( text =String.format(
                            stringResource(id =   R.string.lbl_button_open_camera)),)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onClickGallery(); onDismiss() }) {
                        Text(  text =String.format(
                            stringResource(id =   R.string.lbl_button_open_gallery)),)
                    }
                }
            }
        }
    }
}
