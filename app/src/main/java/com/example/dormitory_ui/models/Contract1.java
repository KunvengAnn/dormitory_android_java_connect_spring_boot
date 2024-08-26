package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;

public class Contract1 {
    @SerializedName("id_Contract1")
    private int idContract1;

    @SerializedName("id_student")
    private Integer idStudent;

    @SerializedName("id_Dormitory1")
    private Integer idDormitory1;

    @SerializedName("id_room")
    private Integer idRoom;

    @SerializedName("date_start_Contract1")
    private String dateStartContract1;

    @SerializedName("date_end_Contract1")
    private String dateEndContract1;

    @SerializedName("studentDTO")
    private StudentDTO studentDTO;

    // Getters and Setters
    public int getIdContract1() {
        return idContract1;
    }

    public void setIdContract1(int idContract1) {
        this.idContract1 = idContract1;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getIdDormitory1() {
        return idDormitory1;
    }

    public void setIdDormitory1(Integer idDormitory1) {
        this.idDormitory1 = idDormitory1;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getDateStartContract1() {
        return dateStartContract1;
    }

    public void setDateStartContract1(String dateStartContract1) {
        this.dateStartContract1 = dateStartContract1;
    }

    public String getDateEndContract1() {
        return dateEndContract1;
    }

    public void setDateEndContract1(String dateEndContract1) {
        this.dateEndContract1 = dateEndContract1;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }
}
