package com.ssafy.ChallenMungs.challenge.panel.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.panel.service.PanelService;
import com.ssafy.ChallenMungs.common.util.Distance;
import com.ssafy.ChallenMungs.common.util.FileManager;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/panel")
@CrossOrigin("*")
@Api(value = "panel", description = "판넬뒤집기와 관련된 컨트롤러에요!")
public class PanelController {
    private Logger log = LoggerFactory.getLogger(PanelController.class);
    @Autowired
    PanelService panelService;

    @Autowired
    Distance distance;

    @Autowired
    FileManager fileManager;


    @PostMapping("/tokenConfirm/makePanelChallenge")
    ResponseEntity makePanelChallenge(
            @RequestParam("title") String title,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("maxParticipantCount") int maxParticipantCount,
            @RequestParam("gameType") int gameType,
            @RequestParam("entryFee") int entryFee,

            @RequestParam("centerLat") Double centerLat,
            @RequestParam("centerLng") Double centerLng,
            @RequestParam("mapSize") Double mapSize,
            @RequestParam("cellSize") Double cellSize
            //센터좌표
            // 맵 전체 크기
            // 셀크기
    ) {
        // 일단 가로 세로 길이를 받아온다
        // 네모가 생기면 아래와 왼쪽 변을 기준으로 (대충)100m로 몇번 나눌 수 있는지 센다
        // 만약 아래변이 2.56km라고 해보자 그럼 26개의 구역으로 나눌수 있다 그러니 딱 100m라고 하지말자 균일한 크기를 위해서도
        // 그럼 이차원 배열의 각 왼쪽위를 두 좌표로 맵핑 할 수 있다. 예를 들어 지도의 가장 서쪽 경도가 a, 가장 동쪽 경도가 b라고 해보자
        // 그럼 idx = 0 이 이차원 배열의 첫번째 번호라고 할 때 n(n <= 25)번째 요소의 경도는 a + (b - a) / 26 * n이다
        // 마찬가지로 왼쪽변이 1.94km라고 해보자 그럼 20개의 구역으로 나눌 수 있고 가장 북쪽 위도가 a, 가장 남쪽 위도가 b라 할때
        // n번째 요소의 위도는 a + (b - a) / 20 * n 이다
        // 그럼 위도 c, 경도 d가 주어 졌을때 이차원배열의 [n][m]에 대응한다고 할 때 n, m은 얼마일까?
        // 다시 예를 들어보자 경기장의 가장 왼쪽위는 (x1, y1)이다 오른쪽아래는 (x2, y2)다 한칸을 대충 k(m)로 잡자 현재위치 (a,b)가 주어쪗을때
        // 이에 대응하는 이차원배열의 index [n][m]는 얼마일까?
        // 먼저 26과 20에 대응하는 수를 구해보자
        // 위도칸수 xd = (x2 - x1) // (k / 1000)
        // 경도칸수 yd = (y2 - y1) // (k / 1000)
        // a <= x1 + ((x2 - x1) / xd) * n
        // b <= y1 + ((y2 - y1) / yd) * m
        // (a - x1) / ((x2 - x1) / xd) = n
        // (b - y1) / ((y2 - y1) / yd) = m
//      log.info("세로 길이:" + getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) + " 한칸길이:" + (cell_size / 1000) + "세로 몇칸:" + (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) / (cell_size / 1000)));
        HashMap<String, Double> newPosition = null;
        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 0.0);
        Double maxLat = newPosition.get("latDistance");
        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 90.0);
        Double maxLng = newPosition.get("lngDistance");
        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 180.0);
        Double minLat = newPosition.get("latDistance");
        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 270.0);
        Double minLng = newPosition.get("lngDistance");

        int cellD = (int) (mapSize / cellSize);
        panelService.save(Challenge.builder()
                .challengeType(2)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .maxParticipantCount(maxParticipantCount)
                .gameType(gameType)
                .entryFee(entryFee)
                .centerLat(centerLat)
                .centerLng(centerLng)
                .maxLat(maxLat).minLat(minLat).maxLng(maxLng).minLng(minLng)
                .cellSize(cellSize).map_size(mapSize)
                .cellD(cellD)
                .currentParticipantCount(0)
                .status(0)
                .build());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/loadResult")
    void loadResult() {
        fileManager.loadResult("7");
    }

}
