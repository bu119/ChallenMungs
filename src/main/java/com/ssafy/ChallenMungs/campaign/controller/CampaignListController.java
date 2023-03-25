package com.ssafy.ChallenMungs.campaign.controller;

import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignShelterDto;
import com.ssafy.ChallenMungs.campaign.service.CampaignListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    //각종 옵션에 따라 캠페인 리스트를 반환하는 api
    @GetMapping("/ongoing")
    @ApiOperation(value = "기부탭", notes = "기부탭에서 캠페인 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<CampaignDto>> getCampaign(@RequestParam String type, @RequestParam int sort){
        //return service.getCampaign(type, sort);
        return new ResponseEntity<List<CampaignDto>>(service.getCampaign(type, sort), HttpStatus.OK);

    }

    //보호소 화면에서 자신의 캠페인 리스트를 보여주는 api
    @GetMapping("/shelter")
    @ApiOperation(value = "보호소 캠페인", notes = "보호소로 로그인시 자신의 캠페인 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<CampaignShelterDto>> getShelter(@RequestParam String loginId){
        return new ResponseEntity<List<CampaignShelterDto>>(service.getShelter(loginId), HttpStatus.OK);

    }


    //2개가 필수 구현이고 나머지(내가 좋아요한 캠페인 등)는 적당히 추가해주세요
    
}
