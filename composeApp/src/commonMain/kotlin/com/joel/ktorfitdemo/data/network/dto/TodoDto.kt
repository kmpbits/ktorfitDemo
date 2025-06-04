package com.joel.ktorfitdemo.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
