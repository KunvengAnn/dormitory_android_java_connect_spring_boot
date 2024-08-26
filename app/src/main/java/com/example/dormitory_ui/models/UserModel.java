package com.example.dormitory_ui.models;

import com.example.dormitory_ui.models.common_models.Role;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    private Integer id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("role")
    private Role role;

    @SerializedName("id_student")
    private String id_student;


    public UserModel() {
    }

    public UserModel(int id, String firstName, String lastName, String email, String password, String token, Role role, String id_student){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.id_student = id_student;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public String getId_student() {
        return id_student;
    }
    public void setId_student(String id_student) {
        this.id_student = id_student;
    }
}
