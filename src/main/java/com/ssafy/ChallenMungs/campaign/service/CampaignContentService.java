package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDetailDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;

public interface CampaignContentService {
    void createCampaign(CampaignInsertDto info);
    boolean isCampaignAble(String loginId);

    int cheerUpCampaign(String loginId, int campaignId);
    CampaignDetailDto viewDetailCampaign(int campaignId);






}
