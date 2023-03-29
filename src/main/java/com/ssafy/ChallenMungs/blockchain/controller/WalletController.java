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
    @ApiOperation(value = "계좌를 입력하면 사용내역을 조회합니다." ,notes="테스트용 계좌: 0xafDe4382c0BD8585E1e9e664fC5b7a8AaBF7D829")
    ResponseEntity<Object> getWalletHistory(@RequestParam String address) throws JsonMappingException, JsonProcessingException {
        log.info("-----------------------------------------");
        JsonNode items = service.getHistory(address);
        String normalChallenge = "0x50Aa5B30442cd67659bF1CA81E7cD4e351898cfd";
        String specialChallenge = "0x6aC40a06633BcF319F0ebd124F189D29d9A390bF";

        // 사용내역의 모든 주소들은 lowercase로 온다.
        String lowerN = normalChallenge.toLowerCase();
        String lowerS = specialChallenge.toLowerCase();

         //사용 내역 바꾸기
        for (JsonNode item : items){
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            if (from.equals(address)){
                if(to.equals(lowerN)){
                    title = "일반 챌린지 참여";
                } else if (to.equals(lowerS)) {
                    title = "특별 챌린지 참여";
                } else{
                    title = "error";
                }
            }
            // 충전
            else{
                title = "충전";
            }

            //전송 시간
            long timstamp = item.get("timestamp").asLong();
            Date date = new Date(timstamp * 1000L);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // 전송 Klaytn(hex)
            String hexvalue = item.get("value").asText();
            // 0x slicing
            hexvalue = hexvalue.substring(2);
            // Decimal로 변환
            BigInteger decimal = new BigInteger(hexvalue, 16);
            BigInteger divisor = new BigInteger("1000000000000000000");
            // 최종값으로 변환
            BigDecimal result = new BigDecimal(decimal).divide(new BigDecimal(divisor));


//            log.info(from);
//            log.info(to);
            log.info(title);
//            log.info(String.valueOf(calendar.get(Calendar.YEAR)));
//            log.info(String.valueOf(calendar.get(Calendar.MONTH)));
//            log.info(String.valueOf(calendar.get(Calendar.DATE)));
//            log.info(String.valueOf(result));

        }




        return new ResponseEntity<Object>(items,HttpStatus.OK);
        }
}
