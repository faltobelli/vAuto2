package com.cox.vAuto.models;

public class DealerIdModel {

    private Integer dealerId;
    private String name;


    public DealerIdModel() {}

    public DealerIdModel(Integer dealerId, String name) {
        this.dealerId = dealerId;
        this.name = name;
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
}
