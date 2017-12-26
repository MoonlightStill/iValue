package com.ices507.troy.ivalue_clock.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ices507.troy.ivalue_clock.entity.Record;
import com.ices507.troy.ivalue_clock.geofence.BaseGeofence;
import com.ices507.troy.ivalue_clock.geofence.GeofenceClient;
import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.activity.StartActivity;
import com.ices507.troy.ivalue_clock.geofence.Point;
import com.ices507.troy.ivalue_clock.utils.RecordsDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocateService extends Service implements GeofenceClient.OnPointStatusListener{
    private final static String TAG = "LocateService";
    public static final long INTERVAL_SHORT = 2000;
    public static final long INTERVAL_TEN = 10000;
    public static final long INTERVAL_HALF = 30000;
    public static final long INTERVAL_MINUTE = 60000;
    public static final long INTERVAL_QUARTER = 15*INTERVAL_MINUTE;

    private Double pastDistance;
    private int timer = 0;
    private boolean inTiming = false;
    private Point recordPoint;

    private IBinder binder = new MyBinder();
    private Thread thread;
    GeofenceClient mGeofenceClient;
    private static final int REQUEST_CODE = 800;
    private static final String ACTION_PLAY = "play";

    public class MyBinder extends Binder {
        public LocateService getService(){
            return LocateService.this;}
    }

    public LocateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand" + startId);

        Notification.Builder builder = new Notification.Builder(this);
        Intent notifyIntent = new Intent(this, StartActivity.class);

        builder.setContentIntent(PendingIntent.getActivity(this, 0, notifyIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("后台持续定位中")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("显示内容")
                .setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        startForeground(110,notification);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onbind");
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stopForeground(true);
        mGeofenceClient.stopLocate();
        thread.interrupt();
        super.onDestroy();
    }

    public void startService(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mGeofenceClient = GeofenceClient.getInstance();
                Point[] point = new Point[]{new Point(45.7426551457,126.6335034370)};
                int code = mGeofenceClient.createGeofence(BaseGeofence.GEOFENCE_TYPE_ROUND,point,40);
                if(code == BaseGeofence.GEOFENCE_ADD_SUCCESS){
                    Log.i(TAG, "GeoFence add success");
                } else if (code == BaseGeofence.GEOFENCE_ADD_FAILED) {
                    mGeofenceClient.stopLocate();
                    Log.i(TAG, "GeoFence add failed");
                }
                mGeofenceClient.setGeofenceStatusChangedListener(LocateService.this);
                mGeofenceClient.startLocate(LocateService.this);

            }
        });
        thread.start();
    }

    @Override
    public long onStatusChanged(double distance, Point point) {
        long interval;
        if (pastDistance == null) {
            if (distance <= 0) {
                record(point,distance);
            }
            pastDistance = distance;
        } else if ((distance > 0 && pastDistance < 0) || (distance < 0 && pastDistance > 0)) {
            timer = 0;
            recordPoint = point;
            inTiming = true;
            pastDistance = distance;
        } else if (inTiming) {
            timer++;
            if (timer >= 10) {
                record(point, pastDistance);
                timer = 0;
                inTiming = false;
            }
        } else {
            pastDistance = distance;
        }

        if(distance > 1000*30){
            interval = INTERVAL_QUARTER;
        }else if(distance > 1000 * 2) {
            interval = INTERVAL_MINUTE;
        }else if(distance > 500) {
            interval = INTERVAL_HALF;
        } else if (distance > 200) {
            interval = INTERVAL_TEN;
        } else if (distance > 0) {
            interval = INTERVAL_SHORT;
        } else {
            interval = INTERVAL_SHORT;
        }
        return interval;
    }

    private void record(Point point, double distance) {
        RecordsDBHelper recordsDBHelper =
                RecordsDBHelper.getInstance(this, RecordsDBHelper.DB_VERSION);
        recordsDBHelper.openWriteLink();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date(System.currentTimeMillis()));
        Record record = new Record();
        record.setTime(time);
        record.setLatitude(point.latitude);
        record.setLongitude(point.longitude);
        record.setDistance(distance);
        record.setType(1);
        record.setArea("ices507");
        recordsDBHelper.insert(record);
        recordsDBHelper.closeLink();
    }
}
