package com.makesmartapps.glacewidgetexample.data

import com.makesmartapps.glacewidgetexample.domain.Task

interface RemoteDataSource {
    suspend fun getTodoTasks():List<Task>
}