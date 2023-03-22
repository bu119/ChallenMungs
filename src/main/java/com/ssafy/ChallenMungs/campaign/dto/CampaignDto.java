package com.ssafy.ChallenMungs.campaign.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampaignDto {
    // 캠페인 목록 필수 정보
    // 썸네일, 제목, 보호소, 응원수, 누적금액, 목표금액

    int campaignId;
    String thumbnail;
    String title;
    String name;
    int collectAmount;
    int targetAmount;
    int loveCount;






    // 기부 탭(누적금액순, 최신순, 좋아요 순)
    // 내가 응원한 캠페인
    // 내가 참여한 캠페인
    // 후원처 탭 : 모금 종료여부, 생성일, 종료일, 출금 누적금액

    // 기본 정보
    // 썸네일, 제목, 보호소, 응원수, 누적금액, 목표금액

    // 필요없는 거
    // 캠페인 아이디, 후원처 아이디, 캠페인 지갑 주소, 출금 누적 금액


}
