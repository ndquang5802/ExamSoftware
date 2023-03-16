package com.assignment.view;

import com.assignment.controller.AltTabStopper;
import com.assignment.model.Account;
import com.assignment.model.Student;
import com.assignment.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.assignment.MVCPattern.Main.accounts;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton exitButton;
    private JButton resetButton;
    private JButton loginButton;
    private JPanel loginPanel;
    private JTextField txtError;

    public Login(String title, String user) throws HeadlessException {
        super(title);
        this.setVisible(true);
        this.setResizable(false);
        this.setContentPane(loginPanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        Image icon = Toolkit.getDefaultToolkit().getImage("src/com/img/logo.jpg");
        this.setIconImage(icon);

        if (user != null) {
            txtUsername.setText(user);
        }
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUsername.setText(null);
                txtPassword.setText(null);
                txtError.setText(null);
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitProgram();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Account account : accounts) {
                    if (txtUsername.getText().equals(account.getUsername()) && txtPassword.getText().equals(account.getPass())) {
                        if (account instanceof Student) {
                            Login.this.setVisible(false);
                            JFrame mainFrame = new MainGUI("Online Examination", txtUsername.getText());
                            mainFrame.setVisible(true);
                            mainFrame.setIconImage(icon);
                        } else if (account instanceof Teacher){
                            Login.this.setVisible(false);
                            JFrame teacherFrame = new TeacherGUI("Student & Exam Management", txtUsername.getText());
                            teacherFrame.setVisible(true);
                            teacherFrame.setIconImage(icon);
                        } else {
                            Login.this.setVisible(false);
                            JFrame adminFrame = new AdminGUI("Course & Teacher Management", txtUsername.getText());
                            adminFrame.setVisible(true);
                            adminFrame.setIconImage(icon);
                        }
                    } else {
                        txtError.setText("Invalid username or password");
                    }
                }
            }
        });
    }

    private void exitProgram() {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to exit", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION){
            System.exit(1);
        }
    }
}
