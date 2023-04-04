package com.ssafy.ChallenMungs.place.controller;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "지도 목록 조회", description = "정보탭의 장소 조회가 가능한 컨트롤러예요!")

public class PlaceController {
    private final PlaceService service;


    // page 파라미터로 받기
    @GetMapping("/lsit")
    @ApiOperation(value = "정보 조회", notes = "선택 지역과 선택 유형을 기준으로 목록을 50개씩 paging하여 조회하는 api 입니다.\n Body 변수(required = false) : cities(List),type(String)\nnumber : 현재 페이지 번호\ntotalPages : 총 페이지 수 (totalPages-1까지 호출 가능)")
    public Page<Place> getPlace(@PageableDefault(size = 300) Pageable pageable, @RequestParam(required = false) String name, @RequestParam(value = "cities", required = false) List<String> cities, @RequestParam(required = false) String type){
        Page<Place> places = service.getPlace(pageable, name, cities, type);
        return places;
    }
    
}
