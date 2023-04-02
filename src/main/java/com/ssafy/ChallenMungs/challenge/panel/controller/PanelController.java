package com.ssafy.ChallenMungs.challenge.panel.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.panel.handler.PanelSocketHandler;
import com.ssafy.ChallenMungs.challenge.panel.service.PanelService;
import com.ssafy.ChallenMungs.common.util.Distance;
import com.ssafy.ChallenMungs.common.util.FileManager;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    @Autowired
    PanelSocketHandler panelSocketHandler;

    @Autowired
    UserService userService;

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
        System.out.println("gogogo");

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/tokenConfirm/getInfo")
    ResponseEntity makePanelChallenge(Long challengeId) {
        System.out.println("파넬이 관리하고 있어요!:" + panelSocketHandler.challengeManager + " " + challengeId);
        if (panelSocketHandler.challengeManager.get(challengeId) == null) {
            log.info("소켓이 관리하고 있지 않아서 정보를 가져오는데 실패 했어요ㅜㅜ");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        log.info("게임 중 정보를 불러와요");
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        HashMap<String, Object> mapDto = new HashMap<String, Object>();
        mapDto.put("title", challenge.getTitle());
        mapDto.put("startDate", challenge.getStartDate().toString());
        mapDto.put("mapInfo", panelSocketHandler.challengeManager.get(challenge.getChallengeId()).getMapInfo());
        mapDto.put("endDate", challenge.getEndDate().toString());
        mapDto.put("entryFee", challenge.getEntryFee());
        mapDto.put("gameType", challenge.getGameType());
        ArrayList<HashMap> newRankInfoList = new ArrayList<>();
        for (com.ssafy.ChallenMungs.challenge.panel.handler.RankVo rv : panelSocketHandler.challengeManager.get(challenge.getChallengeId()).rankInfo) {
            User u = userService.findUserByLoginId((String) rv.getLoginId()); // 팀전일 경우 LoginId가 ArrayList라 고쳐야햄
            HashMap<String, Object> newRankInfoMap = new HashMap<>();
            newRankInfoMap.put("loginId", u.getLoginId());
            newRankInfoMap.put("name", u.getName());
            newRankInfoMap.put("profile", u.getProfile());
            newRankInfoMap.put("rank", rv.getTeamRank());
            newRankInfoMap.put("teamId", rv.getTeamId());
            newRankInfoMap.put("point", rv.getPanelCount());
            newRankInfoList.add(newRankInfoMap);
        }
        mapDto.put("rankInfo", newRankInfoList);
        return new ResponseEntity(mapDto, HttpStatus.OK);
    }


}
