package com.example.todoapp.data.model

import com.example.todoapp.data.entity.TodoEntity

fun TodoEntity.toTodo(): Todo {
    return Todo(
        userId = this.userId,
        id = this.id,
        title = this.title,
        completed = this.completed
    )
}
fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        userId = this.userId,
        id = this.id,
        title = this.title,
        completed = this.completed
    )
}
