package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Member

interface MemberRepository {

    suspend fun getMemberInfo(): Resource<Member>
}