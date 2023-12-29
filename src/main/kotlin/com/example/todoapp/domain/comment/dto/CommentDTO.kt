package com.example.todoapp.domain.comment.dto

import com.example.todoapp.domain.todo.model.Todo
import javax.xml.stream.events.Comment


data class CommentDTO(
    val id: Long?,
    val content: String,
    val writer: Long
)
{}

