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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WalletController {
    private final WalletService service;
    Response res=new Response();
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    //후원처 계좌 생성
    @PostMapping("/special")
    @ApiOperation(value = "후원처 유저의 계좌를 db에 넣어요" ,notes="캠페인슬롯주소1, 캠페인슬롯주소2, 유저아이디(꼭 db에 있는걸로!)가 필요합니다.")
    ResponseEntity<Object> specialUser(@RequestParam String campaign1, @RequestParam String campaign2,@RequestParam String loginId) {
        try{
            service.insertSpecialWallet(campaign1,campaign2,loginId);
            return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<Object>(res.makeSimpleRes("실패 "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //일반 유저 계좌 생성
    @PostMapping("/normal")
    @ApiOperation(value = "일반 유저의 계좌를 db에 넣어요." ,notes="저금통 주소, 지갑주소, 유저아이디(꼭 db에 있는걸로!)가 필요합니다. 순서 지켜서 넣어주세요.")
    ResponseEntity<Object> nomalUser(@RequestParam String piggybank, @RequestParam String wallet,@RequestParam String loginId) {
        try{
            service.insertNomalWallet(piggybank,wallet,loginId);
            return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Object>(res.makeSimpleRes("실패 "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/balance")
    @ApiOperation(value = "계좌를 입력하면 잔액을 알려줍니다. 없는 계좌면 0, 잘못된 계좌면 에러를 반환합니다." ,notes="백엔드에서 캠페인 송금 가능여부 편하게 개발하려고 만들었어요. 혹시 필요하면 사용하세요. \n" +
            "테스트용 지원이 계좌: 0xa82866b793c35b4742c5f637be56bbdba6662e41")
    ResponseEntity<Object> getBalance(@RequestParam String address) {
        String balance= service.getBalance(address);
        if(balance.equals("error")) return new ResponseEntity<Object>(res.makeSimpleRes("계좌 번호 형식 확인!!"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(res.makeSimpleRes(service.getBalance(address)),HttpStatus.OK);
    }
    private Logger logger = LoggerFactory.getLogger(CampaignContentController.class);
    @GetMapping("/myWalletHistory")
    @ApiOperation(value = "계좌를 입력하면 내 지갑의 사용내역을 조회합니다." ,notes="테스트용 계좌: 0x8fF38b2DCD450747f7dF9B709Fb764d48E4AE77A \n일반챌린지 계좌 : 0x50Aa5B30442cd67659bF1CA81E7cD4e351898cfd \n특별챌린지 계좌 : 0x6aC40a06633BcF319F0ebd124F189D29d9A390bF")
    ResponseEntity<Object> viewMyWallet(@RequestParam String address) throws JsonProcessingException {
        return new ResponseEntity<Object>(service.viewMyWallet(address),HttpStatus.OK);
    }

    @GetMapping("/myPiggyBankHistory")
    @ApiOperation(value = "계좌를 입력하면 내 저금통의 사용내역을 조회합니다." ,notes="테스트용 계좌: 0x11DF180C2B89fEd295780D5D89cc5f9d596A4027 \n일반챌린지 계좌 : 0x50Aa5B30442cd67659bF1CA81E7cD4e351898cfd \n특별챌린지 계좌 : 0x6aC40a06633BcF319F0ebd124F189D29d9A390bF")
    ResponseEntity<Object> viewMyPiggyBank(@RequestParam String address) throws JsonProcessingException {
        return new ResponseEntity<Object>(service.viewMyPiggyBank(address),HttpStatus.OK);
    }

}
