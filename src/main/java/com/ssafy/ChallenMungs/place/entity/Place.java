package com.ssafy.ChallenMungs.place.entity;

import lombok.*;
import software.amazon.ion.Decimal;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeId;

    // 장소 이름
    @Column(name="name")
    private String name;

    // 시도 명칭
    @Column(name="city")
    private String city;

    // 주소
    @Column(name="address")
    private String address;

    // 전화번호
    @Column(name="number")
    private String number;

    // 카테고리
    @Column(name="type")
    private String type;

    //위도
    @Column(name="lat")
    private String lat;

    // 경도
    @Column(name="lng")
    private String lng;

}
