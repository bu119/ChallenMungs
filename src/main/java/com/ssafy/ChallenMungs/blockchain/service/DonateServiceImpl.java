package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Comment;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.CommentRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class DonateServiceImpl implements  DonateService{


    private final UserRepository userRepo;
    private final CampaignListRepository campaignRepo;
    private final CommentRepository commentRepo;

    //------기부 관련-------
    @Override
    public void donate(int campaignId,int money,String memo,String loginId) {
        //기부금액 업데이트 및 목표금액 도달 체크
        Campaign campaign=campaignRepo.findCampaignByCampaignId(campaignId);
        addCollectAmount(campaignId,money);
        //캠페인 응원 댓글 추가
        addComment(campaign,memo,loginId);
        //기부자의 기부금액 업데이트
        updateUserDonate(loginId,money);
    }
    @Override
    public void addCollectAmount(int campaignId,int money){
        Campaign campaign=campaignRepo.findCampaignByCampaignId(campaignId);
        //만약 이 기부로 목표금액이 다 채워진다면
        if(isEndFund(campaign,money)){
            campaign.setEnd(true);
            campaign.setEndDate(getNowTime());
        }
        //캠페인 모금된 금액 업데이트
        int newCollected=campaign.getCollectAmount()+money;
        campaign.setCollectAmount(newCollected);
        campaignRepo.save(campaign);
    }
    @Override
    public void addComment(Campaign campaign,String memo,String loginId){
        Comment comment=new Comment();
        comment.setCampaign(campaign);
        comment.setMsg(memo);
        comment.setUser(userRepo.findUserByLoginId(loginId));
        commentRepo.save(comment);
    }

    @Override
    public void updateUserDonate(String loginId,int money) {
        User user=userRepo.findUserByLoginId(loginId);
        int newTotal=money+user.getTotalDonate();
        user.setTotalDonate(newTotal);
        userRepo.save(user);
    }
    @Override
    public boolean isEndFund(Campaign campaign, int money) {
        if((campaign.getCollectAmount()+money)>=campaign.getTargetAmount())
            return true;
        return false;
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
    //------출금 관련-------
    @Override
    public void transfer(int fromCampaignId,int toCampaignId,int money){
        addCollectAmount(toCampaignId,money);
        plusWithdraw(fromCampaignId,money);
    }
    @Override
    public void plusWithdraw(int campaignId, int money) {
        //만약 isEnd가 true고 출금금액이 목표금액 이상이면 계좌 회수
        Campaign campaign=campaignRepo.findCampaignByCampaignId(campaignId);
        if(isEndCampaign(campaign,money)){
            campaign.setWalletAddress("none");
        }
        //출금 금액 업데이트
        int newWithdraw=campaign.getWithdrawAmount()+money;
        campaign.setWithdrawAmount(newWithdraw);
        campaignRepo.save(campaign);

    }

    @Override
    public boolean isEndCampaign(Campaign campaign, int money) {
        if(campaign.isEnd()&&(campaign.getWithdrawAmount()+money)>=campaign.getTargetAmount()){
            return true;
        }
        return false;
    }


}
