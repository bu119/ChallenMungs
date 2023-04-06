package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.challenmungs.domain.entity.member.Auth
import com.ssafy.challenmungs.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun requestLogin(body: RequestBody): Resource<Auth> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestLogin(body).toDomainModel()
        }

    override suspend fun requestJoin(name: String, accessToken: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestJoin(name, accessToken).toDomainModel()
        }

    override suspend fun requestShelterJoin(
        shelterName: String,
        inviteCode: String,
        memberId: String,
        password: String
    ): Resource<String> = wrapToResource(Dispatchers.IO) {
        authRemoteDataSource.requestShelterJoin(shelterName, inviteCode, memberId, password)
            .toDomainModel()
    }

    override suspend fun requestInviteCode(shelterName: String, email: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestInviteCode(shelterName, email).toDomainModel()
        }
}