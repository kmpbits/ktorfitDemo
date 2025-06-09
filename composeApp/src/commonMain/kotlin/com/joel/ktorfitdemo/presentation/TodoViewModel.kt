package com.joel.ktorfitdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.ktorfitdemo.domain.repository.TodoRepository
import com.joel.ktorfitdemo.domain.state.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> = _state.asStateFlow()

    init {
        getTodos()
    }

    fun onAction(action: TodoAction) {
        when (action) {
            TodoAction.AddTodo -> addTodo()
            TodoAction.DismissAddUpdateDialog -> updateDialogVisibility(false)
            TodoAction.ShowAddUpdateDialog -> updateDialogVisibility(true)
            is TodoAction.UpdateIsChecked -> updateIsChecked(action.isChecked)
            is TodoAction.UpdateTitle -> updateTitle(action.title)
            is TodoAction.UpdateTodo -> updateTodo(action.id)
        }
    }

    fun updateDialogVisibility(isVisible: Boolean) {
        _state.update {
            it.copy(
                isAddUpdateDialogVisible = isVisible,
                title = if (isVisible) "Add Todo" else "Update Todo",
                buttonTitle = if (isVisible) "Add" else "Update",
                todoTitle = "",
                isChecked = false
            )
        }
    }

    private fun updateTitle(title: String) {
        _state.update {
            it.copy(
                todoTitle = title
            )
        }
    }

    private fun updateIsChecked(isChecked: Boolean) {
        _state.update {
            it.copy(
                isChecked = isChecked
            )
        }
    }

    private fun getTodos() = viewModelScope.launch {
        repository.getTodos().collectLatest { responseState ->
            _state.update {
                it.copy(
                    todoListState = responseState
                )
            }
        }
    }

    private fun addTodo() = viewModelScope.launch {
        val title = state.value.todoTitle
        val isChecked = state.value.isChecked

        repository.addTodo(title, isChecked).collectLatest { response ->
            when (response) {
                is ResponseState.Error -> {} // Not needed for this demo
                ResponseState.Loading -> {} // Not needed for this demo
                is ResponseState.Success -> {
                    val newList =
                        (state.value.todoListState as ResponseState.Success).data.toMutableList()
                            .also {
                                it.add(0, response.data)
                            }

                    _state.update {
                        it.copy(
                            todoListState = ResponseState.Success(newList),
                            isAddUpdateDialogVisible = false,
                            todoTitle = "",
                            isChecked = false
                        )
                    }
                }
            }
        }
    }

    private fun updateTodo(id: Int) = viewModelScope.launch {

    }
}