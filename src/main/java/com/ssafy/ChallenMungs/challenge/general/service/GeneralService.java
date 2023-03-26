package com.ssafy.ChallenMungs.challenge.general.service;


import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralService {

    private final GeneralRepository generalRepository;

    public GeneralService(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
    }

    // 챌린지를 생성하는 함수
    public Long saveChallenge(Challenge challenge) {
        return generalRepository.save(challenge).getChallengeId();
    }

    // 챌린지 id를 활용하여 챌린지를 조회하는 함수
    public Challenge findByChallengeId(Long challengeId) {
        return generalRepository.findByChallengeId(challengeId);
    }

    // 챌린지 인원을 수정하는 함수
//    public void updateChallengePeople(Long challengeId, int currentParticipantCount) {
//        Challenge challenge = generalRepository.findByChallengeId(challengeId);
//        if (challenge == null) {
//            throw new IllegalArgumentException("존재하지 않는 챌린지 ID입니다.");
//        }
//        challenge.setCurrentParticipantCount(currentParticipantCount);
//        generalRepository.save(challenge);
//    }



}

