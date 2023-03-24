package com.ssafy.ChallenMungs.challenge.general.service;


import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRepository;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {

    private final GeneralRepository generalRepository;

    public GeneralService(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
    }

    public Long saveChallenge(Challenge challenge) {
        return generalRepository.save(challenge).getChallengeId();
    }

//    public List<Challenge> findAllChallenges() {
//        return generalRepository.findAll();
//    }

}

