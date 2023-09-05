package com.makesmartapps.glacewidgetexample

import com.makesmartapps.glacewidgetexample.ui.theme.RemoteDataSource

class TaskRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getTodoTasks() = remoteDataSource.getTodoTasks()
}