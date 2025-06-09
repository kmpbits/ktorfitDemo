package com.joel.ktorfitdemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.ktorfitdemo.domain.state.ResponseState
import com.joel.ktorfitdemo.presentation.TodoAction
import com.joel.ktorfitdemo.presentation.TodoViewModel
import com.joel.ktorfitdemo.presentation.components.AddUpdateTodoDialog
import com.joel.ktorfitdemo.presentation.components.TodoItem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()

    var idClicked by remember {
        mutableStateOf<Int?>(null)
    }

    if (state.isAddUpdateDialogVisible) {
        AddUpdateTodoDialog(
            onDismiss = { viewModel.onAction(TodoAction.DismissAddUpdateDialog) },
            dialogTitle = state.title,
            todoTitle = state.todoTitle,
            buttonTitle = state.buttonTitle,
            onAddToDo = { viewModel.onAction(TodoAction.AddUpdateTodo(idClicked)) },
            onTodoUpdate = { viewModel.onAction(TodoAction.UpdateTitle(it)) },
            isChecked = state.isChecked,
            onCheckedChange = { viewModel.onAction(TodoAction.UpdateIsChecked(it)) }
        )
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Todo List") }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        idClicked = null
                        viewModel.onAction(TodoAction.ShowAddUpdateDialog())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Todo"
                    )
                }
            }
        ) { values ->
            when(val response = state.todoListState) {
                is ResponseState.Error -> Text(response.message)
                ResponseState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                is ResponseState.Success -> {
                    val todos = response.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = values,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(todos) { todo ->
                            TodoItem(
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        idClicked = todo.id
                                        viewModel.onAction(TodoAction.ShowAddUpdateDialog(todo))
                                    },
                                todo = todo,
                                onCheckChanged = {
                                    viewModel.onAction(TodoAction.UpdateTodoCheck(todo))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}