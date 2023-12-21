package com.example.todoapp.domain.comment.dto

data class CommentModifyDTO(
    val content: String,
    val writer: String,
    val password: String
)
