package com.ssafy.ChallenMungs.challenge.common.service;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.repository.MyChallengeRepository;
import com.ssafy.ChallenMungs.user.entity.User;
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

    // 동일 챌린지에 참여한 모든 유저 목록 가져오기
    public List<MyChallenge> findAllByChallengeId(Long challengeId) {
        return myChallengeRepository.findAllByChallengeId(challengeId);
    }

    public MyChallenge findByLoginIdAndChallengeId(String loginId, Long challengeId) {
        return myChallengeRepository.findByLoginIdAndChallengeId(loginId, challengeId);
    }

    // 동일 챌린지에 참여한 모든 유저 목록 가져오기
    public List<MyChallenge> findByChallengeIdAndSuccessResult(Long challengeId) {
        return myChallengeRepository.findByChallengeIdAndSuccessResult(challengeId, true);
    }

    public User findByLoginId(String loginId) {
        return findByLoginId(loginId);
    }

    public void delete(MyChallenge mc) {
        myChallengeRepository.delete(mc);
    }
}
