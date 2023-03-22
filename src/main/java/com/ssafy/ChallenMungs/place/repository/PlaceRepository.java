package com.ssafy.ChallenMungs.place.repository;

import com.ssafy.ChallenMungs.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 지역 & 유형 선택
    List<Place> findByCityInAndTypeIn(List cities, List types);
}
