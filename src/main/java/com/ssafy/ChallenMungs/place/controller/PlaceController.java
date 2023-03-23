package com.ssafy.ChallenMungs.place.controller;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/lsit")
    @ApiOperation(value = "정보 조회", notes = "선택 지역과 선택 유형을 기준으로 목록을 조회하는 api 입니다.")
    public ResponseEntity<List<Place>> getPlace(@RequestParam(required = false) List<String> cities, @RequestParam(required = false) String type){
        return new ResponseEntity<List<Place>>(service.getPlace(cities, type), HttpStatus.OK);
    }
    
}
