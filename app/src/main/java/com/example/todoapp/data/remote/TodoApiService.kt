package com.example.todoapp.data.remote

import com.example.todoapp.data.model.Todo
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApiService {
    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    @GET("/todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Todo
}