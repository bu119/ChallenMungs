package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;

import java.util.List;

public interface CampaignListService {
    // 기부 탭
    List<CampaignDto> getCampaign(String type, int sort);

    // 후원처 탭
    List<Campaign> getShelter(String loginId);

    // 내가 참여한 캠페인
    List<CampaignDto> getUserParticipate(String loginId);

    // 내가 응원한 캠페인
    List<CampaignDto> getUserLove(String loginId);
}
