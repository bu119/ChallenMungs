package com.ssafy.ChallenMungs.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//캠페인 목록 중 하나를 클릭했을 때 보이는 모든 정보들입니다.
public class CampaignDetailDto {
    String title;
    String thumbnail;
    String writer;
    int lovecnt;
    int collectAmount;
    int targetAmount;

    List<ContentDto> contentList;



}
