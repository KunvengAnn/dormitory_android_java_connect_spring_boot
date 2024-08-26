package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Room1 {
    @SerializedName("id_room")
    private int idRoom;

    @SerializedName("room_number")
    private int roomNumber;

    @SerializedName("room_price")
    private double roomPrice;

    @SerializedName("room_max_capacity")
    private int roomMaxCapacity;

    @SerializedName("quantity_person")
    private int quantityPerson;

    @SerializedName("status_is_empty_room")
    private boolean statusIsEmptyRoom;

    @SerializedName("status_room_is_boy")
    private boolean statusRoomIsBoy;

    @SerializedName("dormitory")
    private Dormitory1 dormitory1;

    @SerializedName("contracts")
    private List<Contract1> contract1s;

    // Getters and Setters
    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getRoomMaxCapacity() {
        return roomMaxCapacity;
    }

    public void setRoomMaxCapacity(int roomMaxCapacity) {
        this.roomMaxCapacity = roomMaxCapacity;
    }

    public int getQuantityPerson() {
        return quantityPerson;
    }

    public void setQuantityPerson(int quantityPerson) {
        this.quantityPerson = quantityPerson;
    }

    public boolean isStatusIsEmptyRoom() {
        return statusIsEmptyRoom;
    }

    public void setStatusIsEmptyRoom(boolean statusIsEmptyRoom) {
        this.statusIsEmptyRoom = statusIsEmptyRoom;
    }

    public boolean isStatusRoomIsBoy() {
        return statusRoomIsBoy;
    }

    public void setStatusRoomIsBoy(boolean statusRoomIsBoy) {
        this.statusRoomIsBoy = statusRoomIsBoy;
    }

    public Dormitory1 getDormitory1() {
        return dormitory1;
    }

    public void setDormitory1(Dormitory1 dormitory1) {
        this.dormitory1 = dormitory1;
    }

    public List<Contract1> getContract1s() {
        return contract1s;
    }

    public void setContract1s(List<Contract1> contract1s) {
        this.contract1s = contract1s;
    }
}

