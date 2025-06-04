package com.joel.ktorfitdemo.domain.repository

import com.joel.ktorfitdemo.domain.model.Todo
import com.joel.ktorfitdemo.domain.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodos(): Flow<ResponseState<List<Todo>>>
}