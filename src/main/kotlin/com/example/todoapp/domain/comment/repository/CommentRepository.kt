package com.example.todoapp.domain.comment.repository

import com.example.todoapp.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByTodoId(todoId: Long): List<Comment>
}