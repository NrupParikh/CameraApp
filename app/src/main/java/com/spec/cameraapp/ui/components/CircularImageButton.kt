package com.spec.cameraapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun CircularImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    fillColor: Color,
    hasIcon: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = fillColor),
        content = {
            Row {
                if (hasIcon) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
                }

            }
        }
    )
}