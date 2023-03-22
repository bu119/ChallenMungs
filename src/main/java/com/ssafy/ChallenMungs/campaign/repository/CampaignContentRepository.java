package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignContentRepository extends JpaRepository<Content, Long> {

}
