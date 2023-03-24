package com.ssafy.ChallenMungs.challenge.common.service;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.repository.MyChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyChallengeService {
    @Autowired
    MyChallengeRepository myChallengeRepository;

    public List<MyChallenge> findAllByLoginId(String loginId) {
        return myChallengeRepository.findAllByLoginId();
    }
}
