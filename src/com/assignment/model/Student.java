package com.assignment.model;

import java.io.Serializable;

public class Student extends Account implements Serializable {
    private String parents;

    public Student(String username, String pass, String fullName, String birthDate, Boolean gender, String email) {
        super(username, pass, fullName, birthDate, gender, email);
    }

    public Student(String username, String pass, String fullName, String birthDate, Boolean gender, String email, String parents) {
        super(username, pass, fullName, birthDate, gender, email);
        this.parents = parents;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }
}
