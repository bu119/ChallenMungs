package com.ssafy.ChallenMungs.challenge.panel.repository;

import com.ssafy.ChallenMungs.challenge.general.entity.General;
import com.ssafy.ChallenMungs.challenge.panel.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PanelRepository extends JpaRepository<Panel, Long>, QuerydslPredicateExecutor<Panel> {
}
