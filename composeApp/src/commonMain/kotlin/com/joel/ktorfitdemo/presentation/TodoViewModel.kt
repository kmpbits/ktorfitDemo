package com.joel.ktorfitdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.ktorfitdemo.domain.repository.TodoRepository
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
            TodoAction.AddTodo -> {}
            TodoAction.DismissAddUpdateDialog -> updateDialogVisibility(false)
            TodoAction.ShowAddUpdateDialog -> updateDialogVisibility(true)
            is TodoAction.UpdateIsChecked -> updateIsChecked(action.isChecked)
            is TodoAction.UpdateTitle -> updateTitle(action.title)
        }
    }

    fun updateDialogVisibility(isVisible: Boolean) {
        _state.update {
            it.copy(
                isAddUpdateDialogVisible = isVisible
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
}