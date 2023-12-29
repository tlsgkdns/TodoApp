package com.example.todoapp.domain.comment.dto


data class CommentDTO(
    val id: Long?,
    val content: String,
    val writer: Long
)
