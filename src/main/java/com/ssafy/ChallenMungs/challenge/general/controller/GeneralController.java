package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.challenge.general.entity.General;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/general")
@CrossOrigin("*")
@Api(value = "generalChallenge", description = "일반챌린지를 관리하는 컨트롤러에요!")
public class GeneralController {
    @Autowired
    GeneralRepository generalRepository;

    @GetMapping("/dddd")
    void dddd() {
        generalRepository.save(General.builder().build());
    }
}
