package com.ssafy.ChallenMungs.campaign.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampaignDto {
    int campaignId;
    String thumbnail;
    String title;
    String name;
    int collectAmount;
    int targetAmount;
    int loveCount;

}
