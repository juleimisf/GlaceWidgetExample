package com.makesmartapps.glacewidgetexample.domain

import com.makesmartapps.glacewidgetexample.data.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TaskRepository(private val remoteDataSource: RemoteDataSource) {
    fun getTasksFlow(): Flow<List<Task>> = flow {
        val tasks = remoteDataSource.getTodoTasks()
        emit(tasks)
    }
}