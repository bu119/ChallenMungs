package com.ssafy.ChallenMungs.blockchain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.ChallenMungs.blockchain.service.WalletService;
import com.ssafy.ChallenMungs.campaign.controller.CampaignContentController;
import com.ssafy.ChallenMungs.common.util.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WalletController {
    private final WalletService service;
    Response res=new Response();
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    //후원처 계좌 생성
    @PostMapping("tokenConfirm/special")
    @ApiOperation(value = "후원처 유저의 계좌를 db에 넣어요" ,notes="캠페인슬롯주소1, 캠페인슬롯주소2")
    ResponseEntity<Object> specialUser(HttpServletRequest request, @RequestParam String campaign1, @RequestParam String campaign2) {
        try{
            String loginId = request.getAttribute("loginId").toString();
            service.insertSpecialWallet(campaign1.toLowerCase(),campaign2.toLowerCase(),loginId);
            return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<Object>(res.makeSimpleRes("실패 "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //일반 유저 계좌 생성
    @PostMapping("tokenConfirm/normal")
    @ApiOperation(value = "일반 유저의 계좌를 db에 넣어요." ,notes="저금통 주소, 지갑주소")
    ResponseEntity<Object> nomalUser(HttpServletRequest request, @RequestParam String piggybank, @RequestParam String wallet) {
        try{
            String loginId = request.getAttribute("loginId").toString();
            service.insertNomalWallet(piggybank.toLowerCase(),wallet.toLowerCase(),loginId);
            return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Object>(res.makeSimpleRes("실패 "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tokenConfirm/balance")
    @ApiOperation(value = "계좌를 입력하면 잔액을 알려줍니다. 없는 계좌면 0, 잘못된 계좌면 에러를 반환합니다." ,notes="type: w(고객 지갑 잔액 조회), p(고객 저금통 잔액 조회)" )
    ResponseEntity<Object> getBalance(HttpServletRequest request, @RequestParam char type) {
        String loginId = request.getAttribute("loginId").toString();
        String balance= service.getBalance(loginId, type);
        if(balance.equals("error")) return new ResponseEntity<Object>(res.makeSimpleRes("계좌 번호 형식 확인!!"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(res.makeSimpleRes(service.getBalance(loginId, type)),HttpStatus.OK);
    }
    private Logger logger = LoggerFactory.getLogger(CampaignContentController.class);
    @GetMapping("tokenConfirm/myWalletHistory")
    @ApiOperation(value = "계좌를 입력하면 내 지갑의 사용내역을 조회합니다." )
    ResponseEntity<Object> viewMyWallet(HttpServletRequest request) throws JsonProcessingException {
        String loginId = request.getAttribute("loginId").toString();
        return new ResponseEntity<Object>(service.viewMyWallet(loginId),HttpStatus.OK);
    }

    @GetMapping("tokenConfirm/myPiggyBankHistory")
    @ApiOperation(value = "계좌를 입력하면 내 저금통의 사용내역을 조회합니다." )
    ResponseEntity<Object> viewMyPiggyBank(HttpServletRequest request) throws JsonProcessingException {
        String loginId = request.getAttribute("loginId").toString();
        return new ResponseEntity<Object>(service.viewMyPiggyBank(loginId),HttpStatus.OK);
    }

    @GetMapping("tokenConfirm/totalDonate")
    @ApiOperation(value = "계좌를 누적 기부총액을 반환합니다.")
    ResponseEntity<Object> getTotalDonate(HttpServletRequest request) throws JsonProcessingException {
        String loginId = request.getAttribute("loginId").toString();
        return new ResponseEntity<Object>(res.makeSimpleRes(service.getTotalDonate(loginId)),HttpStatus.OK);
    }

    @GetMapping("tokenConfirm/myWalletAddress")
    @ApiOperation(value = "내 지갑 주소를 반환합니다.")
    ResponseEntity<Object> getWalletAddress(HttpServletRequest request) throws JsonProcessingException {
        String loginId = request.getAttribute("loginId").toString();
        return new ResponseEntity<Object>(res.makeSimpleRes(service.getWalletAddress(loginId)),HttpStatus.OK);
    }

    @GetMapping("viewCampaignWallet")
    @ApiOperation(value = "캠페인 모금액 출,입금 기록을 반환합니다.",notes="fromOnly : true(출금내역만 조회)\n toOnly : true(모금내역만 조회)\nreceipt : null(후원), url일 때(출금) ")
    ResponseEntity<Object> viewCampaignWallet(int campaignId, boolean fromOnly, boolean toOnly) throws JsonProcessingException {
        if(fromOnly && toOnly) return new ResponseEntity<Object>(res.makeSimpleRes("둘 중 하나만 ture가 가능합니다."),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(service.viewCampaignWallet(campaignId, fromOnly, toOnly),HttpStatus.OK);
    }

    @GetMapping("sendKlay")
    @ApiOperation(value = "임의로 클레이튼을 전송합니다.")
    ResponseEntity<Object> sendKlay(@RequestParam String from, @RequestParam String to, @RequestParam int amount) {
        service.sendKlay(from, to, amount);
        return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
    }
}
