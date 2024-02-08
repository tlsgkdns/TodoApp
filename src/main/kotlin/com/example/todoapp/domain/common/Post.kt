package com.example.todoapp.domain.common

import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.infra.security.SecurityUtil
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@EntityListeners(AuditingEntityListener::class)
class Post: BaseTimeEntity()
{
    @Id
    @GeneratedValue
    var id: Long? = null
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @CreatedBy
    @JoinColumn(name = "writer")
    var writer: Member? = null
    fun isLoginMemberPostThis(memberRepository: MemberRepository): Boolean
    {
        return writer != SecurityUtil.getLoginMember(memberRepository)
    }
}