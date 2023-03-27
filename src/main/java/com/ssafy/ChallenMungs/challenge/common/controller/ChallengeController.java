package com.ssafy.ChallenMungs.challenge.common.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.common.util.Distance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    Distance distance;

    @PostMapping("/tokenConfirm/getList")
    @ApiOperation(value = "챌린지 리스트를 불러오는 메서드에요")
    ResponseEntity getList(HttpServletRequest request, @RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("type") int type, @RequestParam("searchValue") String searchValue, @RequestParam("myChallenge") boolean myChallenge, @RequestParam("onlyTomorrow") boolean onlyTomorrow) { // type 1: 전체, 2: 일반, 3: 판넬 4: 보물
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
                                    distance.getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) > distanceLimit)
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
                            distance.getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) <= distanceLimit
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
                            distance.getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) <= distanceLimit
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
            HashMap<Long, Boolean> map = new HashMap<>();
            for (MyChallenge mc : myChallenges) {
                map.put(mc.getChallengeId(), true);
            }
            List<Challenge> removeList = new ArrayList<>();
            for (int i = 0; i < challenges.size(); i++) {
                if (map.get(challenges.get(i).getChallengeId()) != null) continue;
                removeList.add(challenges.get(i));
            }
            for (Challenge r : removeList) {
                challenges.remove(r);
            }
        }

        if (onlyTomorrow) {
            log.info("내일 시작하는 챌린지만을 골라요!");
            List<Challenge> removeList = new ArrayList<>();
            for (int i = 0; i < challenges.size(); i++) {
                if (LocalDate.now().plusDays(1).equals(challenges.get(i).getStartDate())) continue;
                removeList.add(challenges.get(i));
            }
            for (Challenge r : removeList) {
                challenges.remove(r);
            }
        }

        List<Challenge> removeList = new ArrayList<>();
        log.info("시작안한 챌린지만을 걸러요!");
        for (int i = 0; i < challenges.size(); i++) {
            if (challenges.get(i).getStatus() != 0) continue;
            removeList.add(challenges.get(i));
        }
        for (Challenge r : removeList) {
            challenges.remove(r);
        }

        return new ResponseEntity(challenges, HttpStatus.OK);
    }


    @PostMapping("/tokenConfirm/getInChallenge")
    ResponseEntity getInChallenge(HttpServletRequest request, @RequestParam("challengeId") long challengeId, @RequestParam("teamId") Integer teamId) {
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        if (challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount()) {
            log.info("이미 풀방이라 들어갈 수 없어요!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if (teamId == null) {
            challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() + 1);
            challengeService.save(challenge);
            String loginId = request.getAttribute("loginId").toString();
            myChallengeService.save(MyChallenge.builder().loginId(loginId).challengeId(challengeId).successCount(0).build());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            if (challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount() / 2) {
                log.info("팀 정원 초과");
                return ResponseEntity.status(HttpStatus.LOCKED).build(); //423
            } else {
                log.info("아직 수용 가능");
                challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() + 1);
                challengeService.save(challenge);
                String loginId = request.getAttribute("loginId").toString();
                myChallengeService.save(MyChallenge.builder().loginId(loginId).challengeId(challengeId).successCount(0).teamId(teamId).build());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
    }

    @PostMapping("/tokenConfirm/getOutChallenge")
    ResponseEntity getOutchallenge(HttpServletRequest request, @RequestParam("challengeId") long challengeId) {
        String loginId = request.getAttribute("loginId").toString();
        myChallengeService.findByLoginIdAndChallengeIdToDelete(loginId, challengeId);
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() - 1);
        if (challenge.getCurrentParticipantCount() == 0) {
            log.info("제가 나가서 이방엔 더이상 사람이 없어요!");
            challengeService.delete(challenge);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 챌린지id로 챌린지를 조회하는 API
    @GetMapping("/tokenConfirm/detail")
    @ApiOperation(value = "챌린지 정보를 조회하는 api입니다.", notes = "challengeId를 활용하여 조회합니다.")
    public ResponseEntity<Challenge> findByChallengeId(HttpServletRequest request, @RequestParam("challengeId") Long challengeId) {
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        if (challenge != null) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}