package com.ssafy.ChallenMungs.Test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor //왜한거임?
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러에요!")
public class testController {
    @GetMapping("/hello")
    @ApiOperation(value = "헬로우 월드", notes = "역사적인 첫 커밋이에요!") //notes는 안적어도 상관없어요
    public String Hello(){
        return "hello world";
    }
}