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
}
