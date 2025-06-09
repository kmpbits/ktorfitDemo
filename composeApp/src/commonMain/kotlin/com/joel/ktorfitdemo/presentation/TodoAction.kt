package com.joel.ktorfitdemo.presentation

import com.joel.ktorfitdemo.domain.model.Todo

sealed interface TodoAction {
    data class UpdateTitle(val title: String) : TodoAction
    data class UpdateIsChecked(val isChecked: Boolean) : TodoAction
    data class ShowAddUpdateDialog(val todo: Todo? = null) : TodoAction
    data object DismissAddUpdateDialog : TodoAction
    data class AddUpdateTodo(val id: Int? = null) : TodoAction
    data class UpdateTodoCheck(val id: Int, val isChecked: Boolean) : TodoAction
}