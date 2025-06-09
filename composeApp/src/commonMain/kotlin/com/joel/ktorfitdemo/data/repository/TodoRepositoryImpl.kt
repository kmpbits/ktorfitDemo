package com.joel.ktorfitdemo.data.repository

import com.joel.ktorfitdemo.data.mapper.toModel
import com.joel.ktorfitdemo.data.network.dto.TodoDto
import com.joel.ktorfitdemo.data.network.service.ApiService
import com.joel.ktorfitdemo.domain.model.Todo
import com.joel.ktorfitdemo.domain.repository.TodoRepository
import com.joel.ktorfitdemo.domain.state.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class TodoRepositoryImpl(
    private val service: ApiService
) : TodoRepository {

    override fun getTodos(): Flow<ResponseState<List<Todo>>> = flow {
        emit(ResponseState.Loading)

        val todos = service.getTodos().map { it.toModel() }
        emit(ResponseState.Success(todos))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An error occurred"))
    }

    override fun addTodo(title: String, completed: Boolean): Flow<ResponseState<Todo>> = flow {
        emit(ResponseState.Loading)

        val dto = service.addTodo(
            title = title,
            completed = completed
        )
        emit(ResponseState.Success(dto.toModel()))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An error occurred"))
    }

    override fun updateTodo(
        id: Int,
        title: String,
        completed: Boolean,
    ): Flow<ResponseState<Todo>> = flow {
        emit(ResponseState.Loading)

        val dto = TodoDto(
            userId = 1,
            id = id,
            title = title,
            completed = completed
        )

        service.updateTodo(id, dto)
        emit(ResponseState.Success(dto.toModel()))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An error occurred"))
    }

    override fun deleteTodo(id: Int): Flow<ResponseState<Unit>> = flow {
        emit(ResponseState.Loading)

        service.deleteTodo(id)
        emit(ResponseState.Success(Unit))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An error occurred"))
    }
}