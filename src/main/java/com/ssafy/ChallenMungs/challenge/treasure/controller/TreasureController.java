package com.ssafy.ChallenMungs.challenge.treasure.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.panel.controller.PanelController;
import com.ssafy.ChallenMungs.challenge.treasure.handler.RankVo;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureSocketHandler;
import com.ssafy.ChallenMungs.common.util.Distance;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/treasure")
@CrossOrigin("*")
@Api(value = "treasure", description = "보물찾기와 관련된 컨트롤러에요")
public class TreasureController {
    @Autowired
    Distance distance;

    @Autowired
    ChallengeService challengeService;

    @Autowired
    TreasureSocketHandler treasureSocketHandler;

    @Autowired
    UserService userService;

    @Autowired
    MyChallengeService myChallengeService;

    private Logger log = LoggerFactory.getLogger(TreasureController.class);

    @PostMapping("/tokenConfirm/makeTreasureChallenge")
    ResponseEntity makePanelChallenge(
            HttpServletRequest request,
            @RequestParam("title") String title,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("maxParticipantCount") int maxParticipantCount,
            @RequestParam("entryFee") int entryFee,

            @RequestParam("centerLat") Double centerLat,
            @RequestParam("centerLng") Double centerLng,
            @RequestParam("mapSize") Double mapSize,
            @RequestParam("cellSize") Double cellSize
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
        Long cId = challengeService.save(Challenge.builder()
                .challengeType(3)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .maxParticipantCount(maxParticipantCount)
                .entryFee(entryFee)
                .centerLat(centerLat)
                .centerLng(centerLng)
                .maxLat(maxLat).minLat(minLat).maxLng(maxLng).minLng(minLng)
                .map_size(mapSize).cellSize(cellSize)
                .currentParticipantCount(1)
                .status(0)
                .build());

        String loginId = request.getAttribute("loginId").toString();
        myChallengeService.save(MyChallenge.builder().challengeId(cId).loginId(loginId).successCount(0).build());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/tokenConfirm/getInfo")
    ResponseEntity makePanelChallenge(@RequestParam("challengeId") Long challengeId) {
        if (treasureSocketHandler.challengeManager.get(challengeId) == null) {
            log.info("소켓이 관리하고 있지 않아서 정보를 가져오는데 실패 했어요ㅜㅜ");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        log.info("게임 중 정보를 불러와요");
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        HashMap<String, Object> mapDto = new HashMap<String, Object>();
        mapDto.put("startDate", challenge.getStartDate().toString());
        mapDto.put("endDate", challenge.getEndDate().toString());
        mapDto.put("entryFee", challenge.getEntryFee());
        ArrayList<HashMap> newRankInfoList = new ArrayList<>();
        for (RankVo rv : treasureSocketHandler.challengeManager.get(challenge.getChallengeId()).rankInfo) {
            User u = userService.findUserByLoginId(rv.getLoginId());
            HashMap<String, Object> newRankInfoMap = new HashMap<>();
            newRankInfoMap.put("loginId", u.getLoginId());
            newRankInfoMap.put("name", u.getName());
            newRankInfoMap.put("profile", u.getProfile());
            newRankInfoMap.put("rank", rv.getTeamRank());
            newRankInfoMap.put("point", rv.getPoint());
            newRankInfoMap.put("myTreasureList", rv.getMyTreasureList());
            newRankInfoList.add(newRankInfoMap);
        }
        mapDto.put("rankInfo", newRankInfoList);
        return new ResponseEntity(mapDto, HttpStatus.OK);
    }

//    @PostMapping("/tokenConfirm/selectComplete")
//    ResponseEntity selectComplete(HttpServletRequest request, @RequestParam("selectList") List<Integer> selectList, @RequestParam("challengeId") Long challengeId) {
//        String loginId = request.getAttribute("loginId").toString();
//        Challenge challenge = challengeService.findByChallengeId(challengeId);
//        for (int idx : selectList) {
//            treasureSocketHandler.challengeManager.get(challengeId).treasureInfo.get(idx).setIsOpened(true);
//            if (treasureSocketHandler.challengeManager.get(challengeId).treasureInfo.get(idx).getType() == true) { // 폭탄이 아니라면
//                int point = treasureSocketHandler.challengeManager.get(challengeId).treasureInfo.get(idx).getPoint();
//                for (RankVo rv : treasureSocketHandler.challengeManager.get(challengeId).rankInfo) {
//                    if (rv.getLoginId().equals(loginId)) {
//                        rv.setPoint(rv.getPoint() + point);
//                        break;
//                    }
//                }
//            } else { // 폭탄이라면
//                for (RankVo rv : treasureSocketHandler.challengeManager.get(challengeId).rankInfo) {
//                    if (rv.getLoginId().equals(loginId)) {
//                        rv.setPoint(0);
//                        break;
//                    }
//                }
//            }
//        }
//        treasureSocketHandler.challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
//            return o2.getPoint() - o1.getPoint();
//        });
//        int rank = 0;
//        int count = Integer.MAX_VALUE;
//        for (RankVo r : treasureSocketHandler.challengeManager.get(challengeId).rankInfo) {
//            if (count > r.getPoint()) {
//                count = r.getPoint();
//                rank++;
//            }
//            r.setTeamRank(rank);
//        }
//        return null;
//    }
}
