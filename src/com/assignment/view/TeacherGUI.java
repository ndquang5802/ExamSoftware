package com.assignment.view;

import com.assignment.controller.DataLabelFormat;
import com.assignment.controller.TeacherController;
import com.assignment.model.*;
import com.lib.XFile;
import com.lib.XUtils;
import netscape.javascript.JSException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherGUI extends JFrame {
    private JPanel teacherPanel;
    private JTabbedPane tabbedPaneTeacher;
    private JTextField txtUsernameStudent;
    private JTextField txtPasswordStudent;
    private JTextField txtFullnameStudent;
    private JRadioButton rdMaleStudent;
    private JRadioButton rdFemaleStudent;
    private JTextField txtEmailStudent;
    private JDatePickerImpl JDatePickerStudent;
    private JTable tbStudent;
    private JButton logoutButton;
    private JButton btnNewStudent;
    private JButton btnAddStudent;
    private JButton btnUpdate;
    private JButton btnDeleteStudent;
    private JLabel lbNameTeacher;
    private JTextField txtParentStudent;
    private JComboBox cbStudent;
    private JComboBox cbCourseStudent;
    private JTextField txtScore;
    private JButton btnNewStudentDetail;
    private JButton btnAddStudentDetail;
    private JButton btnUpdateStudentDetail;
    private JButton btnDeleteStudentDetail;
    private JTable tbStudentDetail;
    private JTextField txtQuesNo;
    private JTextField txtAns1;
    private JTextField txtAns2;
    private JTextField txtAns3;
    private JTextField txtAns4;
    private JButton btnNewQuesBank;
    private JButton btnAddQuesBank;
    private JButton btnUpdateQuesBank;
    private JButton btnDeleteQuesBank;
    private JButton btnDeleteAllQuesBank;
    private JTable tbQuesBank;
    private JLabel lbErrorUsernameStudent;
    private JLabel lbErrorPasswordStudent;
    private JLabel lbErrorFullnameStudent;
    private JLabel lbErrorDOBStudent;
    private JLabel lbErrorGenderStudent;
    private JLabel lbErrorEmailStudent;
    private JLabel lbErrorParentNameStudent;
    private JLabel lbErrorQuesNo;
    private JLabel lbErrorAns1;
    private JLabel lbErrorAns2;
    private JLabel lbErrorAns3;
    private JLabel lbErrorAns4;
    private JLabel lbErrorCorrectAns;
    private JButton btnSaveQuesBank;
    private JRadioButton rdAns1;
    private JRadioButton rdAns2;
    private JRadioButton rdAns3;
    private JRadioButton rdAns4;
    private JComboBox cbCourseExamSubject;
    private JButton btnUploadExamSubject;
    private JTextField txtExamSubject;
    private JButton btnNewExamSubject;
    private JButton btnAddExamSubject;
    private JButton btnUpdateExamSubject;
    private JButton btnDeleteExamSubject;
    private JTable tbExamSubject;
    private JDatePickerImpl JDatePickerExamDate;
    private JLabel lbErrorScore;
    private JLabel lbErrorChooseStudentCourse;
    private JLabel lbErrorChooseStudent;
    private JTextField txtQuestion;
    private JLabel lbErrorQuestion;
    private JLabel lbErrorChooseCourseExamSubject;
    private JLabel lbErrorChooseExamFile;
    private JLabel lbErrorChooseExamDate;
    private JComboBox cbSeacherStudent;
    private JTextField txtSeacherStudent;
    private JButton btnSeacherStudent;
    private JLabel lbErrorSeacherStudent;
    private JComboBox cbSeachStudentDetail;
    private JTextField txtSeachStudentDetail;
    private JButton btnSeachStudentDetail;
    private JLabel lbErrorSeachStudentDetail;
    private JTextField txtSearchExamSubject;
    private JLabel lbErrorSearchExamSubject;
    private JButton btnSearchExamSubject;
    TeacherController teacherController;
    String fileCourseDetailPath = "src/com/assignment/file/courseDetail.dat";
    String fileStudentPath = "src/com/assignment/file/student.dat";
    String fileStudentDetailPath = "src/com/assignment/file/studentDetail.dat";
    String fileQuesBankPath = "src/com/assignment/file/questionBank.dat";
    String fileExamSubjectPath = "src/com/assignment/file/examSubject.dat";
    int rowStudent = -1;
    int rowStudentDetail = -1;
    int rowQuesBank = -1;
    int rowExamSubject = -1;
    String chooseStudent;
    String chooseStudentCourse;
    String examFilePath;

    public TeacherGUI(String title, String user) throws HeadlessException {
        super(title);
        this.setResizable(false);
        this.setContentPane(teacherPanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        lbNameTeacher.setText("Welcome " + user);

        tbStudent.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[] {
                    "Username", "Password", "Full name", "DOB", "Gender", "Email", "Parent's name"
                }
        ));

        tbStudentDetail.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[] {
                        "Username", "Full name", "Course ID", "Course Name", "Score"
                }
        ));

        tbQuesBank.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[] {
                        "No", "Question", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "Correct Answer"
                }
        ));

        tbExamSubject.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[] {
                        "Course ID", "Course Name", "Exam name", "Exam date"
                }
        ));

        teacherController = new TeacherController((DefaultTableModel) tbStudent.getModel(),
                                                    (List<Student>) XFile.readObject(fileStudentPath),
                                                    (DefaultComboBoxModel) cbStudent.getModel(),
                                                    (List<CourseDetail>) XFile.readObject(fileCourseDetailPath),
                                                    (DefaultComboBoxModel) cbCourseStudent.getModel(),
                                                    (DefaultComboBoxModel) cbCourseExamSubject.getModel(),
                                                    (List<StudentDetail>) XFile.readObject(fileStudentDetailPath),
                                                    (DefaultTableModel) tbStudentDetail.getModel(),
                                                    (DefaultTableModel) tbQuesBank.getModel(),
                                                    (List<QuestionBank>) XFile.readObject(fileQuesBankPath),
                                                    (DefaultTableModel) tbExamSubject.getModel(),
                                                    (List<ExamSubject>) XFile.readObject(fileExamSubjectPath));

        cbStudent.setModel(teacherController.getModelComboStudent());
        cbCourseStudent.setModel(teacherController.getModelComboCourse());
        cbCourseExamSubject.setModel(teacherController.getModelComboCourseExamSubject());

        teacherController.fillToStudentTable();
        teacherController.fillToStudentDetailTable();
        teacherController.fillToQuesBankTable();
        teacherController.fillToExamSubjectTable();

        for (CourseDetail courseDetail: teacherController.getListCourseDetail()) {
            if (courseDetail.getTeacher().getUsername().equals(user)){
                teacherController.getModelComboCourse().addElement(courseDetail.getCourse().getName());
            }

        }

        for (CourseDetail courseDetail: teacherController.getListCourseDetail()) {
            if (courseDetail.getTeacher().getUsername().equals(user)){
                teacherController.getModelComboCourseExamSubject().addElement(courseDetail.getCourse().getName());
            }

        }

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                logoutTeacher(user);

            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logoutTeacher(user);
            }
        });
        btnNewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newStudent();
            }
        });
        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
        tbStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbStudentClick();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }
        });
        btnDeleteStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowStudent == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the student");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        cbStudent.removeItem(txtUsernameStudent.getText());
                        //remove
                        teacherController.deleteStudent(txtUsernameStudent.getText());
                        //fill to table
                        teacherController.fillToStudentTable();
                        //save to file
                        XFile.writeObject(fileStudentPath, teacherController.getListStudent());

                        teacherController.deleteStudentInStDetail(txtUsernameStudent.getText());
                        teacherController.fillToStudentDetailTable();
                        XFile.writeObject(fileStudentDetailPath, teacherController.getListStudentDetail());

                        newStudent();
                    }
                }
            }
        });
        btnNewStudentDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newStudentDetail();
            }
        });
        btnAddStudentDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudentDetail();
            }
        });
        cbStudent.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getStudentFromCombo();
            }
        });
        cbCourseStudent.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getStudentCourseFromCombo();
            }
        });
        btnUpdateStudentDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentDetail();
            }
        });
        tbStudentDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbStudentDetailClick();
            }
        });
        btnDeleteStudentDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowStudentDetail == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the student detail");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        //remove
                        teacherController.deleteStudentDetail(chooseStudent, chooseStudentCourse);
                        //fill to table
                        teacherController.fillToStudentDetailTable();
                        //save to file
                        XFile.writeObject(fileStudentDetailPath, teacherController.getListStudentDetail());
                        newStudentDetail();
                    }
                }
            }
        });
        btnNewQuesBank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newQuesBank();
            }
        });
        btnAddQuesBank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveQuesBank();
            }
        });
        tbQuesBank.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbQuesBankClick();
            }
        });
        btnUpdateQuesBank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuesBank();
            }
        });
        btnDeleteQuesBank.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowQuesBank == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the question");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        //remove
                        teacherController.deleteQuesBank(Integer.parseInt(txtQuesNo.getText()));
                        //fill to table
                        teacherController.fillToQuesBankTable();
                        //save to file
                        XFile.writeObject(fileQuesBankPath, teacherController.getListQuesBank());
                        newQuesBank();
                    }
                }
            }
        });
        btnSaveQuesBank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int result = fc.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String fpath = fc.getSelectedFile().getPath();
                    JSONArray jsonArray = new JSONArray();
                    try {
                        for (QuestionBank quesBank : teacherController.getListQuesBank()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", quesBank.getQuesNo());
                            jsonObject.put("question", quesBank.getQuestion());
                            jsonObject.put("option 1", quesBank.getAnswer1());
                            jsonObject.put("option 2", quesBank.getAnswer2());
                            jsonObject.put("option 3", quesBank.getAnswer3());
                            jsonObject.put("option 4", quesBank.getAnswer4());
                            jsonObject.put("correct", quesBank.getCorrectAnswer());
                            jsonArray.add(jsonObject);
                        }
                    } catch (JSException i) {
                        i.printStackTrace();
                    }
                    XFile.writeJSON(fpath, jsonArray);
                }
            }
        });
        btnNewExamSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newExamSubject();
            }
        });
        btnAddExamSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExamSubject();
            }
        });
        btnUploadExamSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadExamFile();
            }
        });
        tbExamSubject.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbExamSubjectClick();
            }
        });
        btnUpdateExamSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateExamSubject();
            }
        });
        btnDeleteExamSubject.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowExamSubject == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the exam subject");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        //remove
                        teacherController.deleteExamSubject(cbCourseExamSubject.getSelectedItem(), txtExamSubject.getText());
                        //fill to table
                        teacherController.fillToExamSubjectTable();
                        //save to file
                        XFile.writeObject(fileExamSubjectPath, teacherController.getListExamSubject());
                        newExamSubject();
                    }
                }
            }
        });
        btnDeleteAllQuesBank.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove all", "Remove",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION){
                    deleteAllQuesBank();
                }
            }
        });
        btnSeacherStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });
        tabbedPaneTeacher.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbStudent.clearSelection();
                tbStudentDetail.clearSelection();
                tbExamSubject.clearSelection();
            }
        });
        btnSeachStudentDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudentDetail();
            }
        });
        btnSearchExamSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchExamSubject();
            }
        });
    }

    private void searchExamSubject() {
        tbExamSubject.clearSelection();
        lbErrorSearchExamSubject.setVisible(txtSearchExamSubject.getText().equals(""));

        boolean flag = false;
        for(int i = 0; i < tbExamSubject.getRowCount(); i++){
            if(tbExamSubject.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSearchExamSubject.getText())){
                tbExamSubject.addRowSelectionInterval(i, i);
                flag = true;
            }
        }

        if (!flag) {
            lbErrorSearchExamSubject.setVisible(true);
        }
    }

    private void searchStudentDetail() {
        tbStudentDetail.clearSelection();
        lbErrorSeachStudentDetail.setVisible(cbSeachStudentDetail.getSelectedIndex() == 0 || txtSeachStudentDetail.getText().equals(""));

        boolean flag = false;
        if (cbSeachStudentDetail.getSelectedItem().toString().equalsIgnoreCase("Username")) {
            for(int i = 0; i < tbStudentDetail.getRowCount(); i++){
                if(tbStudentDetail.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSeachStudentDetail.getText())){
                    tbStudentDetail.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < tbStudentDetail.getRowCount(); i++){//For each row
                if(tbStudentDetail.getModel().getValueAt(i, 2).toString().equalsIgnoreCase(txtSeachStudentDetail.getText())){
                    tbStudentDetail.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        }
        if (!flag) {
            lbErrorSeachStudentDetail.setVisible(true);
        }
    }

    private void searchStudent() {
        tbStudent.clearSelection();
        lbErrorSeacherStudent.setVisible(cbSeacherStudent.getSelectedIndex() == 0 || txtSeacherStudent.getText().equals(""));

        boolean flag = false;
        if (cbSeacherStudent.getSelectedItem().toString().equalsIgnoreCase("Username")) {
            for(int i = 0; i < tbStudent.getRowCount(); i++){
                if(tbStudent.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSeacherStudent.getText())){
                    tbStudent.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < tbStudent.getRowCount(); i++){//For each row
                if(tbStudent.getModel().getValueAt(i, 2).toString().equalsIgnoreCase(txtSeacherStudent.getText())){
                    tbStudent.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        }
        if (!flag) {
            lbErrorSeacherStudent.setVisible(true);
        }
    }

    private void deleteAllQuesBank() {
        teacherController.deleteAllQuesBank();
        //save file
        XFile.writeObject(fileQuesBankPath, teacherController.getListQuesBank());
        //clear form
        newQuesBank();
    }

    private void updateExamSubject() {
        if (rowExamSubject == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the exam subject");
        } else {
            lbErrorChooseExamDate.setVisible(JDatePickerExamDate.getModel().getValue() == null ||
                    XUtils.convertDatetoString((Date) JDatePickerExamDate.getModel().getValue()).compareTo(LocalDate.now().toString()) < 0);

            if (!lbErrorChooseExamDate.isVisible()) {
                //edit List
                teacherController.editExamSubject(cbCourseExamSubject.getSelectedItem(),
                        txtExamSubject.getText(),
                        XUtils.convertDatetoString((Date) JDatePickerExamDate.getModel().getValue())
                );
                //fill to table
                teacherController.fillToExamSubjectTable();
                //save file
                XFile.writeObject(fileExamSubjectPath, teacherController.getListExamSubject());
                tbExamSubject.addRowSelectionInterval(rowExamSubject, rowExamSubject);
            }
        }

    }

    private void tbExamSubjectClick() {
        cbCourseExamSubject.setEnabled(false);
        txtExamSubject.setEditable(false);
        rowExamSubject = tbExamSubject.getSelectedRow();
        showExamSubjectDetail(rowExamSubject);
    }

    private void showExamSubjectDetail(int rowExamSubject) {
        String course = (String) tbExamSubject.getValueAt(rowExamSubject, 1);
        cbCourseExamSubject.setSelectedItem(course);

        String exam = (String) tbExamSubject.getValueAt(rowExamSubject, 2);
        txtExamSubject.setText(exam);

        String examDate = (String) tbExamSubject.getValueAt(rowExamSubject, 3);
        Calendar c = Calendar.getInstance();
        c.setTime(XUtils.convertStringtoDate(examDate));
        JDatePickerExamDate.getJFormattedTextField().setValue(c);
    }

    private void uploadExamFile() {
        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            examFilePath = fc.getSelectedFile().getPath();
            txtExamSubject.setText(fc.getSelectedFile().getName());
        }
    }

    private void saveExamSubject() {
        lbErrorChooseCourseExamSubject.setVisible(cbCourseExamSubject.getSelectedIndex() == 0);
        lbErrorChooseExamFile.setVisible(txtExamSubject.getText().equals(""));
        lbErrorChooseExamDate.setVisible(JDatePickerExamDate.getModel().getValue() == null ||
                XUtils.convertDatetoString((Date) JDatePickerExamDate.getModel().getValue()).compareTo(LocalDate.now().toString()) < 0);

        for (ExamSubject examSubject : teacherController.getListExamSubject()) {
            if (examSubject.getCourse().getName().equals(cbCourseExamSubject.getSelectedItem())) {
                lbErrorChooseCourseExamSubject.setVisible(true);
            }
            if (examSubject.getExamName().equals(txtExamSubject.getText())) {
                lbErrorChooseExamFile.setVisible(true);
            }
        }

        if (!lbErrorChooseCourseExamSubject.isVisible() &&
                !lbErrorChooseExamFile.isVisible() &&
                !lbErrorChooseExamDate.isVisible()) {
            List<QuestionBank> questionBanks = (List<QuestionBank>) XFile.readJSON(examFilePath);

            teacherController.addExamSubject(cbCourseExamSubject.getSelectedItem(),
                    txtExamSubject.getText(),
                    questionBanks,
                    XUtils.convertDatetoString((Date) JDatePickerExamDate.getModel().getValue())
            );
            //fill to Table
            teacherController.fillToExamSubjectTable();
            //save file
            XFile.writeObject(fileExamSubjectPath, teacherController.getListExamSubject());
            newExamSubject();
        }
    }

    private void newExamSubject() {
        cbCourseExamSubject.setEnabled(true);
        cbCourseExamSubject.setSelectedIndex(0);
        txtExamSubject.setText("");
        JDatePickerExamDate.getModel().setValue(null);
        lbErrorChooseCourseExamSubject.setVisible(false);
        lbErrorChooseExamFile.setVisible(false);
        lbErrorChooseExamDate.setVisible(false);
    }

    private void logoutTeacher(String user) {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure to logout", "Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION){
            JFrame frame = new Login("Login", user);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose();
        }
    }

    private void updateQuesBank() {
        if (rowQuesBank == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the question");
        } else {
            lbErrorQuesNo.setVisible(txtQuesNo.getText().equals("") || isCharacter(txtQuesNo.getText()) || isSpecial(txtQuesNo.getText()));
            lbErrorQuestion.setVisible(txtQuestion.getText().equals(""));
            lbErrorAns1.setVisible(txtAns1.getText().equals(""));
            lbErrorAns2.setVisible(txtAns2.getText().equals(""));
            lbErrorAns3.setVisible(txtAns3.getText().equals(""));
            lbErrorAns4.setVisible(txtAns4.getText().equals(""));
            lbErrorCorrectAns.setVisible(!rdAns1.isSelected() && !rdAns2.isSelected() && !rdAns3.isSelected() && !rdAns4.isSelected());

            for (QuestionBank questionBank : teacherController.getListQuesBank()) {
                try {
                    if (questionBank.getQuesNo() != Integer.parseInt(txtQuesNo.getText()) && questionBank.getQuestion().equals(txtQuestion.getText())) {
                        lbErrorQuestion.setVisible(true);
                    }
                } catch (Exception e) {
                    lbErrorQuesNo.setVisible(true);
                }
            }

            if (!lbErrorQuesNo.isVisible() &&
                    !lbErrorQuestion.isVisible() &&
                    !lbErrorAns1.isVisible() &&
                    !lbErrorAns2.isVisible() &&
                    !lbErrorAns3.isVisible() &&
                    !lbErrorAns4.isVisible() &&
                    !lbErrorCorrectAns.isVisible()) {
                String correctAns;
                if (rdAns1.isSelected()) {
                    correctAns = "A";
                } else if (rdAns2.isSelected()) {
                    correctAns = "B";
                } else if (rdAns3.isSelected()) {
                    correctAns = "C";
                } else {
                    correctAns = "D";
                }
                //edit List
                teacherController.editQuesBank(Integer.parseInt(txtQuesNo.getText()),
                        txtQuestion.getText(),
                        txtAns1.getText(),
                        txtAns2.getText(),
                        txtAns3.getText(),
                        txtAns4.getText(),
                        correctAns
                );
                //fill to table
                teacherController.fillToQuesBankTable();
                //save file
                XFile.writeObject(fileQuesBankPath, teacherController.getListQuesBank());
                tbQuesBank.addRowSelectionInterval(rowQuesBank, rowQuesBank);
            }
        }
    }

    private void tbQuesBankClick() {
        txtQuesNo.setEditable(false);
        rowQuesBank = tbQuesBank.getSelectedRow();
        showQuesBankDetail(rowQuesBank);
    }

    private void showQuesBankDetail(int rowQuesBank) {
        int quesNo = (Integer) tbQuesBank.getValueAt(rowQuesBank, 0);
        txtQuesNo.setText(String.valueOf(quesNo));

        String question = (String) tbQuesBank.getValueAt(rowQuesBank, 1);
        txtQuestion.setText(question);

        String ans1 = (String) tbQuesBank.getValueAt(rowQuesBank, 2);
        txtAns1.setText(ans1);

        String ans2 = (String) tbQuesBank.getValueAt(rowQuesBank, 3);
        txtAns2.setText(ans2);

        String ans3 = (String) tbQuesBank.getValueAt(rowQuesBank, 4);
        txtAns3.setText(ans3);

        String ans4 = (String) tbQuesBank.getValueAt(rowQuesBank, 5);
        txtAns4.setText(ans4);

        String correctAns = (String) tbQuesBank.getValueAt(rowQuesBank, 6);
        switch (correctAns) {
            case "A" -> rdAns1.setSelected(true);
            case "B" -> rdAns2.setSelected(true);
            case "C" -> rdAns3.setSelected(true);
            case "D" -> rdAns4.setSelected(true);
        }
    }

    private void saveQuesBank() {
        lbErrorQuesNo.setVisible(txtQuesNo.getText().equals("") || isCharacter(txtQuesNo.getText()) || isSpecial(txtQuesNo.getText()));
        lbErrorQuestion.setVisible(txtQuestion.getText().equals(""));
        lbErrorAns1.setVisible(txtAns1.getText().equals(""));
        lbErrorAns2.setVisible(txtAns2.getText().equals(""));
        lbErrorAns3.setVisible(txtAns3.getText().equals(""));
        lbErrorAns4.setVisible(txtAns4.getText().equals(""));
        lbErrorCorrectAns.setVisible(!rdAns1.isSelected() && !rdAns2.isSelected() && !rdAns3.isSelected() && !rdAns4.isSelected());

        for (QuestionBank questionBank : teacherController.getListQuesBank()) {
            try {
                if (questionBank.getQuesNo() == Integer.parseInt(txtQuesNo.getText())) {
                    lbErrorQuesNo.setVisible(true);
                }
                if (questionBank.getQuestion().equals(txtQuestion.getText())) {
                    lbErrorQuestion.setVisible(true);
                }
            } catch (Exception e) {
                lbErrorQuesNo.setVisible(true);
            }
        }

        if (!lbErrorQuesNo.isVisible() &&
                !lbErrorQuestion.isVisible() &&
                !lbErrorAns1.isVisible() &&
                !lbErrorAns2.isVisible() &&
                !lbErrorAns3.isVisible() &&
                !lbErrorAns4.isVisible() &&
                !lbErrorCorrectAns.isVisible()) {
            String correctAns;
            if (rdAns1.isSelected()) {
                correctAns = "A";
            } else if (rdAns2.isSelected()) {
                correctAns = "B";
            } else if (rdAns3.isSelected()) {
                correctAns = "C";
            } else {
                correctAns = "D";
            }
            teacherController.addQuesBank(Integer.parseInt(txtQuesNo.getText()),
                    txtQuestion.getText(),
                    txtAns1.getText(),
                    txtAns2.getText(),
                    txtAns3.getText(),
                    txtAns4.getText(),
                    correctAns
            );
            //fill to Table
            teacherController.fillToQuesBankTable();
            //save file
            XFile.writeObject(fileQuesBankPath, teacherController.getListQuesBank());
            newQuesBank();
        }
    }

    private void newQuesBank() {
        tbQuesBank.clearSelection();
        txtQuesNo.setEditable(true);
        lbErrorQuesNo.setVisible(false);
        lbErrorQuestion.setVisible(false);
        lbErrorAns1.setVisible(false);
        lbErrorAns2.setVisible(false);
        lbErrorAns3.setVisible(false);
        lbErrorAns4.setVisible(false);
        lbErrorCorrectAns.setVisible(false);
        txtQuesNo.setText("");
        txtQuestion.setText("");
        txtAns1.setText("");
        txtAns2.setText("");
        txtAns3.setText("");
        txtAns4.setText("");
        rdAns1.setSelected(true);
    }

    private void tbStudentDetailClick() {
        cbStudent.setEnabled(false);
        cbCourseStudent.setEnabled(false);
        txtScore.setEditable(true);
        rowStudentDetail = tbStudentDetail.getSelectedRow();
        showStudentDDetail(rowStudentDetail);
    }

    private void showStudentDDetail(int rowStudentDetail) {
        String username = (String) tbStudentDetail.getValueAt(rowStudentDetail, 0);
        cbStudent.setSelectedItem(username);

        String course = (String) tbStudentDetail.getValueAt(rowStudentDetail, 3);
        cbCourseStudent.setSelectedItem(course);

        Double score = (Double) tbStudentDetail.getValueAt(rowStudentDetail, 4);
        txtScore.setText(score.toString());
    }

    private void updateStudentDetail() {
        if (rowStudentDetail == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the student detail");
        } else {
            lbErrorScore.setVisible(isCharacter(txtScore.getText()) || isSpecial(txtScore.getText()));

//            for (StudentDetail studentDetail : teacherController.getListStudentDetail()) {
//                if (studentDetail.getStudent().getUsername().equals(chooseStudent) && studentDetail.getCourse().getName().equals(chooseStudentCourse)) {
//                    lbErrorChooseStudent.setVisible(true);
//                    lbErrorChooseStudentCourse.setVisible(true);
//                    break;
//                }
//            }

            if (!lbErrorScore.isVisible()) {
                //edit List
                teacherController.editStudentDetail(chooseStudent, chooseStudentCourse, Double.parseDouble(txtScore.getText()));
                //fill to table
                teacherController.fillToStudentDetailTable();
                //save file
                XFile.writeObject(fileStudentDetailPath, teacherController.getListStudentDetail());
                tbStudentDetail.addRowSelectionInterval(rowStudentDetail, rowStudentDetail);
                lbErrorChooseStudent.setVisible(false);
                lbErrorChooseStudentCourse.setVisible(false);
                lbErrorScore.setVisible(false);
            }
        }
    }

    private void saveStudentDetail() {
        lbErrorChooseStudent.setVisible(cbStudent.getSelectedIndex() == 0);
        lbErrorChooseStudentCourse.setVisible(cbCourseStudent.getSelectedIndex() == 0);
        lbErrorScore.setVisible(isCharacter(txtScore.getText()) || isSpecial(txtScore.getText()));

        for (StudentDetail studentDetail : teacherController.getListStudentDetail()) {
            if (studentDetail.getStudent().getUsername().equals(chooseStudent) && studentDetail.getCourse().getName().equals(chooseStudentCourse)) {
                lbErrorChooseStudent.setVisible(true);
                lbErrorChooseStudentCourse.setVisible(true);
                break;
            }
        }

        if (!lbErrorChooseStudent.isVisible() && !lbErrorChooseStudentCourse.isVisible() && !lbErrorScore.isVisible()) {
            teacherController.addStudentDetail(chooseStudent,
                    chooseStudentCourse,
                    (txtScore.getText().equals("")) ? 0.0 : Double.parseDouble(txtScore.getText())
            );
            //fill to Table
            teacherController.fillToStudentDetailTable();
            //save file
            XFile.writeObject(fileStudentDetailPath, teacherController.getListStudentDetail());
            newStudentDetail();
        }
    }

    private void getStudentCourseFromCombo() {
        chooseStudentCourse = cbCourseStudent.getSelectedItem().toString();
    }

    private void getStudentFromCombo() {
        chooseStudent = cbStudent.getSelectedItem().toString();
    }

    private void newStudentDetail() {
        tbStudentDetail.clearSelection();
        lbErrorChooseStudent.setVisible(false);
        lbErrorChooseStudentCourse.setVisible(false);
        lbErrorScore.setVisible(false);
        cbStudent.setEnabled(true);
        cbCourseStudent.setEnabled(true);
        cbStudent.setSelectedIndex(0);
        cbCourseStudent.setSelectedIndex(0);
        txtScore.setText("");
        txtScore.setEditable(false);
    }

    private void editStudent() {
        if (rowStudent == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the student");
        } else {
            lbErrorUsernameStudent.setVisible(txtUsernameStudent.getText().equals("") || isSpecial(txtUsernameStudent.getText()));
            lbErrorPasswordStudent.setVisible(txtPasswordStudent.getText().equals(""));
            lbErrorFullnameStudent.setVisible(txtFullnameStudent.getText().equals("") || isNumeric(txtFullnameStudent.getText()) || isSpecial(txtFullnameStudent.getText()));
            lbErrorDOBStudent.setVisible(JDatePickerStudent.getModel().getValue() == null ||
                    XUtils.convertDatetoString((Date) JDatePickerStudent.getModel().getValue()).compareTo(LocalDate.now().toString()) > 0);
            lbErrorGenderStudent.setVisible(!rdMaleStudent.isSelected() && !rdFemaleStudent.isSelected());
            lbErrorEmailStudent.setVisible(txtEmailStudent.getText().equals("") || CheckEmail(txtEmailStudent.getText()));
            lbErrorParentNameStudent.setVisible(txtParentStudent.getText().equals("") || isNumeric(txtParentStudent.getText()) || isSpecial(txtParentStudent.getText()));

            for (Student student : teacherController.getListStudent()) {
                if (!student.getUsername().equals(txtUsernameStudent.getText()) && student.getEmail().equals(txtEmailStudent.getText())) {
                    lbErrorEmailStudent.setVisible(true);
                    break;
                }
            }

            if (!lbErrorUsernameStudent.isVisible() &&
                    !lbErrorPasswordStudent.isVisible() &&
                    !lbErrorFullnameStudent.isVisible() &&
                    !lbErrorDOBStudent.isVisible() &&
                    !lbErrorGenderStudent.isVisible() &&
                    !lbErrorEmailStudent.isVisible() &&
                    !lbErrorParentNameStudent.isVisible()) {
                //edit List
                teacherController.editTeacher(txtUsernameStudent.getText(),
                                            txtPasswordStudent.getText(),
                                            txtFullnameStudent.getText(),
                                            XUtils.convertDatetoString((Date) JDatePickerStudent.getModel().getValue()),
                                            rdMaleStudent.isSelected(),
                                            txtEmailStudent.getText(),
                                            txtParentStudent.getText()
                                            );
                //fill to table
                teacherController.fillToStudentTable();
                //save file
                XFile.writeObject(fileStudentPath, teacherController.getListStudent());
                tbStudent.addRowSelectionInterval(rowStudent, rowStudent);
            }
        }
    }

    private void tbStudentClick() {
        txtUsernameStudent.setEditable(false);
        rowStudent = tbStudent.getSelectedRow();
        showTeacherDetail(rowStudent);
    }

    private void showTeacherDetail(int rowStudent) {
        String usernameTeacher = (String) tbStudent.getValueAt(rowStudent, 0);
        txtUsernameStudent.setText(usernameTeacher);

        String passwordTeacher = (String) tbStudent.getValueAt(rowStudent, 1);
        txtPasswordStudent.setText(passwordTeacher);

        String fullNameTeacher = (String) tbStudent.getValueAt(rowStudent, 2);
        txtFullnameStudent.setText(fullNameTeacher);

        String bDay = (String) tbStudent.getValueAt(rowStudent, 3);
        Calendar c = Calendar.getInstance();
        c.setTime(XUtils.convertStringtoDate(bDay));
        JDatePickerStudent.getJFormattedTextField().setValue(c);

        String gender = (String) tbStudent.getValueAt(rowStudent, 4);
        boolean check = gender.equals("male");
        rdMaleStudent.setSelected(check);
        rdFemaleStudent.setSelected(!check);

        String emailTeacher = (String) tbStudent.getValueAt(rowStudent, 5);
        txtEmailStudent.setText(emailTeacher);

        String parent = (String) tbStudent.getValueAt(rowStudent, 6);
        txtParentStudent.setText(parent);
    }

    private void saveStudent() {
        lbErrorUsernameStudent.setVisible(txtUsernameStudent.getText().equals("") || isSpecial(txtUsernameStudent.getText()));
        lbErrorPasswordStudent.setVisible(txtPasswordStudent.getText().equals(""));
        lbErrorFullnameStudent.setVisible(txtFullnameStudent.getText().equals("") || isNumeric(txtFullnameStudent.getText()) || isSpecial(txtFullnameStudent.getText()));
        lbErrorDOBStudent.setVisible(JDatePickerStudent.getModel().getValue() == null ||
                XUtils.convertDatetoString((Date) JDatePickerStudent.getModel().getValue()).compareTo(LocalDate.now().toString()) > 0);
        lbErrorGenderStudent.setVisible(!rdMaleStudent.isSelected() && !rdFemaleStudent.isSelected());
        lbErrorEmailStudent.setVisible(txtEmailStudent.getText().equals("") || CheckEmail(txtEmailStudent.getText()));
        lbErrorParentNameStudent.setVisible(txtParentStudent.getText().equals("") || isNumeric(txtParentStudent.getText()) || isSpecial(txtParentStudent.getText()));

        for (Student student : teacherController.getListStudent()) {
            if (student.getUsername().equals(txtUsernameStudent.getText())) {
                lbErrorUsernameStudent.setVisible(true);
            }
            if (student.getEmail().equals(txtEmailStudent.getText())) {
                lbErrorEmailStudent.setVisible(true);
            }
        }

        if (!lbErrorUsernameStudent.isVisible() &&
                !lbErrorPasswordStudent.isVisible() &&
                !lbErrorFullnameStudent.isVisible() &&
                !lbErrorDOBStudent.isVisible() &&
                !lbErrorGenderStudent.isVisible() &&
                !lbErrorEmailStudent.isVisible() &&
                !lbErrorParentNameStudent.isVisible()) {
            teacherController.addTeacher(txtUsernameStudent.getText(),
                                        txtPasswordStudent.getText(),
                                        txtFullnameStudent.getText(),
                                        XUtils.convertDatetoString((Date) JDatePickerStudent.getModel().getValue()),
                                        rdMaleStudent.isSelected(),
                                        txtEmailStudent.getText(),
                                        txtParentStudent.getText()
                                        );
            cbStudent.addItem(txtUsernameStudent.getText());
            //fill to Table
            teacherController.fillToStudentTable();
            //save file
            XFile.writeObject(fileStudentPath, teacherController.getListStudent());
            newStudent();
        }
    }

    private void newStudent() {
        tbStudent.clearSelection();
        txtUsernameStudent.setEditable(true);
        lbErrorUsernameStudent.setVisible(false);
        lbErrorPasswordStudent.setVisible(false);
        lbErrorFullnameStudent.setVisible(false);
        lbErrorDOBStudent.setVisible(false);
        lbErrorGenderStudent.setVisible(false);
        lbErrorEmailStudent.setVisible(false);
        lbErrorParentNameStudent.setVisible(false);
        txtUsernameStudent.setText("");
        txtPasswordStudent.setText("");
        txtFullnameStudent.setText("");
        JDatePickerStudent.getModel().setValue(null);
        rdMaleStudent.setSelected(true);
        txtEmailStudent.setText("");
        txtParentStudent.setText("");
    }

    private void createUIComponents() {
        UtilDateModel model = new UtilDateModel();
        UtilDateModel modelExam = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        JDatePickerStudent = new JDatePickerImpl(panel, new DataLabelFormat());

        JDatePanelImpl panelExam = new JDatePanelImpl(modelExam, p);
        JDatePickerExamDate = new JDatePickerImpl(panelExam, new DataLabelFormat());
    }

    private boolean CheckEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.find();
    }

    public static boolean isNumeric(String strNum) {
        char[] chars = strNum.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCharacter(String strChar) {
        char[] chars = strChar.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpecial(String strChar) {
        char[] chars = strChar.toCharArray();
        for (char c : chars) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != ' ') {
                return true;
            }
        }
        return false;
    }
}
