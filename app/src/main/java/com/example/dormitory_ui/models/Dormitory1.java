package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dormitory1 {
    @SerializedName("id_dormitory")
    private int idDormitory1;

    @SerializedName("dormitory_name")
    private String Dormitory1Name;

    @SerializedName("rooms")
    private List<Room1> rooms;

    @SerializedName("contracts")
    private List<Contract1> Contract1s;

    // Getters and Setters
    public int getIdDormitory1() {
        return idDormitory1;
    }

    public void setIdDormitory1(int idDormitory1) {
        this.idDormitory1 = idDormitory1;
    }

    public String getDormitory1Name() {
        return Dormitory1Name;
    }

    public void setDormitory1Name(String Dormitory1Name) {
        this.Dormitory1Name = Dormitory1Name;
    }

    public List<Room1> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room1> rooms) {
        this.rooms = rooms;
    }

    public List<Contract1> getContract1s() {
        return Contract1s;
    }

    public void setContract1s(List<Contract1> Contract1s) {
        this.Contract1s = Contract1s;
    }
}
