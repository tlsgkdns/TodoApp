package com.example.todoapp.domain.todo.dto

import com.example.todoapp.domain.comment.dto.CommentDTO
import java.time.LocalDateTime

data class TodoDTO(
    val id: Long,
    val title: String,
    val content: String,
    val writer: Long,
    val createdDate: LocalDateTime,
    val complete: Boolean,
    val commentList: List<CommentDTO> = listOf()
)