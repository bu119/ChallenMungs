package com.ssafy.ChallenMungs.blockchain.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/donate")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "donate", description = "블록체인 거래 내역 중 캠페인 기부와 관련된 컨트롤러입니다.")
public class DonateController {
    //유저계좌->캠페인계좌
    //만약 모금액이 목표금액과 같아지면 모금 종료 || 혹은 종료일이 되면 모금 종료
    //입력된 기부금액이 목표금액을 넘어서지 않도록 프론트에 요청하기



    //출금하기
    //캠페인 모금종료된 상태이고, 목표금액만큼 전부 출금했으면 계좌 반납
    //계좌 반납되어 계좌 속성이 none으로 표시되는 캠페인은 '캠페인 종료' 판정


}
