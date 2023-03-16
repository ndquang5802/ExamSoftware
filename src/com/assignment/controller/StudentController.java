package com.assignment.controller;

import com.assignment.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    List<StudentDetail> listStudentDetail;
    List<ExamSubject> listExamSubject;
    DefaultComboBoxModel modelComboChooseCourse;
    List<Student> listStudent;
    List<CourseDetail> listCourseDetail;
    Student tempStudent;
    Course tempCourse;

    public StudentController(List<StudentDetail> listStudentDetail,
                             List<ExamSubject> listExamSubject,
                             DefaultComboBoxModel modelComboChooseCourse,
                             List<Student> listStudent,
                             List<CourseDetail> listCourseDetail) {
        this.listStudentDetail = listStudentDetail;
        this.listExamSubject = listExamSubject;
        this.modelComboChooseCourse = modelComboChooseCourse;
        this.listStudent = listStudent;
        this.listCourseDetail = listCourseDetail;

        if (this.listExamSubject == null || this.listExamSubject.size() == 0) {
            this.listExamSubject = new ArrayList<>();
        }
    }

    public List<StudentDetail> getListStudentDetail() {
        return listStudentDetail;
    }

    public void setListStudentDetail(List<StudentDetail> listStudentDetail) {
        this.listStudentDetail = listStudentDetail;
    }

    public List<ExamSubject> getListExamSubject() {
        return listExamSubject;
    }

    public void setListExamSubject(List<ExamSubject> listExamSubject) {
        this.listExamSubject = listExamSubject;
    }

    public DefaultComboBoxModel getModelComboChooseCourse() {
        return modelComboChooseCourse;
    }

    public void setModelComboChooseCourse(DefaultComboBoxModel modelComboChooseCourse) {
        this.modelComboChooseCourse = modelComboChooseCourse;
    }

    public void editStudentDetail(String chooseStudent, String chooseStudentCourse, Double score) {
        for (Student student : listStudent) {
            if (student.getUsername().equals(chooseStudent)) {
                tempStudent = student;
            }
        }
        for (CourseDetail courseDetail : listCourseDetail) {
            if (courseDetail.getCourse().getName().equals(chooseStudentCourse)) {
                tempCourse = courseDetail.getCourse();
            }
        }
        for (StudentDetail sd : listStudentDetail) {
            if (sd.getStudent().getUsername().equals(chooseStudent) && sd.getCourse().getName().equals(chooseStudentCourse)) {
                sd.setScore(score);
                break;
            }
        }
    }
}
