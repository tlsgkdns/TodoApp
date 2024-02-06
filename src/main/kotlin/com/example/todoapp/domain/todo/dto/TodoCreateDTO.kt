package com.example.todoapp.domain.todo.dto

import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.todo.model.Todo
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import java.util.Date

data class TodoCreateDTO (
    @field:Length(min = 1, max = 200, message = "title length must between 1 and 200")
    var title: String,
    @field:Length(min = 1, max = 1000, message = "content length must between 1 and 1000")
    var content: String,
    var createdDate: LocalDateTime
)
{
    fun to(memberRepository: MemberRepository): Todo
    {
        return Todo(title = title, content = content,
            createdDate = createdDate, complete = false).apply {setWriter(memberRepository)}
    }
}