package com.makesmartapps.glacewidgetexample.data.remote

import com.makesmartapps.glacewidgetexample.domain.Task
import retrofit2.http.GET

interface RestApiService {
    @GET("todos")
    suspend fun listTodo(): List<Task>
}