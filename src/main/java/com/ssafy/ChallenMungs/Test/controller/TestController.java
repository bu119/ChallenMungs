package com.ssafy.ChallenMungs.Test.controller;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.Test.entity.Test;
import com.ssafy.ChallenMungs.Test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러에요!")
public class TestController {

    private final TestService service;

    //db와 상호작용 필요없는 일반 api
    @GetMapping("/hello")
    @ApiOperation(value = "헬로우 월드", notes = "역사적인 첫 커밋이에요!") //notes는 안적어도 상관없어요
    public String Hello(){
        return "hello world";
    }


    //jpa가 기본적으로 제공하는 함수를 이용해 db와 상호작용
    @GetMapping("/testDefault")
    @ApiOperation(value = "jpa 기본 동작 확인", notes = "jpa 기본 쿼리 메서드 확인용 - 테스트 테이블의 칼럼 수를 반환한다.")
    public long getCount(){
        return service.getCount();
    }


    //jpa가 기본적으로 제공하지 않지만, 비교적 간단한 쿼리문인 경우
    @GetMapping("/testCustom")
    @ApiOperation(value = "jpa 기본 동작 확인", notes = "jpa 커스텀 쿼리 메서드 확인용 - 이름에 '안녕'을 포함하는 칼럼 리스트를 반환")
    public List<TestDto> getTest(){
        List<Test> list = service.getCustom("안녕");
        return list.stream()
                .map(b -> new TestDto(b.getName()))
                .collect(Collectors.toList());
    }

    //jpa가 기본적으로 제공하지 않고, 비교적 복잡한 쿼리문인 경우
    @GetMapping("/testQueryDsl")
    public List <TestDto> getQueryDsl(){
        List<Test> list = service.getQueryDsl();
        return list.stream()
                .map(b -> new TestDto(b.getName()))
                .collect(Collectors.toList());
    }
}