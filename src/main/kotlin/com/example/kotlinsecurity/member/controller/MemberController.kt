package com.example.kotlinsecurity.member.controller

import com.example.kotlinsecurity.common.dto.BaseResponse
import com.example.kotlinsecurity.member.dto.MemberRequest
import com.example.kotlinsecurity.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid memberRequest: MemberRequest): BaseResponse<Unit> {
        return BaseResponse(message = memberService.signUp(memberRequest))
    }
}