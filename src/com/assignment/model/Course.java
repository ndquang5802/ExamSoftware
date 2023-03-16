package com.assignment.model;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseID;
    private String name;

    private boolean status;

    private String teacherName;

    public Course(String courseID, String name) {
        this.courseID = courseID;
        this.name = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
