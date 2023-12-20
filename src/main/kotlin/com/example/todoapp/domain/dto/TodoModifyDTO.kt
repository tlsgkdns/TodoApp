package com.example.todoapp.domain.dto

data class TodoModifyDTO(
    var title: String,
    var content: String,
    var writer: String,
)