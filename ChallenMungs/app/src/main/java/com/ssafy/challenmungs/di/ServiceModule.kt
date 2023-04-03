package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.AuthInterceptorClient
import com.ssafy.challenmungs.NoAuthInterceptorClient
import com.ssafy.challenmungs.WalletInterceptorClient
import com.ssafy.challenmungs.data.remote.service.AuthApiService
import com.ssafy.challenmungs.data.remote.service.MemberApiService
import com.ssafy.challenmungs.data.remote.service.PanelApiService
import com.ssafy.challenmungs.data.remote.service.WalletApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthApiService(
        @NoAuthInterceptorClient retrofit: Retrofit
    ): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideMemberApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): MemberApiService =
        retrofit.create(MemberApiService::class.java)

    @Provides
    @Singleton
    fun provideWalletApiService(
        @WalletInterceptorClient retrofit: Retrofit
    ): WalletApiService =
        retrofit.create(WalletApiService::class.java)

    @Provides
    @Singleton
    fun provideChallengeApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): PanelApiService = retrofit.create(PanelApiService::class.java)
}