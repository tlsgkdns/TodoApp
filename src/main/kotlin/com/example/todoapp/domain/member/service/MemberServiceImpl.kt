package com.example.todoapp.domain.member.service

import com.example.todoapp.domain.member.dto.*
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
import com.example.todoapp.infra.security.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalStateException

@Service
class MemberServiceImpl (
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
): MemberService {
    @Transactional
    override fun registerMember(memberRegisterDTO: MemberRegisterDTO):MemberDTO {
        val member: Member? = memberRepository.findByUsername(memberRegisterDTO.username)
        if(member != null)
        {
            throw IllegalStateException("이미 등록된 ID입니다.")
        }
        return memberRepository.save(memberRegisterDTO.to(encoder))
            .let { MemberDTO.from(it) }
    }

    @Transactional
    override fun signIn(memberSignInDTO: MemberSignInDTO): SignInResponse {
        val member = memberRepository.findByUsername(memberSignInDTO.username)
            ?.takeIf { encoder.matches(memberSignInDTO.password, it.password) }
            ?:throw IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다.")
        val token = tokenProvider.createToken("${member.username}:${member.type}")
        return SignInResponse(member.username, member.type, token)
    }

    @Transactional
    override fun getMember(username: String): MemberDTO {
        val member = memberRepository.findByUsername(username)
            ?: throw IllegalStateException("없는 유저입니다.")
        return MemberDTO.from(member)
    }

    @Transactional
    override fun getMember(id: Long): MemberDTO {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw IllegalStateException("없는 유저입니다.")
        return MemberDTO.from(member)
    }

    @Transactional
    override fun modifyMember(username: String, memberUpdateDTO: MemberUpdateDTO): MemberDTO {
        val member = memberRepository.findByUsername(username) ?: throw IllegalStateException("없는 유저입니다.")
        if(memberRepository.findByUsername(memberUpdateDTO.newUsername ?: "") != null)
            throw IllegalStateException("이미 존재하는 아이디입니다.")
        member.updateMember(MemberUpdateDTO(memberUpdateDTO.newUsername ?: username,
            memberUpdateDTO.newPassword ?: member.password), encoder)
        return MemberDTO.from(memberRepository.save(member))
    }

    @Transactional
    override fun deleteMember(username: String) {
        val member = memberRepository.findByUsername(username) ?: throw IllegalStateException("없는 유저입니다.")
        memberRepository.delete(member)
    }
}