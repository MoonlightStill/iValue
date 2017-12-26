package com.ices507.troy.ivalue_clock.geofence;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by troy on 17-11-21.
 */

public class GeofenceClient {
    private static final String TAG = "GeofenceClient";
    private OnPointStatusListener onGeofenceStatusChangedListener;
    private long lastInterval;

    private BaseGeofence geofence;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;
    private static GeofenceClient mGeofenceClient;


    public GeofenceClient() {
    }

     public static GeofenceClient getInstance() {
        if(mGeofenceClient == null)
        {
            mGeofenceClient = new GeofenceClient();
        }
        return mGeofenceClient;
    }


    public int createGeofence(int geofenceType,Point points[],double radius){
        int code;
        switch (geofenceType){
            case BaseGeofence.GEOFENCE_TYPE_ROUND:
                geofence = new RoundGeofence();
                code = geofence.setGeofence(points, radius);
                break;
            case BaseGeofence.GEOFENCE_TYPE_POLYGON:
                code = BaseGeofence.GEOFENCE_TYPE_POLYGON;
                break;
            default:
                code = BaseGeofence.GEOFENCE_TYPE_NOTEXIST;
                break;
        }
        return code;
    }

    private void setDefaultOption() {
        //设置高精度定位模式，同时使用网络和GPS定位，优先返回精度较高的结果
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置单次定位
        mLocationClientOption.setOnceLocation(true);
        mLocationClientOption.setOnceLocationLatest(true);
        //设置定位Http相应超时时间，默认为30s，不能低于8s,持续定位情况下超时后下一次定位请求依然会发出
        mLocationClientOption.setHttpTimeOut(20000);
        //设置定位缓存功能，默认true
        mLocationClientOption.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(mLocationClientOption);
    }

    private void changeInterval(long interval){
        if(interval == lastInterval) return;
        lastInterval = interval;
        if (interval < 0) {
            Log.e(TAG, "Now inner geofence");
//            onGeofenceChangeListener.onNearBoundary(interval);
        }else if(0 == interval) {
            mLocationClientOption.setOnceLocation(true);
            mLocationClientOption.setOnceLocationLatest(true);
        }else {
            Log.e(TAG, "interval"+interval);
            mLocationClientOption.setInterval(interval);
            mLocationClientOption.setOnceLocation(false);
            mLocationClientOption.setOnceLocationLatest(false);
        }
        mLocationClient.stopLocation();
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();
    }

    /**
     * @description Use Amap Api to get the current location and return the distance between current
     * point and geofence to the callback function.

     * @param context

     * @return

     * @throws
     **/
    public void startLocate(Context context){

        mLocationClient = new AMapLocationClient(context);
        mLocationClientOption = new AMapLocationClientOption();
        setDefaultOption();
        mLocationClient.startLocation();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if(aMapLocation.getErrorCode() == 0){
                        Point point = new Point(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        Log.e(TAG, "latitude"+aMapLocation.getLatitude());
                        Log.e(TAG, "longitude"+aMapLocation.getLongitude());
                        try {
                            double distance = geofence.juggePointStatus(point);
                            long interval = onGeofenceStatusChangedListener.onStatusChanged(distance,point);
                            changeInterval(interval);
                        }catch (NullPointerException e){
                            Log.e("ListenerError", e.toString());
                        }
                    }else {
                        Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode()
                                + ",ErrInfo:" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

    }

    public void stopLocate(){
        Log.e(TAG, "stop locate");
        mLocationClient.stopLocation();
        if(mLocationClient != null){
            mLocationClient.onDestroy();
        }
        mGeofenceClient = null;
    }
    
    public void setGeofenceStatusChangedListener(OnPointStatusListener onGeofenceStatusChangedListener) {
        this.onGeofenceStatusChangedListener = onGeofenceStatusChangedListener;
    }

    /**
     * Created by troy on 17-11-21.
     */

    public interface OnPointStatusListener {
        long onStatusChanged(double distance, Point point);
    }
}
