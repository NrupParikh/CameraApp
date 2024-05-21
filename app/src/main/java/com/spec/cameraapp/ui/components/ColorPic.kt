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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.spec.cameraapp.R
import com.spec.cameraapp.viewmodels.MainViewModel
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType
import io.mhssn.colorpicker.ext.toHex

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorPic(mainViewModel: MainViewModel) {
    val colorValueTransIsSelected =
        mainViewModel.colorValueTransIsSelected.observeAsState()
    val colorValueTrans = mainViewModel.colorValueTrans.observeAsState()
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var color by remember { mutableStateOf(colorValueTrans.value) }
        val colorPickerType by remember {
            mutableStateOf<ColorPickerType>(ColorPickerType.Ring())
        }
        var showDialog by remember {
            mutableStateOf(false)
        }
        ColorPickerDialog(
            show = showDialog,
            type = colorPickerType,
            properties = DialogProperties(),
            onDismissRequest = {
                showDialog = false
            },

            onPickedColor = {
                showDialog = false
                mainViewModel.colorValueTrans.value = it
                Log.d("JBK", "ColorPic: ${it.toHex()}")
                Log.d("JBK", "ColorPic value: ${it.value}")
                Log.d("JBK", "ColorPic  int: ${it.value.toInt()}")
            },
        )
        /* ColorPicker(type = colorPickerType) {
             color = it.value.toInt()
         }
         Spacer(modifier = Modifier.height(20.dp))
         Row(
             horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 24.dp)
         ) {
             val (alpha, red, green, blue) = color
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Hex")
                 Text(text = "#${color}")
             }
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Alpha")
                 Text(text = alpha.toString())
             }
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Red")
                 Text(text = red.toString())
             }
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Green")
                 Text(text = green.toString())
             }
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Blue")
                 Text(text = blue.toString())
             }
         }
         Spacer(modifier = Modifier.height(20.dp))
         Box(
             modifier = Modifier
                 .size(80.dp, 50.dp)
                 .clip(RoundedCornerShape(50))
                 .border(
                     0.3.dp,
                     SolidColor(androidx.compose.ui.graphics.Color.Red),
                     RoundedCornerShape(50)
                 )
                 .transparentBackground(verticalBoxesAmount = 8)
                 .background(
                     SolidColor(androidx.compose.ui.graphics.Color.Red),
                     RoundedCornerShape(50)
                 )
         )
         Spacer(modifier = Modifier.height(24.dp))
         Text(text = "Color Picker Type",
             )
         Spacer(modifier = Modifier.height(20.dp))
         LazyVerticalGrid(
             modifier = Modifier.fillMaxWidth(),
             horizontalArrangement = Arrangement.spacedBy(16.dp),
             columns = GridCells.Fixed(2),
             content = {
                 item {
                     OutlinedButton(onClick = {
                         colorPickerType = ColorPickerType.Classic()
                     }, shape = RoundedCornerShape(50)) {
                         Text(text = "Classic")
                     }
                 }
                 item {
                     OutlinedButton(onClick = {
                         colorPickerType = ColorPickerType.Circle()
                     }, shape = RoundedCornerShape(50)) {
                         Text(text = "Circle")
                     }
                 }
                 item {
                     OutlinedButton(onClick = {
                         colorPickerType = ColorPickerType.Ring()
                     }, shape = RoundedCornerShape(50)) {
                         Text(text = "Ring")
                     }
                 }
                 item {
                     OutlinedButton(onClick = {
                         colorPickerType = ColorPickerType.SimpleRing()
                     }, shape = RoundedCornerShape(50)) {
                         Text(text = "Simple Ring")
                     }
                 }
             })*/
        OutlinedButton(onClick = {
            showDialog = true
        }, shape = RoundedCornerShape(50)) {
            Text(text = stringResource(id = R.string.lbl_select_color))
        }
    }
}
