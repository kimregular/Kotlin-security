package com.example.kotlinsecurity.member.repository

import com.example.kotlinsecurity.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
}