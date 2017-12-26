package com.ices507.troy.ivalue_clock.geofence;

/**
 * Created by troy on 17-12-10.
 *
 * @Description: All customized geofences should extend BaseGeofence which defines global status
 * parameters and methods that must be implemented.
 * @Modified By:
 */

public abstract class BaseGeofence {
    public static final int BOUNDARY_OUT = 0x001;
    public static final int BOUNDARY_IN = 0x002;
    public static final int GEOFENCE_ADD_SUCCESS = 0x101;
    public static final int GEOFENCE_ADD_FAILED = 0x102;
    public static final int GEOFENCE_TYPE_ROUND = 0x201;
    public static final int GEOFENCE_TYPE_POLYGON = 0x202;
    public static final int GEOFENCE_TYPE_NOTEXIST = 0x203;
    public static final int GEOFENCE_DELETE_SUCCESS = 0x301;
    public static final int GEOFENCE_DELETE_FAILED = 0x302;

    abstract public int setGeofence(Point points[], double radius);
    abstract public double juggePointStatus(Point point);
    abstract public int deleteGeofence();
}
