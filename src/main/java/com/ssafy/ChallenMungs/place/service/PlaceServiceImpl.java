package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.repository.PlaceRepository;
import com.ssafy.ChallenMungs.user.controller.UserController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Place> getPlace(Pageable pageable, List<String> cities, String type) {
        Page<Place> page;

        if(cities.isEmpty()) {
            // 선택 안함
            if(type == null){
                page = jpaRepo.findAll(pageable);
            }
            // 시설 유형
            else{
                page = jpaRepo.findByType(pageable, type);
            }
        }
        else{
            // 지역 + 시설 유형
            if(type != null){
                page = jpaRepo.findByCityInAndType(pageable, cities, type);
            }
            // 지역
            else{
                page = jpaRepo.findByCityIn(pageable, cities);
            }
        }

        return new PageImpl<Place>(page.getContent().stream()
                .map(b -> new Place(b.getPlaceId(), b.getName(), b.getCity(), b.getAddress(), b.getNumber(), b.getType(), b.getLat(), b.getLng()))
                .collect(Collectors.toList())
                , pageable, page.getTotalElements());
    }

//    @Override
//    public Page<Place> pagingList(Pageable pageable) {
//        Page<Place> page = jpaRepo.findAll(pageable);
//
//        return new PageImpl<Place>(jpaRepo.findAll(pageable).getContent().stream()
//                .map(b -> new Place(b.getPlaceId(), b.getName(), b.getCity(), b.getAddress(), b.getNumber(), b.getType(), b.getLat(), b.getLng()))
//                .collect(Collectors.toList())
//                , pageable, page.getTotalElements());
//    }
}
