package com.ices507.troy.ivalue_clock.entity;

/**
 * Created by troy on 17-12-25.
 *
 * @Description:
 * @Modified By:
 */

public class Record {
    private String time;
    private double longitude;
    private double latitude;
    private String area;
    private int type;
    private double distance;

    public Record(String time, double longitude, double latitude, String area, int type,double distance) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.type = type;
        this.distance = distance;
    }

    public Record() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
