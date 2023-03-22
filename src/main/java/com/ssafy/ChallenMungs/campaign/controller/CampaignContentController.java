package com.ssafy.ChallenMungs.campaign.controller;

import com.ssafy.ChallenMungs.Test.service.TestService;
import com.ssafy.ChallenMungs.campaign.dto.CampaignDetailDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignDto;
import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import com.ssafy.ChallenMungs.campaign.service.CampaignContentService;
import com.ssafy.ChallenMungs.user.dto.Res1;
import com.ssafy.ChallenMungs.user.dto.Res2;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/campaign/content")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "login", description = "캠페인 작성, 응원, 기부와 관련된 컨트롤러입니다. ")
public class CampaignContentController {
     private final CampaignContentService  service;

     //todo 캠페인 생성하는 api
     @PostMapping("/create")
     @ApiOperation(value = "캠페인을 생성합니다." ,notes=" 캠페인 제목, 후원처아이디, 썸네일 이미지 링크, 내용 리스트가 필요합니다. \n " +
             "내용 리스트의 아이템 하나는 type(img,bold,nomal)과 body(이미지 링크 혹은 내용)이 필요합니다.\n " +
             "img는 이미지, bold는 굵은 글씨,normal은 일반글씨입니다. img src 대신 멀티파트로 보내는 걸 원하면 지원에게 말하세요.")
     ResponseEntity<String> createCampaign(@RequestBody CampaignInsertDto info) {
          try{
               service.createCampaign(info);
          }catch(Exception e){
               return new ResponseEntity<String>("실패",HttpStatus.OK);
          }
          return new ResponseEntity<String>("성공",HttpStatus.OK);
     }

     //todo 캠페인 만드는게 가능한지 반환하는 api
     @PostMapping("/isCampaignAble")
     @ApiOperation(value = "캠페인을 만들 수 있는지 체크합니다" ,notes="후원처의 아이디이고, 현재 진행중인 캠페인이 2개 미만인 경우에만 true를 반환합니다.")
     ResponseEntity<Boolean> isCampaignAble(@RequestParam String loginId) {
          return new ResponseEntity<Boolean>(service.isCampaignAble(loginId),HttpStatus.OK);
     }

    //todo 캠페인에 좋아요 누르는 api
    @PostMapping("/cheerup")
    @ApiOperation(value = "캠페인을 응원합니다." ,notes="캠페인을 응원합니다. 이미 응원을 했는데 또 호출하면 아무일도 일어나지 않고, '중복' 문자열을 반환합니다.")
    ResponseEntity<String> cheerUpCampaign(@RequestParam String loginId,@RequestParam int campaignId) {

         return new ResponseEntity<String>("성공",HttpStatus.OK);
    }

    //캠페인 자세히 보는 api. 캠페인dto 수정 필요
    @PostMapping("/detail")
    @ApiOperation(value = "캠페인을 자세히 봅니다." ,notes="")
    ResponseEntity<Object> viewDetailCampaign(@RequestParam int campaignId) {
         try{
             return new ResponseEntity<Object>(service.viewDetailCampaign(campaignId),HttpStatus.OK);
         }catch(Exception e){
             return new ResponseEntity<Object>("실패",HttpStatus.OK);
         }
    }


    //todo 기부하는 api
     // 저금통,블록체인 로직과 합작해야 하므로 일단 나중에...

}
