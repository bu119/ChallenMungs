package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.repository.AuthRepositoryImpl
import com.ssafy.challenmungs.data.remote.repository.ChallengeRepositoryImpl
import com.ssafy.challenmungs.data.remote.repository.DonateRepositoryImpl
import com.ssafy.challenmungs.data.remote.repository.MemberRepositoryImpl
import com.ssafy.challenmungs.data.remote.repository.WalletRepositoryImpl
import com.ssafy.challenmungs.domain.repository.AuthRepository
import com.ssafy.challenmungs.domain.repository.ChallengeRepository
import com.ssafy.challenmungs.domain.repository.DonateRepository
import com.ssafy.challenmungs.domain.repository.MemberRepository
import com.ssafy.challenmungs.domain.repository.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideMemberRepository(
        memberRemoteDataSourceImpl: MemberRemoteDataSourceImpl
    ): MemberRepository = MemberRepositoryImpl(memberRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideWalletRepository(
        walletRemoteDataSourceImpl: WalletRemoteDataSourceImpl
    ): WalletRepository = WalletRepositoryImpl(walletRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideChallengeRepository(
        challengeRemoteDataSourceImpl: ChallengeRemoteDataSourceImpl
    ): ChallengeRepository = ChallengeRepositoryImpl(challengeRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideDonateRepository(
        donateRemoteDataSourceImpl: DonateRemoteDataSourceImpl
    ): DonateRepository = DonateRepositoryImpl(donateRemoteDataSourceImpl)
}