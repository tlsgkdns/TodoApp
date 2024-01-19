package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.dto.SearchKeywordDTO
import com.example.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryDslTodoRepository {
    fun getTodos(pageable: Pageable, orderByAsc: Boolean, searchKeywordDTO : SearchKeywordDTO?): Page<Todo>
}