package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class GeneralScheduler {
    @Autowired
    GeneralBoardService generalBoardService;

    @Scheduled(cron = "1 0 0 * * *")
    void checkRejectCount() {
        List<GeneralBoard> boards = generalBoardService.findAllByRegisterDay(LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1));
    }
}
