package com.makesmartapps.glacewidgetexample

import com.makesmartapps.glacewidgetexample.ui.theme.RestApi

class MainRepository(private val restApi: RestApi) {
    suspend fun getTodoTasks() = restApi.getTodoTasks()
}