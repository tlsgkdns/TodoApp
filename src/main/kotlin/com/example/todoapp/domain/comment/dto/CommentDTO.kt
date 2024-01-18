package com.example.todoapp.domain.comment.dto

import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.todo.model.Todo



data class CommentDTO(
    val id: Long?,
    val content: String,
    val writer: Long
)
{
    companion object{
        fun from(comment: Comment): CommentDTO
        {
            return CommentDTO(
                id = comment.id,
                content = comment.content,
                writer = comment.writer?.id!!
            )
        }
    }
}

