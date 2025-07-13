package com.example.kotlinsecurity.member.entity

import com.example.kotlinsecurity.common.status.Gender
import com.example.kotlinsecurity.member.dto.MemberResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
) // 아이디로도 중복이 되지 않지만 로그인 아이디로도 중복되지 않는 유니크 키 설정
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    var loginId: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 10)
    var name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @Column(nullable = false, length = 30)
    var email: String,
) {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    private fun LocalDate.formatDate(): String = this.toString()

    fun toDto(): MemberResponse =
        MemberResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)

}