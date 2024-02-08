package com.example.todoapp.infra.security

import com.example.todoapp.domain.common.Post
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SecurityUtil {
    companion object{
        private fun getUsername(): String
        {
            return (SecurityContextHolder.getContext()?.authentication?.principal as? UserDetails)?.username ?: "anonymous"
        }
        fun getLoginMember(memberRepository: MemberRepository): Member
        {
            return memberRepository.findByUsername(getUsername()) ?:
            Member(-1, "anonymous", "anonymous");
        }
        fun checkWriterEqualsLoginMember(post: Post, memberRepository: MemberRepository)
        {
            if(post.isLoginMemberPostThis(memberRepository))
                throw NotHaveAuthorityException(post.javaClass.name)
        }
    }
}