package com.assignment.model;

import java.io.Serializable;
import java.util.List;

public class StudentDetail implements Serializable {
    private Student student;
    private Course course;
    private double score;

    public StudentDetail(Student student, Course course, double score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
