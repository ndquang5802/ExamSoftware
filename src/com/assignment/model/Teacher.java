package com.assignment.model;

import java.io.Serializable;
import java.util.List;

public class Teacher extends Account implements Serializable {
    private String position;

    public Teacher(String username, String pass, String fullName, String birthDate, Boolean gender, String email) {
        super(username, pass, fullName, birthDate, gender, email);
    }

    public Teacher(String username, String pass, String fullName, String birthDate, Boolean gender, String email, String position) {
        super(username, pass, fullName, birthDate, gender, email);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
