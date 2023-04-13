package com.ssafy.ChallenMungs.place.service;

import com.ssafy.ChallenMungs.place.entity.Place;
import com.ssafy.ChallenMungs.place.repository.PlaceRepository;
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
    public Page<Place> getPlace(Pageable pageable, String name, List<String> cities, String type) {
        Page<Place> page;

        if(cities == null) {
            // 선택 안함
            if(type == null){
                if(name == null){
                    page = jpaRepo.findAll(pageable);
                }
                else{
                    page = jpaRepo.findByNameContaining(pageable,name);
                }
            }
            // 시설 유형
            else{
                if(name == null){
                    page = jpaRepo.findByType(pageable, type);
                }
                else{
                    page = jpaRepo.findByNameContainingAndType(pageable, name, type);
                }
            }
        }
        else{
            // 지역 + 시설 유형
            if(type != null){
                if(name == null){
                    page = jpaRepo.findByCityInAndType(pageable, cities, type);
                }
                else{
                    page = jpaRepo.findByNameContainingAndCityInAndType(pageable, name, cities, type);
                }
            }
            // 지역
            else{
                if(name == null){
                    page = jpaRepo.findByCityIn(pageable, cities);
                }
                else{
                    page = jpaRepo.findByNameContainingAndCityIn(pageable, name, cities);
                }
            }
        }

        return new PageImpl<Place>(page.getContent().stream()
                .map(b -> new Place(b.getPlaceId(), b.getName(), b.getCity(), b.getAddress(), b.getNumber(), b.getType(), b.getLat(), b.getLng()))
                .collect(Collectors.toList())
                , pageable, page.getTotalElements());
    }

}
