package com.example.todoapp.domain.member.dto

import com.example.todoapp.domain.member.model.Member
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberRegisterDTO (val username: String, val password: String)
{
    fun to(encoder: PasswordEncoder) = Member(
        username = this.username,
        password = encoder.encode(password)
    )
}