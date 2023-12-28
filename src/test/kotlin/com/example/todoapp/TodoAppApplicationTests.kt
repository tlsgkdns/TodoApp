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

    }
}
