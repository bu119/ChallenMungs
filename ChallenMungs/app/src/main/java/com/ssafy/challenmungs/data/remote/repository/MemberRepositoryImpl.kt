package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSource
import com.ssafy.challenmungs.domain.entity.member.Member
import com.ssafy.challenmungs.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
) : MemberRepository {

    override suspend fun getMemberInfo(): Resource<Member> =
        wrapToResource(Dispatchers.IO) {
            memberRemoteDataSource.getMemberInfo().toDomainModel()
        }

    override suspend fun setWallet(
        memberId: String,
        piggyBank: String,
        wallet: String
    ): Resource<String> = wrapToResource(Dispatchers.IO) {
        memberRemoteDataSource.setWallet(memberId, piggyBank, wallet).toDomainModel()
    }
}