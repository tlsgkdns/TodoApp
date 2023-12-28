package com.example.todoapp.domain.member.dto

import com.example.todoapp.domain.member.model.MemberType

data class SignInResponse(val name: String?, val type: MemberType, val token: String)