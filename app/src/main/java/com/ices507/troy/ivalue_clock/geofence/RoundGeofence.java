package com.ices507.troy.ivalue_clock.geofence;

import android.util.Log;

/**
 * Created by troy on 17-12-10.
 *
 * @Description:
 * @Modified By:
 */

public class RoundGeofence extends BaseGeofence {
    private Point center;
    private double radius;


    @Override
    public int setGeofence(Point[] points,double radius) {
        try {
            center = points[0];
            this.radius = radius;
            return GEOFENCE_ADD_SUCCESS;
        } catch (NullPointerException e){
            Log.e("NullPointError","Points is null");
            return GEOFENCE_ADD_FAILED;
        }
    }

    @Override
    public double juggePointStatus(Point point) {
        Log.e("distance",String.valueOf(Point.computeDistance(point, center) - radius));
        return Point.computeDistance(point, center) - radius;
    }

    @Override
    public int deleteGeofence() {
        return 0;
    }
}
