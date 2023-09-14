package com.makesmartapps.glacewidgetexample.ui.theme

class RestApiImpl(private val restApiService: RestApiService): RemoteDataSource {
    override suspend fun getTodoTasks(): List<Task> {
        return restApiService.listTodo()
    }
}