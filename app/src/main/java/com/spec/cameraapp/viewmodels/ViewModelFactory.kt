package com.spec.cameraapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spec.cameraapp.repository.ProjectRepository

class ViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return modelClass.getDeclaredConstructor(ProjectRepository::class.java)
                .newInstance(repository)
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}