package com.example.todoapp.domain.todo.dto

import java.util.Date

data class TodoDTO(
    var title: String,
    var content: String,
    var writer: String,
    val createdDate: Date
)