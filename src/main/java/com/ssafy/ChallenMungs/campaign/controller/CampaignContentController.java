package com.ssafy.ChallenMungs.campaign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/campaign/content")
@CrossOrigin("*")
@Api(value = "login", description = "캠페인 작성, 응원, 기부와 관련된 컨트롤러입니다. ")
public class CampaignContentController {


     //todo 캠페인 생성하는 api
     @PostMapping("/upload")
     @ApiOperation(value = "캠페인 생성", notes = "캠페인을 생성하는 api 입니다.")
     public ResponseEntity<String> uploadAndGetLink() {
         return new ResponseEntity<String>("하하하", HttpStatus.OK);
     }
    //todo 캠페인에 좋아요 누르는 api
    //todo 캠페인 좋아요 취소하는 api
    //todo 댓글(응원)쓰는 api
    //todo 기부하는 api

}
