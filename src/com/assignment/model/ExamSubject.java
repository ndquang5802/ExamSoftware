package com.assignment.model;

import com.lib.XUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExamSubject implements Serializable {
    private Course course;
    private String examName;
    private List<QuestionBank> questionBanks;
    private Date examDate;

    public ExamSubject(Course course, String examName, List<QuestionBank> questionBanks, String examDate) {
        this.course = course;
        this.examName = examName;
        this.questionBanks = questionBanks;
        this.examDate = XUtils.convertStringtoDate(examDate);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<QuestionBank> getQuestionBanks() {
        return questionBanks;
    }

    public void setQuestionBanks(List<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = XUtils.convertStringtoDate(examDate);
    }
}
