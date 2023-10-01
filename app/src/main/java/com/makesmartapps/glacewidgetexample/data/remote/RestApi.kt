package com.makesmartapps.glacewidgetexample.data.remote

import com.makesmartapps.glacewidgetexample.domain.Task

interface RemoteDataSource {
    suspend fun getTodoTasks():List<Task>
}