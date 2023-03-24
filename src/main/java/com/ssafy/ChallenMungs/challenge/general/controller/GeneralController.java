package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.intellij.lang.annotations.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/general")
@CrossOrigin("*")
@Api(value = "generalChallenge", description = "일반챌린지를 관리하는 컨트롤러에요!")
public class GeneralController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GeneralService generalService;


    @PostMapping("/create")
    @ApiOperation(value = "일반챌린지 생성")
    public ResponseEntity<Long> createGeneral(
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

        return ResponseEntity.ok(challengeId);
    }


}