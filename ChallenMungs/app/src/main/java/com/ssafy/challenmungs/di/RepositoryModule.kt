package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.mypage.MyWalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.repository.*
import com.ssafy.challenmungs.domain.repository.*
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

    @Provides
    @Singleton
    fun providePanelRepository(
        panelRemoteDataSourceImpl: PanelRemoteDataSourceImpl
    ): PanelRepository = PanelRepositoryImpl(panelRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideMyWalletRepository(
        myWalletRemoteDataSourceImpl: MyWalletRemoteDataSourceImpl
    ): MyWalletRepository = MyWalletRepositoryImpl(myWalletRemoteDataSourceImpl)
}