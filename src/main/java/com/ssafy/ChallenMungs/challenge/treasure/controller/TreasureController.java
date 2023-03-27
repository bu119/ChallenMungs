package com.ssafy.ChallenMungs.challenge.treasure.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/challenge")
@CrossOrigin("*")
@Api(value = "treasure", description = "보물찾기와 관련된 컨트롤러에요")
public class TreasureController {
//    @PostMapping("/tokenConfirm/makePanelChallenge")
//    ResponseEntity makePanelChallenge(
//            @RequestParam("title") String title,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//            @RequestParam("maxParticipantCount") int maxParticipantCount,
//            @RequestParam("gameType") int gameType,
//            @RequestParam("entryFee") int entryFee,
//
//            @RequestParam("centerLat") Double centerLat,
//            @RequestParam("centerLng") Double centerLng,
//            @RequestParam("mapSize") Double mapSize,
//            @RequestParam("cellSize") Double cellSize
//    ) {
//        HashMap<String, Double> newPosition = null;
//        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 0.0);
//        Double maxLat = newPosition.get("latDistance");
//        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 90.0);
//        Double maxLng = newPosition.get("lngDistance");
//        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 180.0);
//        Double minLat = newPosition.get("latDistance");
//        newPosition = distance.getPosition(centerLat, centerLng, mapSize / 2, 270.0);
//        Double minLng = newPosition.get("lngDistance");
//
//        int cellD = (int) (mapSize / cellSize);
//        panelService.save(Challenge.builder()
//                .challengeType(2)
//                .title(title)
//                .startDate(startDate)
//                .endDate(endDate)
//                .maxParticipantCount(maxParticipantCount)
//                .gameType(gameType)
//                .entryFee(entryFee)
//                .centerLat(centerLat)
//                .centerLng(centerLng)
//                .maxLat(maxLat).minLat(minLat).maxLng(maxLng).minLng(minLng)
//                .cellSize(cellSize).map_size(mapSize)
//                .cellD(cellD)
//                .currentParticipantCount(0)
//                .status(0)
//                .build());
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
