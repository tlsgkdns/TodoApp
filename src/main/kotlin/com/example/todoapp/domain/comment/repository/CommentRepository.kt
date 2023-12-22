package com.example.todoapp.domain.comment.repository

import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByTodoId(todoId: Long): List<Comment>
}