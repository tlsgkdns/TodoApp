package com.example.todoapp.domain.todo.dto

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.todo.model.Todo
import java.time.LocalDateTime

data class TodoDTO(
    val id: Long,
    val title: String,
    val content: String,
    val writer: Long,
    val complete: Boolean,
    val commentList: List<CommentDTO> = listOf()
)
{
    companion object {
        fun from(todo: Todo): TodoDTO
        {
            return TodoDTO(
                id = todo.id!!,
                title = todo.title,
                content = todo.content,
                writer = todo.writer?.id!!,
                complete = todo.complete,
                commentList = todo.commentSet.map { CommentDTO.from(it) }
            )
        }
    }
}