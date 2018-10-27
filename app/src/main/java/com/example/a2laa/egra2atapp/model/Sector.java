package com.example.a2laa.egra2atapp.model;

import java.util.Map;

public class Sector {
    private String sectorName;
    private Map<String, Service> services;

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }
}
