package com.assignment.controller;

import com.assignment.model.*;
import com.lib.XUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {
    DefaultTableModel modelStudentTable;
    List<Student> listStudent;
    DefaultComboBoxModel modelComboStudent;
    List<CourseDetail> listCourseDetail;
    DefaultComboBoxModel modelComboCourse;
    DefaultComboBoxModel modelComboCourseExamSubject;
    List<StudentDetail> listStudentDetail;
    DefaultTableModel modelStudentDetailTable;
    DefaultTableModel modelQuesBankTable;
    List<QuestionBank> listQuesBank;
    DefaultTableModel modelExamSubjectTable;
    List<ExamSubject> listExamSubject;
    Student tempStudent;
    Course tempCourse;
    Course tempExamCourse;

    public TeacherController(DefaultTableModel modelStudentTable,
                             List<Student> listStudent,
                             DefaultComboBoxModel modelComboStudent,
                             List<CourseDetail> listCourseDetail,
                             DefaultComboBoxModel modelComboCourse,
                             DefaultComboBoxModel modelComboCourseExamSubject,
                             List<StudentDetail> listStudentDetail,
                             DefaultTableModel modelStudentDetailTable,
                             DefaultTableModel modelQuesBankTable,
                             List<QuestionBank> listQuesBank,
                             DefaultTableModel modelExamSubjectTable,
                             List<ExamSubject> listExamSubject) {
        this.modelStudentTable = modelStudentTable;
        this.listStudent = listStudent;
        this.modelComboStudent = modelComboStudent;
        this.listCourseDetail = listCourseDetail;
        this.modelComboCourse = modelComboCourse;
        this.modelComboCourseExamSubject = modelComboCourseExamSubject;
        this.listStudentDetail = listStudentDetail;
        this.modelStudentDetailTable = modelStudentDetailTable;
        this.modelQuesBankTable = modelQuesBankTable;
        this.listQuesBank = listQuesBank;
        this.modelExamSubjectTable = modelExamSubjectTable;
        this.listExamSubject = listExamSubject;

        if (this.listStudent == null || this.listStudent.size() == 0) {
            this.listStudent = new ArrayList<>();
            this.listStudent.add(new Student("quangnd", "12345", "Nguyen Duy Quang", "2002-08-05", true, "quangnd@fpt.edu.vn", "Parent's name"));
        } else {
            for (Student student: listStudent) {
                this.modelComboStudent.addElement(student.getUsername());
            }
        }

        if (this.listStudentDetail == null || this.listStudentDetail.size() == 0) {
            this.listStudentDetail = new ArrayList<>();
        }

        if (this.listQuesBank == null || this.listQuesBank.size() == 0) {
            this.listQuesBank = new ArrayList<>();
        }

        if (this.listExamSubject == null || this.listExamSubject.size() == 0) {
            this.listExamSubject = new ArrayList<>();
        }
    }

    public List<Student> getListStudent() {
        return listStudent;
    }

    public void setListStudent(List<Student> listStudent) {
        this.listStudent = listStudent;
    }

    public DefaultComboBoxModel getModelComboStudent() {
        return modelComboStudent;
    }

    public void setModelComboStudent(DefaultComboBoxModel modelComboStudent) {
        this.modelComboStudent = modelComboStudent;
    }

    public List<CourseDetail> getListCourseDetail() {
        return listCourseDetail;
    }

    public void setListCourseDetail(List<CourseDetail> listCourseDetail) {
        this.listCourseDetail = listCourseDetail;
    }

    public DefaultComboBoxModel getModelComboCourse() {
        return modelComboCourse;
    }

    public void setModelComboCourse(DefaultComboBoxModel modelComboCourse) {
        this.modelComboCourse = modelComboCourse;
    }

    public List<StudentDetail> getListStudentDetail() {
        return listStudentDetail;
    }

    public void setListStudentDetail(List<StudentDetail> listStudentDetail) {
        this.listStudentDetail = listStudentDetail;
    }

    public DefaultComboBoxModel getModelComboCourseExamSubject() {
        return modelComboCourseExamSubject;
    }

    public void setModelComboCourseExamSubject(DefaultComboBoxModel modelComboCourseExamSubject) {
        this.modelComboCourseExamSubject = modelComboCourseExamSubject;
    }

    public List<QuestionBank> getListQuesBank() {
        return listQuesBank;
    }

    public void setListQuesBank(List<QuestionBank> listQuesBank) {
        this.listQuesBank = listQuesBank;
    }

    public List<ExamSubject> getListExamSubject() {
        return listExamSubject;
    }

    public void setListExamSubject(List<ExamSubject> listExamSubject) {
        this.listExamSubject = listExamSubject;
    }

    public void fillToStudentTable() {
        modelStudentTable.setRowCount(0);
        for (Student student : listStudent) {
            Object[] rowStudent = new Object[] {
                    student.getUsername(),
                    student.getPass(),
                    student.getFullName(),
                    XUtils.convertDatetoString(student.getBirthDate()),
                    student.getGender() ? "male" : "female",
                    student.getEmail(),
                    student.getParents()
            };
            modelStudentTable.addRow(rowStudent);
        }
    }

    public void addTeacher(String username, String password, String fullName, String DOB, Boolean gender, String email, String parent) {
        Student student = new Student(username, password, fullName, DOB, gender, email, parent);
        listStudent.add(student);
    }

    public void editTeacher(String username, String password, String fullName, String DOB, Boolean gender, String email, String parent) {
        for (Student student : listStudent) {
            if (student.getUsername().equals(username)) {
                student.setPass(password);
                student.setFullName(fullName);
                student.setBirthDate(DOB);
                student.setGender(gender);
                student.setEmail(email);
                student.setParents(parent);
                break;
            }
        }
    }

    public void deleteStudent(String username) {
        for (Student student : listStudent) {
            if (student.getUsername().equals(username)) {
                listStudent.remove(student);
                break;
            }
        }
    }

    public void fillToStudentDetailTable() {
        modelStudentDetailTable.setRowCount(0);
        for (StudentDetail studentDetail : listStudentDetail) {
            Object[] rowStudentDetail = new Object[] {
                    studentDetail.getStudent().getUsername(),
                    studentDetail.getStudent().getFullName(),
                    studentDetail.getCourse().getCourseID(),
                    studentDetail.getCourse().getName(),
                    studentDetail.getScore()
            };
            modelStudentDetailTable.addRow(rowStudentDetail);
        }
    }

    public void addStudentDetail(String chooseStudent, String chooseStudentCourse, Double score) {
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
        StudentDetail studentDetail = new StudentDetail(tempStudent, tempCourse, score);
        listStudentDetail.add(studentDetail);
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

    public void deleteStudentDetail(String chooseStudent, String chooseStudentCourse) {
        for (StudentDetail sd : listStudentDetail) {
            if (sd.getStudent().getUsername().equals(chooseStudent) && sd.getCourse().getName().equals(chooseStudentCourse)) {
                listStudentDetail.remove(sd);
                break;
            }
        }
    }

    public void fillToQuesBankTable() {
        modelQuesBankTable.setRowCount(0);
        for (QuestionBank questionBank : listQuesBank) {
            Object[] rowQuesBank = new Object[] {
                    questionBank.getQuesNo(),
                    questionBank.getQuestion(),
                    questionBank.getAnswer1(),
                    questionBank.getAnswer2(),
                    questionBank.getAnswer3(),
                    questionBank.getAnswer4(),
                    questionBank.getCorrectAnswer()
            };
            modelQuesBankTable.addRow(rowQuesBank);
        }
    }

    public void addQuesBank(int no, String question, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        QuestionBank questionBank = new QuestionBank(no, question, ans1, ans2, ans3, ans4, correctAns);
        listQuesBank.add(questionBank);
    }

    public void editQuesBank(int no, String question, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        for (QuestionBank questionBank : listQuesBank) {
            if (questionBank.getQuesNo() == no) {
                questionBank.setQuestion(question);
                questionBank.setAnswer1(ans1);
                questionBank.setAnswer2(ans2);
                questionBank.setAnswer3(ans3);
                questionBank.setAnswer4(ans4);
                questionBank.setCorrectAnswer(correctAns);
                break;
            }
        }
    }

    public void deleteQuesBank(int no) {
        for (QuestionBank questionBank : listQuesBank) {
            if (questionBank.getQuesNo() == no) {
                listQuesBank.remove(questionBank);
                break;
            }
        }
    }

    public void fillToExamSubjectTable() {
        modelExamSubjectTable.setRowCount(0);
        for (ExamSubject examSubject : listExamSubject) {
            Object[] rowExamSubject = new Object[] {
                    examSubject.getCourse().getCourseID(),
                    examSubject.getCourse().getName(),
                    examSubject.getExamName(),
                    XUtils.convertDatetoString(examSubject.getExamDate())
            };
            modelExamSubjectTable.addRow(rowExamSubject);
        }
    }

    public void addExamSubject(Object chooseCourse, String fileName, List<QuestionBank> questionBanks, String examDate) {
        for (CourseDetail courseDetail : listCourseDetail) {
            if (courseDetail.getCourse().getName().equals(chooseCourse)) {
                tempExamCourse = courseDetail.getCourse();
            }
        }
        ExamSubject examSubject = new ExamSubject(tempExamCourse, fileName, questionBanks, examDate);
        listExamSubject.add(examSubject);
    }

    public void editExamSubject(Object selectedItem, String fileName, String examDate) {
        for (ExamSubject examSubject : listExamSubject) {
            if (examSubject.getCourse().getName().equals(selectedItem) && examSubject.getExamName().equals(fileName)) {
                examSubject.setExamDate(examDate);
                break;
            }
        }
    }

    public void deleteExamSubject(Object selectedItem, String fileName) {
        for (ExamSubject examSubject : listExamSubject) {
            if (examSubject.getCourse().getName().equals(selectedItem) && examSubject.getExamName().equals(fileName)) {
                listExamSubject.remove(examSubject);
                break;
            }
        }
    }

    public void deleteAllQuesBank() {
        //clear all in model Table
        modelQuesBankTable.setRowCount(0);
        //clear all in list
        listQuesBank.removeAll(listQuesBank);
    }

    public void deleteStudentInStDetail(String username) {
        listStudentDetail.removeIf(sd -> sd.getStudent().getUsername().equals(username));
    }
}
