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

    public List<Challenge> findByChallengeType(int i) {
        return challengeRepository.findByChallengeType(i);
    }
}
