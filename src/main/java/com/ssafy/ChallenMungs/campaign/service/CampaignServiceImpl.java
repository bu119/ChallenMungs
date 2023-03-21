package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService{
    private final CampaignRepository jpaRepo;


    @Override
    public List<CampaignDto> getCampaign(String type, int sort) {
        List<Campaign> list;
        if (type == "date"){
            list = jpaRepo.findByIsEndFalseOrderByRegistDateDesc();
            if (sort == 0){
                Collections.reverse(list);
            }
        }
        else if (type == "amount"){
            list = jpaRepo.findByIsEndFalseOrderByCollectAmountDesc();
            if (sort == 0){
                Collections.reverse(list);
            }
        }
        else if (type == "love"){
             list = jpaRepo.findByIsEndFalse();
            if (sort == 0){
                // Love DB 만들어졌을 때 실행
            }
        }
        else {
            // 정렬 조건 없으면 최신순 정렬
            list = jpaRepo.findByIsEndFalseOrderByRegistDateDesc();
        }
        // 이후 수정 필요!
        int loveCount = 0;
        return list.stream()
                .map(b -> new CampaignDto(b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveCount ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Campaign> getShelter(String name) {
        return null;
    }

    @Override
    public List<CampaignDto> getUserParticipate(String name) {
        return null;
    }

    @Override
    public List<CampaignDto> getUserLove(String name) {
        return null;
    }
}
