package com.example.kotlinsecurity.common.service

import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByLoginId(username) ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("해당 유저는 없습니다.")

    private fun createUserDetails(member: Member): UserDetails =
        User(
            member.loginId,
            passwordEncoder.encode(member.password),
            member.memberRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
}