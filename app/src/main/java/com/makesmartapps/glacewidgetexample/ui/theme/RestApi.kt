package com.makesmartapps.glacewidgetexample.ui.theme

interface RestApi {
    suspend fun getTodoTasks(): List<Task>
}