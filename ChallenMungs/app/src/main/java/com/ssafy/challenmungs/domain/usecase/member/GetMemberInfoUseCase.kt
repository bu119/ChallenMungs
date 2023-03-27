package com.ssafy.challenmungs.domain.usecase.member

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Member
import com.ssafy.challenmungs.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMemberInfoUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Resource<Member> = withContext(Dispatchers.IO) {
        memberRepository.getMemberInfo()
    }
}