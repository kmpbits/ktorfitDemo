package com.joel.ktorfitdemo.presentation

sealed interface TodoAction {
    data class UpdateTitle(val title: String) : TodoAction
    data class UpdateIsChecked(val isChecked: Boolean) : TodoAction
    data object ShowAddUpdateDialog : TodoAction
    data object DismissAddUpdateDialog : TodoAction
    data object AddTodo : TodoAction
    data class UpdateTodo(val id: Int) : TodoAction
}