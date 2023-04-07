package com.ssafy.ChallenMungs.challenge.common.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Long save(Challenge build) {
        return challengeRepository.save(build).getChallengeId();
    }

    // 챌린지 id를 활용하여 챌린지를 조회하는 함수
    public Challenge findByChallengeId(Long challengeId) {
        return challengeRepository.findByChallengeId(challengeId);
    }

    public void delete(Challenge challenge) {
        challengeRepository.delete(challenge);
    }


    public List<Challenge> findAllByStatusAndChallengeType(int status, int challengeType) { //status(0:시작안함 1:진행중 2:끝), challengeType(1:일반, 2:판넬, 3:보물)
        return challengeRepository.findAllByStatusAndChallengeType(status, challengeType);
    }

    public List<Long> findAllByStatus(int i) {
        return challengeRepository.findAllByStatus(i).stream().map(e -> {
            return e.getChallengeId();
        }).collect(Collectors.toList());
    }
}
