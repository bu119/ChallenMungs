package com.ssafy.challenmungs.domain.usecase.auth

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestShelterJoinUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        shelterName: String,
        inviteCode: String,
        memberId: String,
        password: String
    ): Resource<String> = withContext(Dispatchers.IO) {
        authRepository.requestShelterJoin(shelterName, inviteCode, memberId, password)
    }
}