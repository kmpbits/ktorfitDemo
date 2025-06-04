package com.joel.ktorfitdemo.domain.state

sealed interface ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>
    data class Error(val message: String) : ResponseState<Nothing>
    data object Loading : ResponseState<Nothing>
}