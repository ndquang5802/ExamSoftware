package com.assignment.testing;

import com.assignment.model.*;
import com.lib.XFile;
import com.lib.XUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TeacherTest {
    String fileCoursePath = "src/com/assignment/file/course.dat";
    List<Course> listCourse;
    String fileStudentPath = "src/com/assignment/file/student.dat";
    List<Student> listStudent;
    String fileCourseDetailPath = "src/com/assignment/file/courseDetail.dat";
    List<CourseDetail> listCourseDetail;
    String fileStudentDetailPath = "src/com/assignment/file/studentDetail.dat";
    List<StudentDetail> listStudentDetail;
    String fileExamSubjectPath = "src/com/assignment/file/examSubject.dat";
    List<ExamSubject> listExamSubject;
    String fileJavaQuestion = "src/com/assignment/file/JavaQuestion.json";
    String fileQuesBankPath = "src/com/assignment/file/questionBank.dat";
    List<QuestionBank> listQuestionBanks;

    @BeforeEach
    public void setUp() {
        //Arrange
        listCourse = (List<Course>) XFile.readObject(fileCoursePath);
        listStudent = (List<Student>) XFile.readObject(fileStudentPath);
        listStudentDetail = (List<StudentDetail>) XFile.readObject(fileStudentDetailPath);
        listExamSubject = (List<ExamSubject>) XFile.readObject(fileExamSubjectPath);
        listCourseDetail = (List<CourseDetail>) XFile.readObject(fileCourseDetailPath);
        listQuestionBanks = (List<QuestionBank>) XFile.readObject(fileQuesBankPath);
    }

    @Test
    public void testAddStudent(){
        int s = listStudent.size();
        Student st = new Student("tronghb", "12345", "Huynh Bao Trong", "2002-01-01", true, "tronghb@fpt.edu.vn", "Parent name");
        listStudent.add(st);
        int expected = s + 1;
        Assertions.assertEquals(expected, listStudent.size());
    }

    @Test
    public void testUpdateStudent(){
        Student st = null;
        for (Student student : listStudent) {
            if (student.getUsername().equals("quangnd")) {
                st = student;
            }
        }
        st.setBirthDate("2002-01-01");
        String expected = XUtils.convertDatetoString(st.getBirthDate());
        Assertions.assertEquals(expected, "2002-01-01");
    }

    @Test
    public void testDeleteStudent(){
        int s = listStudent.size();
        listStudent.removeIf(student -> student.getUsername().equals("quangnd"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listStudent.size());
    }

    @Test
    public void testAddStudentDetail(){
        int s = listStudentDetail.size();
        StudentDetail sd = new StudentDetail(listStudent.get(0), listCourse.get(0), 0.0);
        listStudentDetail.add(sd);
        int expected = s + 1;
        Assertions.assertEquals(expected, listStudentDetail.size());
    }

    @Test
    public void testUpdateStudentDetail(){
        StudentDetail sd = null;
        for (StudentDetail studentDetail : listStudentDetail) {
            if (studentDetail.getStudent().getUsername().equals("quangnd") && studentDetail.getCourse().getCourseID().equals("PROG191")) {
                sd = studentDetail;
            }
        }
        sd.setScore(9.5);
        double expected = sd.getScore();
        Assertions.assertEquals(expected, 9.5);
    }

    @Test
    public void testDeleteStudentDetail(){
        int s = listStudentDetail.size();
        listStudentDetail.removeIf(studentDetail -> studentDetail.getStudent().getUsername().equals("quangnd") && studentDetail.getCourse().getCourseID().equals("PROG191"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listStudentDetail.size());
    }

    @Test
    public void testAddExamSubject() {
        List<QuestionBank> questionBanks = (List<QuestionBank>) XFile.readJSON(fileJavaQuestion);
        int s = listExamSubject.size();
        ExamSubject es = new ExamSubject(listCourse.get(0), "JavaQuestion.json", questionBanks, "2022-09-04");
        listExamSubject.add(es);
        int expected = s + 1;
        Assertions.assertEquals(expected, listExamSubject.size());
    }

    @Test
    public void testUpdateExamSubject(){
        ExamSubject es = null;
        for (ExamSubject examSubject : listExamSubject) {
            if (examSubject.getCourse().getCourseID().equals("PROG191") && examSubject.getExamName().equals("JavaQuestion.json")) {
                es = examSubject;
            }
        }
        es.setExamDate("2022-09-04");
        String expected = XUtils.convertDatetoString(es.getExamDate());
        Assertions.assertEquals(expected, "2022-09-04");
    }

    @Test
    public void testDeleteExamSubject(){
        int s = listExamSubject.size();
        listExamSubject.removeIf(examSubject -> examSubject.getCourse().getCourseID().equals("PROG191") && examSubject.getExamName().equals("JavaQuestion.json"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listExamSubject.size());
    }

    @Test
    public void testAddQuesBank(){
        int s = listQuestionBanks.size();
        QuestionBank qb = new QuestionBank(1, "2 + 2 = ?", "1", "2", "3", "4", "D");
        listQuestionBanks.add(qb);
        int expected = s + 1;
        Assertions.assertEquals(expected, listQuestionBanks.size());
    }

    @Test
    public void testUpdateQuesBank(){
        QuestionBank qb = null;
        for (QuestionBank questionBank : listQuestionBanks) {
            if (questionBank.getQuesNo() == 1) {
                qb = questionBank;
            }
        }
        qb.setCorrectAnswer("A");
        String expected = qb.getCorrectAnswer();
        Assertions.assertEquals(expected, "A");
    }

    @Test
    public void testDeleteQuesBank(){
        int s = listQuestionBanks.size();
        listQuestionBanks.removeIf(questionBank -> questionBank.getQuesNo() == 1);
        int expected = s - 1;
        Assertions.assertEquals(expected, listQuestionBanks.size());
    }

    @Test
    public void testDeleteAllQuesBank(){
        int s = listQuestionBanks.size();
        listQuestionBanks.removeAll(listQuestionBanks);
        int expected = s - 1;
        Assertions.assertEquals(expected, listQuestionBanks.size());
    }
}
