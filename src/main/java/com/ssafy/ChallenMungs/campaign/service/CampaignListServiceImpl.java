package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignShelterDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignListServiceImpl implements CampaignListService {
    private final CampaignListRepository jpaRepo;
    private final UserRepository userRepo;

    // 기부탭의 캠페인 목록
    @Override
    public List<CampaignDto> getCampaign(String type, int sort) {
        List<Campaign> list;
        if (type.equals("date")){
            list = jpaRepo.findByIsEndFalseOrderByRegistDateDesc();
//            list=jpaRepo.findAll();
            if (sort == 0){
                Collections.reverse(list);
            }
        }
        else if (type.equals("amount")){
            list = jpaRepo.findByIsEndFalseOrderByCollectAmountDesc();
            if (sort == 0){
                Collections.reverse(list);
            }
        }
        else if (type.equals("love")){
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
                .map(b -> new CampaignDto(b.getCampaignId(),b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveCount ))
                .collect(Collectors.toList());
    }

    // 후원처 로그인시 캠페인 목록
    @Override
    public List<CampaignShelterDto> getShelter(String loginId) {
        List<Campaign> list;
        User shelterUser = userRepo.findUserByLoginId(loginId);
        list = jpaRepo.findAllByUser(shelterUser);

        return list.stream()
                .map(b -> new CampaignShelterDto(b.getCampaignId(), b.getThumbnail(), b.getTitle(), b.getTargetAmount(), b.getCollectAmount(), b.getWithdrawAmount(), b.getRegistDate(), b.isEnd(), b.getEndDate()))
                .collect(Collectors.toList());
    }

    // 해당 유저가 참여한 캠페인 목록
    @Override
    public List<CampaignDto> getUserParticipate(String loginId) {
        return null;
    }

    // 해당 유저가 응원한 캠페인 목록
    @Override
    public List<CampaignDto> getUserLove(String loginId) {
        return null;
    }
}
