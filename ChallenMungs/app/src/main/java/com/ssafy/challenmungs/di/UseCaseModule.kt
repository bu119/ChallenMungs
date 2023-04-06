package com.ssafy.challenmungs.di

import com.ssafy.challenmungs.domain.repository.*
import com.ssafy.challenmungs.domain.usecase.auth.*
import com.ssafy.challenmungs.domain.usecase.challenge.*
import com.ssafy.challenmungs.domain.usecase.donate.GetBalanceUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignInfoUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import com.ssafy.challenmungs.domain.usecase.donate.RequestDonateUseCase
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
    fun provideRequestShelterJoinUseCase(authRepository: AuthRepository): RequestShelterJoinUseCase =
        RequestShelterJoinUseCase(authRepository)

    @Singleton
    @Provides
    fun provideRequestInviteCodeUseCase(authRepository: AuthRepository): RequestInviteCodeUseCase =
        RequestInviteCodeUseCase(authRepository)

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
    fun provideGetChallengeInfoUseCase(challengeRepository: ChallengeRepository): GetChallengeInfoUseCase =
        GetChallengeInfoUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetBasicTodayUseCase(challengeRepository: ChallengeRepository): GetBasicTodayUseCase =
        GetBasicTodayUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetBasicHistoryUseCase(challengeRepository: ChallengeRepository): GetBasicHistoryUseCase =
        GetBasicHistoryUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetParticipantsUseCase(challengeRepository: ChallengeRepository): GetParticipantsUseCase =
        GetParticipantsUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetChallengeParticipationFlagUseCase(challengeRepository: ChallengeRepository): GetChallengeParticipationFlagUseCase =
        GetChallengeParticipationFlagUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideRequestParticipateUseCase(challengeRepository: ChallengeRepository): RequestParticipateUseCase =
        RequestParticipateUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideRequestWithDrawUseCase(challengeRepository: ChallengeRepository): RequestWithDrawUseCase =
        RequestWithDrawUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideRequestRejectUseCase(challengeRepository: ChallengeRepository): RequestRejectUseCase =
        RequestRejectUseCase(challengeRepository)

    @Singleton
    @Provides
    fun provideGetCampaignListUseCase(donateRepository: DonateRepository): GetCampaignListUseCase =
        GetCampaignListUseCase(donateRepository)

    @Singleton
    @Provides
    fun provideGetCampaignInfoUseCase(donateRepository: DonateRepository): GetCampaignInfoUseCase =
        GetCampaignInfoUseCase(donateRepository)

    @Singleton
    @Provides
    fun provideGetBalanceUseCase(donateRepository: DonateRepository): GetBalanceUseCase =
        GetBalanceUseCase(donateRepository)

    @Singleton
    @Provides
    fun provideRequestDonateUseCase(donateRepository: DonateRepository): RequestDonateUseCase =
        RequestDonateUseCase(donateRepository)

    @Singleton
    @Provides
    fun provideGetPanelInfoUseCase(panelRepository: PanelRepository): GetPanelInfoUseCase =
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