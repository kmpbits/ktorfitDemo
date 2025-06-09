package com.joel.ktorfitdemo.data.network.service

import com.joel.ktorfitdemo.data.network.dto.TodoDto
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import kotlin.random.Random

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): List<TodoDto>

    @POST("todos")
    @FormUrlEncoded
    suspend fun addTodo(
        @Field("userId") userId: Int = 1,
        @Field("id") id: Int = Random.nextInt(),
        @Field("title") title: String,
        @Field("completed") completed: Boolean,
    ): TodoDto

    @PUT("todos/{id}")
    @FormUrlEncoded
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Field("userId") userId: Int = 1,
        @Field("title") title: String,
        @Field("completed") completed: Boolean,
    ): TodoDto

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int)
}