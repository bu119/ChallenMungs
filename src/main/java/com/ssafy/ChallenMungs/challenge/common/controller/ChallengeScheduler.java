package com.ssafy.ChallenMungs.challenge.common.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ChallengeScheduler {
    private Logger log = LoggerFactory.getLogger(ChallengeScheduler.class);
    @Autowired
    ChallengeService challengeService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void startChallenge() {
        log.info("스케쥴러가 동작해요!");

        List<Challenge> challenges = challengeService.findAll();
        for (Challenge c : challenges) {
            LocalDate today = LocalDate.now();
            if (c.getStartDate().equals(today)) {

            }
        }
    }
}
