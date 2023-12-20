package com.example.todoapp.domain.service

import com.example.todoapp.domain.dto.TodoCreateDTO
import com.example.todoapp.domain.dto.TodoDTO
import com.example.todoapp.domain.dto.TodoModifyDTO
import com.example.todoapp.domain.model.Todo
import com.example.todoapp.domain.repository.TodoRepository
import infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository
): TodoService {
    override fun getTodo(todoId: Long): TodoDTO {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toDTO()
    }

    override fun getTodoList(): List<TodoDTO> {
        return todoRepository.findAll().map { it.toDTO() }
    }

    @Transactional
    override fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        val (title, content, writer) = todoModifyDTO
        todo.title = title; todo.content = content; todo.writer = writer
        return todoRepository.save(todo).toDTO()
    }

    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return todoRepository.delete(todo)
    }

    override fun createTodo(createDTO: TodoCreateDTO): TodoDTO {
        return todoRepository.save(Todo(
            title = createDTO.title,
            content = createDTO.content,
            writer = createDTO.writer,
            createdDate = createDTO.createdDate
        )).toDTO()
    }
    private fun Todo.toDTO(): TodoDTO{
        return TodoDTO(
            title = title,
            content = content,
            writer = writer,
            createdDate = createdDate
        )
    }
}