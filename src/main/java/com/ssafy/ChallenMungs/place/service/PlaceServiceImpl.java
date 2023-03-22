package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository jpaRepo;

    @Override
    public List<Place> getPlace(List cities, List types) {
        List<Place> list;
        list = jpaRepo.findByCityInAndTypeIn(cities, types);

        return list.stream()
                .map(b -> new Place(b.getPlaceId(), b.getName(), b.getCity(), b.getAddress(), b.getNumber(), b.getType(), b.getLat(), b.getLng()))
                .collect(Collectors.toList());
    }
}
