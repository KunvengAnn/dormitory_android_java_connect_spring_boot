package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;

public class Contract {

    @SerializedName("id_contract")
    private int idContract;

    @SerializedName("id_student")
    private String idStudent;

    @SerializedName("id_dormitory")
    private int idDormitory;

    @SerializedName("id_room")
    private int idRoom;

    @SerializedName("date_start_contract")
    private String dateStartContract;

    @SerializedName("date_end_contract")
    private String dateEndContract;


    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdDormitory() {
        return idDormitory;
    }

    public void setIdDormitory(int idDormitory) {
        this.idDormitory = idDormitory;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getDateStartContract() {
        return dateStartContract;
    }

    public void setDateStartContract(String dateStartContract) {
        this.dateStartContract = dateStartContract;
    }

    public String getDateEndContract() {
        return dateEndContract;
    }

    public void setDateEndContract(String dateEndContract) {
        this.dateEndContract = dateEndContract;
    }
}
