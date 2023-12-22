package com.example.todoapp

import com.example.todoapp.domain.todo.service.TodoService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TodoAppApplicationTests(@Autowired private val todoService: TodoService) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun testTodoGetList()
    {
        val ascList = todoService.getTodoList(TodoGetListDTO(false))
        val descList = todoService.getTodoList(TodoGetListDTO(true))
        println("ASC: ")
        for(a in ascList) print("${a.id} ")
        println()
        println("DESC: ")
        for(a in descList) print("${a.id} ")
    }
}
