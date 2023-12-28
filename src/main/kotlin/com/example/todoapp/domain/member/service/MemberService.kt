package com.example.todoapp.domain.member.service

import com.example.todoapp.domain.member.dto.*
import org.springframework.stereotype.Service

interface MemberService {
    fun registerMember(memberRegisterDTO: MemberRegisterDTO): MemberDTO
    fun signIn(memberSignInDTO: MemberSignInDTO):SignInResponse
    fun getMember(username: String): MemberDTO

    fun modifyMember(username: String, memberUpdateDTO: MemberUpdateDTO): MemberDTO
    fun deleteMember(username: String)
}