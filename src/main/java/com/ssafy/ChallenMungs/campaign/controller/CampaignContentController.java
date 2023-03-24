package com.ssafy.ChallenMungs.campaign.controller;


import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import com.ssafy.ChallenMungs.campaign.service.CampaignContentService;
import com.ssafy.ChallenMungs.user.controller.UserController;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserController.class);
     @PostMapping("/create")
     @ApiOperation(value = "캠페인을 생성합니다. 로그인아이디는 꼭 db에 있는걸로 넣으세요." ,notes=" 캠페인 제목, 후원처아이디,종료일(yy.mm.dd) 썸네일 이미지 링크, 내용 리스트가 필요합니다. \n " +
             "내용 리스트의 아이템 하나는 type(img,bold,nomal)과 body(이미지 링크 혹은 내용)이 필요합니다.\n " +
             "img는 이미지, bold는 굵은 글씨,normal은 일반글씨입니다. img src 대신 멀티파트로 보내는 걸 원하면 지원이에게 말하세요.")
     ResponseEntity<String> createCampaign(@RequestBody CampaignInsertDto info) {

          try{
               service.createCampaign(info);
          }catch(Exception e){
              logger.info("exception: "+e.getMessage());
              return new ResponseEntity<String>("실패",HttpStatus.OK);
          }
          return new ResponseEntity<String>("성공",HttpStatus.OK);
     }

    //캠페인 자세히 보는 api.
    @GetMapping("/detail")
    @ApiOperation(value = "캠페인을 자세히 봅니다." ,notes="캠페인 아이디를 넘겨주면 더 자세한 정보를 알려줍니다.")
    ResponseEntity<Object> viewDetailCampaign(@RequestParam int campaignId) {
        try{
            return new ResponseEntity<Object>(service.viewDetailCampaign(campaignId),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Object>("실패",HttpStatus.OK);
        }
    }



    @PostMapping("/cheerup")
    @ApiOperation(value = "캠페인을 응원합니다." ,notes="캠페인을 응원합니다. 이미 응원을 했는데 또 호출하면 아무일도 일어나지 않고, '중복' 문자열을 반환합니다.\n" +
            "중복시 에러를 반환하길 원한다면 지원이에게 말하세요.")
    ResponseEntity<String> cheerUpCampaign(@RequestParam String loginId,@RequestParam int campaignId) {

         if(service.cheerUpCampaign(loginId,campaignId)==0){
             return new ResponseEntity<String>("성공",HttpStatus.OK);
         }
         else if(service.cheerUpCampaign(loginId,campaignId)==1){
             return new ResponseEntity<String>("중복",HttpStatus.OK);
         }
        return new ResponseEntity<String>("실패",HttpStatus.OK);

    }

    //todo 캠페인 만드는게 가능한지 반환하는 api
    @PostMapping("/isCampaignAble")
    @ApiOperation(value = "캠페인을 만들 수 있는지 체크합니다" ,notes="후원처의 아이디이고, 현재 진행중인 캠페인이 2개 미만인 경우에만 true를 반환합니다.")
    ResponseEntity<Boolean> isCampaignAble(@RequestParam String loginId) {
        return new ResponseEntity<Boolean>(service.isCampaignAble(loginId),HttpStatus.OK);
    }




}
