package com.joel.ktorfitdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.ktorfitdemo.domain.state.ResponseState
import com.joel.ktorfitdemo.presentation.TodoViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Todo List") }
                )
            }
        ) { values ->
            when(val response = state.todoListState) {
                is ResponseState.Error -> Text(response.message)
                ResponseState.Loading -> CircularProgressIndicator()
                is ResponseState.Success -> {
                    val todos = response.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = values,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(todos) { todo ->
                            Text(todo.title)
                        }
                    }
                }
            }
        }
    }
}