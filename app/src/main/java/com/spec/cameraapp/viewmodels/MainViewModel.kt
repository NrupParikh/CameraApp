package com.spec.cameraapp.viewmodels

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val projectRepository: ProjectRepository) :
    ViewModel() {

    val projectList = MutableLiveData<List<Project>>()
    val imageUrl = MutableLiveData<String>()
    val selectedTransformation = MutableLiveData<String>()

    // ============ ROUNDED_CORNER
    var roundCornerSliderValueTrans = MutableLiveData(0f)
    var roundCornerSliderValueTransIsSelected = MutableLiveData(false)

    // ============ CIRCLE_CROP
    var circleCropSliderValueTrans = MutableLiveData(350f)
    var circleCropSliderValueTransIsSelected = MutableLiveData(false)

    // ============ RESIZE_IMAGE
    var resizeSliderValueTrans = MutableLiveData(350f)
    var resizeSliderValueTransIsSelected = MutableLiveData(false)

    // ============ BLUR
    var blurSliderValueTrans = MutableLiveData(1f)
    var blurSliderValueTransIsSelected = MutableLiveData(false)

    // ============ COLOR_FILTER
    var colorValueTransIsSelected = MutableLiveData(false)
    var colorValueTrans = MutableLiveData(Color.Transparent)

    // ============ MASK
    var maskValueTransIsSelected = MutableLiveData(false)
    val selectedMaskType = MutableLiveData<String>()

    init {
        getAllProjects()
    }

    fun getAllProjects() {
        viewModelScope.launch(Dispatchers.IO) {
            projectList.postValue(projectRepository.getAllProjects())
        }
    }

    fun createNewProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.createNewProject(project)
        }
    }

    private suspend fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.deleteProject(project)
        }
    }

    fun deleteItem(it: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProject(it)
        }
    }

    // =================== STORE IMAGE IN CACHE DIR OF APPLICATION
    fun setTempPath(imageUri: Uri, current: Context): String {

        val inputStream = current.contentResolver.openInputStream(imageUri)

        val currentTime = Instant.now().epochSecond

        val tempFile = File(current.cacheDir, "_image${currentTime}")
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        Log.d("JBK", "Path ${tempFile.path}")
        ///data/user/0/com.spec.cameraapp/cache/_image1715594264.jpeg
        return tempFile.path
    }

    private fun getExceptionType(imageUri: Uri, current: Context): String {
        val mimeType = current.contentResolver.getType(imageUri)
        return mimeType?.substringAfterLast('/') ?: "jpg"
    }

    // =================== SHARE IMAGE VIA INTENT
    fun shareImageViaIntent(context: Context, bitmap: ImageBitmap?, mimeType: String) {
        val imageUri = bitmap?.asAndroidBitmap()?.let { getUriFromBitmap(context, it) }
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val shareChooserIntent = Intent.createChooser(shareIntent, "Share with")
        context.startActivity(shareChooserIntent)
    }


    // =================== GET URI FROM BITMAP
    private fun getUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val cacheDir = context.cacheDir
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(cacheDir, fileName)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.toUri()
    }

}