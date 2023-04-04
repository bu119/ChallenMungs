package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceService {
    Page<Place> getPlace(Pageable pageable, String title, List<String> cities, String type);
}
