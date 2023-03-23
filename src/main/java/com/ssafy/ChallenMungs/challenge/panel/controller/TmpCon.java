package com.ssafy.ChallenMungs.challenge.panel.controller;

import com.ssafy.ChallenMungs.challenge.panel.entity.Panel;
import com.ssafy.ChallenMungs.challenge.panel.repository.PanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TmpCon {

    @Autowired
    PanelRepository panelRepository;

    @GetMapping("/tt")
    void tt() {
        panelRepository.save(Panel.builder().build());
    }
}
