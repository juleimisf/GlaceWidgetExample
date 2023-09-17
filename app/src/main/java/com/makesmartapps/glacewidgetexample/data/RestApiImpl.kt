package com.makesmartapps.glacewidgetexample.data

import com.makesmartapps.glacewidgetexample.domain.Task

class RestApiImpl(private val restApiService: RestApiService): RemoteDataSource {
    override suspend fun getTodoTasks(): List<Task> {
        return restApiService.listTodo()
    }
}