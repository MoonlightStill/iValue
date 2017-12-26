package com.ices507.troy.ivalue_clock.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.customview.CustomTableView;
import com.ices507.troy.ivalue_clock.entity.Record;
import com.ices507.troy.ivalue_clock.entity.WiFiInfo;
import com.ices507.troy.ivalue_clock.geofence.GeofenceClient;
import com.ices507.troy.ivalue_clock.service.LocateService;
import com.ices507.troy.ivalue_clock.utils.RecordsDBHelper;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by troy on 17-11-6.
 */

public class MainFragment extends BaseFragment {
    private final String TAG = "MainFragment";
    private TextView tv;
    private View view;
    private Button btnStart;
    private LocateService locateService;
    private boolean bound;
    private RelativeLayout layoutRecords;
    private CustomTableView recordsTable;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main,container,false);
        layoutRecords = (RelativeLayout) view.findViewById(R.id.layout_record);
        btnStart = (Button) view.findViewById(R.id.btn_locate);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutRecords.removeView(recordsTable);
                showRecords();
            }
        });
        Log.i("inflate", "Mainfragment inflate successfully");
        Log.i("container", container.toString());
        showRecords();
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GeofenceClient mGeoFence = GeofenceClient.getInstance();
        Log.e("BaseGeofence", mGeoFence.toString());
        Intent intent = new Intent(this.getActivity().getApplicationContext(), LocateService.class);
        this.getActivity()
                .bindService(intent, connection_binder, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bound) {
            this.getActivity().unbindService(connection_binder);
            bound = false;
        }
    }

    private void showRecords() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(WIFI_SERVICE);
        recordsTable = new CustomTableView(getActivity(),3);
        recordsTable.clearRows();
        recordsTable.addRow(new String[]{"时间", "区域边界距离", "类型"});
        RecordsDBHelper recordsDBHelper =
                RecordsDBHelper.getInstance(getActivity(), RecordsDBHelper.DB_VERSION);
        recordsDBHelper.openReadLink();
        ArrayList<Record> records = recordsDBHelper.getAll();

        for(int i=0;i < records.size(); i ++) {
            Record record = records.get(i);
            recordsTable.addRow(new Object[]{
                    record.getTime(),record.getDistance(),record.getType() == 1? "自动":"手动"});
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        recordsTable.setLayoutParams(lp);
        layoutRecords.addView(recordsTable);
    }

    private ServiceConnection connection_binder = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            locateService = ((LocateService.MyBinder) service).getService();
            locateService.startService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Service Disconnected",name.toString());
            bound = false;
        }
    };
}
