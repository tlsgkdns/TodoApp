package com.example.todoapp.domain.todo.dto

import org.hibernate.validator.constraints.Length

data class TodoModifyDTO(
    @field:Length(min = 1, max = 200, message = "title length must between 1 and 200")
    val title: String,
    @field:Length(min = 1, max = 1000, message = "content length must between 1 and 1000")
    val content: String,
)