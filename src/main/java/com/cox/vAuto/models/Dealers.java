package com.cox.vAuto.models;

import java.util.List;

public class Dealers {



    private Integer dealerId;
    private String name;
    private List<VehicleInfoModel>  vehicles;

    public Dealers(Integer dealerId, String name, List<VehicleInfoModel> vehicles) {
        this.dealerId = dealerId;
        this.name = name;
        this.vehicles = vehicles;
    }

    public Dealers() {

    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VehicleInfoModel> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleInfoModel> vehicles) {
        this.vehicles = vehicles;
    }
}
