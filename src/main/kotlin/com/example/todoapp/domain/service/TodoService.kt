package com.example.todoapp.domain.service

import com.example.todoapp.domain.dto.TodoCreateDTO
import com.example.todoapp.domain.dto.TodoDTO
import com.example.todoapp.domain.dto.TodoModifyDTO

interface TodoService {
    fun getTodo(todoId: Long): TodoDTO

    fun getTodoList(): List<TodoDTO>

    fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO

    fun deleteTodo(todoId: Long)

    fun createTodo(createDTO: TodoCreateDTO): TodoDTO
}