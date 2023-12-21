package com.example.todoapp.domain.todo.dto

import java.util.Date

data class TodoDTO(
    val id: Long,
    val title: String,
    val content: String,
    val writer: String,
    val createdDate: Date,
    val complete: Boolean
)