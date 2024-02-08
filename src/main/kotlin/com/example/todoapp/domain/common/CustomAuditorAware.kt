package com.example.todoapp.domain.common

import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomAuditorAware(
    private val memberRepository: MemberRepository
): AuditorAware<Member> {
    override fun getCurrentAuditor(): Optional<Member> {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map { it.authentication }
            .map { it.principal as UserDetails}
            .map { memberRepository.findByUsername(it.username) }
    }
}