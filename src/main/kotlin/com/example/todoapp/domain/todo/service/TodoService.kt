package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import org.springframework.data.domain.Pageable

interface TodoService {
    fun getTodo(todoId: Long): TodoDTO

    fun getTodoList(orderByASC: Boolean = true, writer: String? = null, pageable: Pageable): List<TodoDTO>

    fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO

    fun deleteTodo(todoId: Long)

    fun createTodo(createDTO: TodoCreateDTO): TodoDTO
}