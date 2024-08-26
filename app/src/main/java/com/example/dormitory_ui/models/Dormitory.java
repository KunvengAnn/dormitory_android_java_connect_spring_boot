package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Dormitory {
    @SerializedName("id_dormitory")
    private Integer id_dormitory;

    @SerializedName("dormitory_name")
    private String dormitory_name;

    @SerializedName("rooms")
    private List<Room> rooms;

    @SerializedName("contracts")
    private List<Contract> contracts;

    /////
    public Integer getId_dormitory() {
        return id_dormitory;
    }

    public void setId_dormitory(Integer id_dormitory) {
        this.id_dormitory = id_dormitory;
    }

    public String getDormitory_name() {
        return dormitory_name;
    }

    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
