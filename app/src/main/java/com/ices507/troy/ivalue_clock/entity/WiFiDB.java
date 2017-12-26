package com.ices507.troy.ivalue_clock.entity;

import java.util.ArrayList;

/**
 * Created by troy on 17-12-12.
 *
 * @Description:
 * @Modified By:
 */

public class WiFiDB {
    private int ID;
    private String description;
    private ArrayList<WiFiInfo> wifiInfos;

    public WiFiDB(int ID, String description, ArrayList<WiFiInfo> wifiInfos) {
        this.ID = ID;
        this.description = description;
        this.wifiInfos = wifiInfos;
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

    public ArrayList<WiFiInfo> getWifiInfos() {
        return wifiInfos;
    }

    public void setWifiInfos(ArrayList<WiFiInfo> wifiInfos) {
        this.wifiInfos = wifiInfos;
    }
}
