package com.ices507.troy.ivalue_clock.entity;

import com.ices507.troy.ivalue_clock.geofence.Point;

import java.util.ArrayList;

/**
 * Created by troy on 17-12-12.
 *
 * @Description:
 * @Modified By:
 */

public class GeofenceDB {
    private int ID;
    private String description;
    private ArrayList<Point> points;
    private double radius;

    public GeofenceDB(int ID, String description, ArrayList<Point> points, double radius) {
        this.ID = ID;
        this.description = description;
        this.points = points;
        this.radius = radius;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
