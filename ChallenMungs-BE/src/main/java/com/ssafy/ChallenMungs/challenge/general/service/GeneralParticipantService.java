package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.repository.MyChallengeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralParticipantService {

    private final MyChallengeRepository participantRepository;

    public GeneralParticipantService(MyChallengeRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    // 참가자 추가
    public void saveParticipant(MyChallenge generalParticipant) {
        participantRepository.save(generalParticipant);
    }

}
