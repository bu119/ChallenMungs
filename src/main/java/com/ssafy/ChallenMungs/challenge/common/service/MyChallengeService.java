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
        return myChallengeRepository.findAllByLoginId(loginId);
    }

    public void save(MyChallenge myChallenge) {
        myChallengeRepository.save(myChallenge);
    }

    public void findByLoginIdAndChallengeIdToDelete(String loginId, long challengeId) {
        myChallengeRepository.deleteByLoginIdAndChallengeId(loginId, challengeId);
    }

    public List<MyChallenge> findAllByChallengeId(Long challengeId) {
        return myChallengeRepository.findAllByChallengeId(challengeId);
    }

    public MyChallenge findByLoginIdAndChallengeId(String loginId, Long challengeId) {
        return myChallengeRepository.findByLoginIdAndChallengeId(loginId, challengeId);
    }
}
