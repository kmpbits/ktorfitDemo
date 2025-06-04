package com.joel.ktorfitdemo.data.mapper

import com.joel.ktorfitdemo.data.network.dto.TodoDto
import com.joel.ktorfitdemo.domain.model.Todo

fun TodoDto.toModel() = Todo(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)