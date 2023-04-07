package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.mypage.MyWalletRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.mypage.MyWalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(
        authApiService: AuthApiService
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authApiService)

    @Provides
    @Singleton
    fun provideMemberDataSource(
        memberApiService: MemberApiService
    ): MemberRemoteDataSource = MemberRemoteDataSourceImpl(memberApiService)

    @Provides
    @Singleton
    fun provideWalletDataSource(
        walletApiService: WalletApiService
    ): WalletRemoteDataSource = WalletRemoteDataSourceImpl(walletApiService)

    @Provides
    @Singleton
    fun provideChallengeDataSource(
        challengeApiService: ChallengeApiService
    ): ChallengeRemoteDataSource = ChallengeRemoteDataSourceImpl(challengeApiService)

    @Provides
    @Singleton
    fun provideDonateDataSource(
        donateApiService: DonateApiService
    ): DonateRemoteDataSource = DonateRemoteDataSourceImpl(donateApiService)

    @Provides
    @Singleton
    fun providePanelDataSource(
        panelApiService: PanelApiService
    ): PanelRemoteDataSource = PanelRemoteDataSourceImpl(panelApiService)

    @Provides
    @Singleton
    fun provideMyWalletDataSource(
        myWalletApiService: MyWalletApiService
    ): MyWalletRemoteDataSource = MyWalletRemoteDataSourceImpl(myWalletApiService)
}