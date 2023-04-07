package com.ssafy.challenmungs.domain.usecase.auth

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Auth
import com.ssafy.challenmungs.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: RequestBody): Resource<Auth> = withContext(Dispatchers.IO) {
        authRepository.requestLogin(body)
    }
}