package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.todo.dto.SearchKeywordDTO
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoService {
    fun getTodo(todoId: Long): TodoDTO

    fun getTodos(pageable: Pageable, searchKeywordDTO: SearchKeywordDTO?, orderByASC: Boolean = true): Page<TodoDTO>

    fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO

    fun deleteTodo(todoId: Long)

    fun createTodo(createDTO: TodoCreateDTO): TodoDTO
}