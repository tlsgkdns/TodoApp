package com.example.todoapp.infra.security

import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.service.MemberService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SecurityUtil {

    companion object{
        private fun getUsername(): String
        {
            return (SecurityContextHolder.getContext().authentication.principal as UserDetails).username ?: "anonymous"
        }
        fun getLoginMember(memberService: MemberService): Member
        {
            return (memberService.getMember(getUsername()).toEntity())
        }
        fun isDifferentWithLoginMember(member: Member): Boolean
        {
            return getUsername() != member.username
        }
    }
}