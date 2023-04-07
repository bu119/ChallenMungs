package com.ssafy.ChallenMungs.challenge.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.blockchain.service.WalletService;
import com.ssafy.ChallenMungs.challenge.common.dto.ChallengeParticipantDto;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.common.util.Distance;
import com.ssafy.ChallenMungs.common.util.FileManager;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/challenge")
@CrossOrigin("*")
@Api(value = "challenge", description = "챌린지를 관리하는 컨트롤러에요!")
public class
ChallengeController {
//    전체 일반 판넬, 보물별로 검색결과 제공
    private Logger log = LoggerFactory.getLogger(ChallengeController.class);

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    @Autowired
    UserService userService;

    @Autowired
    Distance distance;

    @Autowired
    FileManager fileManager;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ChallengeRepository challengeRepo;

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepo;






    ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/tokenConfirm/getList")
    @ApiOperation(value = "챌린지 리스트를 불러오는 메서드에요")
    ResponseEntity getList(HttpServletRequest request, @RequestParam(name = "lat", required = false) Double lat, @RequestParam(name = "lng", required = false) Double lng, @RequestParam("type") int type, @RequestParam(name = "searchValue", required = false) String searchValue, @RequestParam("myChallenge") boolean myChallenge, @RequestParam("onlyTomorrow") boolean onlyTomorrow) { // type 1: 전체, 2: 일반, 3: 판넬 4: 보물
        log.info("챌린지 리스트를 구할게요!");
        log.info("거리제한은 3km에요!");
        double distanceLimit = 3.0;
        List<Challenge> challenges = null;
        List<Challenge> temp;
        switch (type) {
            case 1:
                log.info("타입이 1이에요 모든 챌린지를 가져올게요");
                if (searchValue != null) {
                    temp = challengeService.findAllByTitleLike("%" + searchValue + "%");
                } else {
                    temp = challengeService.findAll();
                }
//                System.out.println(temp.size());
                challenges = temp.stream().filter(c -> {
                    if (
                            (lat == null && lng == null) ||
                            !((c.getChallengeType() == 2 || c.getChallengeType() == 3) &&
                                    distance.getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) > distanceLimit)
                    ) return true;
                    return false;
                }).collect(Collectors.toList());
//                System.out.println(":::" + challenges.size());
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
                            (lat != null && lng != null) &&
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
                            (lat != null && lng != null) &&
                            distance.getDistance(lat, lng, c.getCenterLat(), c.getCenterLng()) <= distanceLimit
                    ) return true;
                    return false;
                }).collect(Collectors.toList());
                break;
            default:
                break;
        }
        for (Challenge c : challenges) {
            System.out.print(c.getChallengeId() + ":" + c.getStartDate() + "     ");
        }
        System.out.println();
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

        for (Challenge c : challenges) {
            System.out.print(c.getChallengeId() + ":" + c.getStartDate() + "    ");
        }
        System.out.println();
        if (onlyTomorrow) {
            log.info("내일 시작하는 챌린지만을 골라요!:" + LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            List<Challenge> removeList = new ArrayList<>();
            for (int i = 0; i < challenges.size(); i++) {
                if (LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1).equals(challenges.get(i).getStartDate())) continue;
                removeList.add(challenges.get(i));
            }
            for (Challenge r : removeList) {
                challenges.remove(r);
            }
        }

        for (Challenge c : challenges) {
            System.out.print(c.getChallengeId() + ":" + c.getStartDate() + "    ");
        }
        System.out.println();
        HashMap<Integer, ArrayList> dto = new HashMap<>();
        dto.put(0, new ArrayList<Challenge>());
        dto.put(1, new ArrayList<Challenge>());
        dto.put(2, new ArrayList<Object>());
        for (Challenge c : challenges) {
            switch (c.getStatus()) {
                case 0:
                    dto.get(0).add(c);
                    break;
                case 1:
                    dto.get(1).add(c);
                    break;
                case 2:
                    HashMap<String, Object> subMap = new HashMap<String, Object>();
                    subMap.put("challengeInfo", c);
                    String loginId = request.getAttribute("loginId").toString();
                    if (myChallengeService.findByLoginIdAndChallengeId(loginId, c.getChallengeId()) != null) subMap.put("successInfo", myChallengeService.findByLoginIdAndChallengeId(loginId, c.getChallengeId()).getSuccessResult());
                    else subMap.put("successInfo", null);
                    dto.get(2).add(subMap);
                    break;
                default:
                    break;
            }
        }

        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping("/getChallengeInfo")
    ResponseEntity getChallengeInfo(@RequestParam("challengeId") long challengeId) {
        log.info("들어온 값은 : " + challengeId);
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        List<MyChallenge> myChallengeList = myChallengeService.findAllByChallengeId(challengeId);
        ArrayList<HashMap> newList = new ArrayList<>();
//        System.out.println("::::::" + myChallengeList.size());
        for (MyChallenge mc : myChallengeList) {
            User u = userService.findByLoginId(mc.getLoginId());
            HashMap<String, Object> newMap = new HashMap<>();
            newMap.put("profile", u.getProfile());
            newMap.put("name", u.getName());
            newList.add(newMap);
        }
        HashMap<String, Object> dto = new HashMap<>();
        dto.put("challenge", challenge);
        dto.put("participant", newList);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Value("${GENERAL_ADDRESS}")
    String generalChallenge;
    @Value("${PANEL_ADDRESS}")
    String panelChallenge;


    @PostMapping("/tokenConfirm/getInChallenge")
    ResponseEntity getInChallenge(HttpServletRequest request, @RequestParam("challengeId") long challengeId, @RequestParam(name = "teamId", required = false) Integer teamId) {
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        if (challenge.getStatus() == 0) {
            if (challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount()) {
                log.info("이미 풀방이라 들어갈 수 없어요!");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build(); // 417
            }
            if (teamId == null) {
                challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() + 1);
                challengeService.save(challenge);
                String loginId = request.getAttribute("loginId").toString();
                myChallengeService.save(MyChallenge.builder().loginId(loginId).challengeId(challengeId).successCount(0).build());

                HashMap<String, String> dto = new HashMap<>();
                dto.put("result", "success");
                return new ResponseEntity(dto, HttpStatus.OK); // 200
            } else {
                if (challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount() / 2) {
                    log.info("팀 정원 초과");
                    return ResponseEntity.status(HttpStatus.LOCKED).build(); //423
                } else {
                    log.info("아직 수용 가능");
                    challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() + 1);
                    challengeService.save(challenge);
                    String loginId = request.getAttribute("loginId").toString();
                    User user = userRepo.findUserByLoginId(loginId);
                    Challenge challen = challengeRepo.findByChallengeId(challengeId);
                    String toAddress;
                    if (challen.getChallengeType() == 1){
                        toAddress = generalChallenge;
                    }else{
                        toAddress = panelChallenge;
                    }
                    walletService.sendKlay(walletRepo.findByUserAndType(user, 'w').getAddress(), toAddress, challen.getEntryFee());
                    myChallengeService.save(MyChallenge.builder().loginId(loginId).challengeId(challengeId).successCount(0).teamId(teamId).build());

                    HashMap<String, String> dto = new HashMap<>();
                    dto.put("result", "success");
                    return new ResponseEntity(dto, HttpStatus.OK);
                }
            }
        } else if (challenge.getStatus() == 1) {
            log.info("진행 중인 방이라 들어갈 수 없어요");
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else { //status가 2인 경우
            log.info("끝난 방이에요!");
            return ResponseEntity.status(HttpStatus.DESTINATION_LOCKED).build(); // 421
        }
    }

    @PostMapping("/tokenConfirm/getGeneralChallengeResult")
    ResponseEntity getGeneralChallengeResult(HttpServletRequest request, @RequestParam("challengeId") long challengeId) {
        log.info("끝난 방이에요!");
        // 여기에 끝난 일반챌린지에 들어갈 때 필요한 정보를 리턴할 수 있게 코드를 작성해 주세요
        log.info("일반 챌린지네요. 결과를 가져 올게요");
        // 모든 사람의 결과를 가져옴
        List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(challengeId);
        return new ResponseEntity(myChallenges, HttpStatus.OK); // 200
    }

    @PostMapping("/tokenConfirm/getSpecialChallengeResult")
    ResponseEntity getSpecialChallengeResult(HttpServletRequest request, @RequestParam("challengeId") long challengeId) {
        log.info("특별 챌린지네요. 결과를 가져 올게요");
        String result = fileManager.loadResult(Long.toString(challengeId));
        return new ResponseEntity(result, HttpStatus.OK); // 200
    }



    // 나갈 때 커런트인원 빼기 안함 방에 있었었는지 여부
    @PostMapping("/tokenConfirm/getOutChallenge")
    ResponseEntity getOutchallenge(HttpServletRequest request, @RequestParam("challengeId") long challengeId) {
        String loginId = request.getAttribute("loginId").toString();
        myChallengeService.findByLoginIdAndChallengeIdToDelete(loginId, challengeId);
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        /////////////
        String toAddress = walletRepo.findByUserAndType(userRepo.findUserByLoginId(loginId), 'w').getAddress();
        String fromAddress;
        if (challenge.getChallengeType() == 1){
            fromAddress = generalChallenge;
        }
        else {
            fromAddress = panelChallenge;
        }
        walletService.sendKlay(fromAddress, toAddress, challenge.getEntryFee());
        /////////////
        challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() - 1);
        if (challenge.getCurrentParticipantCount() == 0) {
            log.info("제가 나가서 이방엔 더이상 사람이 없어요!");
            challengeService.delete(challenge);
        } else {
            challengeService.save(challenge);
        }

        HashMap<String, String> dto = new HashMap<>();
        dto.put("result", "success");
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    // 챌린지id로 챌린지를 조회하는 API
//    @GetMapping("/tokenConfirm/detail")
//    @ApiOperation(value = "챌린지 정보를 조회하는 api입니다.", notes = "challengeId를 활용하여 조회합니다.")
//    public ResponseEntity<Challenge> findByChallengeId(HttpServletRequest request, @RequestParam("challengeId") Long challengeId) {
//        Challenge challenge = challengeService.findByChallengeId(challengeId);
//        if (challenge != null) {
//            return ResponseEntity.ok(challenge);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/tokenConfirm/participants")
    @ApiOperation(value = "동일 챌린지에 참여한 모든 유저를 가져오는 api입니다.", notes = "challengeId를 활용하여 조회합니다.")
    public ResponseEntity<List<ChallengeParticipantDto>> findAllByChallengeId(@RequestParam("challengeId") Long challengeId) {

        List<MyChallenge> participants = myChallengeService.findAllByChallengeId(challengeId);

        List<ChallengeParticipantDto> dtoList = new ArrayList<>();
        for (MyChallenge my : participants) {
            String boardUserId = my.getLoginId();
            String name = userService.findUserByLoginId(boardUserId).getName();
            String profileUrl = userService.findUserByLoginId(boardUserId).getProfile();

            ChallengeParticipantDto dto = new ChallengeParticipantDto(
                    my.getIdx(),
                    my.getChallengeId(),
                    my.getLoginId(),
                    name,
                    profileUrl,
                    my.getSuccessCount()
            );
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/tokenConfirm/isHere")
    ResponseEntity isHere(HttpServletRequest request, @RequestParam("challengeId") Long challengeId) {
        String loginId = request.getAttribute("loginId").toString();
        HashMap<String, Boolean> dto = new HashMap<>();
        if (myChallengeService.findByLoginIdAndChallengeId(loginId, challengeId) == null) dto.put("result", false);
        else dto.put("result", true);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
}
