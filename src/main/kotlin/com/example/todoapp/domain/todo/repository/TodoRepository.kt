package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository


interface TodoRepository: JpaRepository<Todo, Long>, QueryDslTodoRepository {

}