package com.makesmartapps.glacewidgetexample.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makesmartapps.glacewidgetexample.data.remote.RemoteDataSource
import com.makesmartapps.glacewidgetexample.domain.TaskRepository

class MainViewModelFactory(private val remoteDataSource: RemoteDataSource) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(TaskRepository(remoteDataSource)) as T

        throw IllegalArgumentException("Unknown class name")
    }
}