package com.ssafy.ChallenMungs.campaign.service;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.campaign.dto.CampaignDetailDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import com.ssafy.ChallenMungs.campaign.dto.ContentDto;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Content;
import com.ssafy.ChallenMungs.campaign.entity.Love;
import com.ssafy.ChallenMungs.campaign.entity.Receipt;
import com.ssafy.ChallenMungs.campaign.repository.CampaignContentRepository;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.LoveRepository;
import com.ssafy.ChallenMungs.campaign.repository.ReceiptRepository;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
    private final WalletRepository walletRepo;
    private final ReceiptRepository receiptRepo;


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

    @Override
    public void uploadReceipt(String url, int campaignId) {
        Receipt receipt = new Receipt();
        receipt.setReceipt(url);
        receipt.setCampaign(listRepo.findCampaignByCampaignId(campaignId));
        receiptRepo.save(receipt);


    }

    public Content initContent(ContentDto dto){
        Content content=new Content();
        content.setBody(dto.getBody());
        content.setType(dto.getType());
        return content;
    }

    public Campaign initCampaign(CampaignInsertDto info){
        User shelter=getUser(info.getLoginId());
        String account=getAddress(info.getLoginId());
        Campaign campaign=new Campaign();
        campaign.setUser(shelter);
        campaign.setName(shelter.getName());
        campaign.setWalletAddress(account);
        campaign.setThumbnail(info.getThumbnail());
        campaign.setTitle(info.getTitle());
        campaign.setTargetAmount(info.getTargetAmount());
        campaign.setRegistDate(getNowTime());
        campaign.setRegistUnix(System.currentTimeMillis() / 1000L);
        campaign.setCollectAmount(0);
        campaign.setWithdrawAmount(0);
        campaign.setEnd(false);

        return campaign;
    }

    public LocalDate getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        String date=now.toString().substring(0,10);
        return stringtoLocalDate(date);

    }
    public LocalDate stringtoLocalDate(String date){
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, dateformatter);
        return ld;
    }
    public User getUser(String loginId){
        return userRepo.findUserByLoginId(loginId);
    }

    @Override
    public boolean isCampaignAble(String loginId) {
        //일반유저면 캠페인 생성 불가
        if(userRepo.findUserByLoginId(loginId).getType()=='n') return false;

        String address=getAddress(loginId);
        
        if(address.equals("disable")) return false;
        return true;
    }

    //후원처 유저의 아이디를 가지고 사용 가능한 캠페인계좌 하나를 반환합니다. 없으면 "disable" 반환
    public String getAddress(String loginId){
        User user=userRepo.findUserByLoginId(loginId);
        //그 유저의 캠페인을 모두 가져와서, none이 아닌 계좌가 2개 미만이면
        List <Campaign> campaigns=listRepo.findAllByUser(user);
        int campaignCnt=0; //사용한 슬롯의 수
        String existAddress="";
        for(Campaign campaign:campaigns){
//            if(!campaign.getWalletAddress().equals("none"))
//            System.out.println("----------------------------------");
//            System.out.println(campaign.getCampaignId());
//            System.out.println(campaign.getEndUnix());
            if(campaign.getEndUnix() == 0){
                campaignCnt++;
                existAddress = campaign.getWalletAddress();
            }
//            else{
//                existAddress=campaign.getWalletAddress();
//            }
        }
        //사용가능한 add가 2개이면(슬롯 하나도 안 쓴 상태면) 첫번째 캠페인 계좌주소를 반환
        if(campaignCnt==0){
             return walletRepo.findByUserAndType(user,'1').getAddress();
        }
        //사용가능한 add가 1개이면 사용하지 않는 것을 반환
        else if(campaignCnt==1){
            //1번 슬롯 사용중이면 2번을 반환
            if(walletRepo.findByUserAndType(user,'1').getAddress().equals(existAddress))
                return walletRepo.findByUserAndType(user,'2').getAddress();
            //2번 슬롯 사용중이면 1번을 반환
            else
                return walletRepo.findByUserAndType(user,'1').getAddress();
        }

        return "disable";
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

    @Override
    public CampaignDetailDto viewDetailCampaign(int campaignId) {
        Campaign campaign=listRepo.findCampaignByCampaignId(campaignId);

        CampaignDetailDto result=new CampaignDetailDto(
                campaign.getTitle(),campaign.getThumbnail(), campaign.getName(),getLoveCnt(campaign),
                campaign.getCollectAmount(),campaign.getTargetAmount(),getContentDtoList(campaign));

        return result;

    }

    @Override
    public int campaignBalance(int campaignId) {
        Campaign campaign=listRepo.findCampaignByCampaignId(campaignId);
        int balance = campaign.getCollectAmount() - campaign.getWithdrawAmount();
        return balance;
    }

    public int getLoveCnt(Campaign campaign){
        return loveRepo.countByCampaign(campaign);
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
