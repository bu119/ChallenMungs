package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralParticipant;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralParticipantRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class GeneralParticipantService {

    private final GeneralParticipantRepository generalParticipantRepository;

    public GeneralParticipantService(GeneralParticipantRepository generalParticipantRepository) {
        this.generalParticipantRepository = generalParticipantRepository;
    }

    public void saveParticipant(GeneralParticipant generalParticipant) {
        generalParticipantRepository.save(generalParticipant);
    }

//    public void updateParticipant(String loginId, Long challengeId) {
//        GeneralParticipant generalParticipant = generalParticipantRepository.findUserByLoginId(loginId);
//        generalParticipant.setChallengeId(challengeId);
//        generalParticipantRepository.save(generalParticipant);
//    }
}
