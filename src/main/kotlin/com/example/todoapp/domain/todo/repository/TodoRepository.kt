package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.io.Writer

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findByWriterUsernameOrderByCreatedDateAsc(pageable: Pageable, username: String): List<Todo>
    fun findByWriterUsernameOrderByCreatedDateDesc(pageable: Pageable, username: String):List<Todo>
    fun findByOrderByCreatedDateAsc(pageable: Pageable): List<Todo>
    fun findByOrderByCreatedDateDesc(pageable: Pageable): List<Todo>
}