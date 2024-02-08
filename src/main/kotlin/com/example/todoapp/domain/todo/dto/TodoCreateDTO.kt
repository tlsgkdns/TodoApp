package com.example.todoapp.domain.todo.dto


import com.example.todoapp.domain.todo.model.Todo
import org.hibernate.validator.constraints.Length


data class TodoCreateDTO (
    @field:Length(min = 1, max = 200, message = "title length must between 1 and 200")
    var title: String,
    @field:Length(min = 1, max = 1000, message = "content length must between 1 and 1000")
    var content: String
)
{
    fun to(): Todo
    {
        return Todo(title = title, content = content, complete = false)
    }
}