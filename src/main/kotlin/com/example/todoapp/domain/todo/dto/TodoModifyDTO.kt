package com.example.todoapp.domain.todo.dto

data class TodoModifyDTO(
    val title: String,
    val content: String,
    val writer: String,
    val password: String
)