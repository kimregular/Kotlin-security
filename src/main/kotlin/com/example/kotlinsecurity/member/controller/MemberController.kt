package com.example.kotlinsecurity.member.controller

import com.example.kotlinsecurity.common.authority.TokenInfo
import com.example.kotlinsecurity.common.dto.BaseResponse
import com.example.kotlinsecurity.member.dto.LoginRequest
import com.example.kotlinsecurity.member.dto.MemberRequest
import com.example.kotlinsecurity.member.dto.MemberResponse
import com.example.kotlinsecurity.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    /**
     * login
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest): BaseResponse<TokenInfo> {
        return BaseResponse(data = memberService.login(loginRequest))
    }

    @GetMapping("/info/{id}")
    fun searchMyInfo(@PathVariable id: Long): BaseResponse<MemberResponse> {
        return BaseResponse(data = memberService.searchMyInfo(id))
    }
}