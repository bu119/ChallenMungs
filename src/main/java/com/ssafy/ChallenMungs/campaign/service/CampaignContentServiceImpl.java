package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDetailDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignContentServiceImpl implements CampaignContentService{
    @Override
    public void createCampaign(CampaignInsertDto info) {

    }

    @Override
    public boolean isCampaignAble(String loginId) {
        return false;
    }

    @Override
    public int cheerUpCampaign() {
        return 0;
    }

    @Override
    public CampaignDetailDto viewDetailCampaign(int campaignId) {
        return null;
    }
}
