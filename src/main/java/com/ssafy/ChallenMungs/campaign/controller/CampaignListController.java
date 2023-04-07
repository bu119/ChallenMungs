package com.ssafy.ChallenMungs.campaign.controller;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignShelterDto;
import com.ssafy.ChallenMungs.campaign.service.CampaignListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign/list")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "캠페인 목록 조회", description = "기부탭, 후원처 캠페인 목록, 마이페이지 캠페인 목록의 조회가 가능한 컨트롤러예요!")

public class CampaignListController {

    private final CampaignListService service;

    // 캠페인 탭 목록조회(최신순, 누적금액순, 응원순)
    @GetMapping("/ongoing")
    @ApiOperation(value = "기부탭", notes = "기부탭에서 캠페인 목록을 조회하는 api 입니다.\n type : date(등록일), amount(누적 금액), love(캠페인 응원수) \n sort(default = 1) : 1(내림차순), 0(오름차순)     * 파라 미터 없이 보내면 기본 내림차순 정렬 \n 기부탭 처음 선택 시 : 파라미터를 넣지 않고 호출하면 캠페인 최신순으로 기본 정렬 됩니다." )
    public ResponseEntity<List<CampaignDto>> getCampaign(@RequestParam(required = false) String title, @RequestParam(required = false, defaultValue="default") String type, @RequestParam(required = false, defaultValue = "1") int sort){
        return new ResponseEntity<List<CampaignDto>>(service.getCampaign(title, type, sort), HttpStatus.OK);
    }

    //보호소 화면 로그인시 캠페인 리스트
    @GetMapping("/shelter")
    @ApiOperation(value = "보호소 캠페인", notes = "보호소로 로그인시 자신의 캠페인 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<CampaignShelterDto>> getShelter(@RequestParam String loginId){
        return new ResponseEntity<List<CampaignShelterDto>>(service.getShelter(loginId), HttpStatus.OK);
    }

    // 내가 응원한 캠페인 리스트
    @GetMapping("/myLove")
    @ApiOperation(value = "내가 응원한 캠페인", notes = "사용자가 응원한 캠페인 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<CampaignDto>> getUserLove(@RequestParam String loginId){
        return new ResponseEntity<List<CampaignDto>>(service.getUserLove(loginId), HttpStatus.OK);
    }

    // 내가 기부한 캠페인 리스트
    @GetMapping("/myDonate")
    @ApiOperation(value = "내가 기부한 캠페인", notes = "사용자가 기부한 캠페인 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<CampaignDto>> getUserDonate(@RequestParam String loginId){
        return new ResponseEntity<List<CampaignDto>>(service.getUserDonate(loginId), HttpStatus.OK);
    }

    
}
