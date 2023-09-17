package com.makesmartapps.glacewidgetexample.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://my-json-server.typicode.com/juleimisf/GlaceWidgetExample/"

    private fun getRetrofit() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiService: RestApiService = getRetrofit().create(RestApiService::class.java)
}