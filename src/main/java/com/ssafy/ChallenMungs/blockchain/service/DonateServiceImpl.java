package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.blockchain.dto.DonationDetailDto;
import com.ssafy.ChallenMungs.blockchain.dto.DonationItemDto;
import com.ssafy.ChallenMungs.blockchain.dto.DonationListDto;
import com.ssafy.ChallenMungs.blockchain.dto.DonationSummaryDto;
import com.ssafy.ChallenMungs.blockchain.entity.Donation;
import com.ssafy.ChallenMungs.blockchain.repository.DonationRepository;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.campaign.controller.CampaignContentController;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.entity.Comment;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.CommentRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DonateServiceImpl implements  DonateService{

    private final UserRepository userRepo;
    private final CampaignListRepository campaignRepo;
    private final CommentRepository commentRepo;
    private final DonationRepository donationRepo;
    private final WalletService walletService;

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
        //기부 내역 추가
        addDonation(campaign,money,loginId);
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

    @Override
    public void addDonation(Campaign campaign,int money,String loginId){
        User user=userRepo.findUserByLoginId(loginId);
        Donation donation=new Donation();
        donation.setUser(user);
        donation.setShelter(campaign.getName());
        donation.setMoney(money);
        donation.setTotalMoney(user.getTotalDonate());
        donation.setDonateDate(LocalDateTime.now());
        donation.setYear(LocalDateTime.now().getYear());
        donationRepo.save(donation);
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
        //만약 isEnd가 true면서 collectedAmount를 모두 빼냈으면 계좌 회수
        Campaign campaign=campaignRepo.findCampaignByCampaignId(campaignId);
        if(isEndCampaign(campaign,money)){
            campaign.setWalletAddress("none");
            campaign.setEndUnix(System.currentTimeMillis() / 1000L);
        }
        //출금 금액 업데이트
        int newWithdraw=campaign.getWithdrawAmount()+money;
        campaign.setWithdrawAmount(newWithdraw);
        campaignRepo.save(campaign);

    }

    @Override
    public boolean isEndCampaign(Campaign campaign, int money) {

        if(campaign.isEnd()&&(campaign.getWithdrawAmount()+money)>=campaign.getCollectAmount()){
            return true;
        }
        return false;
    }


    @Override
    public boolean checkCampaignTransferAble(int campaignId) {
        Campaign campaign=campaignRepo.findCampaignByCampaignId(campaignId);
        //잔액이 없는 경우
        if((campaign.getCollectAmount()-campaign.getWithdrawAmount())<=0)  return false;
        //이월할 캠페인이 없는 경우
        if(donationRepo.getRunningCampaignCnt(campaign.getUser().getName())!=2) return false;

        return true;
    }
     

    //----내 기부내역 조회-----
    @Override
    public Map<String,List<DonationItemDto>> viewMyDonations(String loginId, int year) {

        Map<String,List<DonationItemDto>> result=new HashMap<>();
        List <Donation> list=donationRepo.findAllByUserAndYearOrderByDonateDateDesc(userRepo.findUserByLoginId(loginId),year);
        for(Donation donate:list){
            DonationItemDto item=new DonationItemDto(donate.getShelter(),donate.getMoney(),donate.getTotalMoney(),donate.getDonateDate().getHour()+":"+donate.getDonateDate().getMinute());
            String day=donate.getDonateDate().getMonthValue()+"."+donate.getDonateDate().getDayOfMonth();
            List <DonationItemDto> dayList=result.getOrDefault(day,new ArrayList<DonationItemDto>());
            dayList.add(item);
            result.put(day,dayList);
        }
        return result;
    }
    @Override
    public DonationDetailDto getDonation(int donationId){
        Donation d=donationRepo.findByDonationId(donationId);
        String day=d.getDonateDate().getYear()+"."+d.getDonateDate().getMonthValue()+"."+d.getDonateDate().getDayOfMonth();
        DonationDetailDto result=new DonationDetailDto(d.getUser().getName(),d.getMoney(),d.getShelter(),day);
        return result;
    }
    @Override
    public DonationSummaryDto getSummary(String loginId, int year){
        User user=userRepo.findUserByLoginId(loginId);
        int cnt=donationRepo.countByUserAndYear(user,year);
        int sumYear= donationRepo.sumYearAmount(loginId,year);
        int sumTotal= donationRepo.sumTotalAmount(loginId);
        DonationSummaryDto result=new DonationSummaryDto(cnt,sumYear,sumTotal);

        return result;
    }





}
