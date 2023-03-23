package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.campaign.dto.CampaignInsertDto;
import com.ssafy.ChallenMungs.challenge.general.entity.General;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general")
@CrossOrigin("*")
@Api(value = "challenge", description = "챌린지를 관리하는 컨트롤러에요!")
public class GeneralController {
    @Autowired
    GeneralRepository generalRepository;

//    @GetMapping("/dddd")
//    void dddd() {
//        generalRepository.save(General.builder().build());
//    }

    @GetMapping("/{challengeId}")
    public General getGeneral(@PathVariable Long challengeId) {
        return generalRepository.findById(challengeId).orElse(null);
    }

    @PostMapping
    public General createGeneral(@RequestBody General general) {
        return generalRepository.save(general);
    }

    @PutMapping("/{challengeId}")
    public General updateGeneral(@PathVariable Long challengeId, @RequestBody General general) {
        general.setChallengeId(challengeId);
        return generalRepository.save(general);
    }

    @DeleteMapping("/{challengeId}")
    public void deleteGeneral(@PathVariable Long challengeId) {
        generalRepository.deleteByChallengeId(challengeId);
    }

}
