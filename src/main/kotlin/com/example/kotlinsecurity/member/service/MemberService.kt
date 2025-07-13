package com.example.kotlinsecurity.member.service

import com.example.kotlinsecurity.common.exception.InvalidInputException
import com.example.kotlinsecurity.member.dto.MemberRequest
import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
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

        return "회원가입이 완료되었습니다."
    }

}