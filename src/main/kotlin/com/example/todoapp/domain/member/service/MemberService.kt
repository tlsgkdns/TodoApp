package com.example.todoapp.domain.member.service

import com.example.todoapp.domain.member.dto.*
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

interface MemberService {
    fun registerMember(memberRegisterDTO: MemberRegisterDTO): MemberDTO
    fun signIn(memberSignInDTO: MemberSignInDTO):SignInResponse
    fun getMember(username: String): MemberDTO
    fun getMember(id: Long): MemberDTO

    fun modifyMember(username: String, memberUpdateDTO: MemberUpdateDTO): MemberDTO
    fun deleteMember(username: String)
}