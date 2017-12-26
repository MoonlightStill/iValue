package com.ices507.troy.ivalue_clock.fragment;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.customview.CustomTableView;
import com.ices507.troy.ivalue_clock.entity.WiFiInfo;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by troy on 17-12-21.
 *
 * @Description:
 * @Modified By:
 */

public class CollectFragment extends BaseFragment {
    private final String TAG = "CollectFragment";
    private View view;
    private Button btnCollect;
    private EditText etDescrition;
    private CustomTableView wtv;
    private RelativeLayout relativeLayout;
    private ArrayList<WiFiInfo> WiFiInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collect,container,false);
        Log.i("inflate", "CollectFragment inflate successfully");
        Log.i("container", container.toString());
        btnCollect = (Button) view.findViewById(R.id.btn_login);
        etDescrition = (EditText) view.findViewById(R.id.et_description);
        wtv = new CustomTableView(getActivity());
        relativeLayout = (RelativeLayout) view.findViewById(R.id.WiFiInfo);
//        btnCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                collectAreaWiFi();
//            }
//        });
        showWiFi();
        return view;
    }

    private void showWiFi(){
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(WIFI_SERVICE);
        ArrayList<ScanResult> scanResult;
        scanResult = (ArrayList<ScanResult>)wifiManager.getScanResults();
        wtv.clearRows();
        WiFiInfos = new ArrayList<WiFiInfo>();
        wtv.addRow(new String[] {"WiFi名称","WiFi物理地址","WiFi信号强度(数值越大越强)"});
        for(Iterator<ScanResult> iterator = scanResult.iterator(); iterator.hasNext();) {
            ScanResult temp = iterator.next();
            if(temp.level < -80) continue;
            WiFiInfo row = new WiFiInfo(temp.SSID,temp.BSSID,temp.level);
            Log.e(TAG, row.toString());
            wtv.addRow(new Object[]{temp.SSID,temp.BSSID,String.valueOf(temp.level)});
            WiFiInfos.add(row);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        wtv.setLayoutParams(lp);
        relativeLayout.addView(wtv);

    }
    private void collectAreaWiFi() {

    }


}
