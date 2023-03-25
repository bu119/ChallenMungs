package com.ssafy.ChallenMungs.challenge.treasure.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
@CrossOrigin("*")
@Api(value = "treasure", description = "보물찾기와 관련된 컨트롤러에요")
public class TreasureController {

}
