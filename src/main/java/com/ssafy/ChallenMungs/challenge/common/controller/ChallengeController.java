package com.ssafy.ChallenMungs.challenge.common.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.user.controller.UserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
@CrossOrigin("*")
@Api(value = "challenge", description = "챌린지를 관리하는 컨트롤러에요!")
public class ChallengeController {
//    전체 일반 판넬, 보물별로 검색결과 제공
    private Logger log = LoggerFactory.getLogger(ChallengeController.class);

    @GetMapping("/getList")
    void getList(@RequestParam("lat") double lat, @RequestParam("lng") double lng) { // type 1: 전체, 2: 일반, 3: 판넬 4: 보물
//        Double lat1 = 37.566086043393824;
//        Double lng1 = 126.98266503548992;
//        Double lat2 = 37.56924758708694;
//        Double lng2 = 126.97708382705669;
//        System.out.println(getDistance(lat1, lng1, lat2, lng2));
        List<Challenge> challenges =
    }
//    가까운 거리 순으로
//    이름검색
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
