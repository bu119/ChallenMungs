package com.ssafy.ChallenMungs.challenge.common.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeService {
    @Autowired
    ChallengeRepository challengeRepository;


    public List<Challenge> findAll() {
        return challengeRepository.findAll();
    }

    public List<Challenge> findAllByChallengeType(int i) {
        return challengeRepository.findAllByChallengeType(i);
    }

    public List<Challenge> findAllByTitleLike(String s) {
        return challengeRepository.findAllByTitleLike(s);
    }

    public List<Challenge> findAllByTitleLikeAndChallengeType(String s, int i) {
        return challengeRepository.findAllByTitleLikeAndChallengeType(s, i);
    }

    public void save(Challenge build) {
        challengeRepository.save(build);
    }

    public Challenge findByChallengeId(Long challengeId) {
        return challengeRepository.findByChallengeId(challengeId);
    }

    public void delete(Challenge challenge) {
        challengeRepository.delete(challenge);
    }


    public List<Challenge> findAllByStatusAndChallengeType(int status, int challengeType) { //status(0:시작안함 1:진행중 2:끝), challengeType(1:일반, 2:판넬, 3:보물)
        return challengeRepository.findAllByStatusAndChallengeType(status, challengeType);
    }
}
