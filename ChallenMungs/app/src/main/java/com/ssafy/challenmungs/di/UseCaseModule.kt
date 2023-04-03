package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.domain.repository.AuthRepository
import com.ssafy.challenmungs.domain.repository.DonateRepository
import com.ssafy.challenmungs.domain.repository.MemberRepository
import com.ssafy.challenmungs.domain.repository.WalletRepository
import com.ssafy.challenmungs.domain.usecase.auth.JoinUseCase
import com.ssafy.challenmungs.domain.usecase.auth.LogInUseCase
import com.ssafy.challenmungs.domain.usecase.auth.SetWalletUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import com.ssafy.challenmungs.domain.usecase.klaytn.CreateAccountUseCase
import com.ssafy.challenmungs.domain.usecase.member.GetMemberInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLogInUseCase(authRepository: AuthRepository): LogInUseCase =
        LogInUseCase(authRepository)

    @Singleton
    @Provides
    fun provideJoinUseCase(authRepository: AuthRepository): JoinUseCase =
        JoinUseCase(authRepository)

    @Singleton
    @Provides
    fun provideSetWalletUseCase(memberRepository: MemberRepository): SetWalletUseCase =
        SetWalletUseCase(memberRepository)

    @Singleton
    @Provides
    fun provideGetMemberInfoUseCase(memberRepository: MemberRepository): GetMemberInfoUseCase =
        GetMemberInfoUseCase(memberRepository)

    @Singleton
    @Provides
    fun provideCreateAccountUseCase(walletRepository: WalletRepository): CreateAccountUseCase =
        CreateAccountUseCase(walletRepository)

    @Singleton
    @Provides
    fun provideGetCampaignListUseCase(donateRepository: DonateRepository): GetCampaignListUseCase =
        GetCampaignListUseCase(donateRepository)
}