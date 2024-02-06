package com.example.todoapp.domain.member.dto

import com.example.todoapp.domain.member.model.Member

data class MemberDTO(val id: Long, val username: String)
{
    companion object
    {
        fun from(member: Member): MemberDTO
        {
            return MemberDTO(member.id!!, member.username)
        }
    }
}
