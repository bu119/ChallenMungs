package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.domain.repository.AuthRepository
import com.ssafy.challenmungs.domain.repository.MemberRepository
import com.ssafy.challenmungs.domain.usecase.auth.GetMemberInfoUseCase
import com.ssafy.challenmungs.domain.usecase.auth.LogInUseCase
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
    fun provideGetMemberInfoUseCase(memberRepository: MemberRepository): GetMemberInfoUseCase =
        GetMemberInfoUseCase(memberRepository)
}