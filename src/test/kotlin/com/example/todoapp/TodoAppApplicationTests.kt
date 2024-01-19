package com.example.todoapp

import com.example.todoapp.domain.comment.dto.CommentPostDTO
import com.example.todoapp.domain.comment.service.CommentService
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.service.TodoService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TodoAppApplicationTests(@Autowired private val todoService: TodoService
    , @Autowired private val commentService: CommentService
) {


}
