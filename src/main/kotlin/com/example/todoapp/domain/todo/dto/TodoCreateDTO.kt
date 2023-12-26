package com.example.todoapp.domain.todo.dto

import org.hibernate.validator.constraints.Length
import java.util.Date

data class TodoCreateDTO (
    @field:Length(min = 1, max = 200, message = "title length must between 1 and 200")
    var title: String,
    @field:Length(min = 1, max = 1000, message = "content length must between 1 and 1000")
    var content: String,
    var writer: String,
    var createdDate: Date
)