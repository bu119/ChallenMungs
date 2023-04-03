package com.ssafy.ChallenMungs.challenge.panel.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.panel.repository.PanelRepositoryForChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanelService {
    @Autowired
    PanelRepositoryForChallenge panelRepositoryForChallenge;

    public Long save(Challenge build) {
        return panelRepositoryForChallenge.save(build).getChallengeId();
    }
}
