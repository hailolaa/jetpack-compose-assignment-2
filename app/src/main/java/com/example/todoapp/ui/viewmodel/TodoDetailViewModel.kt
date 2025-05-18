package com.example.todoapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Int = savedStateHandle["todoId"] ?: 0

    private val _uiState = MutableStateFlow(TodoDetailUiState())
    val uiState: StateFlow<TodoDetailUiState> = _uiState.asStateFlow()

    init {
        loadTodoDetails()
    }

    private fun loadTodoDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getTodo(todoId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Unknown error occurred"
                        )
                    }
                }
                .collect { todo ->
                    _uiState.update {
                        it.copy(
                            todo = todo,
                            isLoading = false,
                            error = null,
                            completed = todo.completed
                        )
                    }
                }
        }
    }
}

data class TodoDetailUiState(
    val todo: Todo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val completed: Boolean = false
)