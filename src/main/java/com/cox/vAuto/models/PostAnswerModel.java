package com.cox.vAuto.models;

import java.util.List;

public class PostAnswerModel {

    public PostAnswerModel(List<Dealers> dealers) {
        this.dealers = dealers;
    }

    private List<Dealers> dealers;

    public List<Dealers> getDealers() {
        return dealers;
    }

    public void setDealers(List<Dealers> dealers) {
        this.dealers = dealers;
    }
}
