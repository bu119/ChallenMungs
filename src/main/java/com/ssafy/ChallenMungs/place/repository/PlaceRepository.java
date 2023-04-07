package com.ssafy.ChallenMungs.place.repository;

import com.ssafy.ChallenMungs.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 지역 & 유형 선택
    Page<Place> findByCityInAndType(Pageable pageable, List cities, String type);

    Page<Place> findByCityIn(Pageable pageable, List cities);

    Page<Place> findByType(Pageable pageable, String type);

    Page<Place> findByNameContainingAndCityInAndType(Pageable pageable, String name, List cities, String type);

    Page<Place> findByNameContainingAndCityIn(Pageable pageable, String name, List cities);

    Page<Place> findByNameContainingAndType(Pageable pageable, String name, String type);

    Page<Place> findAll(Pageable pageable);

    Page<Place> findByNameContaining(Pageable pageable, String name);



//    List<Place> findAll(List cities, String type);
}
