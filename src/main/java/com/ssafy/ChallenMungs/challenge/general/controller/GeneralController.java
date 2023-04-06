package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.blockchain.service.WalletService;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralParticipantService;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/general")
@CrossOrigin("*")
@Api(value = "generalChallenge", description = "일반챌린지를 관리하는 컨트롤러에요!")
public class GeneralController {

    private Logger log = LoggerFactory.getLogger(GeneralController.class);

    @Autowired
    GeneralService generalService;

    @Autowired
    GeneralParticipantService generalParticipantService;

    @Autowired
    UserRepository userRepo;
    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepository walletRepo;
    @Value("${GENERAL_ADDRESS}")
    String generalChallenge;
    @Value("${PANEL_ADDRESS}")
    String panelChallenge;

    // 일반챌린지를 생성하는 API - 생성시 참가자 테이블에 생성자를 생성자로 추가
    @PostMapping("/tokenConfirm/create")
    @ApiOperation(value = "일반챌린지를 생성하는 api입니다.", notes = "결과 값으로 challengeId를 반환합니다.")
    public ResponseEntity<Long> createGeneral(
            HttpServletRequest request,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("maxParticipantCount") int maxParticipantCount,
//            @RequestParam("currentParticipantCount") int currentParticipantCount,
            @RequestParam("entryFee") int entryFee,
            @RequestParam("campaignId") int campaignId,
            @RequestParam("successCondition") int successCondition
    ) {
        Long challengeId = generalService.saveChallenge(
                Challenge.builder()
                        .title(title)
                        .description(description)
                        .startDate(startDate)
                        .endDate(endDate)
                        .maxParticipantCount(maxParticipantCount)
                        .currentParticipantCount(1)
                        .entryFee(entryFee)
                        .campaignId(campaignId)
                        .successCondition(successCondition)
                        .challengeType(1)
                        .status(0)
                        .period(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                        .build()
        );

        String loginId = request.getAttribute("loginId").toString();
        String fromAddress = walletRepo.findByUserAndType(userRepo.findUserByLoginId(loginId), 'w').getAddress();
        walletService.sendKlay(fromAddress, generalChallenge, entryFee);
        generalParticipantService.saveParticipant(
                MyChallenge.builder()
                        .loginId(loginId)
                        .challengeId(challengeId)
                        .successCount(0)
                        .build()
        );


        return ResponseEntity.ok(challengeId);
    }



}