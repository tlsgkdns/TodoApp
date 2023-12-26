package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Writer

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository
): TodoService {
    override fun getTodo(todoId: Long): TodoDTO {
        val todo = getValidatedTodo(todoId)
        return todo.toDTO()
    }

    override fun getTodoList(orderByASC: Boolean, writer: String?): List<TodoDTO> {
        val list = if(orderByASC) todoRepository.getTodoListWithAsc()
        else todoRepository.getTodoListWithDesc()
        return if(writer != null) list.map { it.toDTO() }.filter { it.writer == writer }
        else list.map { it.toDTO()}
    }

    @Transactional
    override fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO {
        val todo = getValidatedTodo(todoId)
        val (title, content, writer) = todoModifyDTO
        todo.title = title; todo.content = content; todo.writer = writer
        return todoRepository.save(todo).toDTO()
    }

    override fun deleteTodo(todoId: Long) {
        val todo = getValidatedTodo(todoId)
        return todoRepository.delete(todo)
    }

    override fun createTodo(createDTO: TodoCreateDTO): TodoDTO {
        val todo = Todo(
            title = createDTO.title,
            content = createDTO.content,
            writer = createDTO.writer,
            createdDate = createDTO.createdDate
        )
        return todoRepository.save(todo).toDTO()
    }

    private fun Todo.toDTO(): TodoDTO {
        return TodoDTO(
            id = id!!,
            title = title,
            content = content,
            writer = writer,
            createdDate = createdDate,
            complete = complete
        )
    }
    private fun getValidatedTodo(todoId: Long): Todo
    {
        return todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId);
    }
}