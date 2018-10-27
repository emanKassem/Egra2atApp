package com.example.a2laa.egra2atapp.model;

import java.util.Map;

public class Ministry {
    private String ministryName;
    private Map<String, Sector> sectors;

    public String getMinistryName() {
        return ministryName;
    }

    public void setMinistryName(String ministryName) {
        this.ministryName = ministryName;
    }

    public Map<String, Sector> getSectors() {
        return sectors;
    }

    public void setSectors(Map<String, Sector> sectors) {
        this.sectors = sectors;
    }
}
