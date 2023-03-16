package com.assignment.model;

import com.lib.XUtils;

import java.io.Serializable;
import java.util.Date;

public class CourseDetail implements Serializable {
    private Teacher teacher;
    private Course course;
    private Date courseFrom;
    private Date courseTo;

    public CourseDetail(Teacher teacher, Course course, String courseFrom, String courseTo) {
        this.teacher = teacher;
        this.course = course;
        this.courseFrom = XUtils.convertStringtoDate(courseFrom);
        this.courseTo = XUtils.convertStringtoDate(courseTo);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getCourseFrom() {
        return courseFrom;
    }

    public void setCourseFrom(String courseFrom) {
        this.courseFrom = XUtils.convertStringtoDate(courseFrom);
    }

    public Date getCourseTo() {
        return courseTo;
    }

    public void setCourseTo(String courseTo) {
        this.courseTo = XUtils.convertStringtoDate(courseTo);
    }
}
