package com.example.todoapp.domain.member.controller

import com.example.todoapp.domain.member.dto.MemberDTO
import com.example.todoapp.domain.member.dto.MemberRegisterDTO
import com.example.todoapp.domain.member.dto.MemberSignInDTO
import com.example.todoapp.domain.member.dto.MemberUpdateDTO
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{username}")
    fun getMember(@PathVariable username: String): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(username))
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('USER')")
    fun updateMember(@PathVariable username: String, @RequestBody memberUpdateDTO: MemberUpdateDTO)
    : ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.modifyMember(username, memberUpdateDTO))
    }
    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('USER')")
    fun deleteMember(@PathVariable username: String): ResponseEntity<Unit>
    {
        println("Hello!")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember(username))
    }

    @PostMapping("/sign-up")
    fun registerMember(@RequestBody memberRegisterDTO: MemberRegisterDTO): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(memberRegisterDTO))
    }

    @PostMapping("/sign-in")
    fun loginMember(@RequestBody memberSignInDTO: MemberSignInDTO) =
        ResponseEntity.status(HttpStatus.OK).body(memberService.signIn(memberSignInDTO))
}