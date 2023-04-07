package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignShelterDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Comment;
import com.ssafy.ChallenMungs.campaign.entity.Love;
import com.ssafy.ChallenMungs.campaign.repository.CampaignContentRepository;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.CommentRepository;
import com.ssafy.ChallenMungs.campaign.repository.LoveRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignListServiceImpl implements CampaignListService {
    private final CampaignListRepository jpaRepo;
    private final CampaignContentRepository contentRepo;
    private final UserRepository userRepo;
    private final LoveRepository loveRepo;
    private final CommentRepository commentRepo;
    private Logger log = LoggerFactory.getLogger(CampaignListServiceImpl.class);


    @Override
    public List<CampaignDto> getCampaign(String title, String type, int sort) {
        List<Campaign> list;

        if(title == null){
            list = jpaRepo.findByIsEndFalseOrderByRegistDateDesc();
        }
        else{
            if (type.equals("date")){
                list = jpaRepo.findByTitleContainingAndIsEndFalseOrderByRegistDateDesc(title);
                if (sort == 0){
                    Collections.reverse(list);
                }
            }
            else if (type.equals("amount")){
                list = jpaRepo.findByTitleContainingAndIsEndFalseOrderByCollectAmountDesc(title);
                if (sort == 0){
                    Collections.reverse(list);
                }
            }
            else if (type.equals("love")){
                list = jpaRepo.findByTitleContainingAndIsEndFalse(title);
                List<CampaignDto> dtoList = list.stream()
                        .map(b -> new CampaignDto(b.getCampaignId(),b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveRepo.countByCampaign(b) ))
                        .collect(Collectors.toList());

                // 내림 차순 정렬
                Collections.sort(dtoList, (a, b) -> a.getLoveCount() - b.getLoveCount());

                if (sort == 0){
                    return dtoList;
                }
                else{
                    Collections.reverse(dtoList);
                    return dtoList;
                }

            }
            else {
                // 정렬 조건 없으면 최신순 정렬
                list = jpaRepo.findByTitleContainingAndIsEndFalseOrderByRegistDateDesc(title);
            }
        }

        return list.stream()
                .map(b -> new CampaignDto(b.getCampaignId(),b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveRepo.countByCampaign(b) ))
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
    public List<CampaignDto> getUserDonate(String loginId) {
        User loginUser = userRepo.findUserByLoginId(loginId);
        List<Comment> donateCampaigns = commentRepo.findAllByUser(loginUser);

        List<Integer> campaignIds = new ArrayList<>();
        for (Comment commentItem:donateCampaigns){
            campaignIds.add(commentItem.getCampaign().getCampaignId());
        }
        //캠페인 아이디로 캠페인 필터링 하기
        List<Campaign> list = jpaRepo.findByCampaignIdIn(campaignIds);

        return  list.stream()
                .map(b -> new CampaignDto(b.getCampaignId(),b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveRepo.countByCampaign(b)))
                .collect(Collectors.toList());
    }

    // 해당 유저가 응원한 캠페인 목록
    @Override
    public List<CampaignDto> getUserLove(String loginId) {
        User loginUser = userRepo.findUserByLoginId(loginId);

        //love에서 로그인 아이디와 일치하는 캠페인 아이디 뽑기
        List<Love> loveCampaigns = loveRepo.findAllByUser(loginUser);
        // 캠페인 아이디 리스트로 저장
        List<Integer> campaignIds = new ArrayList<>();
        for(Love loveItem:loveCampaigns){
            campaignIds.add(loveItem.getCampaign().getCampaignId());
        }
        //캠페인 아이디로 캠페인 필터링 하기
        List<Campaign> list = jpaRepo.findByCampaignIdIn(campaignIds);

        return  list.stream()
                .map(b -> new CampaignDto(b.getCampaignId(),b.getThumbnail(),b.getTitle(), b.getName(), b.getCollectAmount(), b.getTargetAmount(), loveRepo.countByCampaign(b)))
                .collect(Collectors.toList());
    }

}
