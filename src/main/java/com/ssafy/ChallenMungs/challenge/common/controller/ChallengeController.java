package com.ssafy.ChallenMungs.challenge.common.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/challenge")
@CrossOrigin("*")
@Api(value = "challenge", description = "챌린지를 관리하는 컨트롤러에요!")
public class ChallengeController {
//    전체 일반 판넬, 보물별로 검색결과 제공
    private Logger log = LoggerFactory.getLogger(ChallengeController.class);

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    @PostMapping("/tokenConfirm/getList")
    @ApiOperation(value = "챌린지 리스트를 불러오는 메서드에요")
    ResponseEntity getList(HttpServletRequest request, @RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("type") int type, @RequestParam("searchValue") String searchValue, @RequestParam("myChallenge") boolean myChallenge, @RequestParam("onlyTommorow") boolean onlyTommrow) { // type 1: 전체, 2: 일반, 3: 판넬 4: 보물
        log.info("챌린지 리스트를 구할게요!");
        log.info("거리제한은 3km에요!");
        double distanceLimit = 3.0;
        List<Challenge> challenges = null;
        List<Challenge> temp = null;
        switch (type) {
            case 1:
                log.info("타입이 1이에요 모든 챌린지를 가져올게요");
                if (searchValue != null) {
                    temp = challengeService.findAllByTitleLike("%" + searchValue + "%");
                } else {
                    temp = challengeService.findAll();
                }
                challenges = temp.stream().filter(c -> {
                    if (
                            !((c.getChallengeType() == 2 || c.getChallengeType() == 3) &&
                                    getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) > distanceLimit)
                    ) return true;
                    return false;
                }).collect(Collectors.toList());
                break;
            case 2:
                log.info("타입이 2이에요 일반 챌린지를 가져올게요");
                if (searchValue != null) {
                    temp = challengeService.findAllByTitleLikeAndChallengeType("%" + searchValue + "%", 1);
                } else {
                    temp = challengeService.findAllByChallengeType(1);
                }
                challenges = temp;
                break;
            case 3:
                log.info("타입이 3이에요 판넬 챌린지를 가져올게요");
                if (searchValue != null) {
                    temp = challengeService.findAllByTitleLikeAndChallengeType("%" + searchValue + "%", 2);
                } else {
                    temp = challengeService.findAllByChallengeType(2);
                }
                challenges = temp.stream().filter(c -> {
                    if (
                            getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) <= distanceLimit
                    ) return true;
                    return false;
                }).collect(Collectors.toList());
                break;
            case 4:
                log.info("타입이 4이에요 보물 챌린지를 가져올게요");
                if (searchValue != null) {
                    temp = challengeService.findAllByTitleLikeAndChallengeType("%" + searchValue + "%", 3);
                } else {
                    temp = challengeService.findAllByChallengeType(3);
                }
                challenges = temp.stream().filter(c -> {
                    if (
                            getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) <= distanceLimit
                    ) return true;
                    return false;
                }).collect(Collectors.toList());
                break;
            default:
                break;
        }
        if (myChallenge) {
            log.info("내 챌린지만을 구해요");
            String loginId = request.getAttribute("loginId").toString();
            List<MyChallenge> myChallenges = myChallengeService.findAllByLoginId(loginId);
            HashMap<Integer, Boolean> map = new HashMap<>();
        } else {

        }

        return new ResponseEntity(challenges, HttpStatus.OK);
    }



    double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구 반경 (km)
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x1 = R * Math.cos(lat1Rad) * Math.cos(lon1Rad);
        double y1 = R * Math.cos(lat1Rad) * Math.sin(lon1Rad);
        double z1 = R * Math.sin(lat1Rad);

        double x2 = R * Math.cos(lat2Rad) * Math.cos(lon2Rad);
        double y2 = R * Math.cos(lat2Rad) * Math.sin(lon2Rad);
        double z2 = R * Math.sin(lat2Rad);

        Vector3D p1 = new Vector3D(x1, y1, z1);
        Vector3D p2 = new Vector3D(x2, y2, z2);

        return R * Vector3D.angle(p1, p2);
    }

}
