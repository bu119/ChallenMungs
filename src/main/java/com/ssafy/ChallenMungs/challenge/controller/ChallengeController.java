package com.ssafy.ChallenMungs.challenge.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SpecialChallenge")
@CrossOrigin("*")
@Api(value = "challenge", description = "챌린지를 관리하는 컨트롤러에요!")
public class ChallengeController {
    
}
