package com.joel.ktorfitdemo.presentation

import com.joel.ktorfitdemo.domain.model.Todo
import com.joel.ktorfitdemo.domain.state.ResponseState

data class TodoState(
    val todoListState: ResponseState<List<Todo>> = ResponseState.Loading,
    val title: String = "Add Todo",
    val todoTitle: String = "",
    val buttonTitle: String = "Add",
    val isChecked: Boolean = false,
    val isAddUpdateDialogVisible: Boolean = false,
)
