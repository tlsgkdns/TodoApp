package com.example.todoapp.domain.member.repository

import com.example.todoapp.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>{

    fun findByUsername(username: String): Member?
}