package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignDetailDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import com.ssafy.ChallenMungs.campaign.dto.ContentDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Content;
import com.ssafy.ChallenMungs.campaign.entity.Love;
import com.ssafy.ChallenMungs.campaign.repository.CampaignContentRepository;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.LoveRepository;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignContentServiceImpl implements CampaignContentService{

    private final CampaignListRepository listRepo;
    private final CampaignContentRepository contentRepo;
    private final LoveRepository loveRepo;
    private final UserRepository userRepo;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Override
    public void createCampaign(CampaignInsertDto info) {
        //캠페인 개요 저장하고
        Campaign campaign=initCampaign(info);
        listRepo.save(campaign);
        listRepo.flush(); //방금 막 넣은 엔티티의 아이디를 가져오기 위함

        //내용 저장하기
        for(int i=0;i<info.getContentList().size();i++){
            Content content=initContent(info.getContentList().get(i));
            content.setSeq(i);
            content.setCampaign(campaign);
            contentRepo.save(content);
        }

    }
    public Content initContent(ContentDto dto){
        Content content=new Content();
        content.setBody(dto.getBody());
        content.setType(dto.getType());
        return content;
    }
    public Campaign initCampaign(CampaignInsertDto info){
        User shelter=getUser(info.getLoginId());
        String account=getAccount(info.getLoginId());
        Campaign campaign=new Campaign();
        campaign.setUser(shelter);
        campaign.setName(shelter.getName());
        campaign.setWalletAddress(account);
        campaign.setThumbnail(info.getThumbnail());
        campaign.setTitle(info.getTitle());
        campaign.setTargetAmount(info.getTargetAmount());
        campaign.setRegistDate(LocalDateTime.now());
        campaign.setCollectAmount(0);
        campaign.setWithdrawAmount(0);
        campaign.setEnd(false);

        return campaign;
    }

    public User getUser(String loginId){
        return userRepo.findUserByLoginId(loginId);
    }

    //todo 지갑
    public String getAccount(String loginId){
        return "testAccount";
    }

    @Override
    public boolean isCampaignAble(String loginId) {
        return false;
    }

    @Override
    public int cheerUpCampaign(String loginId, int campaignId) {
        Campaign campaign=listRepo.findCampaignByCampaignId(campaignId);
        User user=userRepo.findUserByLoginId(loginId);
        //잘못된 입력
        if(user==null||campaign==null) return 2;
        boolean isExist=isExistLove(user,campaign);
        //첫 조아요
         if(!isExist){
             Love love=new Love();
             love.setCampaign(campaign);
             love.setUser(user);
            loveRepo.save(love);
            return 0;
         }
         //중복 조아요
         return 1;
    }
    public boolean isExistLove(User user,Campaign campaign){
        if(loveRepo.countByUserAndCampaign(user,campaign)==0) return false;
        return true;
    }

    //todo lovecnt 로직만들기
    @Override
    public CampaignDetailDto viewDetailCampaign(int campaignId) {
        Campaign campaign=listRepo.findCampaignByCampaignId(campaignId);
        int loveCnt=0;
        CampaignDetailDto result=new CampaignDetailDto(
                campaign.getTitle(),campaign.getThumbnail(), campaign.getName(),0,
                campaign.getCollectAmount(),campaign.getTargetAmount(),getContentDtoList(campaign));

        return result;

    }
    public List<ContentDto> getContentDtoList(Campaign campaign){
        List<Content> list=contentRepo.findAllByCampaign(campaign);
        List <ContentDto> contentDtoList=new ArrayList<>();
        for(Content content:list){
            ContentDto dto=new ContentDto(content.getType(),content.getBody());
            contentDtoList.add(dto);
        }
        return contentDtoList;

    }
}
