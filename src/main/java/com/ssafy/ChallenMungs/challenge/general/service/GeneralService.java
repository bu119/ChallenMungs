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

    // 챌린지에 참가 - 챌린지 참가인원을 1 추가하는 함수
    public void entryChallenge(Long challengeId) {
        Challenge challenge = generalRepository.findByChallengeId(challengeId);
        // 챌린지가 있으면
        if (challenge != null) {
            // 원래 참가자 수에 1을 더함
            int currentParticipants = challenge.getCurrentParticipantCount();
            challenge.setCurrentParticipantCount(currentParticipants + 1);
            generalRepository.save(challenge);
        }
    }
//    public void entryChallenge(Long challengeId) throws Exception {
//        Challenge challenge = generalRepository.findByChallengeId(challengeId).orElse(null);
//        if (challenge == null) {
//            throw new Exception("존재하지 않는 챌린지입니다.");
//        }
//        if (challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount()) {
//            throw new Exception("챌린지 참가자 인원이 다 찼습니다.");
//        }
//        challenge.setCurrentParticipantCount(challenge.getCurrentParticipantCount() + 1);
//        generalRepository.save(challenge);
//    }

    // 챌린지의 최대 참가자 수를 초과하는지 확인하는 함수
    private boolean isChallengeFull(Challenge challenge) {
        // 다찼으면 true 덜 찼으면 false
        return challenge.getCurrentParticipantCount() >= challenge.getMaxParticipantCount();
    }

    // 챌린지에서 나가기 - 챌린지 참가인원을 1 감소하는 함수, 나밖에 없으면 챌린지 제거, 참가자 테이블 제거
    public void leaveChallenge(Long challengeId) {
        Challenge challenge = generalRepository.findByChallengeId(challengeId);
        // 챌린지가 있으면
        if (challenge != null) {
            // 현재 참가자 수
            int currentParticipants = challenge.getCurrentParticipantCount();

            // 현재 참가자가 자신뿐이면 챌린지 삭제
            if (currentParticipants == 1) {
                generalRepository.delete(challenge);
            } else {
                // 현재 참가자 수에서 1을 뺌
                challenge.setCurrentParticipantCount(currentParticipants - 1);
                generalRepository.save(challenge);
            }
        }
    }



}

