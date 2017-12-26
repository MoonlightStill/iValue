package com.ices507.troy.ivalue_clock.entity;

/**
 * Created by troy on 17-12-10.
 *
 * @Description: point in geography with latitude and longitude two parameters.
 * @Modified By:
 */

public class GeoPoint {
    public double latitude;
    public double longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @description compute the distance of point a and point b on earth.

     * @param a
     * @param b

     * @return distance in m.
     **/

    public static double computeDistance(GeoPoint a, GeoPoint b){

        double lon1 = (Math.PI / 180) * a.longitude;
        double lon2 = (Math.PI / 180) * b.longitude;
        double lat1 = (Math.PI / 180) * a.latitude;
        double lat2 = (Math.PI / 180) * b.latitude;

        double R = 6371004;//radius of the earth
        double distance = Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        return distance*1000;
    }
}
