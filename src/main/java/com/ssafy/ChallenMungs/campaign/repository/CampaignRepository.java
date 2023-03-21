package com.ssafy.ChallenMungs.campaign.repository;

import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long>  {



    // 진행중인 캠페인 - 생성일순(최신순)
    List<Campaign> findByIsEndFalseOrderByRegistDateDesc();

    // 진행중인 캠페인 - 모금액순(많은순)
    List<Campaign> findByIsEndFalseOrderByCollectAmountDesc();

    // 진행중인 캠페인
    List<Campaign> findByIsEndFalse();

    // 내가 응원한 캠페인


    // 내가 참여한 캠페인


    // User의 챌린지


}
