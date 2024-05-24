package com.spec.cameraapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.spec.cameraapp.R
import com.spec.cameraapp.viewmodels.MainViewModel

@Composable
fun MaskPic(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var showDialog by remember {
            mutableStateOf(false)
        }

        MaskCollectionDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onItemClick = {
                showDialog = false
                mainViewModel.selectedMaskType.value = it
                Log.d("TAG", "TSelected")
            }
        )
        OutlinedButton(onClick = {
            showDialog = true
        }, shape = RoundedCornerShape(50)) {
            Text(text = stringResource(id = R.string.lbl_select_mask_image))
        }
    }
}