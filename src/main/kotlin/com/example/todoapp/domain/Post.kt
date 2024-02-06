package com.example.todoapp.domain

import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
open class Post
{
    @Id
    @GeneratedValue
    var id: Long? = null
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "writer")
    var writer: Member? = null
    fun setWriter(memberRepository: MemberRepository)
    {
        writer = SecurityUtil.getLoginMember(memberRepository)
    }
    fun checkLoginMemberPostThis(memberRepository: MemberRepository)
    {
        if(writer != SecurityUtil.getLoginMember(memberRepository))
            throw NotHaveAuthorityException(this.javaClass.name)
    }
}