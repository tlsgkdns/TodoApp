package com.example.todoapp.domain.todo.controller

import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import com.example.todoapp.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos")
class TodoController (
    private val todoService: TodoService
){

    @GetMapping("{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.getTodo(todoId))
    }
    @GetMapping()
    fun getTodoList(): ResponseEntity<List<TodoDTO>>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.getTodoList())
    }

    @PostMapping()
    fun crateTodo(@RequestBody todoDTO: TodoCreateDTO): ResponseEntity<TodoDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoService.createTodo(todoDTO))
    }

    @PutMapping("/{todoId}")
    fun modifyTodo(@PathVariable todoId: Long, @RequestBody todoModifyDTO: TodoModifyDTO)
    : ResponseEntity<TodoDTO>
    {
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