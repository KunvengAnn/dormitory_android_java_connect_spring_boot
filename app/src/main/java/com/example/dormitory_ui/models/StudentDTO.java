package com.example.dormitory_ui.models;

import com.google.gson.annotations.SerializedName;

public class StudentDTO {
    @SerializedName("id_student")
    private String idStudent;

    @SerializedName("student_name")
    private String studentName;

    @SerializedName("student_sex")
    private boolean studentSex;

    @SerializedName("date_of_birth_student")
    private String dateOfBirthStudent;

    @SerializedName("student_class")
    private String studentClass;

    @SerializedName("student_email")
    private String student_email;

    @SerializedName("department_of_student")
    private String departmentOfStudent;

    @SerializedName("student_phone")
    private String studentPhone;

    @SerializedName("citizenIdentification")
    private String citizenIdentification;

    @SerializedName("student_imageUrl")
    private String studentImageUrl;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("descriptionAboutSelf")
    private String descriptionAboutSelf;

    @SerializedName("birthPlace")
    private String birthPlace;

    // Getters and Setters
    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudent_email(){
        return student_email;
    }

    public void setStudent_email(String student_email){
        this.student_email =  student_email;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isStudentSex() {
        return studentSex;
    }

    public void setStudentSex(boolean studentSex) {
        this.studentSex = studentSex;
    }

    public String getDateOfBirthStudent() {
        return dateOfBirthStudent;
    }

    public void setDateOfBirthStudent(String dateOfBirthStudent) {
        this.dateOfBirthStudent = dateOfBirthStudent;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getDepartmentOfStudent() {
        return departmentOfStudent;
    }

    public void setDepartmentOfStudent(String departmentOfStudent) {
        this.departmentOfStudent = departmentOfStudent;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getCitizenIdentification() {
        return citizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getStudentImageUrl() {
        return studentImageUrl;
    }

    public void setStudentImageUrl(String studentImageUrl) {
        this.studentImageUrl = studentImageUrl;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescriptionAboutSelf() {
        return descriptionAboutSelf;
    }

    public void setDescriptionAboutSelf(String descriptionAboutSelf) {
        this.descriptionAboutSelf = descriptionAboutSelf;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}
