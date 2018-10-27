package com.example.a2laa.egra2atapp.model;

import android.net.Uri;

import java.util.List;
import java.util.Map;

public class Service {
    private String duration;
    private String fees;
    private Map<String, String > files;
    private String location;
    private String nationality;
    private String negotiability;
    private String onlineService;
    private String repaymentOfViolations;
    private String serviceName;
    private Map<String, String> serviceSteps;
    private String website;
    private String workingHours;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNegotiability() {
        return negotiability;
    }

    public void setNegotiability(String negotiability) {
        this.negotiability = negotiability;
    }

    public String getOnlineService() {
        return onlineService;
    }

    public void setOnlineService(String onlineService) {
        this.onlineService = onlineService;
    }

    public String getRepaymentOfViolations() {
        return repaymentOfViolations;
    }

    public void setRepaymentOfViolations(String repaymentOfViolations) {
        this.repaymentOfViolations = repaymentOfViolations;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Map<String, String> getServiceSteps() {
        return serviceSteps;
    }

    public void setServiceSteps(Map<String, String> serviceSteps) {
        this.serviceSteps = serviceSteps;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
