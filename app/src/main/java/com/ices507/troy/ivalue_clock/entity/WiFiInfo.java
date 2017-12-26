package com.ices507.troy.ivalue_clock.entity;

import android.content.SharedPreferences;

/**
 * Created by troy on 17-11-21.
 */

public class WiFiInfo {
    private String SSID;
    private String BSSID;
    private int level;

    public WiFiInfo(String SSID, String BSSID, int level) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.level = level;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
