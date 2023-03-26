package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;

public interface DonateService {
    void addCollectAmount(int campaignId,int money,String memo,String loginId); //모금액 플러스(누적 금액 플러스)
    void plusWithdraw(int campaignId,int money); //출금 누적 금액 플러스. 이월할때도 호출

    boolean isEndFund(Campaign campaign, int money); //addCollectAmount 전, 현재 모인금액+money가 목표금액 이상이면 isEnd를 true로 바꿈
    void isEndCampaign(Campaign campaign,int money); //pluswithdraw 전, 모인금액을 모두 출금+모금종료상태이면 returnAccount를 호출
    void returnAccount(int campaignId);//계좌 반납(목표금액만큼 전부 출금한 경우)
    void addComment(Campaign campaign,String memo,String loginId);
    void updateUserDonate(String loginId,int money);



}
