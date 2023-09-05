package com.makesmartapps.glacewidgetexample.ui.theme

interface RemoteDataSource {
    suspend fun getTodoTasks(): List<Task>
}