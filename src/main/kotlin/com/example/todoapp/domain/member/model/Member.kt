package com.example.todoapp.domain.member.model

import com.example.todoapp.domain.member.dto.MemberRegisterDTO
import com.example.todoapp.domain.member.dto.MemberUpdateDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "username")
    var username: String,
    @Column(name = "password")
    var password: String,
    @Enumerated(EnumType.STRING)
    val type: MemberType = MemberType.USER
){
    fun updateMember(request: MemberUpdateDTO, encoder: PasswordEncoder)
    {
        this.username = request.newUsername ?: this.username
        this.password = request.newPassword
            ?.takeIf { it.isNotBlank() }
            ?.let{encoder.encode(it)}
            ?:this.password
    }

}
