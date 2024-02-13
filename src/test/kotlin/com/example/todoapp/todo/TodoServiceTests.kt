package com.example.todoapp.todo

import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import com.example.todoapp.domain.todo.service.TodoServiceImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoServiceTests @Autowired constructor(
    private val mockMvc: MockMvc
): DescribeSpec({
    extension(SpringExtension)
    afterContainer { clearAllMocks() }
    val todoRepository = mockk<TodoRepository>()
    val memberRepository = mockk<MemberRepository>()
    val todoService = TodoServiceImpl(todoRepository, memberRepository)
    describe("GET /todo/{num}")
    {
        val member = Member(1, "User", "dsdasd")
        context("num이 존재한다면")
        {
            val todoId = 1433236L
            it("200 status Code로 응답한다.")
            {
                every { todoRepository.findByIdOrNull(any()) } returns
                        Todo(
                            title = "Mock Title",
                            content = "Mock Content",
                            complete = false
                        ).apply { this.id = 1; this.writer = member}
                every{
                    memberRepository.findByIdOrNull(any())
                } returns member
                val result = todoService.getTodo(todoId)
                result.title shouldBe "Mock Title"
            }
        }
    }
})