package com.ssafy.challenmungs.data.remote.datasource.member

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface MemberRemoteDataSource {

    suspend fun getMemberInfo(): MemberResponse

    suspend fun setWallet(memberId: String, piggyBank: String, wallet: String): ResultResponse
}