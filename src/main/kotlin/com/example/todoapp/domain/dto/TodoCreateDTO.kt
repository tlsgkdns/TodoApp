package com.example.todoapp.domain.dto

import java.util.Date

data class TodoCreateDTO (
    var title: String,
    var content: String,
    var writer: String,
    var createdDate: Date
)