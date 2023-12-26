package com.example.todoapp.domain.todo.controller

import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import com.example.todoapp.domain.todo.service.TodoService
import infra.exception.InvalidateDTOError
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todo")
class TodoController (
    private val todoService: TodoService
){
    @GetMapping("{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.getTodo(todoId))
    }
    @GetMapping("/list")
    fun getTodoList(orderByASC: Boolean = true, writer: String? = null):
            ResponseEntity<List<TodoDTO>>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.getTodoList(orderByASC, writer))
    }
    @PostMapping()
    fun createTodo(@RequestBody @Valid todoDTO: TodoCreateDTO, bindingResult: BindingResult):
            ResponseEntity<TodoDTO>
    {
        if(bindingResult.hasErrors())
        {
            throw InvalidateDTOError("createDTO", bindingResult.fieldError?.defaultMessage ?: "")
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoService.createTodo(todoDTO))
    }

    @PutMapping("/{todoId}")
    fun modifyTodo(@PathVariable todoId: Long, @RequestBody @Valid todoModifyDTO: TodoModifyDTO,
                   bindingResult: BindingResult)
    : ResponseEntity<TodoDTO>
    {
        if(bindingResult.hasErrors())
        {
            throw InvalidateDTOError("modifyDTO", bindingResult.fieldError?.defaultMessage ?: "")
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.modifyTodo(todoId, todoModifyDTO))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): ResponseEntity<Unit>
    {
        todoService.deleteTodo(todoId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }
}