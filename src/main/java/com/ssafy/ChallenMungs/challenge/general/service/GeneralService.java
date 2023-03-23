package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.general.entity.General;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GeneralService {

    private final GeneralRepository GeneralRepository;

    public GeneralService(GeneralRepository GeneralRepository) {
        this.GeneralRepository = GeneralRepository;
    }

    public General save(General general) {
        return GeneralRepository.save(general);
    }


}
