package com.joel.ktorfitdemo.domain.repository

import com.joel.ktorfitdemo.domain.model.Todo
import com.joel.ktorfitdemo.domain.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodos(): Flow<ResponseState<List<Todo>>>

    fun addTodo(title: String, completed: Boolean): Flow<ResponseState<Todo>>

    fun updateTodo(
        id: Int,
        title: String,
        completed: Boolean,
    ): Flow<ResponseState<Todo>>

    fun deleteTodo(id: Int): Flow<ResponseState<Unit>>
}