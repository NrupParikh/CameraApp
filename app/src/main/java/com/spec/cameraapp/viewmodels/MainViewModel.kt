package com.spec.cameraapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val projectRepository: ProjectRepository) :
    ViewModel() {

    val projectList = MutableLiveData<List<Project>>()
    val imageUrl = MutableLiveData<String>()

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
}