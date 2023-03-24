package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralParticipant;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralParticipantService;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/general")
@CrossOrigin("*")
@Api(value = "generalChallenge", description = "일반챌린지를 관리하는 컨트롤러에요!")
public class GeneralController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GeneralService generalService;

    @Autowired
    GeneralParticipantService generalParticipantService;


    @PostMapping("/tokenConfirm/create")
    @ApiOperation(value = "일반챌린지 생성")
    public ResponseEntity<Long> createGeneral(
            HttpServletRequest request,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("maxParticipantCount") int maxParticipantCount,
            @RequestParam("currentParticipantCount") int currentParticipantCount,
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
                        .currentParticipantCount(currentParticipantCount)
                        .entryFee(entryFee)
                        .campaignId(campaignId)
                        .successCondition(successCondition)
                        .challengeType(1)
                        .build()
        );

        String loginId = request.getAttribute("loginId").toString();
        generalParticipantService.saveParticipant(
                GeneralParticipant.builder()
                        .loginId(loginId)
                        .challengeId(challengeId)
                        .successCount(0)
                        .build()
        );

        return ResponseEntity.ok(challengeId);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<List<Challenge>> findByChallengeId(@RequestParam("challengeId") Long challengeId) {
        List<Challenge> challenges = generalService.findByChallengeId(challengeId);
        if (!challenges.isEmpty()) {
            return ResponseEntity.ok(challenges);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}