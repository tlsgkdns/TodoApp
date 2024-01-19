package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.io.Writer

interface TodoRepository: JpaRepository<Todo, Long>, QueryDslTodoRepository {

}