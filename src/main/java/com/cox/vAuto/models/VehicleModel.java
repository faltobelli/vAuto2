package com.cox.vAuto.models;

public class VehicleModel {

    private Integer vehicleId;
    private Integer year;
    private String make;
    private String model;
    private Integer dealerId;



    public VehicleModel() {}

    public VehicleModel(Integer vehicleId, Integer year, String make, String model, Integer dealerId) {
        this.vehicleId = vehicleId;
        this.year = year;
        this.make = make;
        this.model = model;
        this.dealerId = dealerId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }
}
