package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignListRepository extends JpaRepository<Campaign, Long>  {

    Campaign findCampaignByCampaignId(int campaignId);

    //진행중인 캠페인 중 특정 유저에게 속한 캠페인
    List<Campaign> findAllByUser(User user);

    // 진행중인 캠페인 - 생성일순(최신순)
    List<Campaign> findByTitleContainingAndIsEndFalseOrderByRegistDateDesc(String title);

    // 진행중인 캠페인 - 모금액순(많은순)
    List<Campaign> findByTitleContainingAndIsEndFalseOrderByCollectAmountDesc(String title);

    // 진행중인 캠페인
    List<Campaign> findByTitleContainingAndIsEndFalse(String title);

    // 검색없이 캠페인 조회
    List<Campaign> findByIsEndFalseOrderByRegistDateDesc();

    // 내가 응원한 캠페인
    List<Campaign> findByCampaignIdIn(List<Integer> campaignIds);


}
