package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.toEntity
import com.example.todoapp.data.model.toTodo
import com.example.todoapp.data.remote.TodoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TodoRepository(
    private val todoApiService: TodoApiService,
    private val todoDao: TodoDao
) {
    fun getTodos(): Flow<List<Todo>> = flow {
        val cache = todoDao.getAllTodos().map { it.toTodo() }
        if (cache.isNotEmpty()) emit(cache)
        try {
            val fetched = todoApiService.getTodos()
            todoDao.insertAll(fetched.map { it.toEntity() })
            emit(todoDao.getAllTodos().map { it.toTodo() })
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun getTodo(id: Int): Flow<Todo> = flow {
        val cache = todoDao.getTodoById(id)?.toTodo()
        if (cache != null) emit(cache)
        try {
            val fetched = todoApiService.getTodo(id)
            todoDao.insertAll(listOf(fetched.toEntity()))
            emit(todoDao.getTodoById(id).toTodo())
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun refreshTodos(): Result<Unit> {
        return try {
            val remoteTodos = todoApiService.getTodos()
            todoDao.insertAll(remoteTodos.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}