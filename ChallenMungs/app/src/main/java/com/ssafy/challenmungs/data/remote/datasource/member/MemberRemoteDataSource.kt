package com.ssafy.challenmungs.data.remote.datasource.member

interface MemberRemoteDataSource {

    suspend fun getMemberInfo(): MemberResponse
}