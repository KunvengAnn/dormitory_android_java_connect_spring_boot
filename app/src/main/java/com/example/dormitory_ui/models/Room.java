package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("id_room")
    private Integer id_room;

    @SerializedName("room_number")
    private Integer room_number;

    @SerializedName("room_price")
    private Double room_price;

    @SerializedName("room_max_capacity")
    private Integer room_max_capacity;

    @SerializedName("quantity_person")
    private Integer quantity_person;

    @SerializedName("status_is_empty_room")
    private Boolean status_is_empty_room;

    @SerializedName("status_room_is_boy")
    private Boolean status_room_is_boy;

    private Integer id_dormitory;

    /////
    public Integer getId_room() {
        return id_room;
    }

    public void setId_room(Integer id_room) {
        this.id_room = id_room;
    }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public Double getRoom_price() {
        return room_price;
    }

    public void setRoom_price(Double room_price) {
        this.room_price = room_price;
    }

    public Integer getRoom_max_capacity() {
        return room_max_capacity;
    }

    public void setRoom_max_capacity(Integer room_max_capacity) {
        this.room_max_capacity = room_max_capacity;
    }

    public Integer getQuantity_person() {
        return quantity_person;
    }

    public void setQuantity_person(Integer quantity_person) {
        this.quantity_person = quantity_person;
    }

    public Boolean getStatus_is_empty_room() {
        return status_is_empty_room;
    }

    public void setStatus_is_empty_room(Boolean status_is_empty_room) {
        this.status_is_empty_room = status_is_empty_room;
    }

    public Boolean getStatus_room_is_boy() {
        return status_room_is_boy;
    }

    public void setStatus_room_is_boy(Boolean status_room_is_boy) {
        this.status_room_is_boy = status_room_is_boy;
    }


    public Integer getId_dormitory(){
        return id_dormitory;
    }

    public void setId_dormitory(Integer id_dormitory){
        this.id_dormitory = id_dormitory;
    }
}
