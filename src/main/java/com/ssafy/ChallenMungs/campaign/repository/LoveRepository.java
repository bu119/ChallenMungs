package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Love;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoveRepository extends JpaRepository<Love, Long> {
    Love findByUserAndCampaign(User user, Campaign campaign);
    int countByUserAndCampaign(User user, Campaign campaign);
}
