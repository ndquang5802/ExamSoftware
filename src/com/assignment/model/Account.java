package com.assignment.model;

import com.lib.XFile;
import com.lib.XUtils;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private String username;
    private String pass;
    private String fullName;
    private Date birthDate;
    private Boolean gender;
    private String email;

    public Account() {
        this.username = "admin";
        this.pass = "admin";
        this.fullName = "Admin";
        this.birthDate = XUtils.convertStringtoDate("2002-08-05");
        this.gender = true;
        this.email = "admin@gmail.com";
    }

    public Account(String username, String pass, String fullName, String birthDate, Boolean gender, String email) {
        this.username = username;
        this.pass = pass;
        this.fullName = fullName;
        this.birthDate = XUtils.convertStringtoDate(birthDate);
        this.gender = gender;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = XUtils.convertStringtoDate(birthDate);
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
