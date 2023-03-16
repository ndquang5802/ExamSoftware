package com.assignment.controller;

import com.assignment.model.*;
import com.lib.XUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    DefaultTableModel modelCourseTable;
    DefaultTableModel modelTeacherTable;
    DefaultTableModel modelCourseDetailTable;
    List<Course> listCourse;
    List<Teacher> listTeacher;
    List<CourseDetail> listCourseDetail;
    DefaultComboBoxModel modelComboCourse;
    DefaultComboBoxModel modelComboTeacher;
    List<ExamSubject> listExamSubject;
    List<StudentDetail> listStudentDetail;
    Teacher tempTeacher;
    Course tempCourse;

    public AdminController() {

    }

    public AdminController(DefaultTableModel modelCourseTable,
                           List<Course> listCourse,
                           DefaultComboBoxModel modelComboCourse,
                           DefaultTableModel modelTeacherTable,
                           List<Teacher> listTeacher,
                           DefaultComboBoxModel modelComboTeacher,
                           DefaultTableModel modelCourseDetailTable,
                           List<CourseDetail> listCourseDetail,
                           List<ExamSubject> listExamSubject,
                           List<StudentDetail> listStudentDetail) {
        this.modelCourseTable = modelCourseTable;
        this.listCourse = listCourse;
        this.modelComboCourse = modelComboCourse;
        this.modelTeacherTable = modelTeacherTable;
        this.listTeacher = listTeacher;
        this.modelComboTeacher = modelComboTeacher;
        this.modelCourseDetailTable = modelCourseDetailTable;
        this.listCourseDetail = listCourseDetail;
        this.listExamSubject = listExamSubject;
        this.listStudentDetail = listStudentDetail;

        if (this.listCourse == null || this.listCourse.size() == 0) {
            this.listCourse = new ArrayList<>();
            this.listCourse.add(new Course("PROG191", "Java Programming"));
        } else {
            for (Course course: listCourse) {
                this.modelComboCourse.addElement(course.getName());
            }
        }

        if (this.listTeacher == null || this.listTeacher.size() == 0) {
            this.listTeacher = new ArrayList<>();
            this.listTeacher.add(new Teacher("khanhttk", "1234", "Tran Thi Kim Khanh", "1990-01-01", false, "khanhttk@fpt.edu.vn", "Department"));
        } else {
            for (Teacher teacher: listTeacher) {
                this.modelComboTeacher.addElement(teacher.getUsername());
            }
        }

        if (this.listCourseDetail == null || this.listCourseDetail.size() == 0) {
            this.listCourseDetail = new ArrayList<>();
        }
    }

    public List<Teacher> getListTeacher() {
        return listTeacher;
    }

    public void setListTeacher(List<Teacher> listTeacher) {
        this.listTeacher = listTeacher;
    }

    public DefaultComboBoxModel getModelComboTeacher() {
        return modelComboTeacher;
    }

    public void setModelComboTeacher(DefaultComboBoxModel modelComboTeacher) {
        this.modelComboTeacher = modelComboTeacher;
    }

    public List<Course> getListCourse() {
        return listCourse;
    }

    public void setListCourse(List<Course> listCourse) {
        this.listCourse = listCourse;
    }

    public DefaultComboBoxModel getModelComboCourse() {
        return modelComboCourse;
    }

    public void setModelComboCourse(DefaultComboBoxModel modelComboCourse) {
        this.modelComboCourse = modelComboCourse;
    }

    public void addCourse(String courseID, String courseName) {
        Course course = new Course(courseID, courseName);
        listCourse.add(course);
    }

    public List<CourseDetail> getListCourseDetail() {
        return listCourseDetail;
    }

    public void setListCourseDetail(List<CourseDetail> listCourseDetail) {
        this.listCourseDetail = listCourseDetail;
    }

    public List<ExamSubject> getListExamSubject() {
        return listExamSubject;
    }

    public void setListExamSubject(List<ExamSubject> listExamSubject) {
        this.listExamSubject = listExamSubject;
    }

    public List<StudentDetail> getListStudentDetail() {
        return listStudentDetail;
    }

    public void setListStudentDetail(List<StudentDetail> listStudentDetail) {
        this.listStudentDetail = listStudentDetail;
    }

    public void fillToCourseTable() {
        modelCourseTable.setRowCount(0);
        for (Course c : listCourse) {
            Object[] row = new Object[] {
                    c.getCourseID(), c.getName()
            };
            modelCourseTable.addRow(row);
        }
    }

    public void editCourse(String courseID, String courseName) {
        for (Course c : listCourse) {
            if (c.getCourseID().equals(courseID)) {
                c.setCourseID(courseID);
                c.setName(courseName);
                break;
            }
        }
    }

    public void deleteCourse(String courseID) {
        for (Course c : listCourse) {
            if (c.getCourseID().equals(courseID)) {
                listCourse.remove(c);
                break;
            }
        }
    }

    public void addTeacher(String username, String password, String fullName, String DOB, Boolean gender, String email, String position) {
        Teacher teacher = new Teacher(username, password, fullName, DOB, gender, email, position);
        listTeacher.add(teacher);
    }

    public void fillToTeacherTable() {
        modelTeacherTable.setRowCount(0);
        for (Teacher t : listTeacher) {
            Object[] rowTeacher = new Object[] {
                    t.getUsername(),
                    t.getPass(),
                    t.getFullName(),
                    XUtils.convertDatetoString(t.getBirthDate()),
                    t.getGender() ? "male" : "female",
                    t.getEmail(),
                    t.getPosition()
            };
            modelTeacherTable.addRow(rowTeacher);
        }
    }

    public void editTeacher(String username, String password, String fullName, String DOB, Boolean gender, String email, String position) {
        for (Teacher t : listTeacher) {
            if (t.getUsername().equals(username)) {
                t.setPass(password);
                t.setFullName(fullName);
                t.setBirthDate(DOB);
                t.setGender(gender);
                t.setEmail(email);
                t.setPosition(position);
                break;
            }
        }
    }

    public void deleteTeacher(String username) {
        for (Teacher t : listTeacher) {
            if (t.getUsername().equals(username)) {
                listTeacher.remove(t);
                break;
            }
        }
    }

    public void addCourseDetail(String chooseUsername, String chooseCourse, String courseFrom, String courseTo) {
        for (Teacher teacher : listTeacher) {
            if (teacher.getUsername().equals(chooseUsername)) {
                tempTeacher = teacher;
            }
        }
        for (Course course : listCourse) {
            if (course.getName().equals(chooseCourse)) {
                tempCourse = course;
            }
        }
        CourseDetail courseDetail = new CourseDetail(tempTeacher, tempCourse, courseFrom, courseTo);
        listCourseDetail.add(courseDetail);
    }

    public void fillToCourseDetailTable() {
        modelCourseDetailTable.setRowCount(0);
        try {
            for (CourseDetail cd : listCourseDetail) {
                Object[] rowCourseDetail = new Object[] {
                        cd.getTeacher().getUsername(),
                        cd.getTeacher().getFullName(),
                        cd.getCourse().getCourseID(),
                        cd.getCourse().getName(),
                        XUtils.convertDatetoString(cd.getCourseFrom()),
                        XUtils.convertDatetoString(cd.getCourseTo())
                };
                modelCourseDetailTable.addRow(rowCourseDetail);
            }
        } catch (NullPointerException nullPointerException) {
            //System.err.println(nullPointerException);
        }
    }

    public void editCourseDetail(String chooseUsername, String chooseCourse, String courseFrom, String courseTo) {
        for (Teacher teacher : listTeacher) {
            if (teacher.getUsername().equals(chooseUsername)) {
                tempTeacher = teacher;
            }
        }
        for (Course course : listCourse) {
            if (course.getName().equals(chooseCourse)) {
                tempCourse = course;
            }
        }
        for (CourseDetail cd : listCourseDetail) {
            if (cd.getTeacher().getUsername().equals(chooseUsername) && cd.getCourse().getName().equals(chooseCourse)) {
                cd.setCourseFrom(courseFrom);
                cd.setCourseTo(courseTo);
                break;
            }
        }
    }

    public void deleteCourseDetail(String chooseUsername, String chooseCourse) {
        for (CourseDetail cd : listCourseDetail) {
            if (cd.getTeacher().getUsername().equals(chooseUsername) && cd.getCourse().getName().equals(chooseCourse)) {
                listCourseDetail.remove(cd);
                break;
            }
        }
    }

    public void deleteCourseInCDetail(String courseID) {
        try {
            listCourseDetail.removeIf(cd -> cd.getCourse().getCourseID().equals(courseID));
        } catch (NullPointerException nullPointerException) {
            //System.err.println(nullPointerException);
        }
    }

    public void deleteCourseInExSubject(String courseID) {
        try {
            listExamSubject.removeIf(es -> es.getCourse().getCourseID().equals(courseID));
        } catch (NullPointerException nullPointerException) {
            //System.err.println(nullPointerException);
        }
    }

    public void deleteCourseInStDetail(String courseID) {
        try {
            listStudentDetail.removeIf(sd -> sd.getCourse().getCourseID().equals(courseID));
        } catch (NullPointerException nullPointerException) {
            //System.err.println(nullPointerException);
        }
    }

    public void deleteTeacherInCDetail(String username) {
        try {
            listCourseDetail.removeIf(cd -> cd.getTeacher().getUsername().equals(username));
        } catch (NullPointerException nullPointerException) {
            //System.err.println(nullPointerException);
        }
    }
}
