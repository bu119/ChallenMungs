package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;

import java.util.List;

public interface PlaceService {
    List<Place> getPlace(List<String> cities, String type);
}
