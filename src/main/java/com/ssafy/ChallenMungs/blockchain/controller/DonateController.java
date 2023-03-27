package com.ssafy.ChallenMungs.blockchain.controller;

import com.ssafy.ChallenMungs.blockchain.service.DonateService;
import com.ssafy.ChallenMungs.common.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/donate")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "donate", description = "블록체인 거래 내역 중 캠페인 기부와 관련된 컨트롤러입니다.")
public class DonateController {

    private final DonateService service;
    Response res=new Response();

    //유저계좌->캠페인계좌
    //만약 모금액이 목표금액보다 커지면 모금 종료 || 혹은 종료일이 되면 모금 종료
    @PostMapping("/sponsor")
    @ApiOperation(value = "특정 캠페인에 모금된 금액을 갱신합니다." ,notes="특정 캠페인에 모금된 금액을 갱신합니다. 기부시 호출되어야 합니다.\n" +
            "캠페인 아이디,기부한 금액,응원문구,기부자의 로그인아이디가 필요합니다.")
    ResponseEntity<Object> donate(@RequestParam int campaignId,@RequestParam int money,@RequestParam String memo, @RequestParam String loginId) {
        service.donate(campaignId,money,memo,loginId);
        return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
    }

    //출금하기
    //캠페인 모금종료된 상태이고, 목표금액만큼 전부 출금했으면 계좌 반납
    //계좌 반납되어 계좌 속성이 none으로 표시되는 캠페인은 '캠페인 종료' 판정
    @PostMapping("/withdraw")
    @ApiOperation(value = "후원처가 모금액을 출금합니다." ,notes="특정 캠페인에서 출금합니다. 목표금액만큼 모두 출금했다면 계좌가 반납됩니다. \n 모인금액을 초과해 출금하지 않도록 주의")
    ResponseEntity<Object> withdraw(@RequestParam int campaignId,@RequestParam int money) {
        service.plusWithdraw(campaignId,money);
        return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
    }

    //이월하기
    @PostMapping("/transfer")
    @ApiOperation(value = "후원처가 모금액을 다른 캠페인으로 이월합니다." ,notes="모인 금액을 초과해 이월할 수 없도록 주의")
    ResponseEntity<Object> withdraw(@RequestParam int fromCampaignId,@RequestParam int toCampaignId,@RequestParam int money) {
        service.transfer(fromCampaignId,toCampaignId,money);

        return new ResponseEntity<Object>(res.makeSimpleRes("성공"),HttpStatus.OK);
    }
    //년도와 아이디를 입력으로 받아 기부내역 리스트를 받아옴

    @PostMapping("/donateList")
    @ApiOperation(value = "나의 년도별 기부 내역을 조회합니다." ,notes="페이징은 논의해보고 정할예정..place 방식 페이징 괜찮으면 그걸로 페이징처리 할게요.")
    ResponseEntity<Object> list(@RequestParam String loginId,@RequestParam int year) {
        return new ResponseEntity<Object>(service.viewMyDonations(loginId,year),HttpStatus.OK);
    }

    //년도와 아이디를 입력으로 받아 디테일한 기부 정보를 가져옴

    //기부내역아이디를 주면 기부증서 내용을 보여줌


    //영수증 이미지 가지고 사용처, 사용일, 사용금액 반환



}
