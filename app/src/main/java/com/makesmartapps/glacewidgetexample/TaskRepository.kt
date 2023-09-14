package com.makesmartapps.glacewidgetexample

import com.makesmartapps.glacewidgetexample.ui.theme.RemoteDataSource
import com.makesmartapps.glacewidgetexample.ui.theme.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository(private val remoteDataSource: RemoteDataSource) {
     suspend fun getTodoTasks() = remoteDataSource.getTodoTasks()

}