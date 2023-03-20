package com.ssafy.ChallenMungs.Test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor //왜한거임?
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러에요!")
public class testController {
    @GetMapping("/hello")
    @ApiOperation(value = "hello world", notes = "역사적인 첫 커밋이에요!")
    public String Hello(){
        return "hello world";
    }
}
