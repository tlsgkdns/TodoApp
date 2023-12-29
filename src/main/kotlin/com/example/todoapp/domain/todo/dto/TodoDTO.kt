package com.example.todoapp.domain.todo.dto

import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.model.Todo
import java.util.Date

data class TodoDTO(
    val id: Long,
    val title: String,
    val content: String,
    val writer: Long,
    val createdDate: Date,
    val complete: Boolean
)
{
    fun toEntity(memberService: MemberService): Todo
    {
        return Todo(id, title, content, memberService.getMember(writer).toEntity()
            , createdDate, complete)
    }
}