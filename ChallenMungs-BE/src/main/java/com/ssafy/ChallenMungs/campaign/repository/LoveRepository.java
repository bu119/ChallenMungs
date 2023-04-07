package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Love;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoveRepository extends JpaRepository<Love, Long> {
    Love findByUserAndCampaign(User user, Campaign campaign);
    int countByUserAndCampaign(User user, Campaign campaign); //유저 조아요 중복체크용
    int countByCampaign(Campaign campaign);
    List<Love> findAllByUser(User user);

//    //select 문으로 추출
//    @Query("select l.campaign from Love l")
//    List<Object> selectCampaign(List<Love> list);
}
