package com.example.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val completed: Boolean,
    val userId: Int
)