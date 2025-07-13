package com.example.kotlinsecurity.member.service

import com.example.kotlinsecurity.common.authority.JwtProvider
import com.example.kotlinsecurity.common.authority.TokenInfo
import com.example.kotlinsecurity.common.exception.InvalidInputException
import com.example.kotlinsecurity.common.status.Role
import com.example.kotlinsecurity.member.dto.LoginRequest
import com.example.kotlinsecurity.member.dto.MemberRequest
import com.example.kotlinsecurity.member.dto.MemberResponse
import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.entity.MemberRole
import com.example.kotlinsecurity.member.repository.MemberRepository
import com.example.kotlinsecurity.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider
) {

    /**
     * 회원가입
     */
    fun signUp(memberRequest: MemberRequest): String {
        var member: Member? = memberRepository.findByLoginId(memberRequest.loginId)

        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 Id 입니다.")
        }

        member = memberRequest.toEntity()
        memberRepository.save(member)

        val memberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * login
     */
    fun login(loginRequest: LoginRequest): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.loginId, loginRequest.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtProvider.createToken(authentication)
    }

    fun searchMyInfo(id: Long): MemberResponse {
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }
}