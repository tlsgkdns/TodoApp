package com.example.todoapp.todo

import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import com.example.todoapp.domain.todo.service.TodoService
import com.example.todoapp.domain.todo.service.TodoServiceImpl
import com.example.todoapp.infra.security.TokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) : DescribeSpec({
    extension(SpringExtension)
    afterContainer { clearAllMocks() }
    val todoRepository = mockk<TodoRepository>()
    val memberRepository = mockk<MemberRepository>()
    val todoService = TodoServiceImpl(todoRepository, memberRepository)
    describe("POST /todo/는")
    {
        context("todo를 생성할 때 인증이 되지 않았다면,")
        {
            it("401 status를 반환한다.")
            {
                val objectMapper = ObjectMapper()
                val json = objectMapper.registerModules(JavaTimeModule()).writeValueAsString(
                    Todo(content = "Hello!", complete = true, title = "TODO")
                )
                val falseToken = "Hello! I'm Token!"
                val result = mockMvc.perform(
                    post("/todo").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer $falseToken")
                        .content(json)
                ).andReturn()
                result.response.status shouldBe 401
            }
        }
    }
}
)