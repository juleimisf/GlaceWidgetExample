package com.makesmartapps.glacewidgetexample.ui.theme

import retrofit2.http.GET

interface RestApiService {
    @GET("todos")
    suspend fun listTodo(): List<Task>
}