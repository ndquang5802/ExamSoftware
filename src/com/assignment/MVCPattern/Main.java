package com.assignment.MVCPattern;

import com.assignment.model.Account;
import com.assignment.model.Student;
import com.assignment.model.Teacher;
import com.assignment.view.Login;
import com.lib.XFile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Account> accounts = new ArrayList<>();
    static String fileTeacherPath = "src/com/assignment/file/teacher.dat";
    static String fileStudentPath = "src/com/assignment/file/student.dat";


    public static void main(String[] args) {
        JFrame frame = new Login("Login",null);
        frame.setLocationRelativeTo(null);

        accounts.add(new Account());

        List<Teacher> teachers = (List<Teacher>) XFile.readObject(fileTeacherPath);
        if (teachers != null) {
            accounts.addAll(teachers);
        }

        List<Student> students = (List<Student>) XFile.readObject(fileStudentPath);
        if (teachers != null) {
            accounts.addAll(students);
        }
    }
}
