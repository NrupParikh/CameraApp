package com.spec.cameraapp.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val projectRepository: ProjectRepository) :
    ViewModel() {

    val projectList = MutableLiveData<List<Project>>()
    val imageUrl = MutableLiveData<String>()
    val selectedTransformation = MutableLiveData<String>()
    var roundCornerSliderValueTrans =MutableLiveData<Float>(0f)
    var roundCornerSliderValueTransisSelected = MutableLiveData<Boolean>(false)
    var blurSliderValueTrans = MutableLiveData<Float>(1f)
    var blurSliderValueTransisSelected = MutableLiveData<Boolean>(false)
    var CircleCropSliderValueTrans = MutableLiveData<Float>(1f)
    var CircleCropSliderValueTransisSelected = MutableLiveData<Boolean>(false)
    var SquareCropSliderValueTrans = MutableLiveData<Float>(1f)
    var SquareCropSliderValueTransisSelected = MutableLiveData<Boolean>(false)

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

    suspend fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.deleteProject(project)
        }
    }

    fun deleteItem(it: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProject(it)
        }
    }

    fun setTempPath(imageUri: Uri, current: Context) :String{

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

    private fun getExtentionType(imageUri: Uri, current: Context): String {
            val mimeType = current.contentResolver.getType(imageUri)
            return mimeType?.substringAfterLast('/') ?: "jpg"
    }
}