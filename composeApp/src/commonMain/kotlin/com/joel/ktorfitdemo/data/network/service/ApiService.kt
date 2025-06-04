package com.joel.ktorfitdemo.data.network.service

import com.joel.ktorfitdemo.data.network.dto.TodoDto
import de.jensklingenberg.ktorfit.http.GET

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): List<TodoDto>
}