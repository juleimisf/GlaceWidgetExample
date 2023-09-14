package com.makesmartapps.glacewidgetexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makesmartapps.glacewidgetexample.ui.theme.RemoteDataSource

class MainViewModelFactory(private val remoteDataSource: RemoteDataSource) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(TaskRepository(remoteDataSource)) as T

        throw IllegalArgumentException("Unknown class name")
    }
}