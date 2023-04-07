package com.ssafy.challenmungs.domain.usecase.auth

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetWalletUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(
        memberId: String,
        piggyBank: String,
        wallet: String
    ): Resource<String> =
        withContext(Dispatchers.IO) {
            memberRepository.setWallet(memberId, piggyBank, wallet)
        }
}