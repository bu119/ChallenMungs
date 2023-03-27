package com.ssafy.challenmungs.data.remote.datasource.member

import com.ssafy.challenmungs.data.remote.service.MemberApiService
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberApiService: MemberApiService
) : MemberRemoteDataSource {

    override suspend fun getMemberInfo(): MemberResponse =
        memberApiService.getMemberInfo()
}