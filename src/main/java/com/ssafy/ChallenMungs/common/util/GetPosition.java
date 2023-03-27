package com.ssafy.ChallenMungs.common.util;

import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;

@Component
public class GetPosition {
    public static final double EARTH_RADIUS = 6371.01;
    public static Point2D.Double calculateCoordinate(double latitude, double longitude, double distance, double direction) {
        double radianDirection = Math.toRadians(direction); // 라디안 단위로 변환
        double radianLatitude = Math.toRadians(latitude); // 라디안 단위로 변환
        double radianLongitude = Math.toRadians(longitude); // 라디안 단위로 변환

        // 새로운 위도 계산
        double newLatitude = Math.asin(Math.sin(radianLatitude) * Math.cos(distance / EARTH_RADIUS)
                + Math.cos(radianLatitude) * Math.sin(distance / EARTH_RADIUS) * Math.cos(radianDirection));
        newLatitude = Math.toDegrees(newLatitude); // 도 단위로 변환

        // 새로운 경도 계산
        double newLongitude = radianLongitude + Math.atan2(Math.sin(radianDirection) * Math.sin(distance / EARTH_RADIUS) * Math.cos(radianLatitude),
                Math.cos(distance / EARTH_RADIUS) - Math.sin(radianLatitude) * Math.sin(newLatitude));
        newLongitude = Math.toDegrees(newLongitude); // 도 단위로 변환

        return new Point2D.Double(newLongitude, newLatitude);
    }
}
