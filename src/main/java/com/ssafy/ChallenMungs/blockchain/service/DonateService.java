package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.blockchain.dto.DonationDetailDto;
import com.ssafy.ChallenMungs.blockchain.dto.DonationSummaryDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;

import java.util.List;
import java.util.Map;

public interface DonateService {
    void donate(int campaignId,int money,String memo,String loginId);
    void addCollectAmount(int campaignId,int money); //모금액 플러스(누적 금액 플러스)
    void plusWithdraw(int campaignId,int money); //출금 누적 금액 플러스. 이월할때도 호출
    void transfer(int fromCampaignId,int toCampaignId,int money);

    boolean isEndFund(Campaign campaign, int money); //addCollectAmount 전, 현재 모인금액+money가 목표금액 이상이면 isEnd를 true로 바꿈
    boolean  isEndCampaign(Campaign campaign,int money); //pluswithdraw 전, 모인금액을 모두 출금+모금종료상태이면 returnAccount를 호출

    void addComment(Campaign campaign,String memo,String loginId);
    void updateUserDonate(String loginId,int money);

    boolean checkCampaignTransferAble(int campaignId); //캠페인 이월 가능한지 체크
    void addDonation(Campaign campaign,int money,String loginId);



    List<Map<String, Object>> viewMyDonations(String loginId, int year);
    DonationDetailDto getDonation(int donationId);
    DonationSummaryDto getSummary(String loginId, int year);
    int totalDonateAll();




}
