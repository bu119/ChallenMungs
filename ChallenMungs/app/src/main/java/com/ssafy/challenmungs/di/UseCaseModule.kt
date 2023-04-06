package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.domain.repository.*
import com.ssafy.challenmungs.domain.usecase.auth.JoinUseCase
import com.ssafy.challenmungs.domain.usecase.auth.LogInUseCase
import com.ssafy.challenmungs.domain.usecase.auth.SetWalletUseCase
import com.ssafy.challenmungs.domain.usecase.challenge.CreatePanelChallengeUseCase
import com.ssafy.challenmungs.domain.usecase.challenge.GetChallengeListUseCase
import com.ssafy.challenmungs.domain.usecase.challenge.GetPanelInfoUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import com.ssafy.challenmungs.domain.usecase.klaytn.CreateAccountUseCase
import com.ssafy.challenmungs.domain.usecase.member.GetMemberInfoUseCase
import com.ssafy.challenmungs.domain.usecase.mypage.*
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
    fun provideGetChallengeListUseCase(challengeRepository: ChallengeRepository): GetChallengeListUseCase =
        GetChallengeListUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetCampaignListUseCase(donateRepository: DonateRepository): GetCampaignListUseCase =
        GetCampaignListUseCase(donateRepository)

    @Singleton
    @Provides
    fun provideGetChallengeInfoUseCase(panelRepository: PanelRepository): GetPanelInfoUseCase =
        GetPanelInfoUseCase(panelRepository)

    @Singleton
    @Provides
    fun provideCreatePanelChallengeUseCase(panelRepository: PanelRepository): CreatePanelChallengeUseCase =
        CreatePanelChallengeUseCase(panelRepository)

    @Singleton
    @Provides
    fun provideGetTotalDonateUseCase(myWalletRepository: MyWalletRepository): GetTotalDonateUseCase =
        GetTotalDonateUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetMyWalletBalanceUseCase(myWalletRepository: MyWalletRepository): GetMyWalletBalanceUseCase =
        GetMyWalletBalanceUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetMyWalletHistoryUseCase(myWalletRepository: MyWalletRepository): GetMyWalletHistoryUseCase =
        GetMyWalletHistoryUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetPiggyBankHistoryUseCase(myWalletRepository: MyWalletRepository): GetPiggyBankHistoryUseCase =
        GetPiggyBankHistoryUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetDonationSummaryUseCase(myWalletRepository: MyWalletRepository): GetDonationSummaryUseCase =
        GetDonationSummaryUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetDonationHistoryUseCase(myWalletRepository: MyWalletRepository): GetDonationHistoryUseCase =
        GetDonationHistoryUseCase(myWalletRepository)

    @Singleton
    @Provides
    fun provideGetDonationDetailUseCase(myWalletRepository: MyWalletRepository): GetDonationDetailUseCase =
        GetDonationDetailUseCase(myWalletRepository)
}