package com.example.todoapp.todo

import com.example.todoapp.domain.todo.dto.SearchKeywordDTO
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoRepositoryTests @Autowired constructor(
    private val todoRepository: TodoRepository
): BehaviorSpec({
        extension(SpringExtension)
        given("todoRepository는")
        {
            beforeTest {
                todoRepository.saveAll(listOf(
                    Todo("hello", "World", false),
                    Todo("This is", "Title", true),
                    Todo("HeartBreaker", "in my heart", true),
                    Todo("My Mind", "is your heart", false),
                    Todo("Can you", "feel this", true),
                    Todo("World", "is mine", true),
                    Todo("you are my special", "You can do this", true),
                    Todo("Could you", "mine is your", true),
                    ))
            }
            `when`("0페이지, 2사이즈의 you란 값을 찾으면")
            {
                val todos = todoRepository.getTodos(
                    PageRequest.of(0, 2),
                    orderByAsc = true,
                    searchKeywordDTO = SearchKeywordDTO(null, "you", null)
                )
                then("2개의 page를 얻는다.") {
                    todos.size shouldBe 2
                }
            }
        }
    }
)
{

}