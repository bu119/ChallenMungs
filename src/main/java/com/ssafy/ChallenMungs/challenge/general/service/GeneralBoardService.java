package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralParticipantRepository;

public class GeneralBoardService {

    private final GeneralBoardRepository generalBoardRepository;

    public GeneralBoardService(GeneralBoardRepository generalBoardRepository) {
        this.generalBoardRepository = generalBoardRepository;
    }
}
