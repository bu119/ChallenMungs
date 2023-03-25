package com.ssafy.ChallenMungs.challenge.panel.controller;


import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.panel.service.PanelService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/panel")
@CrossOrigin("*")
@Api(value = "panel", description = "판넬뒤집기와 관련된 컨트롤러에요!")
public class PanelController {
    private Logger log = LoggerFactory.getLogger(PanelController.class);
    @Autowired
    PanelService panelService;

    @PostMapping("/tokenConfirm/makePanelChallenge")
    ResponseEntity makePanelChallenge(
            @RequestParam("title") String title,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("maxParticipantCount") int maxParticipantCount,
            @RequestParam("gameType") int gameType,
            @RequestParam("entryFee") int entryFee,
            @RequestParam("successCondition") int successCondition,
            @RequestParam("leftTopLat") Double leftTopLat,
            @RequestParam("leftTopLng") Double leftTopLng,
            @RequestParam("rightBottomLat") Double rightBottomLat,
            @RequestParam("rightBottomLng") Double rightBottomLng
    ) {
        panelService.save(Challenge.builder()
                .challengeType(2)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .maxParticipantCount(maxParticipantCount)
                .gameType(gameType)
                .entryFee(entryFee)
                .successCondition(successCondition)
                .leftTopLat(leftTopLat)
                .leftTopLng(leftTopLng)
                .rightBottomLat(rightBottomLat)
                .rightBottomLng(rightBottomLng)
                .centerLat((leftTopLat + rightBottomLat) / 2)
                .centerLng((leftTopLng + rightBottomLng) / 2)
                .currentParticipantCount(0)
                .status(0)
                .build());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
