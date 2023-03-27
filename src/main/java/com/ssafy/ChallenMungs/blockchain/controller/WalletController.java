package com.ssafy.ChallenMungs.blockchain.controller;

import com.ssafy.ChallenMungs.blockchain.service.WalletService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WalletController {
    private final WalletService service;
    //후원처 계좌 생성
    @PostMapping("/special")
    @ApiOperation(value = "후원처 유저의 계좌를 db에 넣어요" ,notes="캠페인슬롯주소1, 캠페인슬롯주소2, 유저아이디(꼭 db에 있는걸로!)가 필요합니다.")
    ResponseEntity<Object> specialUser(@RequestParam String campaign1, @RequestParam String campaign2,@RequestParam String loginId) {
        try{
            service.insertSpecialWallet(campaign1,campaign2,loginId);
            return new ResponseEntity<Object>("성공",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Object>("실패 "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //일반 유저 계좌 생성
    @PostMapping("/normal")
    @ApiOperation(value = "일반 유저의 계좌를 db에 넣어요." ,notes="저금통 주소, 지갑주소, 유저아이디(꼭 db에 있는걸로!)가 필요합니다. 순서 지켜서 넣어주세요.")
    ResponseEntity<Object> nomalUser(@RequestParam String piggybank, @RequestParam String wallet,@RequestParam String loginId) {
        try{
            service.insertNomalWallet(piggybank,wallet,loginId);
            return new ResponseEntity<Object>("성공",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Object>("실패 "+ e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //todo 각종 송금 관련..



}
