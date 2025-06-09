package com.joel.ktorfitdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.ktorfitdemo.domain.model.Todo
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
            is TodoAction.AddUpdateTodo -> {
                action.id?.let {
                    updateTodo(it)
                } ?: run {
                    addTodo()
                }
            }
            TodoAction.DismissAddUpdateDialog -> updateDialogVisibility(false, null)
            is TodoAction.ShowAddUpdateDialog -> updateDialogVisibility(true, action.todo)
            is TodoAction.UpdateIsChecked -> updateIsChecked(action.isChecked)
            is TodoAction.UpdateTitle -> updateTitle(action.title)
            is TodoAction.UpdateTodoCheck -> updateTodoCheck(action.todo)
        }
    }

    private fun updateTodoCheck(todo: Todo) {
        updateTitle(todo.title)
        updateIsChecked(!todo.completed) // This is needed because we are only changing the isChecked value
        updateTodo(todo.id)
    }

    private fun updateDialogVisibility(isVisible: Boolean, todo: Todo?) {
        _state.update {
            it.copy(
                isAddUpdateDialogVisible = isVisible,
                title = if (todo == null) "Add Todo" else "Update Todo",
                buttonTitle = if (todo == null) "Add" else "Update",
                todoTitle = todo?.title ?: "",
                isChecked = todo?.completed ?: false
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

    // This API is onlu for testing purpose. On new fetch, the new added Todo will be gone
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

    // This API is onlu for testing purpose. On new fetch, the new update Todo will be reverted
    private fun updateTodo(id: Int) = viewModelScope.launch {
        val title = state.value.todoTitle
        val isChecked = state.value.isChecked

        repository.updateTodo(
            id = id,
            title = title,
            completed = isChecked
        ).collectLatest { response ->
            when (response) {
                is ResponseState.Error -> {} // Not needed for this demo
                ResponseState.Loading -> {} // Not needed for this demo
                is ResponseState.Success -> {
                    val successState = state.value.todoListState as? ResponseState.Success
                    val newList = successState?.data?.let {
                        updateItemById(
                            list = it,
                            id = id,
                            newItem = response.data,
                            idSelector = { item -> item.id }
                        )
                    } ?: listOf()

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

    private fun <T> updateItemById(
        list: List<T>,
        id: Int,
        newItem: T,
        idSelector: (T) -> Int,
    ): List<T> {
        val mutableList = list.toMutableList()
        val index = mutableList.indexOfFirst { idSelector(it) == id }
        if (index >= 0) {
            mutableList[index] = newItem
        }
        return mutableList
    }
}