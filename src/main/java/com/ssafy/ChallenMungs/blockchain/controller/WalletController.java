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


    //일반 유저 계좌 생성

    //todo 각종 송금 관련..

    @PostMapping("/test")
    @ApiOperation(value = "캠페인을 자세히 봅니다." ,notes="캠페인 아이디를 넘겨주면 더 자세한 정보를 알려줍니다.")
    public void viewDetailCampaign(@RequestParam String loginId) {
        service.deleteTest(loginId);
    }


}
