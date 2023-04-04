package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSource
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.challenmungs.data.remote.service.AuthApiService
import com.ssafy.challenmungs.data.remote.service.DonateApiService
import com.ssafy.challenmungs.data.remote.service.MemberApiService
import com.ssafy.challenmungs.data.remote.service.WalletApiService
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
    ): AuthRemoteDataSourceImpl = AuthRemoteDataSourceImpl(authApiService)

    @Provides
    @Singleton
    fun provideMemberDataSource(
        memberApiService: MemberApiService
    ): MemberRemoteDataSourceImpl = MemberRemoteDataSourceImpl(memberApiService)

    @Provides
    @Singleton
    fun provideWalletDataSource(
        walletApiService: WalletApiService
    ): WalletRemoteDataSourceImpl = WalletRemoteDataSourceImpl(walletApiService)

    @Provides
    @Singleton
    fun provideDonateDataSource(
        donateApiService: DonateApiService
    ): DonateRemoteDataSource = DonateRemoteDataSourceImpl(donateApiService)
}