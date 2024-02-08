package com.example.todoapp.domain.comment.dto

import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.todo.model.Todo


data class CommentPostDTO(
    val content: String,
    val password: String,
)
{
    fun to(todo: Todo): Comment
    {
        return Comment(content = content, password = password, todo = todo)
    }
}
