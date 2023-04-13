package com.ssafy.ChallenMungs.campaign.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampaignShelterDto {
    private int campaignId;
    private String thumbnail;
    private String title;
    private int targetAmount;
    private int collectAmount;
    private int withdrawAmount;
    private LocalDate registDate;
    private boolean isEnd;
    private LocalDate endDate;

}

