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
    @ApiOperation(value = "정보 조회", notes = "선택 지역과 선택 유형을 기준으로 목록을 조회하는 api 입니다.")
    public Page<Place> getPlace(@PageableDefault(size = 50) Pageable pageable, @RequestParam(required = false) List<String> cities, @RequestParam(required = false) String type){
        Page<Place> places = service.getPlace(pageable, cities, type);
        return places;
    }

//    @GetMapping("/paging")
//    @ApiOperation(value = "Paging", notes = "Paging 연습용")
//    public Page<Place> pagingList(@PageableDefault(size = 20) Pageable pageable){
//        Page<Place> places = service.pagingList(pageable);
//        return places;
//    }
    
}
