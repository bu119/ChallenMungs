package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class GeneralParticipantService {

    private final GeneralParticipantRepository generalParticipantRepository;

    public GeneralParticipantService(GeneralParticipantRepository generalParticipantRepository) {
        this.generalParticipantRepository = generalParticipantRepository;
    }

    // 참가자 추가
    public void saveParticipant(MyChallenge generalParticipant) {
        generalParticipantRepository.save(generalParticipant);
    }

}
