package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralParticipant;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class GeneralParticipantService {

    private final GeneralParticipantRepository generalParticipantRepository;

    public GeneralParticipantService(GeneralParticipantRepository generalParticipantRepository) {
        this.generalParticipantRepository = generalParticipantRepository;
    }

    // 참가자 추가
    public void saveParticipant(GeneralParticipant generalParticipant) {
        generalParticipantRepository.save(generalParticipant);
    }

    // 참가자 삭제
    public void deleteParticipant(Long challengeId, String loginId) {
        generalParticipantRepository.deleteByChallengeIdAndLoginId(challengeId, loginId);
    }
    // 챌린지에 참가한 사용자인지 확인하는 함수
    public GeneralParticipant isUserParticipated(String loginId, Long challengeId) {
        return generalParticipantRepository.findByLoginIdAndChallengeId(loginId, challengeId);
    }



}
