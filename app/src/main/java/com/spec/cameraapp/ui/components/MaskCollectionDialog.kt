package com.spec.cameraapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.spec.cameraapp.utils.transformationList
import com.spec.cameraapp.viewmodels.MainViewModel

@Composable
fun MaskCollectionDialog(
    mainViewModel: MainViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onItemClick: () -> Unit

) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            val lazyListState = rememberLazyGridState()
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                shape = MaterialTheme.shapes.extraLarge,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format(
                            stringResource(id = R.string.lbl_select_mask_image)
                        ),
                        style = TextStyle(
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        state = lazyListState
                    ) {
                        items(transformationList.size)
                        {
                            MaskGridItem(
                                transformationList[it],
                                false,
                                onItemClick = { selectedTransformation, isClick ->
                                    if (isClick) {
                                        Log.d("TAG", "YES")
                                        onItemClick()
                                        onDismiss()
                                    } else {
                                        Log.d("TAG", "NO")
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

//
//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun PreviewMaskCollectionDialogNight() {
//    CameraAppTheme {
//        MaskCollectionDialog(
//            mainViewModel = MainViewModel(projectRepository = ProjectRepository()),
//            showDialog = true
//        ) {
//
//        }
//    }
//}
//
//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//fun PreviewMaskCollectionDialogLight() {
//    CameraAppTheme {
//        MaskCollectionDialog(
//            mainViewModel,
//            showDialog = true
//        ) {
//
//        }
//    }
//}
