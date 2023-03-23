package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.repository.PlaceRepository;
import com.ssafy.ChallenMungs.user.controller.UserController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository jpaRepo;
    private Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);

    @Override
    public List<Place> getPlace(List<String> cities, String type) {
        List<Place> list;

        if(cities.isEmpty()) {
            // 선택 안함
            if(type == null){
                list = jpaRepo.findAll();
            }
            // 시설 유형
            else{
                list = jpaRepo.findByType(type);
            }
        }
        else{
            // 지역 + 시설 유형
            if(type != null){
                list = jpaRepo.findByCityInAndType(cities, type);
            }
            // 지역
            else{
                list = jpaRepo.findByCityIn(cities);
            }
        }

        return list.stream()
                .map(b -> new Place(b.getPlaceId(), b.getName(), b.getCity(), b.getAddress(), b.getNumber(), b.getType(), b.getLat(), b.getLng()))
                .collect(Collectors.toList());
    }
}
