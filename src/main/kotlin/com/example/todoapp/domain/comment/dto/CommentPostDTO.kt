package com.example.todoapp.domain.comment.dto


data class CommentPostDTO(
    val content: String,
    val password: String,
    val writer: String
)
