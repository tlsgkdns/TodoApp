package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
}