package com.example.todoapp.domain.comment.dto

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder

data class CommentModifyDTO(
    val content: String,
    val password: String
)
