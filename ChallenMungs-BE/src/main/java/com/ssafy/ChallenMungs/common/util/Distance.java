package com.ssafy.ChallenMungs.common.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Distance {
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구 반경 (km)
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x1 = R * Math.cos(lat1Rad) * Math.cos(lon1Rad);
        double y1 = R * Math.cos(lat1Rad) * Math.sin(lon1Rad);
        double z1 = R * Math.sin(lat1Rad);

        double x2 = R * Math.cos(lat2Rad) * Math.cos(lon2Rad);
        double y2 = R * Math.cos(lat2Rad) * Math.sin(lon2Rad);
        double z2 = R * Math.sin(lat2Rad);

        Vector3D p1 = new Vector3D(x1, y1, z1);
        Vector3D p2 = new Vector3D(x2, y2, z2);

        return R * Vector3D.angle(p1, p2);
    }
    public HashMap<String, Double> getPosition(double latitude, double longitude, double distance, double direction) {
        double EARTH_RADIUS = 6371.01;

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

        HashMap<String, Double> value = new HashMap<>();
        value.put("latDistance", newLatitude);
        value.put("lngDistance", newLongitude);
        return value;
    }
}
