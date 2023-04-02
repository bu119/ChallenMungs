package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignContentRepository extends JpaRepository<Content, Long> {

    List <Content>findAllByCampaign(Campaign campaign);

}
