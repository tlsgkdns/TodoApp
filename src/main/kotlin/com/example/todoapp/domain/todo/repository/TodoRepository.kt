package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TodoRepository: JpaRepository<Todo, Long> {
    @Query("select t from Todo t order by t.id DESC")
    fun getTodoListWithDesc(pageable: Pageable): List<Todo>

    @Query("select t from Todo t order by t.id asc")
    fun getTodoListWithAsc(pageable: Pageable): List<Todo>
}